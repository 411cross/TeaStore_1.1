package operation;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import listview.Address;
import object.User;
import okhttp_tools.okHttpTools;

import static operation.GeneralOperation.user;

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
            okht.postTools(URL, tokenJson, token, 1);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList responseList = okht.getResponse();
        String json = GeneralOperation.tokenToString((String) responseList.get(1));
        JSONObject object = new JSONObject(json);
        JSONObject data = object.getJSONObject("data");

        ArrayList userArrayList = new ArrayList();
        userArrayList.add(0, data.getInt("userId"));
        userArrayList.add(1, data.getString("name"));
        userArrayList.add(2, data.getString("avatar"));
        userArrayList.add(3, data.getInt("finish"));
        userArrayList.add(4, data.getInt("score"));

        return userArrayList;
    }

    /**
     * modifyUserInfo
     * 已登录用户修改地址
     * User user, String name(opt), String imagePath(opt) -> void
     */
    public static void modifyUserInfo(User user, String name, String imagePath) throws JSONException {

        okHttpTools okht = new okHttpTools();
        String token = user.getToken();
        String tokenJson = GeneralOperation.tokenToJson(token);
        String URL = "http://139.199.226.190:8080/api/v1/modifyInfo";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Authorization", tokenJson);
        jsonObject.put("name", name);
        jsonObject.put("avatar", imagePath);
        String json = jsonObject.toString();

        try {
            okht.postTools(URL, json, token, 1);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        ArrayList responseList = okhttpT.getResponse();//接受响应responseList    get(0)：状态码  get（1）：整个内容。
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
        } catch (JSONException e){
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
        ArrayList responseList = okhttpT.getResponse();
        return responseList;
    }


}
