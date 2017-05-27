package com.example.peek_mapdemotest.buy_test1;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
public class AddressList extends ActionBarActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, AdapterView.OnItemLongClickListener {

    private ArrayList<Address> addressList = new ArrayList<Address>();
    User user = GeneralOperation.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

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
        Dialog alertDialog1 = new AlertDialog.Builder(this).
                setView(myLongClickView).create();
        alertDialog1.show();

        Button button1 = (Button) myLongClickView.findViewById(R.id.modifyButton);
        Button button2 = (Button) myLongClickView.findViewById(R.id.deleteButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

//                                Log.i("modify111", ED1.getText().toString());
//                                Log.i("modify122", ED2.getText().toString());
                                try {
                                    ArrayList modifyResponse=UserOperation.ModifyAddress(addID, ED1.getText().toString(), ED2.getText().toString(), ED3.getText().toString());
//                                    if (Integer.parseInt((String) modifyResponse.get(0)) != 201) {
//                                        JSONObject object = new JSONObject((String) modifyResponse.get(1));
//                                        String message = object.getString("message");
//                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(getApplicationContext(), "修改成功！", Toast.LENGTH_SHORT).show();
//                                    GeneralOperation.getUser().setAddressList(UserOperation.getAddress());
//                                    }
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
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        });
        return false;
    }



}
