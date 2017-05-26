package com.example.peek_mapdemotest.buy_test1;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

                Toast.makeText(AddressList.this, String.valueOf(address.getAddress_id()), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(AddressList.this, goods_detail.class);
//                intent.putExtra("address_id", address.getAddress_id());
//                intent.putExtra("address_name", address.getName());
//                intent.putExtra("address_phone", address.getPhone());
//                intent.putExtra("address_content", address.getContent());
//                startActivity(intent);

            }
        });

        addressListView.setOnItemLongClickListener(this);

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
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        Address address =addressList.get(i);
        final int addID = address.getAddress_id();
        final View myLongClickView = layoutInflater.inflate(R.layout.useraddress_longclick, null);
        Dialog alertDialog = new AlertDialog.Builder(this).
                setView(myLongClickView).create();
        alertDialog.show();
        Button button1 = (Button) myLongClickView.findViewById(R.id.modifyButton);
        Button button2 = (Button) myLongClickView.findViewById(R.id.deleteButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //修改对话框
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
                        Toast.makeText(getApplicationContext(),"删除成功！",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        return false;
    }
}
