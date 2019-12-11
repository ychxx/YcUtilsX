package com.yc.yclibx.comment;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle3.LifecycleProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 消息传递（订阅和注册）
 */
public class YcRxBus implements IRxBus {
    private volatile static YcRxBus mInstance;
    private final Subject<Object> mBus;//存储发送的数据
    private final Map<Class<?>, Object> mStickyEventMap;//存储延迟发送的数据（未注册就先发送的数据）

    private YcRxBus() {
        mBus = PublishSubject.create().toSerialized();
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    public static YcRxBus newInstance() {
        if (mInstance == null) {
            synchronized (YcRxBus.class) {
                if (mInstance == null) {
                    mInstance = new YcRxBus();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void post(Object sendMessage) {
        mBus.onNext(sendMessage);
    }

    @Override
    public void postSticky(Object sendMessage) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(sendMessage.getClass(), sendMessage);
        }
        post(sendMessage);
    }

    public <T> Observable<T> registerUnsafe(Class<T> messageClass) {
        return mBus.ofType(messageClass)
                .doOnDispose(() -> Log.i("YcRxBus", "RxBus取消订阅了:" + messageClass.getName()))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <T> Observable<T> register(Class<T> messageClass, @NonNull LifecycleOwner owner) {
        return register(messageClass, owner, Lifecycle.Event.ON_DESTROY);
    }

    @Override
    public <T> Observable<T> register(Class<T> messageClass, LifecycleOwner owner, Lifecycle.Event event) {
        LifecycleProvider<Lifecycle.Event> provider = AndroidLifecycle.createLifecycleProvider(owner);
        return mBus.ofType(messageClass)
                .doOnDispose(() -> Log.i("YcRxBus", "RxBus取消订阅了:" + messageClass.getName()))
                .compose(provider.bindUntilEvent(event))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public <T> Observable<T> registerStickyUnsafe(Class<T> messageClass) {
        Observable<T> observable = mBus.ofType(messageClass)
                .doOnDispose(() -> Log.v("YcRxBus", "RxBus取消订阅了:" + messageClass.getName()))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        final Object object = mStickyEventMap.get(messageClass);
        if (object != null) {
            return observable.mergeWith(Observable.create(new ObservableOnSubscribe<T>() {
                @Override
                public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                    subscriber.onNext(messageClass.cast(object));
                }
            }));
        } else {
            return observable;
        }
    }

    public <T> Observable<T> registerSticky(Class<T> messageClass, LifecycleOwner owner) {
        return registerSticky(messageClass, owner, Lifecycle.Event.ON_DESTROY);
    }

    @Override
    public <T> Observable<T> registerSticky(Class<T> messageClass, LifecycleOwner owner, Lifecycle.Event event) {
        LifecycleProvider<Lifecycle.Event> provider = AndroidLifecycle.createLifecycleProvider(owner);
        Observable<T> observable = mBus.ofType(messageClass)
                .doOnDispose(() -> Log.v("YcRxBus", "RxBus取消订阅了:" + messageClass.getName()))
                .compose(provider.bindUntilEvent(event))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        final Object object = mStickyEventMap.get(messageClass);

        if (object != null) {
            return observable.mergeWith(Observable.create(new ObservableOnSubscribe<T>() {
                @Override
                public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                    subscriber.onNext(messageClass.cast(object));
                }
            }));
        } else {
            return observable;
        }
    }


    /**
     * 移除指定eventType的Sticky事件
     */
    public <T> T unRegisterSticky(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void unRegisterAllSticky() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }


}
