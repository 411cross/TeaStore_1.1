package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.peek_mapdemotest.buy_test1.R;
import com.example.peek_mapdemotest.buy_test1.buy_Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import listview.GoodsClass;
import listview.GoodsClassAdapter;
import listview.Store;
import listview.StoreAdapter;
import operation.UserOperation;

/**
 * Created by derrickJ on 2017/5/28.
 */

public class ClassFragment extends android.support.v4.app.Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private ArrayList<GoodsClass> classList = new ArrayList<GoodsClass>();

    public static ClassFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ClassFragment classFragment = new ClassFragment();
        classFragment.setArguments(args);
        return classFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class_fragment, container, false);

        try {
            ArrayList responseList = UserOperation.getClassListRes();
            if (Integer.parseInt((String) responseList.get(0)) != 200) {
                JSONObject jsonObject = new JSONObject((String) responseList.get(1));
                String message = jsonObject.getString("message");
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            } else {
                classList = UserOperation.getClassList(responseList);
                if (classList.size() == 0) {
//                    Toast.makeText(StoreFragment.this, "暂无分类", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GoodsClassAdapter goodsClassAdapter = new GoodsClassAdapter(view.getContext(), R.layout.class_item, classList);
        ListView storeListView = (ListView) view.findViewById(R.id.class_list);
        Log.i("TESTTESTETSTEST", "onCreateView: " + view.findViewById(R.id.store_list));
        storeListView.setAdapter(goodsClassAdapter);
        storeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GoodsClass goodsClass = classList.get(i);
//                Toast.makeText(buy_Activity.this, goods.getGoods_name(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("sc_id", goodsClass.getClass_id());
                bundle.putInt("flag", 2);
                Intent intent = new Intent(view.getContext(), buy_Activity.class);
                intent.putExtras(bundle);
//                intent.putExtra("store_description", stores.getDescription());
                startActivity(intent);
            }
        });

        return view;
    }

}
