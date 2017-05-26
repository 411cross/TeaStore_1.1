package com.example.peek_mapdemotest.buy_test1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class AddressList extends ActionBarActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
