package com.hxh.component.basicore.rx;



import com.hxh.component.basicore.rx.normalhttpstyle.ExceptionHandle;
import com.hxh.component.basicore.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hxh on 2017/4/23.
 */
public class RxUtils {
    /**
     * 切换线程
     * @param <T>
     * @return
     */
    public static <T>Observable.Transformer<T,T> io_main()
    {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        ;
            }
        }   ;
    }

    /**
     * 切换线程，并且加入 重试机制
     * @param <T>
     * @return
     */
    public static <T>Observable.Transformer<T,T> io_main_AddretryWhen()
    {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(new RxRetryWhen(3,2000))
                        ;
            }
        }   ;
    }

    /**
     * 此类可以进行重试当做，触发条件是  连接超时时候，网络框架会抛出一个异常，此方法会拦截到异常，然后进行重试
     */
    public static class RxRetryWhen implements Func1<Observable<? extends Throwable>,Observable<?>>
    {

        private int retryTime;
        private int retrycount;
        private int currentRetryCount =0;
        public RxRetryWhen(int retrycount,int retryTime)
        {
            this.retrycount = retrycount;
            this.retryTime = retryTime;
        }

        @Override
        public Observable<?> call(Observable<? extends Throwable> observable) {
            return observable
                    .flatMap(new Func1<Throwable, Observable<?>>() {
                        @Override
                        public Observable<?> call(Throwable throwable) {
                            if(++currentRetryCount<=retrycount)
                            {
                                Log.d("正在重试");
                                return Observable.timer(retryTime, TimeUnit.MILLISECONDS);
                            }
                            return Observable.error(ExceptionHandle.handleException(throwable));
                        }
                    });
        }
    }


    public static <T>Observable.Transformer<T,T> getSubscribe()
    {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {

                return tObservable
                        .compose(RxUtils.<T>io_main())
                        ;
            }
        };
    }

}
