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

/**
 * Created by derrickJ on 2017/5/26.
 */

public class AddressAdapter extends ArrayAdapter<Address> {

    private int resource;


    //    private List<Goods> mGoodsList;
    public AddressAdapter(Context context, int resourceID, List<Address> objects) {
        super(context, resourceID, objects);
        resource = resourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Address address = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource, parent, false);

//        TextView addressId = (TextView) view.findViewById(R.id.address_id);
        TextView addressName = (TextView) view.findViewById(R.id.address_name);
        TextView addressPhone = (TextView) view.findViewById(R.id.address_phone);
        TextView addressContent = (TextView) view.findViewById(R.id.address_content);

//        addressId.setText(address.getAddress_id());
        addressName.setText(address.getName());
        addressPhone.setText(address.getPhone());
        addressContent.setText(address.getContent());

        return view;
    }

}

