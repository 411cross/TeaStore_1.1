package com.example.peek_mapdemotest.buy_test1;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import listview.Address;
import listview.Goods;
import object.User;
import okhttp_tools.okHttpTools;
import operation.GeneralOperation;
import operation.UserOperation;

import static operation.UserOperation.getAddress;

/**
 * Created by chf on 2017/5/28.
 */

public class pay extends AppCompatActivity {
    private Button pay_button;
    private User user = null;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private Button chooseadd;
    private String address_id = null;

    private RadioOnClick radioOnClick = new RadioOnClick(0);
    private ListView areaRadioListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);

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
        pay_button = (Button) findViewById(R.id.pay_button);
        chooseadd = (Button) findViewById(R.id.choosadd);
        tv1 = (TextView) findViewById(R.id.name);
        tv2 = (TextView) findViewById(R.id.priceofgood);
        tv3 = (TextView) findViewById(R.id.number);
        tv4 = (TextView) findViewById(R.id.totalprice);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        final String price = intent.getStringExtra("price");
        final String number = intent.getStringExtra("number");
        final String goods_id = intent.getStringExtra("goods_id");
        final double total = Double.parseDouble(intent.getStringExtra("total"));
        tv1.setText(name);
        tv2.setText(price);
        tv3.setText(number);
        tv4.setText(total + "");
        user = GeneralOperation.getUser();

        chooseadd.setOnClickListener(new RadioClickListener());
        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address_id != null) {
                    try {
                        UserOperation.CreateOrder(user, address_id, number, goods_id);
                        Toast.makeText(pay.this, "提交成功", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(pay.this, "请选择收货地址", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class RadioClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            ArrayList<Address> addressList = new ArrayList<Address>();
            User user = GeneralOperation.getUser();
            try {
                ArrayList responseList = UserOperation.getAddress();
                addressList = user.getAddressList();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String[] area = new String[addressList.size()];
            for (int i = 0; i < addressList.size(); i++) {
                area[i] = addressList.get(i).getContent();
            }
            AlertDialog ad = new AlertDialog.Builder(pay.this).setTitle("选择收货地址")
                    .setSingleChoiceItems(area, radioOnClick.getIndex(), radioOnClick).create();
            radioOnClick.setArea(area);
            chooseadd.setText((addressList.get(radioOnClick.getIndex())).getContent());
            address_id = (addressList.get(radioOnClick.getIndex())).getAddress_id() + "";
            areaRadioListView = ad.getListView();
            ad.show();
        }
    }

    class RadioOnClick implements DialogInterface.OnClickListener {
        private int index;
        private String[] area;

        public RadioOnClick(int index) {
            this.index = index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setArea(String[] a) {
            this.area = a;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            setIndex(which);
            chooseadd.setText(area[which]);
            dialog.dismiss();
        }
    }
}