package com.liuchuanzheng.mvpretrofitrxjava_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);
        textView = findViewById(R.id.textView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                doYuansheng();
                break;
        }
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
