package com.liuchuanzheng.mvpretrofitrxjava_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liuchuanzheng.mvpretrofitrxjava_demo.mvp.model.Book;
import com.liuchuanzheng.mvpretrofitrxjava_demo.mvp.presenter.BookPresenter;
import com.liuchuanzheng.mvpretrofitrxjava_demo.mvp.view.IBookView;
import com.liuchuanzheng.mvpretrofitrxjava_demo.retrofitservice.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);
        textView = findViewById(R.id.textView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                doYuansheng();
                break;
            case R.id.button2:
                doWithRxjava();
                break;
            case R.id.button3:
                doWithMvp();
                break;
        }
    }

    private void doWithMvp() {
        IBookView bookView = new IBookView() {
            @Override
            public void onSuccess(Book mBook) {
                textView.setText("");
                textView.setText(mBook.toString());
            }

            @Override
            public void onError(String result) {

            }
        };
        BookPresenter bookPresenter = new BookPresenter(MainActivity.this);
        bookPresenter.addView(bookView);
        bookPresenter.getSearchBook("金瓶梅", null, 0, 1);
    }

    private void doWithRxjava() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                //添加实体类转换工具
                .addConverterFactory(GsonConverterFactory.create())
                //添加rxjava支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        //被观察者
        Observable<Book> observable = service.getSearchBook2("金瓶梅", null, 0, 1);
        observable.subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
                .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
                .subscribe(new Observer<Book>() {//订阅并执行
                               @Override
                               public void onCompleted() {
                                    //所有事件都完成，可以做些操作。。。
                               }

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace(); //请求过程中发生错误
                               }

                               @Override
                               public void onNext(Book book) {
                                   textView.setText("");
                                   textView.setText(book.toString());
                               }
                           }
                );

    }

    private void doYuansheng() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        Call<Book> call =  service.getSearchBook("金瓶梅", null, 0, 1);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                textView.setText("");
                textView.setText(response.body()+"");
            }
            @Override
            public void onFailure(Call<Book> call, Throwable t) {
            }
        });
    }
}
