package com.example.peek_mapdemotest.buy_test1;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

import listview.Store;
import listview.StoreAdapter;
import object.User;
import operation.GeneralOperation;
import operation.UserOperation;

/**
 * Created by derrick on 2017/5/27.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StoresList extends ActionBarActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, AdapterView.OnItemLongClickListener {

    private ArrayList<Store> storesList = new ArrayList<Store>();
    private TextView welTV;
    private TextView outTV;
    private User user = null;
    public ArrayList list = new ArrayList();

    public StoresList() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_list);

        welTV = (TextView) findViewById(R.id.welcomeTV);
        outTV = (TextView) findViewById(R.id.outTV);
        user = GeneralOperation.getUser();
        if(user.getName().equals("null")){
            welTV.setText(user.getUsername() + " 欢迎你！");
        }
        else{
            welTV.setText(user.getName()+" 欢迎你！");
        }
        welTV.setOnClickListener(this);
        outTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    list = GeneralOperation.logout(user);
                    if (Integer.parseInt((String) list.get(0)) == 204) {
                        Toast.makeText(getApplicationContext(), "退出成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity_buy.class);
                        startActivity(intent);
//            String Authorization = data.getString("Authorization");
                    } else {
                        JSONObject object = new JSONObject((String) list.get(1));
                        String message = object.getString("message");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            ArrayList responseList = UserOperation.getStoreListRes();
            if (Integer.parseInt((String) responseList.get(0)) != 200) {
                JSONObject jsonObject = new JSONObject((String) responseList.get(1));
                String message = jsonObject.getString("message");
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            } else {
                storesList = UserOperation.getStoreList(responseList);
                if (storesList.size() == 0) {
                    Toast.makeText(this, "暂无商铺", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StoreAdapter storesAdapter = new StoreAdapter(StoresList.this, R.layout.stores_item, storesList);
        ListView storeListView = (ListView) findViewById(R.id.store_list);
        storeListView.setAdapter(storesAdapter);
        storeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Store stores = storesList.get(i);
//                Toast.makeText(buy_Activity.this, goods.getGoods_name(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("store_id", stores.getStore_id());
                Intent intent = new Intent(StoresList.this, buy_Activity.class);
                intent.putExtras(bundle);
//                intent.putExtra("store_description", stores.getDescription());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("TAG1", "onRestart: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("TAG2", "onStart: ");
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View view) {

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
                        Intent intent = new Intent(StoresList.this, AddressList.class);
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
                Intent intent = new Intent(getApplicationContext(),AccountMessage.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return false;
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }


}
