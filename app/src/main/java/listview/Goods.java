package listview;

/**
 * Created by Administrator on 2017/5/23.
 */
public class Goods {

    private int goods_id;
    private int store_id;
    private int class_id;
    private String goods_name;
    private String description;
    private String price;
    private int stock;
    private int sold_thumb;
    private String thumb;

    public Goods(int goods_id, int store_id, int class_id, String goods_name, String description, String price, int stock, int sold_thumb, String thumb) {
        this.setGoods_id(goods_id);
        this.setClass_id(class_id);
        this.setStore_id(store_id);
        this.setGoods_name(goods_name);
        this.setDescription(description);
        this.setPrice(price);
        this.setStock(stock);
        this.setSold_thumb(sold_thumb);
        this.setThumb(thumb);
    }


    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getSold_thumb() {
        return sold_thumb;
    }

    public void setSold_thumb(int sold_thumb) {
        this.sold_thumb = sold_thumb;
    }
}
