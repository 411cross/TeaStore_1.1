package com.example.peek_mapdemotest.buy_test1;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import operation.Base64Tool;
import operation.GeneralOperation;
import operation.GetImage;
import operation.UserOperation;

/**
 * Created by Administrator on 2017/5/27.
 */
public class ModifyMessage extends AppCompatActivity {
    private ImageView headIconImage;
    private EditText modifyNameET;
    private Button changeHeadIconButton;
    private Button submitButton;


    private static final int PHOTO_REQUEST_CAREMA = 1;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private static final int PHOTO_REQUEST_CUT = 3;
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;

    private String baseString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_account_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        headIconImage = (ImageView) findViewById(R.id.person_headIcon);

        try {
            String avatarString = "http://" + (String) UserOperation.userInfo(GeneralOperation.getUser()).get(2);
            Log.i("TEST GET2", (String) UserOperation.userInfo(GeneralOperation.getUser()).get(2));
            new GetImage( headIconImage, avatarString).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        modifyNameET = (EditText) findViewById(R.id.modify_name);
        if (GeneralOperation.getUser().getName().equals("null")) {

        } else {
            modifyNameET.setText(GeneralOperation.getUser().getName());
        }
        changeHeadIconButton = (Button) findViewById(R.id.changeHeadButton);
        submitButton = (Button) findViewById(R.id.submitButton);

        changeHeadIconButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                changeHeadIcon();
            }

            private void changeHeadIcon() {
                final CharSequence[] items = {"相册", "拍照"};
                AlertDialog dlg = new AlertDialog.Builder(ModifyMessage.this).setTitle("选择图片").setItems(items, new DialogInterface.OnClickListener() {
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
                                startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
                            } else {
                                Toast.makeText(ModifyMessage.this, "未找到存储卡，无法存储照片！",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).create();
                dlg.show();

            }
        });

        /**
         * submitButton
         * 信息修改完成后 确认按钮
         * onClick 发送新的用户信息
         */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File file = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/base64.txt");
                try {
                    if (file.isFile() && file.exists()) { //判断文件是否存在

                        InputStreamReader read = new InputStreamReader(new FileInputStream(file));//考虑到编码格式
                        BufferedReader bufferedReader = new BufferedReader(read);

                        String tempString = null;
                        while ((tempString = bufferedReader.readLine()) != null) {
                            baseString = baseString + tempString;
                        }
                        read.close();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    UserOperation.modifyUserInfo(GeneralOperation.getUser(), modifyNameET.getText().toString(), baseString);
                    Intent intent = new Intent(getApplicationContext(),AccountMessage.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                Uri uri = data.getData();
                crop(uri);

            }

        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                crop(Uri.fromFile(tempFile));


            } else {
                Toast.makeText(ModifyMessage.this, "未找到存储卡，无法存储照片！",
                        Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            if (data != null) {
//                Bundle extras = data.getExtras();
                final Bitmap bitmap = data.getParcelableExtra("data");
//                final Bitmap bitmap = extras.getParcelable("data");
                headIconImage.setImageBitmap(bitmap);

                // 保存图片到internal storage
                FileOutputStream outputStream;

                try {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    // out.close();
                    // final byte[] buffer = out.toByteArray();
                    // outputStream.write(buffer);
                    outputStream = ModifyMessage.this.openFileOutput("_head_icon.jpg", Context.MODE_PRIVATE);
                    out.writeTo(outputStream);
                    out.close();
                    outputStream.close();


                    String base64 = "data:image/jpeg;base64," + Base64Tool.imageToBase(getApplicationContext().getFilesDir().getAbsolutePath() + "/_head_icon.jpg");
//                    System.out.println(base64);

                    File file = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/base64.txt");
                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(base64);
                    bw.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


}
