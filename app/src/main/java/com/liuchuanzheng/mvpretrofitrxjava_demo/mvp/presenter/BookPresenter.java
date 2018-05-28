package com.liuchuanzheng.mvpretrofitrxjava_demo.mvp.presenter;

import android.content.Context;

import com.liuchuanzheng.mvpretrofitrxjava_demo.mvp.model.Book;
import com.liuchuanzheng.mvpretrofitrxjava_demo.mvp.view.IBookView;
import com.liuchuanzheng.mvpretrofitrxjava_demo.mvp.view.IView;
import com.liuchuanzheng.mvpretrofitrxjava_demo.retrofitservice.RetrofitService;
import com.liuchuanzheng.mvpretrofitrxjava_demo.util.RetrofitHelper;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 刘传政 on 2018/5/28 0028.
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
public class BookPresenter implements IPresenter {
    private Context context;
    private IView view;
    private Book mBook;

    public BookPresenter(Context context) {

        this.context = context;
    }

    @Override
    public void addView(IView ivew) {
        view = ivew;
    }
    public void getSearchBook(String name,String tag,int start,int count){
        mBook = null;
        RetrofitService service = RetrofitHelper.getInstance(context).getService();
        Observable<Book> observable = service.getSearchBook2( name, tag, start, count);
        observable.subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
                .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
                .subscribe(new Observer<Book>() {//订阅并执行
                               @Override
                               public void onCompleted() {
                                   //所有事件都完成，可以做些操作。。。
                                   if (mBook != null){
                                       ((IBookView)view).onSuccess(mBook);
                                   }
                               }


                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace(); //请求过程中发生错误
                                   ((IBookView)view).onError("请求失败！！"+e.toString());
                               }

                               @Override
                               public void onNext(Book book) {
                                   mBook = book;
                               }
                           }
                );

    }
}
