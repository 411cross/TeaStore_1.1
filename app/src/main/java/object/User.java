package object;

import java.util.ArrayList;

import listview.Address;
import listview.Order_gen;

/**
 * Created by Administrator on 2017/5/25.
 */
public class User {
    private String username = null;
    private String password = null;
    private String email = null;
    private String token = null;
    private int userId;
    private ArrayList<Address> addressList;
    private Address selectedAddress;
    private String name =null;
    private ArrayList<Order_gen> orderList;

    public User() {

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(ArrayList<Address> addressList) {
        this.addressList = addressList;
    }


    public Address getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Order_gen> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order_gen> orderList) {
        this.orderList = orderList;
    }
}
