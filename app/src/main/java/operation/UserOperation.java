package operation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
    public static ArrayList userInfo(String json) throws JSONException {

        JSONObject object = new JSONObject(json);
        JSONObject data = object.getJSONObject("data");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, data.getInt("userId"));
        arrayList.add(1, data.getString("name"));
        arrayList.add(2, data.getString("avatar"));
        arrayList.add(3, data.getInt("finish"));
        arrayList.add(4, data.getInt("score"));

        return arrayList;
    }

    /**
     * upgradeJson 登录用户申请成为代理商
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
        String URL="http://localhost:8888/api/v1/updateToken";

        try {
            okht.postTools(URL,tokenJson, oldToken, 1);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList responseList= okht.getResponse();
        String newToken = GeneralOperation.tokenToString((String)responseList.get(1));
        System.out.println((String)responseList.get(1));
        user.setToken(newToken);

    }

}
