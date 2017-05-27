package operation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by derrickJ on 2017/5/27.
 */

/**
 * GetImage 使用方法
 *
 * 在需要通过 url 获取图片的 Activity 中的适当位置插入
 * new GetImage(ImageView imageView, String url).execute();
 * 这个语句会自动 set 好 imageView，不需要再 set
 *
 * ImageView imageView: 可以传入已声明的 ImageView， 也可以使用 (ImageView) findViewById(R.id.imageView_id)
 * String url: 由于连接时协议不能为空，所以传入的 url 前一定要加上 "http://"
 * .execute() 是执行 doInBackground 方法
 */

public class GetImage extends AsyncTask {

    private ImageView imageView;
    private String imagePath;

    public GetImage(ImageView imageView, String imagePath) {
        setImageView(imageView);
        setImagePath(imagePath);
    }

    @Override
    protected Object doInBackground(Object[] params) {

        String imageURL = getImagePath();
        try {
            // 创建一个URL
            URL url = new URL(imageURL);
            Log.i("测试URL", url.toString());
            // 从URL获取对应资源的 InputStream
            InputStream inputStream = url.openStream();
            // 用inputStream来初始化一个Bitmap 虽然此处是Bitmap，但是URL不一定非得是Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            // 关闭 InputStream
            inputStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        // 此处的形参o，是doInBackground的返回值
        getImageView().setImageBitmap((Bitmap)o);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}