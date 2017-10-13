package com.hxh.component.basicore.CommonConponet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.hxh.component.basicore.util.Utils.getApplicationContext;

/**
 * Created by mardawang on 2017/5/28.
 */

public class BitmapView {

    private static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/xiaoai/";


    /**
     * 判断Bitmap对象是否有效
     *
     * @param bmp Bitmap对象
     * @return true if bitmap is not null and not be recycled
     * @author amy_xie
     */
    public static boolean isBitmapAvailable(Bitmap bmp) {
        return bmp != null && !bmp.isRecycled();
    }

    // 将生成的图片保存到本地
    public static String saveBitmap(Bitmap bitmap, String name) {
        File file;
        String savePath;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            savePath = SDPATH;
        } else {
            savePath = getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + "/xiaoai/";
        }
        try {
            file = new File(savePath + name + ".jpg");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return file.getAbsolutePath();
    }

    //质量压缩发，设置bitmap小于100kb

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.PNG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static Bitmap getResizeBitmap(String imageFilePath, int targetValue) {
        int targetWidth;
        int targetHeight;
        Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);

        if (bitmap.getWidth() > bitmap.getHeight()) {
            targetWidth = targetValue;
            targetHeight = bitmap.getHeight() / bitmap.getWidth() * targetWidth;
        } else {
            targetHeight = targetValue;
            targetWidth = (targetHeight * bitmap.getWidth()) / bitmap.getHeight();
        }

        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);
    }


    public static Bitmap resizeBitmapFromFile(String imageFilePath,
                                              int reqWidth, int reqHeight) {
        Bitmap bitmap = null;
        int width, height;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFilePath, options);
        width = options.outWidth;
        height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqWidth || width > reqHeight) {
            while ((height / inSampleSize) > reqWidth
                    && (width / inSampleSize) > reqHeight) {
                inSampleSize *= 2;
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        try {
            bitmap = BitmapFactory.decodeFile(imageFilePath, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    //删除目录
    public static void deleteDir() {
        File dir = new File(SDPATH);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    //删除文件家
    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }
}
