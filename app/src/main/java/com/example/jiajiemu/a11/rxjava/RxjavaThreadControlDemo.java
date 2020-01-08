package com.example.jiajiemu.a11.rxjava;

import android.os.SystemClock;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jiajiemu on 2017/11/7.
 */

public class RxjavaThreadControlDemo {

            public void doThread() {


                Observable.just(100, 200, 300, 400)
                        .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                        .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer number) {
                                Log.d("RxjavaThreadControlDemo", "number:" + number);
                            }
                        });


                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("start");

                        SystemClock.sleep(2000);
                        subscriber.onNext("sleep 2000 start");

                        SystemClock.sleep(3000);
                        subscriber.onNext("sleep 3000 start");

                        SystemClock.sleep(5000);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                        .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                Log.v("xhw","onCompleted");
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                Log.v("xhw","onError");
                            }

                            @Override
                            public void onNext(String nextContent) {
                                Log.v("xhw","onNext"+nextContent);
                            }
                        });
            }
}
