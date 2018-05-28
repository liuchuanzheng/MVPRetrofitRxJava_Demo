package com.liuchuanzheng.mvpretrofitrxjava_demo.mvp.view;

import com.liuchuanzheng.mvpretrofitrxjava_demo.mvp.model.Book;

/**
 * Created by 刘传政 on 2018/5/28 0028.
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
public interface IBookView extends IView {
    void onSuccess(Book mBook);
    void onError(String result);
}
