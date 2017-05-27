package com.example.peek_mapdemotest.buy_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/5/24.
 */
public class goods_detail extends ActionBarActivity {
    private ImageView imv1;
    private TextView nameTV;
    private TextView descriptionTV;
    private TextView priceTV;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_detail);
        Intent intent = getIntent();
        String detail_name = intent.getStringExtra("detail_name");
        String detail_price = intent.getStringExtra("detail_price");
        String detail_description = intent.getStringExtra("detail_description");
//        int data_ImageID = intent.getIntExtra("goods_imageID", -1);
        int data_ImageID = R.mipmap.test;

        imv1 = (ImageView) findViewById(R.id.img1);
        nameTV = (TextView) findViewById(R.id.detail_name);
        priceTV = (TextView) findViewById(R.id.detail_price);
        descriptionTV = (TextView) findViewById(R.id.detail_description);

        button1 = (Button) findViewById(R.id.button1);

        imv1.setImageResource(data_ImageID);
        nameTV.setText(detail_name);
        priceTV.setText(detail_price);
        descriptionTV.setText(detail_description);

    }
}
