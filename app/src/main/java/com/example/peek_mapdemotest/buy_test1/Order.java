package com.example.peek_mapdemotest.buy_test1;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import operation.GeneralOperation;

public class Order extends AppCompatActivity {
    private Spinner sp;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private int n;
    private double total;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        final Spinner sp = (Spinner) findViewById(R.id.spinner1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //设置 ToolBar 返回箭头
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button button1 = (Button) findViewById(R.id.buy_button);

        Intent intent = getIntent();
        final String goods_id = intent.getStringExtra("goods_id");
        final String price = intent.getStringExtra("price");
        final String name = intent.getStringExtra("name");
        tv1 = (TextView) findViewById(R.id.textView4);
        tv1.setText(price);
        tv2 = (TextView) findViewById(R.id.nameofgood);
        tv2.setText(name);
        tv3 = (TextView) findViewById(R.id.totalprice);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = sp.getSelectedItem().toString();
                n = Integer.valueOf(s).intValue();
                total = n * (Double.parseDouble(price));
                tv3.setText("" + total);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Order.this, pay.class);
                intent.putExtra("price", price);
                intent.putExtra("name", name);
                intent.putExtra("number", n + "");
                intent.putExtra("goods_id", goods_id);
                intent.putExtra("total", total + "");
                startActivity(intent);
            }
        });

    }

}
