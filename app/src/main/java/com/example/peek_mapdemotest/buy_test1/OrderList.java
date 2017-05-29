package com.example.peek_mapdemotest.buy_test1;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

import listview.Order_gen;
import listview.Order_genAdapter;
import object.User;
import operation.GeneralOperation;
import operation.UserOperation;


/**
 * Created by chf on 2017/5/29.
 */

public class OrderList extends AppCompatActivity {
    private ArrayList<Order_gen> orderList = new ArrayList<Order_gen>();
    private ListView orderlistview;
    User user = GeneralOperation.getUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderlist);

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

//        Toast.makeText(OrderList.this, "fuck!", Toast.LENGTH_SHORT).show();
        orderlistview=(ListView)findViewById(R.id.orderList);
        try {
            ArrayList responseList = UserOperation.getOrder();
            orderList = user.getOrderList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Order_genAdapter orderAdapter = new Order_genAdapter(OrderList.this, R.layout.orderdetail,orderList);
        ListView listView1 = (ListView) findViewById(R.id.orderList);
        listView1.setAdapter(orderAdapter);

//        String[] a =new String[orderList.size()];
//        for(int i=0;i<orderList.size();i++){
//            a[i]=orderList.get(i).getOrder_id()+"        "+orderList.get(i).getCreated_at();
//        }
//        orderlistview.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, a));
    }

}
