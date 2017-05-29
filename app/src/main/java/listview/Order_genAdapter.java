package listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.peek_mapdemotest.buy_test1.R;

import java.util.List;

import operation.GetImage;

/**
 * Created by Administrator on 2017/5/23.
 */
public class Order_genAdapter extends ArrayAdapter<Order_gen>{

    private int resource;


    //    private List<Goods> mGoodsList;
    public Order_genAdapter(Context context, int resourceID, List<Order_gen> objects) {
        super(context, resourceID, objects);
        resource = resourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order_gen og = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        TextView order_no = (TextView) view.findViewById(R.id.order_no);
        TextView pay = (TextView) view.findViewById(R.id.pay);
        TextView created_at = (TextView) view.findViewById(R.id.created_at);
        pay.setText(og.getPay());
        order_no.setText(og.getOrder_no());
        created_at.setText(og.getCreated_at());
        return view;

    }

}
