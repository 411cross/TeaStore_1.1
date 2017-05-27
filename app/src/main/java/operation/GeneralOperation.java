package operation;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import object.User;
import okhttp_tools.okHttpTools;

/**
 * Created by Administrator on 2017/5/25.
 */
public class GeneralOperation {

    public static User user = null;

    public static String createJsonString(Object value) {
        Gson gson = new Gson();
        String string = gson.toJson(value);
        return string;
    }


    public static User getUser(String jsonString) {
        User user = new User();
        Gson gson = new Gson();
        user = gson.fromJson(jsonString, User.class);
        return user;
    }

    /**
     blabla
     */
    public static String tokenToString(String json) throws JSONException {

        JSONObject object = new JSONObject(json);
        JSONObject data = object.getJSONObject("data");
        String token = data.getString("token");

        return token;

    }

    public static String tokenToJson(String string) throws JSONException {

        String newToken = "Bearer " + string;
        JSONObject object = new JSONObject();
        object.put("Authorization", newToken);
        String json = object.toString();

        return json;

    }


//    public static User getAgent(String jsonString) {
//        Agent agent =new Agent();
//        Gson gson = new Gson();
//        agent = gson.fromJson(jsonString,Agent.class);
//        return agent;
//    }
//    public static User getAdmin(String jsonString) {
//        Admin admin =new Admin();
//        Gson gson = new Gson();
//        admin = gson.fromJson(jsonString,Admin.class);
//        return admin;
//    }

    public static ArrayList register(String username, String password, String email) throws JSONException {
        okHttpTools okhttpT = new okHttpTools();
        JSONObject jObject = new JSONObject();
        jObject.put("username", username);
        jObject.put("password", password);
        jObject.put("email", email);
        String userRegJson = jObject.toString();
        String URL = "http://139.199.226.190:8080/api/v1/register";
        try {
            okhttpT.postTools(URL, userRegJson, null, 0);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return okhttpT.getResponse();

    }

    public static ArrayList login(String username, String password) throws JSONException {
        okHttpTools okhttpT = new okHttpTools();    // 新建HTTP代理
        JSONObject jObject = new JSONObject();
        jObject.put("username", username);
        jObject.put("password", password);
        String userjson = jObject.toString();       //转换成JSON串
        String URL = "http://139.199.226.190:8080/api/v1/login";  //请求URL   每个操作都有一个URL
        try {
            okhttpT.postTools(URL, userjson, null, 0);      //提交JSON 到服务器
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList responseList = okhttpT.getResponse();//接受响应responseList    get(0)：状态码  get（1）：整个内容。
        if (Integer.parseInt((String) okhttpT.getResponse().get(0)) == 201) {
            JSONObject object = new JSONObject((String) okhttpT.getResponse().get(1));
            JSONObject data = object.getJSONObject("data");
            String token = data.getString("token");
            int userId = data.getInt("userId");
            Log.i("TOKEN", token);
            String email = data.getString("email");
            user = new User(username, password, email);
            user.setToken(token);
            user.setUserId(userId);
        }
        return responseList;
    }


//    public static String convert(String unicode) {
//        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
//        String[] strs = unicode.split("\\\\u");
//        String returnStr = "";
//        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
//        for (int i = 1; i < strs.length; i++) {
//            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
//        }
//        return returnStr;
//    }

    public static User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static ArrayList logout(User user) throws JSONException {
        okHttpTools okhttpT = new okHttpTools();    // 新建HTTP代理
        JSONObject jObject = new JSONObject();
        String Authorization = "Bearer " + user.getToken();
        jObject.put("Authorization", Authorization);
        String userjson = jObject.toString();       //转换成JSON串
        String URL = "http://139.199.226.190:8080/api/v1/logout";  //请求URL   每个操作都有一个URL
        try {
            okhttpT.postTools(URL, userjson,Authorization, 2);      //提交JSON 到服务器
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList responseList = okhttpT.getResponse();//接受响应responseList    get(0)：状态码  get（1）：整个内容。

        return responseList;
    }


}
