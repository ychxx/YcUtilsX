package com.yc.ycutilsx.rxjava;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 基础
 */

public class Base {
    /**
     * Observable创建方式1（调用create）
     */
    private void create1() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onComplete();
                // 1.最为关键的是onComplete和onError必须唯一并且互斥, 即不能发多个onComplete, 也不能发多个onError,
                // 也不能先发一个onComplete, 然后再发一个onError, 反之亦然(需要自行在代码中进行控制)
                // 2.多个onComplete是可以正常运行的, 依然是收到第一个onComplete就不再接收了, 但若是发送多个onError,
                // 则收到第二个onError事件会导致程序会崩溃.
//                e.onError(new NullPointerException());
            }
            //subscribe()有多个重载的方法:
            //public final Disposable subscribe() {}
            //public final Disposable subscribe(Consumer<? super T> onNext) {}
            //public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {}
            //public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete) {}
            //public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete, Consumer<? super Disposable> onSubscribe) {}
            //public final void subscribe(Observer<? super T> observer) {}
            //不带任何参数的subscribe() 表示下游不关心任何事件,你上游尽管发你的数据去吧, 老子可不管你发什么.
            //带有一个Consumer参数的方法表示下游只关心onNext事件, 其他的事件我假装没看见, 因此我们如果只需要onNext事件可以这么写
            //带有Observer就是完整的
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {}
            @Override
            public void onNext(Integer integer) {}
            @Override
            public void onError(Throwable e) {}
            @Override
            public void onComplete() {}
        });
    }

    /**
     * Observable创建方式2（原样发射）
     */
    private void create2() {
        // just中传递的参数将直接在Observer的onNext()方法中接收到
        Observable<String> observable11 = Observable.just("Hello");
        // 发射多个 参数最多10个
        Observable<String> observable12 = Observable.just("Hello1", "Hello2", "Hello3");

        // 发射数组(和just差不多，没有参数数量限制而已)
        Observable<String> observable21 = Observable.fromArray("Hello1", "Hello2", "Hello3");
        Observable<String> observable22 = Observable.fromArray(new String[]{});

        // 发射集合
        Observable<String> observable3 = Observable.fromIterable(new ArrayList<String>());

        //fromCallable和fromFuture创建暂时没看明白，留待以后补充
    }
    // mTestNum 要定义为成员变量
    private Integer mTestNum = 100;
    /**
     * Observable创建方式3（调用defer延迟创建(观察者订阅_被观察者_时才创建,且每次创建都是_新的_)）
     */
    private void create3() {
        //defer() 只有观察者订阅的时候才会创建_新的_被观察者，所以每订阅一次就会打印一次，并且都是打印 i 最新的值。
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(mTestNum);
            }
        });
        mTestNum = 200;
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {}
            @Override
            public void onNext(Integer integer) {
                Log.d("TAG", "================onNext " + integer);}
            @Override
            public void onError(Throwable e) {}
            @Override
            public void onComplete() {}
        };
        observable.subscribe(observer);
        mTestNum = 300;
        observable.subscribe(observer);
        /* 打印结果：
        ================onNext 200
        ================onNext 300
        */
    }

    /**
     * Observable创建方式4（调用time 延迟发射）
     */
    private void create4() {
        //延迟2秒后，发射一个0L
        Observable<Long> observable1 = Observable.timer(2, TimeUnit.SECONDS);
        //每隔2000毫秒发射一个aLong，从0开始到无穷大。
        Observable<Long> observable2 = Observable.interval(2000, TimeUnit.MILLISECONDS);
        //参数说明 （aLong起始数值，发射次数，延迟initialDelay开始第一次发射，两次发射间隔时间，时间单位）
        //start：起始数值
        //count：发射数量
        //initialDelay：延迟执行时间
        //period：发射周期时间
        //unit：时间单位
        Observable<Long> observable3 = Observable.intervalRange(2, 3, 2, 3, TimeUnit.SECONDS);
    }
    /**
     * Observable创建方式5（调用Range 发射特定整数序列）
     */
    private void create5() {
        //从2开始发射3个数字（即发射2,3,4）
        Observable<Integer> observable1 = Observable.range(2, 3);
    }
    /**
     * 线程切换
     */
    private void schedulersThread() {
        // Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
        // Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
        // Schedulers.newThread() 代表一个常规的新线程
        // AndroidSchedulers.mainThread() 代表Android的主线程

        //上游多次指定线程时，只有第一次指定生效
        //下游每次指定线程时，都会生效
        Observable.just(1)
                .subscribeOn(Schedulers.io())//上游
                .observeOn(AndroidSchedulers.mainThread());//下游
    }
}
