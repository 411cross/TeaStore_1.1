package com.example.peek_mapdemotest.buy_test1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import listview.Goods;
import listview.GoodsAdapter;
import object.User;
import operation.GeneralOperation;

/**
 * Created by Administrator on 2017/5/24.
 */
public class buy_Activity extends ActionBarActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private List<Goods> goodsList = new ArrayList<Goods>();
    private TextView welTV;
    private TextView outTV;
    private User user=null;
    public ArrayList list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_content);
        welTV= (TextView) findViewById(R.id.welcomeTV);
        outTV= (TextView) findViewById(R.id.outTV);
        user =GeneralOperation.getUser();
        welTV.setText(user.getUsername() +"欢迎你！");
        welTV.setOnClickListener(this);
        outTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    list = GeneralOperation.logout(user);
//                    if(list.get(0)==){
//
//                    }
//                    else{
//
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        initGoods();

        GoodsAdapter goodsAdapter = new GoodsAdapter(buy_Activity.this, R.layout.goods_item, goodsList);
        ListView listView1 = (ListView) findViewById(R.id.listView1);
        listView1.setAdapter(goodsAdapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Goods goods = goodsList.get(i);
                Toast.makeText(buy_Activity.this, goods.getGoods_name(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(buy_Activity.this,goods_detail.class);
                intent.putExtra("goods_name",goods.getGoods_name());
                intent.putExtra("goods_imageID",goods.getImageId());
                startActivity(intent);
            }
        });

    }

    private void initGoods() {
        for (int i = 0; i < 2; i++) {
            Goods goods = new Goods("apple", R.drawable.abc_menu_hardkey_panel_holo_dark);
            goodsList.add(goods);
            Goods bbbb = new Goods("bbbb",R.drawable.abc_menu_hardkey_panel_holo_dark);
            goodsList.add(bbbb);
            Goods cccc = new Goods("cccc", R.drawable.abc_menu_hardkey_panel_holo_dark);
            goodsList.add(cccc);
            Goods dddd = new Goods("dddd", R.drawable.abc_menu_hardkey_panel_holo_dark);
            goodsList.add(dddd);
            Goods eeee = new Goods("eeee", R.drawable.abc_menu_hardkey_panel_holo_dark);
            goodsList.add(eeee);

        }


    }

    @Override
    public void onClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.user_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.createAddress:
                final TextView  TV1 = new TextView(this);
                TV1.setText("收货人：");
                final EditText inputServer = new EditText(this);
                final TextView  TV2 = new TextView(this);
                TV1.setText("收货地址：");
                final EditText inputServer1 = new EditText(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("创建收货地址").setIcon(android.R.drawable.ic_dialog_info).setView(TV1).setView(inputServer).setView(TV2).setView(inputServer1)
                        .setNegativeButton("Cancel", null);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        inputServer.getText().toString();
                    }
                });
                builder.show();
                Toast.makeText(this, "创建收货地址", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkAddress:
                Toast.makeText(this, "查看收货地址", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Address1:
                Toast.makeText(this, "设置地址一为收货地址成功！", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Address2:
                Toast.makeText(this, "設置地址二为收货地址成功！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return false;
    }
}
