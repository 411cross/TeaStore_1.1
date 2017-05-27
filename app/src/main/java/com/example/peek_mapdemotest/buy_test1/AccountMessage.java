package com.example.peek_mapdemotest.buy_test1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;

import operation.GeneralOperation;
import operation.UserOperation;

/**
 * Created by Administrator on 2017/5/27.
 */
public class AccountMessage extends ActionBarActivity {
    private ImageView avatarImage;
    private TextView accountName;
    private TextView accountEmail;
    private Button bianji_button;

    private static final int PHOTO_REQUEST_CAREMA = 1;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private static final int PHOTO_REQUEST_CUT = 3;
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_message);

        try {
            String avatarString = (String) UserOperation.userInfo(GeneralOperation.getUser()).get(2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        avatarImage = (ImageView) findViewById(R.id.avatarImage);
        accountName= (TextView) findViewById(R.id.account_name);
        accountName.setText(GeneralOperation.getUser().getUsername().toString());
        accountEmail= (TextView) findViewById(R.id.account_email);
        accountEmail.setText(GeneralOperation.getUser().getEmail().toString());
        bianji_button = (Button) findViewById(R.id.bianji_button);


        bianji_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeHeadIcon();
            }

            private void changeHeadIcon() {
                final CharSequence[] items = { "相册", "拍照" };
                AlertDialog dlg = new AlertDialog.Builder(AccountMessage.this).setTitle("选择图片").setItems(items, new  DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // 这里item是根据选择的方式，
                        if (item == 0) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent,
                                    PHOTO_REQUEST_GALLERY);
                        } else {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                tempFile = new File(Environment.getExternalStorageDirectory(),
                                        PHOTO_FILE_NAME);
                                Uri uri = Uri.fromFile(tempFile);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                startActivityForResult(intent,PHOTO_REQUEST_CAREMA);
                            } else {
                                Toast.makeText(AccountMessage.this, "未找到存储卡，无法存储照片！",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).create();
                dlg.show();

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                Uri uri = data.getData();
                Log.e("图片路径？？", data.getData() + "");
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(AccountMessage.this, "未找到存储卡，无法存储照片！",
                        Toast.LENGTH_SHORT).show();
            }

//        } else if (requestCode == PHOTO_REQUEST_CUT) {
//            if (data != null) {
//                final Bitmap bitmap = data.getParcelableExtra("data");
//                headIcon.setImageBitmap(bitmap);
//                // 保存图片到internal storage
//                FileOutputStream outputStream;
//                try {
//                    ByteArrayOutputStream out = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//                    out.flush();
//                    // out.close();
//                    // final byte[] buffer = out.toByteArray();
//                    // outputStream.write(buffer);
//                    outputStream = MainActivity.this.openFileOutput("_head_icon.jpg", Context.MODE_PRIVATE);
//                    out.writeTo(outputStream);
//                    out.close();
//                    outputStream.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            try {
                if (tempFile != null && tempFile.exists())
                    tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

}
