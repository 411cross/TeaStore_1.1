package com.example.peek_mapdemotest.buy_test1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import listview.Address;
import listview.AddressAdapter;
import listview.Goods;
import operation.UserOperation;


public class AddressList extends ActionBarActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private List<Address> addressList = new ArrayList<Address>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

        try {
            addressList = UserOperation.getAddress(addressList);
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
                Toast.makeText(AddressList.this, address.getAddress_id(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddressList.this, goods_detail.class);
                intent.putExtra("address_id", address.getAddress_id());
                intent.putExtra("address_name", address.getName());
                intent.putExtra("address_phone", address.getPhone());
                intent.putExtra("address_content", address.getContent());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
