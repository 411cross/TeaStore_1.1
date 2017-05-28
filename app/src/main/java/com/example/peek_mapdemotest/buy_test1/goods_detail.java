package com.example.peek_mapdemotest.buy_test1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import operation.Base64Tool;
import operation.GetImage;

/**
 * Created by Administrator on 2017/5/24.
 */
public class goods_detail extends AppCompatActivity {
    private ImageView imv1;
    private TextView nameTV;
    private TextView descriptionTV;
    private TextView priceTV;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String detail_name = intent.getStringExtra("detail_name");
        String detail_price = intent.getStringExtra("detail_price");
        String detail_description = intent.getStringExtra("detail_description");
        final String detail_thumb = "http://" + intent.getStringExtra("detail_thumb");
//        int data_ImageID = intent.getIntExtra("goods_imageID", -1);
        int data_ImageID = R.mipmap.test;

        new GetImage((ImageView) findViewById(R.id.detail_thumb), detail_thumb).execute();
        nameTV = (TextView) findViewById(R.id.detail_name);
        priceTV = (TextView) findViewById(R.id.detail_price);
        descriptionTV = (TextView) findViewById(R.id.detail_description);

        button1 = (Button) findViewById(R.id.button1);

//        new Thread(){
//
//            public void run() {
//                Bitmap bitmap = null;
//                try {
//                    bitmap = Base64Tool.getBitmap(detail_thumb);
//                    Log.i("哈哈哈哈", bitmap.toString());
//                    Log.i("呵呵呵", );
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }.start();

        nameTV.setText(detail_name);
        priceTV.setText(detail_price);
        descriptionTV.setText(detail_description);

    }
}
