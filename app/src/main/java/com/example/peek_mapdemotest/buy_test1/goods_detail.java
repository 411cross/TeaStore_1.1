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
    private TextView TV1;
    private TextView TV2;
    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_detail);
        Intent intent = getIntent();
        String data_name=intent.getStringExtra("goods_name");
        int data_ImageID=intent.getIntExtra("goods_imageID",-1);


        imv1 = (ImageView) findViewById(R.id.img1);
        TV1 = (TextView) findViewById(R.id.tv1);
        TV2 = (TextView) findViewById(R.id.tv2);
        button1 = (Button) findViewById(R.id.button1);

        imv1.setImageResource(data_ImageID);
        TV1.setText(data_name);

    }
}
