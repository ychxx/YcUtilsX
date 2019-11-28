package com.yc.ycutilsx.rxjava;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 操作符
 */

public class Operator {
    /**
     * 转换
     */
    private void map() {
        //对上游发送的每一个事件应用一个函数, 使得每一个事件都按照指定的函数去变化.
        //Integer转成String
        Observable.just(1).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "转换成" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
            }
        });

        //将一个发送事件的上游Observable变换为多个发送事件的Observables，然后将它们发射的事件合并后放进一个单独的Observable里.
        //一个Observable<Integer>拆分成多个Observable<String>
        Observable.just(1).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                return Observable.just(integer + "");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
            }
        });
        //跟flatMap类似，区别在于是有序的
        Observable.just(1).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                return Observable.just(integer + "");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
            }
        });
    }

    private void take() {
        //只接受前2个
        Observable.just(1, 2, 3, 4)
                .take(2)
                .subscribe(integer -> Log.e("asd", integer + ""));
    }

    private void doOnNext() {
        //接受前处理
        Observable.just(1, 2, 3, 4)
                .doOnNext(integer -> Log.e("asd", "接受前处理"))
                .subscribe(integer -> Log.e("asd", integer + ""));
    }
}
