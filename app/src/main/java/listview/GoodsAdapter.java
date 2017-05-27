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
public class GoodsAdapter extends ArrayAdapter<Goods>{

    private int resource;


//    private List<Goods> mGoodsList;
    public GoodsAdapter(Context context, int resourceID, List<Goods> objects) {
        super(context, resourceID, objects);
        resource = resourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Goods Goods = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        ImageView goodsImage = (ImageView) view.findViewById(R.id.goods_image);
        TextView goodsName = (TextView) view.findViewById(R.id.goods_name);
        TextView price = (TextView) view.findViewById(R.id.price);

        String imageURL = "http://" + Goods.getThumb();
        new GetImage(goodsImage, imageURL).execute();
        goodsName.setText(Goods.getGoods_name());
        price.setText(Goods.getPrice());
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
