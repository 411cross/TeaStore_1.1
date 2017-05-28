package com.example.peek_mapdemotest.buy_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import operation.GeneralOperation;


public class MainActivity_buy extends AppCompatActivity {


    private EditText ET1;
    private EditText ET2;
    private Button button1;
    private TextView tvTest;
    private Button button2;


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_buy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button1 = (Button) findViewById(R.id.button1);
        ET1 = (EditText) findViewById(R.id.ET1);
        ET2 = (EditText) findViewById(R.id.ET2);
//        tvTest = (TextView) findViewById(R.id.Test_TV);
        button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ET1.getText().toString();
                String password = ET2.getText().toString();
                try {
                    ArrayList list = GeneralOperation.login(username, password);

                    Log.i("1230", (String) list.get(1));
                    if (Integer.parseInt((String) list.get(0)) != 201) {
                        JSONObject object = new JSONObject((String) list.get(1));
                        Log.i("jonlist1", (String) list.get(1));
                        String message = object.getString("message");
                        Log.i("jonlist2", message);
                        Toast.makeText(MainActivity_buy.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity_buy.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity_buy.this, StoresList.class);
                        startActivity(intent);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_buy.this, register.class);
                startActivity(intent);

            }
        });


    }

    private void showResponse(final String responseData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvTest.setText(responseData);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_buy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
