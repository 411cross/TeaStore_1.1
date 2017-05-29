package com.example.peek_mapdemotest.buy_test1;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import listview.Goods;
import listview.GoodsAdapter;
import object.User;
import operation.GeneralOperation;
import operation.UserOperation;

/**
 * Created by Administrator on 2017/5/24.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class buy_Activity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private ArrayList<Goods> goodsList = new ArrayList<Goods>();
    private TextView welTV;
    private TextView outTV;
    private User user = null;
    public ArrayList list = new ArrayList();

    public buy_Activity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_content);

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

        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        int sc_id = bundle.getInt("sc_id");
        int flag = bundle.getInt("flag");

        try {
            ArrayList responseList = new ArrayList();
            if (flag == 1) {
                responseList = UserOperation.getGoodsListRes(sc_id);
            } else if (flag == 2) {
                responseList = UserOperation.getGoodsListByClassRes(sc_id);
            }

            if (Integer.parseInt((String) responseList.get(0)) != 200) {
                JSONObject jsonObject = new JSONObject((String) responseList.get(1));
                String message = jsonObject.getString("message");
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            } else {
                goodsList = UserOperation.getGoodList(responseList);
                if (goodsList.size() == 0) {
                    Toast.makeText(this, "暂无商品", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GoodsAdapter goodsAdapter = new GoodsAdapter(buy_Activity.this, R.layout.goods_item, goodsList);
        ListView listView1 = (ListView) findViewById(R.id.listView1);
        listView1.setAdapter(goodsAdapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Goods goods = goodsList.get(i);
//                Toast.makeText(buy_Activity.this, goods.getGoods_name(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(buy_Activity.this, goods_detail.class);
                intent.putExtra("goods_id", goods.getGoods_id() + "");
                intent.putExtra("detail_name", goods.getGoods_name());
                intent.putExtra("detail_thumb", goods.getThumb());
                intent.putExtra("detail_price", goods.getPrice());
                intent.putExtra("detail_description", goods.getDescription());
                startActivity(intent);
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View view) {
//
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.user_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.createAddress:


                LayoutInflater layoutInflater = LayoutInflater.from(this);
                final View myLoginView = layoutInflater.inflate(R.layout.user_address, null);
                Dialog alertDialog = new AlertDialog.Builder(this).
                        setTitle("创建收货地址").setView(myLoginView).
                        setIcon(android.R.drawable.ic_dialog_info).
                        setPositiveButton("提交", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                EditText ED1 = (EditText) myLoginView.findViewById(R.id.Consignee_name);
                                EditText ED2 = (EditText) myLoginView.findViewById(R.id.Consignee_address);
                                EditText ED3 = (EditText) myLoginView.findViewById(R.id.Consignee_phonenumber);
                                Log.i("111", ED1.getText().toString());
                                Log.i("122", ED2.getText().toString());
                                try {
                                    ArrayList list1;
                                    list1 = UserOperation.CreateAddress(GeneralOperation.getUser(), ED1.getText().toString(), ED3.getText().toString(), ED2.getText().toString());
                                    if (Integer.parseInt((String) list1.get(0)) != 201) {
                                        JSONObject object = new JSONObject((String) list1.get(1));
                                        String message = object.getString("message");
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "创建成功！", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).
                        setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        }).
                        create();
                alertDialog.show();
//                final TextView  TV1 = new TextView(this);
//                TV1.setText("收货人：");
//                final EditText inputServer = new EditText(this);
//                final TextView  TV2 = new TextView(this);
//                TV1.setText("收货地址：");
//                final EditText inputServer1 = new EditText(this);
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("创建收货地址").setIcon(android.R.drawable.ic_dialog_info).setView(TV1).setView(inputServer).setView(TV2).setView(inputServer1)
//                        .setNegativeButton("Cancel", null);
//                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int which) {
//                        inputServer.getText().toString();
//                    }
//                });
//                builder.show();
                Toast.makeText(this, "创建收货地址", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkAddress:
                try {
                    ArrayList responseList = UserOperation.getAddress();
                    if (Integer.parseInt((String) responseList.get(0)) != 200) {
                        JSONObject jsonObject = new JSONObject((String) responseList.get(1));
                        String message = jsonObject.getString("message");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(buy_Activity.this, AddressList.class);
                        if (user.getAddressList().size() == 0) {
                            Toast.makeText(this, "无收货地址", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(intent);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.CheckCountantMessage:
                Intent intent = new Intent(getApplicationContext(), AccountMessage.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return false;
    }
}
