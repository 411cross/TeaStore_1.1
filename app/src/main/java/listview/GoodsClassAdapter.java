package listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.peek_mapdemotest.buy_test1.R;

import java.util.List;

/**
 * Created by derrickJ on 2017/5/27.
 */

public class GoodsClassAdapter extends ArrayAdapter<GoodsClass>{

    private int resource;


    //    private List<Goods> mGoodsList;
    public GoodsClassAdapter(Context context, int resourceID, List<GoodsClass> objects) {
        super(context, resourceID, objects);
        resource = resourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsClass goodsClass = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource, parent, false);
//        ImageView storeImage = (ImageView) view.findViewById(R.id.store_image);
        TextView className = (TextView) view.findViewById(R.id.class_name);
//        TextView storeDescription = (TextView) view.findViewById(R.id.price);

        className.setText(goodsClass.getName());
//        storeDescription.setText(store.getDescription());
        return view;
    }

//    @Override
//    public GoodsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_item,parent,false);
//        ViewHolder holder = new ViewHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(GoodsAdapter.ViewHolder holder, int position) {
//Goods goods = mGoodsList.get(position);
//        holder.goodsImage.setImageResource(goods.getImageId());
//        holder.goodsName.setText(goods.getGoods_name());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mGoodsList.size();
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView goodsImage;
//        TextView goodsName;
//        public ViewHolder(View itemView) {
//            super(itemView);
//            goodsImage = (ImageView) itemView.findViewById(R.id.goods_image);
//            goodsName = (TextView) itemView.findViewById(R.id.goods_name);
//        }
//    }
//
//    public GoodsAdapter(List<Goods> goodsList){
//        mGoodsList=goodsList;
//    }
}
