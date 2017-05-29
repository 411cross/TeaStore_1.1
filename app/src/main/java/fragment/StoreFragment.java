package fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peek_mapdemotest.buy_test1.R;
import com.example.peek_mapdemotest.buy_test1.StoresList;
import com.example.peek_mapdemotest.buy_test1.buy_Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import listview.Store;
import listview.StoreAdapter;
import operation.UserOperation;

import static com.example.peek_mapdemotest.buy_test1.R.id.textView;

/**
 * Created by derrickJ on 2017/5/28.
 */

public class StoreFragment extends android.support.v4.app.Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private ArrayList<Store> storesList = new ArrayList<Store>();

    public static StoreFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        StoreFragment storeFragment = new StoreFragment();
        storeFragment.setArguments(args);
        return storeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment, container, false);

        try {
            ArrayList responseList = UserOperation.getStoreListRes();
            if (Integer.parseInt((String) responseList.get(0)) != 200) {
                JSONObject jsonObject = new JSONObject((String) responseList.get(1));
                String message = jsonObject.getString("message");
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            } else {
                storesList = UserOperation.getStoreList(responseList);
                if (storesList.size() == 0) {
//                    Toast.makeText(StoreFragment.this, "暂无商铺", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StoreAdapter storesAdapter = new StoreAdapter(view.getContext(), R.layout.stores_item, storesList);
        ListView storeListView = (ListView) view.findViewById(R.id.store_list);
        Log.i("TESTTESTETSTEST", "onCreateView: " + view.findViewById(R.id.store_list));
        storeListView.setAdapter(storesAdapter);
        storeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Store stores = storesList.get(i);
//                Toast.makeText(buy_Activity.this, goods.getGoods_name(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("sc_id", stores.getStore_id());
                bundle.putInt("flag", 1);
                Intent intent = new Intent(view.getContext(), buy_Activity.class);
                intent.putExtras(bundle);
//                intent.putExtra("store_description", stores.getDescription());
                startActivity(intent);
            }
        });

        return view;
    }
}


