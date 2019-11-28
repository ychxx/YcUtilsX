package com.yc.yclibx.comment;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import io.reactivex.Observable;

/**
 *
 */
public interface IRxBus {
    void post(Object sendMessage);

    void postSticky(Object sendMessage);

    <T> Observable<T> register(Class<T> messageClass, LifecycleOwner owner, Lifecycle.Event event);

    <T> Observable<T> registerSticky(Class<T> messageClass, LifecycleOwner owner, Lifecycle.Event event);


//    void unRegister(Class<T> sendMessage);
}
