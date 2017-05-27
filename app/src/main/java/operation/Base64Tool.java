package operation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
import object.User;


/**
 * Created by derrickJ on 2017/5/26.
 */

public class Base64Tool {

    public static String imageToBase(String imagePath) {

        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imagePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String baseString = encoder.encode(data);

        return baseString;//返回Base64编码过的字节数组字符串
    }

    public static String baseToImage(String baseString) throws IOException {

        User user = GeneralOperation.getUser();
        int userId = user.getUserId();
        //对字节数组字符串进行Base64解码并生成图片
        if (baseString == null) //图像数据为空
            return "Blank Image";

        BASE64Decoder decoder = new BASE64Decoder();
            //Base64解码
            byte[] b = decoder.decodeBuffer(baseString);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imagePath = "/Users/derrickJ/AndroidStudioProjects/TeaStore_1.1/app/src/main/res/mipmap-xxxhdpi/" + userId + ".jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imagePath);
            out.write(b);
            out.flush();
            out.close();

            return imagePath;

    }


}
