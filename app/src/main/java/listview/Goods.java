package listview;

/**
 * Created by Administrator on 2017/5/23.
 */
public class Goods {
    private String goods_name;
    private int imageId;

    public Goods(String name, int imageId) {
        this.goods_name = name;
        this.imageId = imageId;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public int getImageId() {
        return imageId;
    }
}
