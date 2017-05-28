package com.example.peek_mapdemotest.buy_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import operation.GeneralOperation;
import operation.GetImage;
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

        avatarImage = (ImageView) findViewById(R.id.avatarImage);
        accountName = (TextView) findViewById(R.id.account_name);

        try {
            String avatarString = "http://" + (String) UserOperation.userInfo(GeneralOperation.getUser()).get(2);
            Log.i("TEST GET2", (String) UserOperation.userInfo(GeneralOperation.getUser()).get(2));
            new GetImage(avatarImage, avatarString).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(GeneralOperation.getUser().getName().equals("null")){
            accountName.setText(GeneralOperation.getUser().getUsername());
        }
        else{
            accountName.setText(GeneralOperation.getUser().getName());
        }

        accountEmail = (TextView) findViewById(R.id.account_email);
        accountEmail.setText(GeneralOperation.getUser().getEmail().toString());
        bianji_button = (Button) findViewById(R.id.bianji_button);
        bianji_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountMessage.this,ModifyMessage.class);
                startActivity(intent);
            }
        });


    }
}