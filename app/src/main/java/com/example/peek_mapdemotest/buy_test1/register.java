package com.example.peek_mapdemotest.buy_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import operation.GeneralOperation;

/**
 * Created by Administrator on 2017/5/25.
 */
public class register extends ActionBarActivity {


    private EditText usernameET;
    private EditText passwordET;
    private EditText emailET;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        usernameET = (EditText) findViewById(R.id.register_et_username);
        passwordET = (EditText) findViewById(R.id.register_et_password);
        emailET = (EditText) findViewById(R.id.register_et_email);
        buttonRegister = (Button) findViewById(R.id.register_button);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                String email = emailET.getText().toString();
                try {

                    if (Integer.parseInt((String) GeneralOperation.register(username, password, email).get(0)) != 201) {
                        JSONObject object = new JSONObject((String) GeneralOperation.register(username, password, email).get(1));
                        String message = object.getString("message");
                        Toast.makeText(register.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(register.this, "注册成功!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(register.this, MainActivity_buy.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
