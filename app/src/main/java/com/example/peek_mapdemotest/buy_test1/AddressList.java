package com.example.peek_mapdemotest.buy_test1;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import listview.Address;
import listview.AddressAdapter;
import object.User;
import operation.GeneralOperation;
import operation.UserOperation;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AddressList extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, AdapterView.OnItemLongClickListener {

    private ArrayList<Address> addressList = new ArrayList<Address>();
    User user = GeneralOperation.getUser();

    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addButton = (FloatingActionButton) findViewById(R.id.add_button);

        try {
            ArrayList responseList = UserOperation.getAddress();
            addressList = user.getAddressList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AddressAdapter addressAdapter = new AddressAdapter(AddressList.this, R.layout.address_item, addressList);
        ListView addressListView = (ListView) findViewById(R.id.addressList);
        addressListView.setAdapter(addressAdapter);
        addressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Address address = addressList.get(i);
                //String.valueOf(address.getAddress_id())
                Toast.makeText(AddressList.this, "已选择地址:"+address.getContent(), Toast.LENGTH_SHORT).show();
                GeneralOperation.user.setSelectedAddress(address);


            }
        });

        addressListView.setOnItemLongClickListener(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(AddressList.this);
                final View myLoginView = layoutInflater.inflate(R.layout.user_address, null);
                Dialog alertDialog = new AlertDialog.Builder(AddressList.this).
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

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }



    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        final Address address =addressList.get(i);
        final int addID = address.getAddress_id();
        final View myLongClickView = layoutInflater.inflate(R.layout.useraddress_longclick, null);
        final CharSequence[] items = {"修改地址", "删除地址"};
        Dialog alertDialog1 = new AlertDialog.Builder(this).setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i== 0) {
                    final View myModifyClickView = layoutInflater.inflate(R.layout.modify_address, null);
                    //修改对话框
                    final EditText ED1 = (EditText) myModifyClickView.findViewById(R.id.modify_name_ET);
                    ED1.setText(address.getName());
                    final  EditText ED2 = (EditText) myModifyClickView.findViewById(R.id.modify_address_ET);
                    ED2.setText(address.getContent());
                    final EditText ED3 = (EditText) myModifyClickView.findViewById(R.id.modify_phon_ET);
                    ED3.setText(address.getPhone());
                    Dialog alertDialog = new AlertDialog.Builder(AddressList.this).
                            setTitle("修改收货地址").setView(myModifyClickView).
                            setIcon(android.R.drawable.ic_dialog_info).
                            setPositiveButton("提交", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub

                                    try {
                                        ArrayList modifyResponse=UserOperation.ModifyAddress(addID, ED1.getText().toString(), ED2.getText().toString(), ED3.getText().toString());
                                        if(Integer.parseInt((String)modifyResponse.get(0))!=201){
                                            JSONObject object = new JSONObject((String) modifyResponse.get(1));
                                            String message = object.getString("message");
                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"修改成功！", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }

                                        finish();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    }).
                            create();
                    alertDialog.show();
                } else {
                    try {
                        ArrayList list = UserOperation.DeleteAddress(addID);
                        if(Integer.parseInt((String)list.get(0))!=204){
                            JSONObject object = new JSONObject((String)list.get(1));
                            String message = object.getString("message");
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                            GeneralOperation.getUser().setAddressList(UserOperation.getAddress());
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        })
                .create();
        alertDialog1.show();
        return false;
    }



}
