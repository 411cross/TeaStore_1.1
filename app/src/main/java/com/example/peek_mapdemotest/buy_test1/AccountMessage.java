package com.example.peek_mapdemotest.buy_test1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import operation.GeneralOperation;
import operation.GetImage;
import operation.UserOperation;

/**
 * Created by Administrator on 2017/5/27.
 */
public class AccountMessage extends AppCompatActivity {
    private ImageView avatarImage;
    private TextView accountName;
    private TextView accountEmail;
    private Button bianji_button;
    private Button modify_password_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //设置 ToolBar 返回箭头
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountMessage.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        avatarImage = (ImageView) findViewById(R.id.avatarImage);
        accountName = (TextView) findViewById(R.id.account_name);

        try {
            String avatarString = "http://" + (String) UserOperation.userInfo(GeneralOperation.getUser()).get(2);
            Log.i("TEST GET2", (String) UserOperation.userInfo(GeneralOperation.getUser()).get(2));
            new GetImage(avatarImage, avatarString).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (GeneralOperation.getUser().getName().equals("null")) {
            accountName.setText(GeneralOperation.getUser().getUsername());
        } else {
            accountName.setText(GeneralOperation.getUser().getName());
        }

        accountEmail = (TextView) findViewById(R.id.account_email);
        accountEmail.setText(GeneralOperation.getUser().getEmail().toString());
        bianji_button = (Button) findViewById(R.id.bianji_button);
        bianji_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountMessage.this, ModifyMessage.class);
                startActivity(intent);
            }
        });

        modify_password_button = (Button) findViewById(R.id.modifyPassword_button);
        modify_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater layoutInflater = LayoutInflater.from(AccountMessage.this);
                final View myModifyPasswordView = layoutInflater.inflate(R.layout.modify_password, null);
                final EditText modify_password_ET = (EditText) myModifyPasswordView.findViewById(R.id.modify_password_ET);
                Dialog alertDialog = new AlertDialog.Builder(AccountMessage.this).
                        setTitle("修改密码").setView(myModifyPasswordView).
                        setIcon(android.R.drawable.ic_dialog_info).
                        setPositiveButton("提交", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub


                                try {
                                    ArrayList setPwdResponse = UserOperation.setPwd(modify_password_ET.getText().toString());
                                    if (Integer.parseInt((String) setPwdResponse.get(0)) != 201) {
                                        JSONObject object = new JSONObject((String) setPwdResponse.get(1));
                                        String message = object.getString("message");
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "修改成功，请重新登录！", Toast.LENGTH_LONG).show();
                                        final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
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


    }
}