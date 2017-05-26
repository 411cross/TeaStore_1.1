package listview;

/**
 * Created by derrickJ on 2017/5/26.
 */

public class Address {

    private int address_id;
    private int userId;
    private String phone;
    private String name;
    private String content;

    public Address(){

    }

    public Address(int address_id, int userId, String phone, String name, String content){
        setAddress_id(address_id);
        setUserId(userId);
        setPhone(phone);
        setName(name);
        setContent(content);
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
