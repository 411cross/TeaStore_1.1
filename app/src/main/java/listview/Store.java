package listview;

/**
 * Created by derrickJ on 2017/5/27.
 */

public class Store {

    private int store_id;
    private int agent_id;
    private String name;
    private String address;
    private String phone;
    private String description;

    public Store(){

    }

    public Store(int store_id, int agent_id, String name, String address, String phone, String description){
        setStore_id(store_id);
        setAgent_id(agent_id);
        setName(name);
        setAddress(address);
        setPhone(phone);
        setDescription(description);
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
