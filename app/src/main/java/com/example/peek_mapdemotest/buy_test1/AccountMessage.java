package com.example.peek_mapdemotest.buy_test1;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import operation.GeneralOperation;
import operation.UserOperation;

/**
 * Created by Administrator on 2017/5/27.
 */
public class AccountMessage extends ActionBarActivity {
    private ImageView avatarImage;
    private TextView accountName;
    private TextView accountEmail;
    private Button bianji_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_message);

        try {
            String avatarString = (String) UserOperation.userInfo(GeneralOperation.getUser()).get(2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        avatarImage = (ImageView) findViewById(R.id.avatarImage);
        accountName= (TextView) findViewById(R.id.account_name);
        accountName.setText(GeneralOperation.getUser().getUsername().toString());
        accountEmail= (TextView) findViewById(R.id.account_email);
        accountEmail.setText(GeneralOperation.getUser().getEmail().toString());
        bianji_button = (Button) findViewById(R.id.bianji_button);
    }
}
