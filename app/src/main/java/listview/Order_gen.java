package listview;

import com.example.peek_mapdemotest.buy_test1.Order;

/**
 * Created by chf on 2017/5/29.
 */

public class Order_gen {
    private int order_id;
    private int userId;
    private int address_id;
    private String order_no;
    private String pay;
    private int status;
    private String finish_time;
    private String created_at;
public Order_gen(){

}
public Order_gen(int order_id,int userId,int address_id,String order_no,String pay,int status,String finish_time,String created_at){
    setOrder_id(order_id);
    setUserId(userId);
    setAddress_id(address_id);
    setOrder_no(order_no);
    setPay(pay);
    setStatus(status);
    setFinish_time(finish_time);
    setCreated_at(created_at);
}
    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
