package com.example.camera;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;

import android.content.ContentResolver;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    //声明ImageView组件
    private ImageView picture;
    //声明两个按钮，一个是选择照片，一个是拍照
    private Button selectPhote, takePhoto;

    //声明一个统一资源识别符
    private Uri imagerUri;
    //声明三个常量，用于intent返回结果
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int CHOOSE_PHOTO = 3;

    //自动生成
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //动态申请读取手机权限
        checkNeedPermissions();

        //自动生成
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        picture = findViewById(R.id.picture);

        selectPhote = findViewById(R.id.button1);

        takePhoto = findViewById(R.id.button2);


        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhone();
            }
        });

        selectPhote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }


    //此方法用于建立图片文件，存储拍照后的图片
    private File createImageFile(String fileName) {
        //建立空文件，第一个参数获取路径，第二个参数是文件名
        File outoutImage = new File(Environment.getExternalStorageDirectory(), fileName);
        try {
            //如果文件文件存在则删除，否则建立新文件
            if (outoutImage.exists()) {
                outoutImage.delete();
            }
            outoutImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outoutImage;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断请求码，不同的请求码来执行不同的操作
        switch (requestCode) {
            case TAKE_PHOTO:
                phoneTake(resultCode);
                break;
            case CROP_PHOTO:
                phoneCrop(resultCode);
                break;
            case CHOOSE_PHOTO:
                phoneChoose(resultCode, data);
                break;
            default:
                break;
        }
    }


    // 从相册中选择照片：
    public void choosePhone() {
        //新建名字为choosephone.jpg的照片，并获取他的Uri
        imagerUri = Uri.fromFile(createImageFile("choosephone.jpg"));
        //打开Android本地图库的intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //设置要选择的格式为图片
        intent.setType("image/*");
        //第二个是选择图片的请求码
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    // 从相册中选取图片，再显示
    private void phoneChoose(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ContentResolver resolver = getContentResolver();
            Uri originalUri = data.getData();
            try {
                Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                if (photo != null) {
                    picture.setImageBitmap(photo);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //调用系统相机拍照，并创建一张jpg文件
    public void takePhoto() {
        imagerUri = Uri.fromFile(createImageFile("takephoto,jpg"));
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imagerUri);

        //启动相机
        startActivityForResult(intent, TAKE_PHOTO);
    }

    //拍照的回调
    private void phoneTake(int resultCode) {
        if (resultCode == RESULT_OK) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(imagerUri, "image/*");
            intent.putExtra("scale", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imagerUri);
            startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
        }
    }

    // 裁剪的回调
    private void phoneCrop(int resultCode) {
        if (resultCode == RESULT_OK) {
            try {
                BufferedInputStream inputStream = new BufferedInputStream(getContentResolver().openInputStream(imagerUri));
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                picture.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    //动态申请权限
    private void checkNeedPermissions() {
        //6.0以上需要动态申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //多个权限一起申请
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
        }
    }


}