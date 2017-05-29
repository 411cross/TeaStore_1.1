package operation;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import listview.Address;
import listview.Goods;
import listview.GoodsClass;
import listview.Order_gen;
import listview.Store;
import object.User;
import okhttp_tools.okHttpTools;

/**
 * Created by derrickJ on 2017/5/26.
 */

public class UserOperation {

    /**
     * infoToList 登录用户获取账号信息
     * String json -> ArrayList list
     */
    public static ArrayList userInfo(User user) throws JSONException {

        okHttpTools okht = new okHttpTools();
        String token = user.getToken();
        String tokenJson = GeneralOperation.tokenToJson(token);
        String URL = "http://139.199.226.190:8080/api/v1/getInfo";

        try {
            okht.postTools(URL, tokenJson, token, 3);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList responseList = okht.getResponse();
        String json = (String) responseList.get(1);
        JSONObject object = new JSONObject(json);
        JSONObject data = object.getJSONObject("data");

        ArrayList userArrayList = new ArrayList();
        userArrayList.add(0, data.getInt("userId"));
        userArrayList.add(1, data.getString("name"));
        userArrayList.add(2, data.getString("avatar"));
        userArrayList.add(3, data.getInt("score"));


        return userArrayList;
    }

    /**
     * modifyUserInfo
     * 已登录用户信息
     * User user, String name(opt), String imagePath(opt) -> void
     */
    public static void modifyUserInfo(User user, String name, String base64) throws JSONException {

        okHttpTools okht = new okHttpTools();
        String token = user.getToken();
        String tokenJson = GeneralOperation.tokenToJson(token);
        String URL = "http://139.199.226.190:8080/api/v1/modifyInfo";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Authorization", tokenJson);
        jsonObject.put("name", name);
        jsonObject.put("avatar", base64);
        String json = jsonObject.toString();


        try {
            okht.postTools(URL, json, token, 1);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GeneralOperation.getUser().setName((String) UserOperation.userInfo(user).get(1));

    }

    /**
     * upgradeJson
     * 登录的用户申请成为代理商
     * ArrayList list -> String json
     */
    public static String upgradeJson(ArrayList list) throws JSONException {

        JSONObject object = new JSONObject();
        object.put("Authorization", list.get(0));
        object.put("name", list.get(1));
        object.put("phone", list.get(2));
        object.put("licence", list.get(3));
        String json = object.toString();

        return json;

    }

    /**
     * upgradeInfo
     * 登录用户获取自己的升级申请
     * String json -> ArrayList list
     */
    public static ArrayList upgardeInfo(String json) throws JSONException {

        JSONObject object = new JSONObject(json);
        JSONObject data = object.getJSONObject("data");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, data.getInt("agent_id"));
        arrayList.add(1, data.getString("userId"));
        arrayList.add(2, data.getString("name"));
        arrayList.add(3, data.getInt("phone"));
        arrayList.add(4, data.getInt("licence"));
        arrayList.add(4, data.getInt("check_status"));

        return arrayList;

    }

    /**
     * updateToken
     * 用户上传现有 token 服务器下发新的 token
     * User user -> void (直接修改对应 user 中的 token 属性）
     */
    public static void updateToken(User user) throws JSONException {

        okHttpTools okht = new okHttpTools();
        String oldToken = user.getToken();
        String tokenJson = GeneralOperation.tokenToJson(oldToken);
        String URL = "http://139.199.226.190:8080/api/v1/updateToken";

        try {
            okht.postTools(URL, tokenJson, oldToken, 1);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList responseList = okht.getResponse();
        String newToken = GeneralOperation.tokenToString((String) responseList.get(1));
        user.setToken(newToken);

    }

    /**
     * CreateAddress
     * 上传收货人，收货地址，收货人电话.
     * User user -> void (直接修改对应 user 中的 token 属性）
     */
    public static ArrayList CreateAddress(User user, String consigneeName, String consigneePhone, String consigneeAddress) throws JSONException {
        okHttpTools okhttpT = new okHttpTools();    // 新建HTTP代理
        JSONObject jObject = new JSONObject();
        String Authorization = "Bearer " + user.getToken();
        jObject.put("Authorization", Authorization);
        jObject.put("phone", consigneePhone);
        jObject.put("name", consigneeName);
        jObject.put("content", consigneeAddress);
        String userjson = jObject.toString();       //转换成JSON串
        String URL = "http://139.199.226.190:8080/api/v1/address/create";  //请求URL   每个操作都有一个URL
        try {
            okhttpT.postTools(URL, userjson, Authorization, 0);      //提交JSON 到服务器
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList responseList = okhttpT.getResponse();
        UserOperation.getAddress();

        //接受响应responseList    get(0)：状态码  get（1）：整个内容。
//        if (Integer.parseInt((String) okhttpT.getResponse().get(0)) == 201) {
//            JSONObject object = new JSONObject((String) okhttpT.getResponse().get(1));
//
//        }
        return responseList;
    }

    /**
     * getAddress
     * 获取用户地址并生成 addressList
     * List<Address> addressList(null) -> List addressList
     */
    public static ArrayList getAddress() throws JSONException {

        ArrayList<Address> addressList = new ArrayList<Address>();
        User user = GeneralOperation.getUser();
        okHttpTools okht = new okHttpTools();
        String token = user.getToken();
        String tokenJson = GeneralOperation.tokenToJson(token);
        String URL = "http://139.199.226.190:8080/api/v1/address/get";

        try {
            okht.postTools(URL, tokenJson, token, 3);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList responseList = okht.getResponse();
        String data = (String) responseList.get(1);
        JSONObject object = new JSONObject(data);
        try {
            JSONArray jsonArray = object.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject addressData = (JSONObject) jsonArray.get(i);
                int address_id = addressData.getInt("address_id");
                int userId = addressData.getInt("userId");
                String phone = addressData.getString("phone");
                String name = addressData.getString("name");
                String content = addressData.getString("content");
                Address address = new Address(address_id, userId, phone, name, content);
                addressList.add(i, address);
            }
            user.setAddressList(addressList);
            GeneralOperation.updateUser(user);
        } catch (JSONException e) {
            user.setAddressList(addressList);
        }

        return responseList;

        //在 Activity 中完善返回码的部分

    }

    /**
     * deleteAddress
     * 删除用户选定地址
     * int addressID -> ArrayList responseList
     */
    public static ArrayList DeleteAddress(int addressID) throws JSONException {
        okHttpTools okhttpT = new okHttpTools();    // 新建HTTP代理
        JSONObject jObject = new JSONObject();
        String Authorization = "Bearer " + GeneralOperation.getUser().getToken();
        jObject.put("Authorization", Authorization);
        jObject.put("address_id", addressID);
        String userjson = jObject.toString();       //转换成JSON串
        String URL = "http://139.199.226.190:8080/api/v1/address/delete";  //请求URL   每个操作都有一个URL
        try {
            okhttpT.postTools(URL, userjson, Authorization, 2);      //提交JSON 到服务器
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserOperation.getAddress();
        ArrayList responseList = okhttpT.getResponse();
        return responseList;
    }

    /**
     * ModifyAddress
     * 修改用户选定地址
     * int addressID -> ArrayList responseList
     */
    public static ArrayList ModifyAddress(int addressID, String consigneeName, String consigneeAddress, String consigneeOphone) throws JSONException {
        okHttpTools okhttpT = new okHttpTools();    // 新建HTTP代理
        JSONObject jObject = new JSONObject();
        String Authorization = "Bearer " + GeneralOperation.getUser().getToken();
        jObject.put("Authorization", Authorization);
        jObject.put("address_id", addressID);
        jObject.put("name", consigneeName);
        jObject.put("phone", consigneeOphone);
        jObject.put("content", consigneeAddress);
        String userjson = jObject.toString();       //转换成JSON串
        String URL = "http://139.199.226.190:8080/api/v1/address/modify";  //请求URL   每个操作都有一个URL
        try {
            okhttpT.postTools(URL, userjson, Authorization, 1);      //提交JSON 到服务器
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList responseList = okhttpT.getResponse();
        UserOperation.getAddress();
        return responseList;
    }


    /**
     * getGoodListRes
     * 获取指定商铺的商品列表的整个 response
     * -> ArrayList responseList
     */
    public static ArrayList getGoodsListRes(int store_id) throws JSONException {

        okHttpTools okhttpT = new okHttpTools();
        JSONObject jsonObject = new JSONObject();
        String Authorization = "Bearer " + GeneralOperation.getUser().getToken();
        jsonObject.put("Authorization", Authorization);
        jsonObject.put("store_id", store_id);
        String json = jsonObject.toString();
        String URL = "http://139.199.226.190:8080/api/v1/shop/getGoodsListByStore?store_id=" + store_id;

        try {
            okhttpT.postTools(URL, json, Authorization, 3);      //提交JSON 到服务器
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList responseList = okhttpT.getResponse();
        return responseList;

    }


    /**
     * getGoodListByClassRes
     * 获取指定商铺的商品列表的整个 response
     * -> ArrayList responseList
     */
    public static ArrayList getGoodsListByClassRes(int class_id) throws JSONException {

        okHttpTools okhttpT = new okHttpTools();
        JSONObject jsonObject = new JSONObject();
        String Authorization = "Bearer " + GeneralOperation.getUser().getToken();
        jsonObject.put("Authorization", Authorization);
        jsonObject.put("store_id", class_id);
        String json = jsonObject.toString();
        String URL = "http://139.199.226.190:8080/api/v1/shop/getGoodsListByClass?class_id=" + class_id;

        try {
            okhttpT.postTools(URL, json, Authorization, 3);      //提交JSON 到服务器
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList responseList = okhttpT.getResponse();
        return responseList;

    }


    /**
     * getGoodList
     * 获取指定商铺的商品列表
     * ArrayList responseList -> ArrayList<Goods> goodsList
     */
    public static ArrayList getGoodList(ArrayList responseList) throws JSONException {

        ArrayList<Goods> goodsList = new ArrayList<Goods>();
        String data = (String) responseList.get(1);
        JSONObject object = new JSONObject(data);

        try {
            JSONArray jsonArray = object.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject goodsData = (JSONObject) jsonArray.get(i);
                int goods_id = goodsData.getInt("goods_id");
                int store_id = goodsData.getInt("store_id");
                int class_id = goodsData.getInt("class_id");
                String name = goodsData.getString("name");
                String description = goodsData.getString("description");
                String price = goodsData.getString("price");
                int stock = goodsData.getInt("stock");
                int sold_amount = goodsData.getInt("sold_amount");
                String thumb = goodsData.getString("thumb");
                Goods good = new Goods(goods_id, store_id, class_id, name, description, price, stock, sold_amount, thumb);
                goodsList.add(i, good);
            }

        } catch (JSONException e) {

        }

        return goodsList;

    }

    /**
     * getStoreListRes
     * 获取商铺列表的整个 response
     * -> ArrayList responseList
     */
    public static ArrayList getStoreListRes() throws JSONException {

        okHttpTools okhttpT = new okHttpTools();
        JSONObject jsonObject = new JSONObject();
        String Authorization = "Bearer " + GeneralOperation.getUser().getToken();
        jsonObject.put("Authorization", Authorization);
        String json = jsonObject.toString();

        String URL = "http://139.199.226.190:8080/api/v1/shop/getStoreList";
        try {
            okhttpT.postTools(URL, json, Authorization, 3);      //提交JSON 到服务器
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList responseList = okhttpT.getResponse();
        return responseList;

    }

    /**
     * getStoreList
     * 获取指定商铺的商品列表
     * ArrayList responseList -> ArrayList<Goods> goodsList
     */
    public static ArrayList getStoreList(ArrayList responseList) throws JSONException {

        ArrayList<Store> storesList = new ArrayList<Store>();
        String data = (String) responseList.get(1);
        JSONObject object = new JSONObject(data);

        try {
            JSONArray jsonArray = object.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject storesData = (JSONObject) jsonArray.get(i);
                int store_id = storesData.getInt("store_id");
                int agent_id = storesData.getInt("agent_id");
                String name = storesData.getString("name");
                String address = storesData.getString("address");
                String phone = storesData.getString("phone");
                String description = storesData.getString("description");
                Store store = new Store(store_id, agent_id, name, address, phone, description);
                storesList.add(i, store);
            }

        } catch (JSONException e) {

        }

        return storesList;

    }

    /**
     * setPwd
     * 用户设置密码
     * ArrayList responseList -> ArrayList<Goods> goodsList
     */

    public static ArrayList setPwd(String newPass) throws JSONException {
        okHttpTools okhttpT = new okHttpTools();    // 新建HTTP代理
        JSONObject jObject = new JSONObject();
        String Authorization = "Bearer " + GeneralOperation.getUser().getToken();
        jObject.put("Authorization", Authorization);
        jObject.put("newPass", newPass);
        String userjson = jObject.toString();       //转换成JSON串
        String URL = "http://139.199.226.190:8080/api/v1/setPwd";  //请求URL   每个操作都有一个URL
        try {
            okhttpT.postTools(URL, userjson, Authorization, 1);      //提交JSON 到服务器
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList responseList = okhttpT.getResponse();
        return responseList;
    }

    /**
     * getStoreListRes
     * 获取商铺列表的整个 response
     * -> ArrayList responseList
     */
    public static ArrayList getClassListRes() throws JSONException {

        okHttpTools okhttpT = new okHttpTools();
        JSONObject jsonObject = new JSONObject();
        String Authorization = "Bearer " + GeneralOperation.getUser().getToken();
        jsonObject.put("Authorization", Authorization);
        String json = jsonObject.toString();

        String URL = "http://139.199.226.190:8080/api/v1/shop/getClassList";
        try {
            okhttpT.postTools(URL, json, Authorization, 3);      //提交JSON 到服务器
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList responseList = okhttpT.getResponse();
        return responseList;

    }

    /**
     * getClassList
     * 获取指定分类的商品列表
     * ArrayList responseList -> ArrayList<Goods> goodsList
     */
    public static ArrayList getClassList(ArrayList responseList) throws JSONException {

        ArrayList<GoodsClass> classList = new ArrayList<GoodsClass>();
        String data = (String) responseList.get(1);
        JSONObject object = new JSONObject(data);

        try {
            JSONArray jsonArray = object.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject storesData = (JSONObject) jsonArray.get(i);
                int class_id = storesData.getInt("class_id");
                String name = storesData.getString("name");
                String description = storesData.getString("description");
                GoodsClass goodsClass = new GoodsClass(class_id, name, description);
                classList.add(i, goodsClass);
            }

        } catch (JSONException e) {

        }

        return classList;

    }

    //获取用户订单
    public static ArrayList getOrder() throws JSONException {

        ArrayList<Order_gen> orderList = new ArrayList<Order_gen>();
        User user = GeneralOperation.getUser();
        okHttpTools okht = new okHttpTools();
        String token = user.getToken();
        JSONObject jObject = new JSONObject();
        String Authorization = "Bearer " + token;
        jObject.put("Authorization", Authorization);
        jObject.put("status",-1);
        String userjson = jObject.toString();
        String URL = "http://139.199.226.190:8080/api/v1/shop/getOrder?status=-1";
        Log.i("book", "123231");
        try {
            okht.postTools(URL, userjson, Authorization, 3);      //提交JSON 到服务器
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList responseList = okht.getResponse();
        String data = (String) responseList.get(1);
        JSONObject object = new JSONObject(data);
        try {
            JSONArray jsonArray = object.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject addressData = (JSONObject) jsonArray.get(i);
                int order_id = addressData.getInt("order_id");
                int userId = addressData.getInt("userId");
                int address_id = addressData.getInt("address_id");
                String order_no = addressData.getString("order_no");
                String pay = addressData.getString("pay");
                int status = addressData.getInt("status");
                String finish_time = addressData.getString("finish_time");
                String created_at = addressData.getString("created_at");
                Order_gen og=new Order_gen(order_id,userId,address_id,order_no,pay,status,finish_time,created_at);
                orderList.add(i, og);
                Log.i("adddddd", order_id+"");
            }
            user.setOrderList(orderList);
            GeneralOperation.updateUser(user);
        } catch (JSONException e) {
            user.setOrderList(orderList);
        }
        return responseList;
    }

    //上传订单信息
    public static boolean CreateOrder(User user, String address_id, String number, String goods_id) throws JSONException {
        okHttpTools okhttpT = new okHttpTools();    // 新建HTTP代理
        JSONObject jObject = new JSONObject();
        JSONObject jObject2 =new JSONObject();
        JSONObject jObject3 =new JSONObject();
        String Authorization = "Bearer " + user.getToken();
        jObject.put("Authorization", Authorization);
        jObject2.put("goods_id", goods_id);
        jObject2.put("coupon_id", "0");
        jObject2.put("amount", number);

        JSONArray ja =new JSONArray();
        ja.put(jObject2);
        jObject.put("goods", ja);
        jObject.put("address_id", address_id);
        String userjson = jObject.toString();
        Log.i("aaaa",userjson);
        //转换成JSON串
        String URL = "http://139.199.226.190:8080/api/v1/shop/createOrder";  //请求URL   每个操作都有一个URL
        try {
            okhttpT.postTools(URL, userjson, Authorization, 0);      //提交JSON 到服务器
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //判断返回结果
        if (Integer.parseInt((String) okhttpT.getResponse().get(0)) == 201) {
            return true;
        }
        return false;
    }


}
