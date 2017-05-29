package listview;

/**
 * Created by derrickJ on 2017/5/29.
 */

public class GoodsClass {

    private int class_id;
    private String name;
    private String description;

    public GoodsClass() {

    }

    public GoodsClass(int class_id, String name, String description) {
        setClass_id(class_id);
        setName(name);
        setDescription(description);
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
