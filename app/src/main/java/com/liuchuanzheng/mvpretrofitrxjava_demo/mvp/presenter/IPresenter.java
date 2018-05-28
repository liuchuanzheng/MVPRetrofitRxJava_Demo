package com.liuchuanzheng.mvpretrofitrxjava_demo.mvp.presenter;

import com.liuchuanzheng.mvpretrofitrxjava_demo.mvp.view.IView;

/**
 * Created by 刘传政 on 2018/5/28 0028.
 * QQ:1052374416
 * 电话:18501231486
 * 作用:控制类。需要有view才能真正工作。工作过程中需要model作为基本单元。
 * 注意事项:
 */
public interface IPresenter {
    /**
     * 让presenter关联view
     * @param ivew
     */
    void addView(IView ivew);
}
