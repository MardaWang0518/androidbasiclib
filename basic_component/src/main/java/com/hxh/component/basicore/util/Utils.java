package com.hxh.component.basicore.util;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.hxh.component.basicore.Config;
import com.hxh.component.basicore.R;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.RandomAccess;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hxh on 2017/4/12.
 */
public class Utils {
    private static Context mContext;
    private static DateTime dateTime;
    private static Boolean isDebug = null;

    private Utils() {
        throw new IllegalStateException("you can't instance me");
    }

    public static void init(Context context) {
        mContext = context;

    }

    public static Context getApplicationContext() {
        if (null == mContext) {
            throw new IllegalStateException("you first call Utils.init()...");
        }
        return mContext;
    }

    public static boolean isDebug() {
        return isDebug == null ? false : isDebug.booleanValue();
    }

    public static void syncIsDebug(Context context) {
        if (null == isDebug) {
            isDebug = context.getApplicationInfo() != null && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }




    public static class FileUtil {

        public static File createFile(byte[] datas) {
            BufferedOutputStream bos = null;
            try {
                String path = mContext.getCacheDir().getPath() + File.separator + generatePictureName() + ".jpeg";
                File imgfile = new File(path);
                bos = new BufferedOutputStream(new FileOutputStream(imgfile));
                bos.write(datas);
                bos.flush();
                return imgfile;
            } catch (Exception e) {

            } finally {
                if (null != bos) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }


        public static final String ROOT_DIR = "xiaoaikeji";

        /**
         * Check if the primary "external" storage device is available.
         */
        public static boolean hasSDCardMounted() {
            String state = Environment.getExternalStorageState();
            return state != null && state.equals(Environment.MEDIA_MOUNTED);
        }

        /**
         * 关闭流
         */
        public static boolean close(Closeable io) {
            if (io != null) {
                try {
                    io.close();
                } catch (IOException e) {
                }
            }
            return true;
        }

        /**
         * 从Uri获取文件路径。 这将获得存储访问的路径
         * 适用于   从相册获取到的Uri ，通过ContentProvider得到的 Uri等
         * 如果是从相册获取到的Uri，则你必须用此方法，因为4.4版本直接用Uri会找不到路径
         *
         * @param context The context.
         * @param uri     The Uri to query.
         * @author paulburke
         */
        @TargetApi(Build.VERSION_CODES.KITKAT)
        public static String getPath(final Context context, final Uri uri) {

            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + docId.substring(type.length() + 1);
                    }

                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            docId.substring(type.length() + 1)
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            return null;
        }

        /**
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.
         *
         * @param context       The context.
         * @param uri           The Uri to query.
         * @param selection     (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         */
        public static String getDataColumn(Context context, Uri uri, String selection,
                                           String[] selectionArgs) {

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {
                    column
            };

            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                        null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int column_index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(column_index);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return null;
        }


        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        public static boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        public static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        public static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }


        /**
         * drawable转成bitmap
         */
        public static Bitmap drawableToBitmap(Drawable drawable) {
            Bitmap bitmap = null;
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if (bitmapDrawable.getBitmap() != null) {
                    return bitmapDrawable.getBitmap();
                }
            }
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }

        public static boolean bitmapToFile(Bitmap bitmap, String file) {

            FileOutputStream out = null;
            boolean result = false;
            try {
                out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
                result = false;
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    result = false;
                }
            }
            return result;
        }

        /**
         * 保存bitmap到应用内的cache文件夹下,并且输出一个File
         *
         * @param context context
         * @param bitmap  bitmap
         * @return File path
         * @throws IOException
         */
        public static File saveBitmap(Context context, Bitmap bitmap) throws IOException {
            String pictureName = context.getCacheDir().getPath() + File.separator + generatePictureName();
            String destinationDirectoryPath = pictureName + ".jpeg";
            File file = new File(destinationDirectoryPath);
            while (file.exists()) {
                file = new File(pictureName + Math.random() * 1000 + ".jpeg");
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
            return file;
        }

        /**
         * 生成图片名,不带扩展名
         *
         * @return 图片名
         */
        public static String generatePictureName() {

            return "IMG_" + DateTime.now().toString("yyyyMMdd_HHmmss");
        }

        /**
         * 生成图片名
         *
         * @return 图片名
         */
        public static String generateJpgPictureName() {
            return "IMG_" + DateTime.now().toString("yyyyMMdd_HHmmss") + ".jpg";
        }

        public static String generatepngPictureName() {
            return "IMG_" + DateTime.now().toString("yyyyMMdd_HHmmss") + ".png";
        }

        public static void saveFileFromBitMap(Bitmap map, File saveFIle) {
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(saveFIle));
                map.compress(Bitmap.CompressFormat.PNG, 100, bos);
                bos.flush();
                bos.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public static boolean writeFileFromIS(File file, InputStream is, boolean append) {
            if (file == null || is == null) return false;
            if (!createOrExistsFile(file)) return false;
            OutputStream os = null;
            try {
                os = new BufferedOutputStream(new FileOutputStream(file, append));
                byte data[] = new byte[1024];
                int len;
                while ((len = is.read(data, 0, 1024)) != -1) {
                    os.write(data, 0, len);
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    is.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }


        /**
         * 判断文件是否存在，不存在则判断是否创建成功
         *
         * @param file 文件
         * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
         */
        public static boolean createOrExistsFile(File file) {
            if (file == null) return false;
            // 如果存在，是文件则返回true，是目录则返回false
            if (file.exists()) return file.isFile();
            if (!createOrExistsDir(file.getParentFile())) return false;
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        public static boolean createOrExistsFile(String filepath) {
            File file = new File(filepath);
            if (file == null) return false;
            // 如果存在，是文件则返回true，是目录则返回false
            if (file.exists()) return file.isFile();
            if (!createOrExistsDir(file.getParentFile())) return false;
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }


        /**
         * 判断目录是否存在，不存在则判断是否创建成功
         *
         * @param file 文件
         * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
         */
        public static boolean createOrExistsDir(File file) {
            // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
            return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
        }
    }

    /**
     * 针对于Package的工具类
     * 提供 得到版本号，版本名字，包名，安装程序等
     */
    public static class Package {
        /**
         * 得到版本代号
         *
         * @param context
         * @return
         */
        public static int getVersionCode(Context context) {
            PackageManager pm = context.getPackageManager();
            PackageInfo packinfo = null;
            try {
                packinfo = pm.getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return packinfo.versionCode;
        }

        /**
         * 得到版本名称
         *
         * @param context
         * @return
         */
        public static String getVersionName(Context context) {
            PackageManager pm = context.getPackageManager();
            PackageInfo pinfo = null;
            try {
                pinfo = pm.getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return pinfo.versionName;
        }

        public static boolean installNormal(Context context, String filePath) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            File file = new File(filePath);
            if (null == file || !file.exists() || !file.isFile() || file.length() <= 0) {
                return false;
            }
            i.setDataAndType(Uri.parse("file://" + filePath), Config.INSTALL_APP_SCHEMA);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }

        public static String getPackageName(Context context) {
            return context.getPackageName();
        }
    }

    /**
     * 提供诸如 px转dp dp转px的工具
     */
    public static class Dimens {
        public static float dpToPx(float dp) {
            return dp * mContext.getResources().getDisplayMetrics().density;
        }

        public static float pxToDp(float px) {
            return px / mContext.getResources().getDisplayMetrics().density;
        }

        public static int dpToPxInt(float dp) {
            return (int) (dpToPx(dp));
        }

        public static int pxToDpInt(float px) {
            return (int) (pxToDp(px));
        }
    }

    /**
     * 网络相关
     */
    public static class NetWork {
        public static final String NETWORK_TYPE_WIFI = "wifi";
        public static final String NETWORK_TYPE_3G = "eg";
        public static final String NETWORK_TYPE_2G = "2g";
        public static final String NETWORK_TYPE_WAP = "wap";
        public static final String NETWORK_TYPE_UNKNOWN = "unknown";
        public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

        private static int getNetWorkType() {
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm == null ? null : cm.getActiveNetworkInfo();
            return ni == null ? -1 : ni.getType();
        }

        private static NetworkInfo getNetWorkInfo() {
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo();
        }

        /**
         * 当前网络是否连接
         *
         * @return
         */
        public static boolean isConnected() {
            if (null != getNetWorkInfo()) {

                return getNetWorkInfo().isConnectedOrConnecting();
            }
            return false;
        }

    }

    public static class Toast {

        private static android.widget.Toast mToast;

        public static void toast(String message) {
            show(message, android.widget.Toast.LENGTH_SHORT);
        }

        public static void toast(int resId) {
            show(mContext.getResources().getString(resId), android.widget.Toast.LENGTH_SHORT);
        }

        public static void toast_long(String message) {
            show(message, android.widget.Toast.LENGTH_LONG);
        }

        private static void show(String message, int duration) {
            if (null == mToast) {
                mToast = android.widget.Toast.makeText(Utils.getApplicationContext(), message, duration);
            } else {
                mToast.setText(message);
                mToast.setDuration(duration);
            }
            mToast.show();
        }
    }

    public static class Text {
        public static boolean isEmpty(java.lang.String str) {
            if (null == str || "".equals(str)) {
                return true;
            }
            return false;
        }


        public static boolean isEmpty(Object str) {
            if (null == str) {
                return true;
            }
            return false;
        }


        public static boolean isEmpty(CharSequence str) {
            if (null == str || "".equals(str)) {
                return true;
            }
            return false;
        }

        /**
         * 判断字符串数组是否为空
         *
         * @param args
         * @return
         */
        public static boolean isEmpty(String... args) {
            if (args == null) return true;
            for (String s : args) {
                if (isEmpty(s)) return true;
            }
            return false;
        }


        public static boolean isEmpty(EditText text) {

            return isEmptyCheck(text, null);
        }

        public static boolean isEmpty(EditText text, String tipmsg) {

            return isEmptyCheck(text, tipmsg);
        }

        private static boolean isEmptyCheck(EditText text, String msg) {
            if (null != text) {
                if (Utils.Text.isEmpty(text.getText().toString().trim())) {
                    if (!isEmpty(msg)) {
                        Toast.toast(msg);
                    } else if (!Utils.Text.isEmpty(text.getHint())) {
                        Toast.toast(text.getHint().toString());
                    } else {
                        Toast.toast("请输入有效信息");
                    }
                    return true;
                }
            } else {
                throw new IllegalStateException("Editext is can't null");
            }
            return false;
        }


        public static boolean isEmpty(TextView tv) {
            if (null != tv && tv.getText().toString().trim().contains("请选择")) {
                Toast.toast("请选择");
                return true;
            }
            return false;
        }

        public static boolean isEmpty(List list) {
            if (null == list || 0 == list.size()) {
                return true;
            }
            return false;
        }
    }

    public static class SP {
        public static String PREFERENCE_NAME = "app_context";

        /**
         * put string preferences
         *
         * @param
         * @param key   The name of the preference to modify
         * @param value The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        public static boolean putString(String key, String value) {
            SharedPreferences settings = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(key, value);
            return editor.commit();
        }

        public static SharedPreferences.Editor editor() {
            SharedPreferences settings = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            return settings.edit();
        }

        /**
         * get string preferences
         *
         * @param
         * @param key The name of the preference to retrieve
         * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this
         * name that is not a string
         * @see #(Context, String, String)
         */
        public static String getString(String key) {
            return getString(key, null);
        }

        /**
         * get string preferences
         *
         * @param
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a string
         */
        public static String getString(String key, String defaultValue) {
            SharedPreferences settings = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            return settings.getString(key, defaultValue);
        }

        /**
         * put int preferences
         *
         * @param
         * @param key   The name of the preference to modify
         * @param value The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        public static boolean putInt(String key, int value) {
            SharedPreferences settings = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt(key, value);
            return editor.commit();
        }

        /**
         * get int preferences
         *
         * @param
         * @param key The name of the preference to retrieve
         * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
         * name that is not a int
         * @see #(Context, String, int)
         */
        public static int getInt(String key) {
            return getInt(key, -1);
        }

        /**
         * get int preferences
         *
         * @param
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a int
         */
        public static int getInt(String key, int defaultValue) {
            SharedPreferences settings = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            return settings.getInt(key, defaultValue);
        }

        /**
         * put long preferences
         *
         * @param
         * @param key   The name of the preference to modify
         * @param value The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        public static boolean putLong(String key, long value) {
            SharedPreferences settings = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putLong(key, value);
            return editor.commit();
        }

        /**
         * get long preferences
         *
         * @param
         * @param key The name of the preference to retrieve
         * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
         * name that is not a long
         * @see #(Context, String, long)
         */
        public static long getLong(String key) {
            return getLong(key, -1);
        }

        /**
         * get long preferences
         *
         * @param
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a long
         */
        public static long getLong(String key, long defaultValue) {
            SharedPreferences settings = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            return settings.getLong(key, defaultValue);
        }

        /**
         * put float preferences
         *
         * @param
         * @param key   The name of the preference to modify
         * @param value The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        public static boolean putFloat(String key, float value) {
            SharedPreferences settings = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putFloat(key, value);
            return editor.commit();
        }

        /**
         * get float preferences
         *
         * @param
         * @param key The name of the preference to retrieve
         * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
         * name that is not a float
         * @see #(Context, String, float)
         */
        public static float getFloat(String key) {
            return getFloat(key, -1);
        }

        /**
         * get float preferences
         *
         * @param
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a float
         */
        public static float getFloat(String key, float defaultValue) {
            SharedPreferences settings = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            return settings.getFloat(key, defaultValue);
        }

        /**
         * put boolean preferences
         *
         * @param
         * @param key   The name of the preference to modify
         * @param value The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        public static boolean putBoolean(String key, boolean value) {
            SharedPreferences settings = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        }

        /**
         * get boolean preferences, default is false
         *
         * @param
         * @param key The name of the preference to retrieve
         * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
         * name that is not a boolean
         * @see #(Context, String, boolean)
         */
        public static boolean getBoolean(String key) {
            return getBoolean(key, false);
        }

        /**
         * get boolean preferences
         *
         * @param
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a boolean
         */
        public static boolean getBoolean(String key, boolean defaultValue) {
            SharedPreferences settings = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            return settings.getBoolean(key, defaultValue);
        }
    }

    public static class Screen {
        public static int getHeight() {
            return mContext.getResources().getDisplayMetrics().heightPixels;
        }

        public static int getWidth() {
            return mContext.getResources().getDisplayMetrics().widthPixels;
        }

    }

    public static class Regx {
        /**
         * 验证Email
         *
         * @param email email地址，格式：zhangsan@zuidaima.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
         * @return 验证成功返回true，验证失败返回false
         */
        public static boolean checkEmail(String email) {
            String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
            return Pattern.matches(regex, email);
        }

        /**
         * 验证身份证号码
         *
         * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
         * @return 验证成功返回true，验证失败返回false
         */
        public static boolean checkIdCard(String idCard) {
            String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
            return Pattern.matches(regex, idCard);
        }

        /**
         * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
         *
         * @param mobile 移动、联通、电信运营商的号码段
         *               <p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
         *               、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
         *               <p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
         *               <p>电信的号段：133、153、180（未启用）、189</p>
         * @return 验证成功返回true，验证失败返回false
         */
        public static boolean checkMobilePhone(String mobile) {
            String regex = "(\\+\\d+)?1[3458]\\d{9}$";
            return Pattern.matches(regex, mobile);
        }

        /**
         * 验证固定电话号码
         *
         * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
         *              <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
         *              数字之后是空格分隔的国家（地区）代码。</p>
         *              <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
         *              对不使用地区或城市代码的国家（地区），则省略该组件。</p>
         *              <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
         * @return 验证成功返回true，验证失败返回false
         */
        public static boolean checkPhone(String phone) {
            String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
            return Pattern.matches(regex, phone);
        }

        /**
         * 验证整数（正整数和负整数）
         *
         * @param digit 一位或多位0-9之间的整数
         * @return 验证成功返回true，验证失败返回false
         */
        public static boolean checkDigit(String digit) {
            String regex = "\\-?[1-9]\\d+";
            return Pattern.matches(regex, digit);
        }

        /**
         * 验证整数和浮点数（正负整数和正负浮点数）
         *
         * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
         * @return 验证成功返回true，验证失败返回false
         */
        public static boolean checkDecimals(String decimals) {
            String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
            return Pattern.matches(regex, decimals);
        }

        /**
         * 验证空白字符
         *
         * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
         * @return 验证成功返回true，验证失败返回false
         */
        public static boolean checkBlankSpace(String blankSpace) {
            String regex = "\\s+";
            return Pattern.matches(regex, blankSpace);
        }

        /**
         * 验证中文
         *
         * @param chinese 中文字符
         * @return 验证成功返回true，验证失败返回false
         */
        public static boolean checkChinese(String chinese) {
            String regex = "^[\u4E00-\u9FA5]+$";
            return Pattern.matches(regex, chinese);
        }

        /**
         * 验证日期（年月日）
         *
         * @param birthday 日期，格式：1992-09-03，或1992.09.03
         * @return 验证成功返回true，验证失败返回false
         */
        public static boolean checkBirthday(String birthday) {
            String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
            return Pattern.matches(regex, birthday);
        }

        /**
         * 验证URL地址
         *
         * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
         * @return 验证成功返回true，验证失败返回false
         */
        public static boolean checkURL(String url) {
            String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
            return Pattern.matches(regex, url);
        }

        /**
         * <pre>
         * 获取网址 URL 的一级域名
         * http://www.zuidaima.com/share/1550463379442688.htm ->> zuidaima.com
         * </pre>
         *
         * @param url
         * @return
         */
        public static String getDomain(String url) {
            Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
            // 获取完整的域名
            // Pattern p=Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(url);
            matcher.find();
            return matcher.group();
        }

        /**
         * 匹配中国邮政编码
         *
         * @param postcode 邮政编码
         * @return 验证成功返回true，验证失败返回false
         */
        public static boolean checkPostcode(String postcode) {
            String regex = "[1-9]\\d{5}";
            return Pattern.matches(regex, postcode);
        }

        /**
         * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
         *
         * @param ipAddress IPv4标准地址
         * @return 验证成功返回true，验证失败返回false
         */
        public static boolean checkIpAddress(String ipAddress) {
            String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
            return Pattern.matches(regex, ipAddress);
        }






    /*又一位前辈的*/

        /**
         * 手机号校验 注：1.支持最新170手机号码 2.支持+86校验
         *
         * @param phoneNum 手机号码
         * @return 验证通过返回true
         */
        public static boolean isMobile(String phoneNum) {
            if (phoneNum == null) {
                return false;
            }
            // 如果手机中有+86则会自动替换掉
            return validation("^[1][3,4,5,7,8][0-9]{9}$",
                    phoneNum.replace("+86", ""));
        }


        public static boolean isBankCard(String cardnum) {
            if (cardnum == null) {
                return false;
            }
            // 如果手机中有+86则会自动替换掉
            return validation("^\\\\d{19}$",
                    cardnum);
        }


        /**
         * 用户名校验,默认用户名长度至少3个字符，最大长度为15<br>
         * 可修改正则表达式以实现不同需求
         *
         * @param username 用户名
         * @return
         */
        public static boolean isUserName(String username) {
            /***
             * 正则表达式为：^[a-z0-9_-]{3,15}$ 各部分作用如下： [a-z0-9_-] -----------
             * 匹配列表中的字符，a-z,0–9,下划线，连字符 {3,15}-----------------长度至少3个字符，最大长度为15
             * 如果有不同需求的可以参考以上修改正则表达式
             */
            return validation("^[a-z0-9_-]{3,15}$", username);
        }

        /**
         * 密码校验
         * 要求6-16位数字和英文字母组合
         *
         * @param pwd
         * @return
         */
        public static boolean isPassword(String pwd) {
            /**
             * ^ 匹配一行的开头位置(?![0-9]+$) 预测该位置后面不全是数字
             * (?![a-zA-Z]+$) 预测该位置后面不全是字母
             * [0-9A-Za-z] {9,16} 由9-16位数字或这字母组成
             */
            return validation("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", pwd);
        }

        /**
         * 邮箱校验
         *
         * @param mail 邮箱字符串
         * @return 如果是邮箱则返回true，如果不是则返回false
         */
        public static boolean isEmail(String mail) {
            return validation(
                    "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",
                    mail);
        }


        /**
         * 正则校验
         *
         * @param
         * @param str 需要校验的字符串
         * @return 验证通过返回true
         */
        public static boolean validation(String pattern, String str) {
            return str != null && Pattern.compile(pattern).matcher(str).matches();
        }

        /**
         * 实际替换动作
         *
         * @param origin  origin
         * @param regular 正则
         * @return
         */
        private static String replaceAction(String origin, String regular) {
            return origin.replaceAll(regular, "*");
        }

        /**
         * 身份证号替换，保留前四位和后四位
         * <p>
         * 如果身份证号为空 或者 null ,返回null ；否则，返回替换后的字符串；
         *
         * @param idCard 身份证号
         * @return
         */
        public static String idCardReplaceWithStar(String idCard) {
            if (TextUtils.isEmpty(idCard)) {
                return "";
            } else {
                return replaceAction(idCard, "(?<=\\d{4})\\d(?=\\d{4})");
            }
        }

        public static boolean isIDCard(String idcard) {
            return validation("/(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)/", idcard);
        }

        /**
         * 银行卡替换，保留后四位
         * <p>
         * 如果银行卡号为空 或者 null ,返回null ；否则，返回替换后的字符串；
         *
         * @param bankCard 银行卡号
         * @return
         */
        public static String bankCardReplaceWithStar(String bankCard) {

            if (TextUtils.isEmpty(bankCard)) {
                return "";
            } else {
                return replaceAction(bankCard, "(?<=\\d{0})\\d(?=\\d{4})");
            }
        }

        /**
         * 用指定的字符定界字符串
         * eg; 12345678,每隔3个字符用"-"定界一次，结果123-456-78
         *
         * @param string    带定界字符串
         * @param step      每几个字符分隔一次
         * @param delimiter 定界符
         * @return 用定界符定界后的字符串
         */
        public static String delimitString(String string, int step, String delimiter) {
            if (string == null || string.isEmpty()) {
                return "";
            }
            StringBuilder builder = new StringBuilder();
            int loop = string.length() / step;
            for (int i = 0; i <= loop; i++) {
                int beginIndex = i * step;
                if (beginIndex + step < string.length()) {
                    builder.append(string.substring(beginIndex, beginIndex + step)).append(delimiter);
                } else {
                    builder.append(string.substring(beginIndex));
                }
            }
            return builder.toString();
        }
    }

    public static class Resource {
        public static String getString(int resid) {
            return mContext.getResources().getString(resid);
        }

        public static Drawable getDrawable(int resid) {
            return mContext.getResources().getDrawable(resid);
        }

        public static int getColor(int resid) {
            return ContextCompat.getColor(mContext, resid);
        }

        public static boolean getBoolean(int resid) {
            return mContext.getResources().getBoolean(resid);
        }

        public static float getDimen(int resid) {
            return mContext.getResources().getDimension(resid);
        }

    }

    public static class Time {
        public static final String INDEFINITE_TIME = "yyyy-MM-dd'T'HH:mm:ss+08:00";

        public static final String YYYY_MM_DD = "yyyy-MM-dd";
        public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
        public static final String YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss";
        public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
        public static final String YYYYMMDD = "yyyy/MM/dd";

        public static final int TOP = 0;
        public static final int NEXT = 1;

        public static final String MM_DD = "MM-dd";
        public static final String HH_MM = "HH:mm";
        public static final String YYYY_M_D = "yyyy-M-d";
        public static final String MM_DD_HH_MM = "MM-dd HH:mm";
        public static final String YYYY_MM = "yyyy-MM";

        public static final String YYYY_MM_DD_CH = "yyyy年MM月dd日";
        public static final String YYYY_MM_CH = "yyyy年MM月";
        public static final String FORMAT_WEEK = "yyyy年MM月dd日 EE";
        private static Calendar cal = Calendar.getInstance();
        private static String dateString;

        /**
         * 得到一个月份的上个月，下个月日期
         * @param datestr
         * @param format
         * @param type
         * @return
         */
        public static String getLastOrNextYearMonth(String datestr,String format,int type)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);

            Date date = null;
            try {
                date = dateFormat.parse(datestr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date); // 设置为当前时间
            if(type == TOP)
            {
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) -1); // 设置为上一个月
            }else if(type == NEXT)
            {
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); // 设置为下一个月
            }

            date = calendar.getTime();
            return dateFormat.format(date);
        }


        /**
         * 根据时间格式返回当前时间
         */

        public static String getNowDate(String format) {
            dateTime = new DateTime();
            return dateTime.toString(format);
        }

        /**
         * 将带时区的时间转换为指定格式
         *
         * @param time
         * @param tartgetformat
         * @return
         */
        public static String getDateForFormate(String time, String tartgetformat) {

            DateFormat format = new SimpleDateFormat(INDEFINITE_TIME);
            Date date = null;
            try {
                date = format.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            format = new SimpleDateFormat(tartgetformat);
            if (date == null) {
                dateString = "";
            } else {
                dateString = format.format(date);
            }

            return dateString;
        }


        /**
         * 将时间戳转换为指定格式的日期
         *
         * @param millseconds
         * @param format
         * @return
         */
        public static String getDateForFormate(long millseconds, String format) {
            Date d = new Date(millseconds);
            return new SimpleDateFormat(format).format(d);
        }

        public static String getNowDateFromType(String format) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }

        /**
         * 获取今天的日期
         */
        public static String getNowYearMonthDay() {
            DateTime dateTime = new DateTime();
            DateTimeFormatter formatter = ISODateTimeFormat.yearMonthDay();
            return dateTime.toString(formatter);
        }

        //时间错
        public static Long date2TimeStamp(String date_str,String format){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(date_str).getTime()/1000;
            } catch (Exception e) {
                e.printStackTrace();
            }
           return Long.valueOf("0");
        }
        //字符串转时间戳
        public static long getTimeRubbing(String timeString) {


            return date2TimeStamp(timeString,YYYY_MM_DD);

        }

        public static String timeStamp2Date(String seconds,String format) {
            if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
                return "";
            }
            if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(new Date(Long.valueOf(seconds+"000")));
        }

        public static String getRubbingToTime( String timeString) {
            if(Long.valueOf(timeString)<=Long.parseLong("-62135596800"))
            {
                return "";
            }


            return timeStamp2Date(timeString,YYYY_MM_DD);
        }

        /**
         * 得到当前的月
         *
         * @return
         */
        public static int getNowMonth() {
            return cal.get(Calendar.MONTH) + 1;
        }

        /**
         * 得到当前的天
         *
         * @return
         */
        public static int getNowDay() {
            return cal.get(Calendar.DATE);
        }

        /**
         * 得到当前的年
         *
         * @return
         */
        public static int getNowYear() {
            return cal.get(Calendar.YEAR);
        }

        private static SimpleDateFormat weekformat;

        /**
         * 判断当前日期是星期几
         *
         * @param pTime 修要判断的时间
         * @return dayForWeek 判断结果
         * @Exception 发生异常
         */
        public static int getDayForWeek(String pTime) {
            int dayForWeek = 0;
            try {
                weekformat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.setTime(weekformat.parse(pTime));
                if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                    dayForWeek = 7;
                } else {
                    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                }
            } catch (Exception e) {

            }

            return dayForWeek;
        }

        /**
         * 把数字的周1 周2 转换为 周一 周二
         *
         * @param wee
         * @return
         */
        public static String getWeekStringFromIntWeek(int wee) {
            switch (wee) {
                case 1:
                    return "一";
                case 2:
                    return "二";

                case 3:
                    return "三";

                case 4:
                    return "四";

                case 5:
                    return "五";

                case 6:
                    return "六";

                case 7:
                    return "日";

            }
            return "";
        }

        /**
         * 返回小时跟分钟
         *
         * @param time
         * @return
         */
        public static String getHourMin(String time) {
            DateTime dateTime = parseDateTime(time);
            return dateTime.toString(HH_MM);
        }

        /**
         * 返回今天凌晨的时间
         *
         * @return
         */
        public static String getDayStartTime(String time) {
            DateTime dateTime = parseYearMonthDay(time);
            DateTime startDate = new DateTime()
                    .withDate(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth())
                    .withTime(0, 0, 0, 0);
            return startDate.toString(Config.ISO8601_FORMATTER);
        }

        /**
         * 返回今天半夜的时间
         *
         * @return
         */
        public static String getDayEndTime(String time) {
            DateTime dateTime = parseYearMonthDay(time);
            DateTime endTime = new DateTime()
                    .withDate(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth())
                    .withTime(23, 59, 59, 999);
            return endTime.toString(Config.ISO8601_FORMATTER);

        }


        public static DateTime parseYearMonthDay(String time) {

            DateTimeFormatter formatter = ISODateTimeFormat.yearMonthDay();
            DateTime dateTime = formatter.parseDateTime(time);
            return dateTime;
        }

        public static String parseDateTime(String dateTimeString, String format) {
            try {
                DateTimeFormatter formatter = ISODateTimeFormat.dateTimeNoMillis();
                DateTime dateTime = formatter.parseDateTime(dateTimeString);
                return dateTime.toString(format, Locale.getDefault());
            } catch (Exception e) {
                e.printStackTrace();
                return dateTimeString;
            }
        }

        /**
         * 得到某年某月的第一天
         */
        public static String getFirstDayOfMonth(int year, int month) {
            DateTime dateTime = new DateTime().withYear(year).withMonthOfYear(month);
            DateTime firstDay = dateTime.withDayOfMonth(1).withTimeAtStartOfDay();
            return firstDay.toString(Config.ISO8601_FORMATTER);
        }

        /**
         * 得到某年某月的最后一天
         */
        public static String getLastDayOfMonth(int year, int month) {
            DateTime dateTime = new DateTime().withYear(year).withMonthOfYear(month);
            DateTime firstDay = dateTime.withDayOfMonth(1).withTimeAtStartOfDay();
            DateTime endDay = firstDay.plusMonths(1).minusMillis(1);
            return endDay.toString(Config.ISO8601_FORMATTER);
        }

        public static DateTimeFormatter defaultDateTimeParser() {
            return ISODateTimeFormat.dateTimeNoMillis();
        }

        public static DateTime parseDateTime(String dateTime) {
            return defaultDateTimeParser().parseDateTime(dateTime);
        }

        /**
         * 友好的方式显示时间,当天的时间只显示时间部分HH:mm,今年内的非当天的只显示日期MM-dd,不是今年内的显示yyyy-MM-dd
         *
         * @param dateTimeString date time string
         * @return 友好显示的时间
         */
        public static String friendlyTime(Context context, String dateTimeString) {
            final DateTime dateTime = parseDateTime(dateTimeString);
            final DateTime now = DateTime.now();
            String friendlyDateTime;
            final LocalDate localDate = dateTime.toLocalDate();
            final LocalDate nowLocalDate = now.toLocalDate();
            if (localDate.isEqual(nowLocalDate)) {
                friendlyDateTime = dateTime.toString(HH_MM);
            } else if (nowLocalDate.minusDays(1).isEqual(localDate)) {
                friendlyDateTime = "昨天";
            } else if (dateTime.getYear() == now.getYear()) {
                friendlyDateTime = dateTime.toString(MM_DD);
            } else {
                friendlyDateTime = dateTime.toString(YYYY_MM_DD);
            }
            return friendlyDateTime;
        }

        /**
         * 获取两个时间的间隔
         *
         * @param start 开始时间
         * @param end   结束时间
         * @return 两个时间点间隔
         */
        public static String friendlyTimeBetween(DateTime start, DateTime end) {
            int days = Days.daysBetween(start, end).getDays();
            int hours = Hours.hoursBetween(start, end).getHours();
            int minutes = Minutes.minutesBetween(start, end).getMinutes();
            if (days > 0) {
                return days + "天";
            } else if (hours > 0) {
                return hours + "小时";
            } else {
                return minutes + "分钟";
            }
        }

        /**
         * 得到一段时间搓中的月数
         *
         * @param time
         * @return
         */
        public static int getMonthForTime(String time) {
            if (!Text.isEmpty(time)) {
                SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
                Date date = null;
                try {
                    date = sdf.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar ca = Calendar.getInstance();
                ca.setTime(date);

                return ca.get(Calendar.MONTH) + 1;
            }
            return -1;
        }

        /**
         * 得到一段时间搓中的天数
         *
         * @param time
         * @return
         */
        public static int getDayForTime(String time) {
            if (!Text.isEmpty(time)) {
                SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
                Date date = null;
                try {
                    date = sdf.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar ca = Calendar.getInstance();
                ca.setTime(date);

                return ca.get(Calendar.DATE);
            }
            return -1;
        }


        /**
         * 截取出日期中的年数，请注意，你的日期格式务必包含YYYY
         *
         * @param time
         * @return
         */
        public static int getYear_substring(String time) {
            time = time.substring(0, 4);
            return Integer.valueOf(time);
        }

        /**
         * 截取出日期中的天数，请注意，你的日期格式务必包含YYYY
         *
         * @param time
         * @return
         */
        public static int getMonth_substring(String time) {
            time = time.substring(5, 7);
            return Integer.valueOf(time);
        }

        /**
         * 对两个日期进行比较
         *
         * @param DATE1
         * @param DATE2
         * @return -1 代表 Date1 小于 Date2    1代表 Date1 大于 Date2   0代表相等
         */
        public static int compare_date(String DATE1, String DATE2) {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date dt1 = df.parse(DATE1);
                Date dt2 = df.parse(DATE2);


                //  return dt1.compareTo(dt2) > 0 ? -1 : 1;

                if (dt1.getTime() > dt2.getTime()) {
                    System.out.println("dt1 在dt2前");
                    return 1;
                } else if (dt1.getTime() < dt2.getTime()) {
                    System.out.println("dt1在dt2后");
                    return -1;
                } else if (dt1.getTime() == dt2.getTime()) {
                    return 0;
                } else {
                    return -1;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return -1;
        }

        public static int compare_date(String DATE1, String DATE2, String format) {

            DateFormat df = new SimpleDateFormat(format);
            try {
                Date dt1 = df.parse(DATE1);
                Date dt2 = df.parse(DATE2);

                //                return dt1.compareTo(dt2) > 0 ? -1 : 1;
                if (dt1.getTime() > dt2.getTime()) {
                    //  System.out.println("dt1 在dt2前");
                    return 1;
                } else if (dt1.getTime() < dt2.getTime()) {
                    // System.out.println("dt1在dt2后");
                    return -1;
                } else if (dt1.getTime() == dt2.getTime()) {
                    return 0;
                } else {
                    return -1;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return -1;
        }

        public static String getNorMalDateTime(String time) {
            time = time.replace("T", "");
            if (time.contains("+")) {
                time = time.substring(0, time.lastIndexOf("+"));
            }
            return time;
        }
    }

    public static class ThreadPool {
        public static final int FixedThread = 0;
        public static final int CachedThread = 1;
        public static final int SingleThread = 2;

        @IntDef({FixedThread, CachedThread, SingleThread})
        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {
        }

        private ExecutorService exec;
        private ScheduledExecutorService scheduleExec;

        private ThreadPool() {
            throw new UnsupportedOperationException("u can't instantiate me...");
        }

        /**
         * ThreadPoolUtils构造函数
         *
         * @param type         线程池类型
         * @param corePoolSize 只对Fixed和Scheduled线程池起效
         */
        public ThreadPool(@Type int type, int corePoolSize) {
            // 构造有定时功能的线程池
            // ThreadPoolExecutor(corePoolSize, Integer.MAX_VALUE, 10L, TimeUnit.MILLISECONDS, new BlockingQueue<Runnable>)
            scheduleExec = Executors.newScheduledThreadPool(corePoolSize);
            switch (type) {
                case FixedThread:
                    // 构造一个固定线程数目的线程池
                    // ThreadPoolExecutor(corePoolSize, corePoolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
                    exec = Executors.newFixedThreadPool(corePoolSize);
                    break;
                case SingleThread:
                    // 构造一个只支持一个线程的线程池,相当于newFixedThreadPool(1)
                    // ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>())
                    exec = Executors.newSingleThreadExecutor();
                    break;
                case CachedThread:
                    // 构造一个缓冲功能的线程池
                    // ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
                    exec = Executors.newCachedThreadPool();
                    break;
            }
        }

        /**
         * 在未来某个时间执行给定的命令
         * <p>该命令可能在新的线程、已入池的线程或者正调用的线程中执行，这由 Executor 实现决定。</p>
         *
         * @param command 命令
         */
        public void execute(Runnable command) {
            exec.execute(command);
        }

        /**
         * 在未来某个时间执行给定的命令链表
         * <p>该命令可能在新的线程、已入池的线程或者正调用的线程中执行，这由 Executor 实现决定。</p>
         *
         * @param commands 命令链表
         */
        public void execute(List<Runnable> commands) {
            for (Runnable command : commands) {
                exec.execute(command);
            }
        }

        /**
         * 待以前提交的任务执行完毕后关闭线程池
         * <p>启动一次顺序关闭，执行以前提交的任务，但不接受新任务。
         * 如果已经关闭，则调用没有作用。</p>
         */
        public void shutDown() {
            exec.shutdown();
        }

        /**
         * 试图停止所有正在执行的活动任务
         * <p>试图停止所有正在执行的活动任务，暂停处理正在等待的任务，并返回等待执行的任务列表。</p>
         * <p>无法保证能够停止正在处理的活动执行任务，但是会尽力尝试。</p>
         *
         * @return 等待执行的任务的列表
         */
        public List<Runnable> shutDownNow() {
            return exec.shutdownNow();
        }

        /**
         * 判断线程池是否已关闭
         *
         * @return {@code true}: 是<br>{@code false}: 否
         */
        public boolean isShutDown() {
            return exec.isShutdown();
        }

        /**
         * 关闭线程池后判断所有任务是否都已完成
         * <p>注意，除非首先调用 shutdown 或 shutdownNow，否则 isTerminated 永不为 true。</p>
         *
         * @return {@code true}: 是<br>{@code false}: 否
         */
        public boolean isTerminated() {
            return exec.isTerminated();
        }


        /**
         * 请求关闭、发生超时或者当前线程中断
         * <p>无论哪一个首先发生之后，都将导致阻塞，直到所有任务完成执行。</p>
         *
         * @param timeout 最长等待时间
         * @param unit    时间单位
         * @return {@code true}: 请求成功<br>{@code false}: 请求超时
         * @throws InterruptedException 终端异常
         */
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return exec.awaitTermination(timeout, unit);
        }

        /**
         * 提交一个Callable任务用于执行
         * <p>如果想立即阻塞任务的等待，则可以使用{@code result = exec.submit(aCallable).get();}形式的构造。</p>
         *
         * @param task 任务
         * @param <T>  泛型
         * @return 表示任务等待完成的Future, 该Future的{@code get}方法在成功完成时将会返回该任务的结果。
         */
        public <T> Future<T> submit(Callable<T> task) {
            return exec.submit(task);
        }

        /**
         * 提交一个Runnable任务用于执行
         *
         * @param task   任务
         * @param result 返回的结果
         * @param <T>    泛型
         * @return 表示任务等待完成的Future, 该Future的{@code get}方法在成功完成时将会返回该任务的结果。
         */
        public <T> Future<T> submit(Runnable task, T result) {
            return exec.submit(task, result);
        }

        /**
         * 提交一个Runnable任务用于执行
         *
         * @param task 任务
         * @return 表示任务等待完成的Future, 该Future的{@code get}方法在成功完成时将会返回null结果。
         */
        public Future<?> submit(Runnable task) {
            return exec.submit(task);
        }

        /**
         * 执行给定的任务
         * <p>当所有任务完成时，返回保持任务状态和结果的Future列表。
         * 返回列表的所有元素的{@link Future#isDone}为{@code true}。
         * 注意，可以正常地或通过抛出异常来终止已完成任务。
         * 如果正在进行此操作时修改了给定的 collection，则此方法的结果是不确定的。</p>
         *
         * @param tasks 任务集合
         * @param <T>   泛型
         * @return 表示任务的 Future 列表，列表顺序与给定任务列表的迭代器所生成的顺序相同，每个任务都已完成。
         * @throws InterruptedException 如果等待时发生中断，在这种情况下取消尚未完成的任务。
         */
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return exec.invokeAll(tasks);
        }

        /**
         * 执行给定的任务
         * <p>当所有任务完成或超时期满时(无论哪个首先发生)，返回保持任务状态和结果的Future列表。
         * 返回列表的所有元素的{@link Future#isDone}为{@code true}。
         * 一旦返回后，即取消尚未完成的任务。
         * 注意，可以正常地或通过抛出异常来终止已完成任务。
         * 如果此操作正在进行时修改了给定的 collection，则此方法的结果是不确定的。</p>
         *
         * @param tasks   任务集合
         * @param timeout 最长等待时间
         * @param unit    时间单位
         * @param <T>     泛型
         * @return 表示任务的 Future 列表，列表顺序与给定任务列表的迭代器所生成的顺序相同。如果操作未超时，则已完成所有任务。如果确实超时了，则某些任务尚未完成。
         * @throws InterruptedException 如果等待时发生中断，在这种情况下取消尚未完成的任务
         */
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws
                InterruptedException {
            return exec.invokeAll(tasks, timeout, unit);
        }

        /**
         * 执行给定的任务
         * <p>如果某个任务已成功完成（也就是未抛出异常），则返回其结果。
         * 一旦正常或异常返回后，则取消尚未完成的任务。
         * 如果此操作正在进行时修改了给定的collection，则此方法的结果是不确定的。</p>
         *
         * @param tasks 任务集合
         * @param <T>   泛型
         * @return 某个任务返回的结果
         * @throws InterruptedException 如果等待时发生中断
         * @throws ExecutionException   如果没有任务成功完成
         */
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            return exec.invokeAny(tasks);
        }

        /**
         * 执行给定的任务
         * <p>如果在给定的超时期满前某个任务已成功完成（也就是未抛出异常），则返回其结果。
         * 一旦正常或异常返回后，则取消尚未完成的任务。
         * 如果此操作正在进行时修改了给定的collection，则此方法的结果是不确定的。</p>
         *
         * @param tasks   任务集合
         * @param timeout 最长等待时间
         * @param unit    时间单位
         * @param <T>     泛型
         * @return 某个任务返回的结果
         * @throws InterruptedException 如果等待时发生中断
         * @throws ExecutionException   如果没有任务成功完成
         * @throws TimeoutException     如果在所有任务成功完成之前给定的超时期满
         */
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws
                InterruptedException, ExecutionException, TimeoutException {
            return exec.invokeAny(tasks, timeout, unit);
        }

        /**
         * 延迟执行Runnable命令
         *
         * @param command 命令
         * @param delay   延迟时间
         * @param unit    单位
         * @return 表示挂起任务完成的ScheduledFuture，并且其{@code get()}方法在完成后将返回{@code null}
         */
        public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
            return scheduleExec.schedule(command, delay, unit);
        }

        /**
         * 延迟执行Callable命令
         *
         * @param callable 命令
         * @param delay    延迟时间
         * @param unit     时间单位
         * @param <V>      泛型
         * @return 可用于提取结果或取消的ScheduledFuture
         */
        public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
            return scheduleExec.schedule(callable, delay, unit);
        }

        /**
         * 延迟并循环执行命令
         *
         * @param command      命令
         * @param initialDelay 首次执行的延迟时间
         * @param period       连续执行之间的周期
         * @param unit         时间单位
         * @return 表示挂起任务完成的ScheduledFuture，并且其{@code get()}方法在取消后将抛出异常
         */
        public ScheduledFuture<?> scheduleWithFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            return scheduleExec.scheduleAtFixedRate(command, initialDelay, period, unit);
        }

        /**
         * 延迟并以固定休息时间循环执行命令
         *
         * @param command      命令
         * @param initialDelay 首次执行的延迟时间
         * @param delay        每一次执行终止和下一次执行开始之间的延迟
         * @param unit         时间单位
         * @return 表示挂起任务完成的ScheduledFuture，并且其{@code get()}方法在取消后将抛出异常
         */
        public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
            return scheduleExec.scheduleWithFixedDelay(command, initialDelay, delay, unit);
        }
    }

    public static class Decode {

        public static void gcBitmap(Bitmap bmp) {
            if (bmp != null && !bmp.isRecycled()) {
                bmp.recycle(); // 回收图片所占的内存
                bmp = null;
                System.gc(); // 提醒系统及时回收
            }
        }

        /**
         * bitmap转为base64
         *
         * @param bitmap
         * @return
         */
        public static String bitmapToBase64(Bitmap bitmap) {

            String result = null;
            ByteArrayOutputStream baos = null;
            try {
                if (bitmap != null) {
                    baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                    baos.flush();
                    baos.close();

                    byte[] bitmapBytes = baos.toByteArray();
                    result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (baos != null) {
                        baos.flush();
                        baos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        /**
         * bitmap转为base64
         *
         * @param bitmap
         * @return
         */
        public static String bitmapToBase64_NO_WRAP(Bitmap bitmap) {

            String result = null;
            ByteArrayOutputStream baos = null;
            try {
                if (bitmap != null) {
                    baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                    baos.flush();
                    baos.close();

                    byte[] bitmapBytes = baos.toByteArray();
                    result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (baos != null) {
                        baos.flush();
                        baos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        /**
         * base64转为bitmap
         *
         * @param base64Data
         * @return
         */
        public static Bitmap base64ToBitmap(String base64Data) {
            Bitmap bitmap = null;
            try {
                byte[] bitmapArray = Base64.decode(base64Data, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                return bitmap;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static class FragmentUtil {

        public static boolean removeFragmentAndAddWhenFragmentAlreadyAdded(FragmentManager fragmentManager, Fragment fragment) {
            if (null != fragmentManager && null != fragment) {
                if (fragment.isAdded()) {
                    fragmentManager.beginTransaction().remove(fragment).commit();
                    return true;
                }
            }
            return false;
        }
    }

    public static class CollectionsUtil {

        private static final Collection NULL_COLLECTION = new NullCollection();

        public static final <T> Collection<T> nullCollection() {
            return (List<T>) NULL_COLLECTION;
        }

        public static class NullCollection extends AbstractList<Object>
                implements RandomAccess, Serializable {

            private static final long serialVersionUID = 5206887786441397812L;

            @Override
            public Object get(int index) {
                return null;
            }

            @Override
            public int size() {
                return 1;
            }

            public boolean contains(Object obj) {
                return null == obj;
            }

            private Object readResolve() {
                return null;
            }
        }


        /**
         * 将对象装换为map
         *
         * @param
         * @param
         * @return
         */
        public static HashMap<String, Object> objToHashMap(Object obj) {
            try {
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                Class clazz = obj.getClass();
                List<Class> clazzs = new ArrayList<Class>();

                do {
                    clazzs.add(clazz);
                    clazz = clazz.getSuperclass();
                } while (!clazz.equals(Object.class));

                for (Class iClazz : clazzs) {
                    Field[] fields = iClazz.getDeclaredFields();
                    for (Field field : fields) {
                        Object objVal = null;
                        field.setAccessible(true);
                        objVal = field.get(obj);
                        hashMap.put(field.getName(), objVal);
                    }
                }
                return hashMap;
            } catch (Exception e) {

            }
            return null;
        }

        public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
            if (map == null)
                return null;

            Object obj = beanClass.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }

                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }

            return obj;
        }


    }

    public static class IntentUtil {
        public static final int INTENT_REQUEST_OPEN_PICK = 0x1;//相册
        public static final int INTENT_REQUEST_OPEN_CAMERA = 0x2;//相机

        /**
         * 得到相册的意图
         *
         * @return
         */
        public static Intent getSystem_PickIntent() {
            return new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }

        public static Intent getSystem_CallPhoneIntent(String phoneNumber) {
            return new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        }

        /**
         * 得到相机意图
         *
         * @param
         * @return
         */
        public static Intent getSystem_CameraIntent() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            return intent;
        }

        /**
         * 得到相机意图
         *
         * @param saveFilePath
         * @return
         */
        public static Intent getSystem_CameraIntent(String saveFilePath) {

            return getSystem_CameraIntent(new File(saveFilePath));
        }

        /**
         * 得到相机意图
         *
         * @param saveFilePath
         * @return
         */
        public static Intent getSystem_CameraIntent(File saveFilePath) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(mContext, saveFilePath));


            return intent;
        }


        /**
         * 得到设置的Intent
         *
         * @return
         */
        public static Intent getSystem_SettingIntent() {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + mContext.getPackageName()));
            return intent;
        }


        private static Uri getUriForFile(Context context, File file) {
            if (context == null || file == null) {
                throw new NullPointerException();
            }
            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(context.getApplicationContext(), "workai.empassist.com.workaipro.fileprovider", file);
            } else {
                uri = Uri.fromFile(file);
            }
            return uri;
        }
    }

    public static class BitmapUtils {
        public static Bitmap scaleImage(Bitmap bm, int newWidth, int newHeight) {
            if (bm == null) {
                return null;
            }
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
            if (bm != null & !bm.isRecycled()) {
                bm.recycle();//销毁原图片
                bm = null;
            }
            return newbm;
        }

        public static Bitmap decode(byte[] bytes) {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

        public static Bitmap createTransparentBitmapFromBitmap(Bitmap bitmap,
                                                               int replaceThisColor) {
            if (bitmap != null) {
                int picw = bitmap.getWidth();
                int pich = bitmap.getHeight();
                int[] pix = new int[picw * pich];
                bitmap.getPixels(pix, 0, picw, 0, 0, picw, pich);

                int sr = (replaceThisColor >> 16) & 0xff;
                int sg = (replaceThisColor >> 8) & 0xff;
                int sb = replaceThisColor & 0xff;

                for (int y = 0; y < pich; y++) {
                    for (int x = 0; x < picw; x++) {
                        int index = y * picw + x;
                /*  int r = (pix[index] >> 16) & 0xff;
                  int g = (pix[index] >> 8) & 0xff;
	              int b = pix[index] & 0xff;*/

                        if (pix[index] == replaceThisColor) {

                            //	                if(x<topLeftHole.x) topLeftHole.x = x;
                            //	                if(y<topLeftHole.y) topLeftHole.y = y;
                            //	                if(x>bottomRightHole.x) bottomRightHole.x = x;
                            //	                if(y>bottomRightHole.y)bottomRightHole.y = y;

                            pix[index] = Color.TRANSPARENT;
                        } else {
                            //break;
                        }
                    }
                }

                Bitmap bm = Bitmap.createBitmap(pix, picw, pich,
                        Bitmap.Config.ARGB_8888);

                return bm;
            }
            return null;
        }


        /**
         * 将bitmap中的某种颜色值替换成新的颜色
         * @param
         * @param oldColor
         * @param newColor
         * @return
         */
        public static Bitmap replaceBitmapColor(Bitmap oldBitmap,int oldColor,int newColor)
        {
            //相关说明可参考 http://xys289187120.blog.51cto.com/3361352/657590/
            Bitmap mBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);
            //循环获得bitmap所有像素点
            int mBitmapWidth = mBitmap.getWidth();
            int mBitmapHeight = mBitmap.getHeight();
            int mArrayColorLengh = mBitmapWidth * mBitmapHeight;
            int[] mArrayColor = new int[mArrayColorLengh];
            int count = 0;
            for (int i = 0; i < mBitmapHeight; i++) {
                for (int j = 0; j < mBitmapWidth; j++) {
                    //获得Bitmap 图片中每一个点的color颜色值
                    //将需要填充的颜色值如果不是
                    //在这说明一下 如果color 是全透明 或者全黑 返回值为 0
                    //getPixel()不带透明通道 getPixel32()才带透明部分 所以全透明是0x00000000
                    //而不透明黑色是0xFF000000 如果不计算透明部分就都是0了
                    int color = mBitmap.getPixel(j, i);
                    //将颜色值存在一个数组中 方便后面修改
                    if (color == oldColor) {
                        mBitmap.setPixel(j, i, newColor);  //将白色替换成透明色
                    }

                }
            }
            return mBitmap;
        }


        /**
         * 图片转圆角
         *
         * @return 转圆角的bitmap
         */
        public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            return output;
        }

    }


    public static class MyBankUtils {
        /**
         * 得到银行卡后四位
         *
         * @param cardNO
         * @return
         */
        public static String getCardNoFourNum(String cardNO) {

            cardNO = cardNO.trim().replace(" ", "");
            cardNO = cardNO.substring(cardNO.length() - 4, cardNO.length());
            return cardNO;
        }

        /**
         * 以*代替银行卡前12位
         *
         * @param cardNO
         * @return
         */
        public static String getCardNoOnlyShowFourNum(String cardNO) {
            cardNO = cardNO.trim().replace(" ", "");
            String founum = "";
            for (int i = 0; i < cardNO.length() - 4; i++) {
                founum += "*";
            }

            founum += cardNO.substring(cardNO.length() - 4, cardNO.length());
            return founum;
        }

        public static String bankinfo_logo_json = "{\"上海农村商业银行\":\"bank_shrcb\",\n" +
                "\"上海浦东发展银行\":\"bank_spdb\",\n" +
                "\"上海银行\":\"bank_shbank\",\n" +
                "\"上饶银行\":\"bank_srbank\",\n" +
                "\"东莞银行\":\"bank_bod\",\n" +
                "\"中信银行\":\"bank_citic\",\n" +
                "\"中国光大银行\":\"bank_ceb\",\n" +
                "\"中国农业银行\":\"bank_abc\",\n" +
                "\"中国工商银行\":\"bank_icbc\",\n" +
                "\"中国建设银行\":\"bank_ccb\",\n" +
                "\"中国民生银行\":\"bank_cmbc\",\n" +
                "\"中国邮政储蓄银行\":\"bank_psbc\",\n" +
                "\"中国银行\":\"bank_boc\",\n" +
                "\"乌鲁木齐市商业银行\":\"bank_urmqccb\",\n" +
                "\"九江银行\":\"bank_jjbank\",\n" +
                "\"交通银行\":\"bank_comm\",\n" +
                "\"兴业银行\":\"bank_cib\",\n" +
                "\"内蒙古银行\":\"bank_h3cb\",\n" +
                "\"包商银行\":\"bank_bsb\",\n" +
                "\"北京农村商业银行\":\"bank_bjrcb\",\n" +
                "\"北京银行\":\"bank_bjbank\",\n" +
                "\"华夏银行\":\"bank_hxbank\",\n" +
                "\"南京银行\":\"bank_njcb\",\n" +
                "\"南昌银行\":\"bank_ncb\",\n" +
                "\"台州银行\":\"bank_tzcb\",\n" +
                "\"哈尔滨银行\":\"bank_hrbank\",\n" +
                "\"嘉兴银行\":\"bank_jxbank\",\n" +
                "\"大连银行\":\"bank_dlb\",\n" +
                "\"天津银行\":\"bank_tccb\",\n" +
                "\"威海市商业银行\":\"bank_whccb\",\n" +
                "\"宁夏银行\":\"bank_nxbank\",\n" +
                "\"宁波银行\":\"bank_nbbank\",\n" +
                "\"尧都农商行\":\"bank_ydrcb\",\n" +
                "\"平安银行\":\"bank_spabank\",\n" +
                "\"广东南粤银行\":\"bank_nybank\",\n" +
                "\"广东发展银行\":\"bank_gdb\",\n" +
                "\"广州银行\":\"bank_gcb\",\n" +
                "\"徽商银行\":\"bank_hsbank\",\n" +
                "\"恒丰银行\":\"bank_egbank\",\n" +
                "\"成都农商银行\":\"bank_cdrcb\",\n" +
                "\"成都银行\":\"bank_cdcb\",\n" +
                "\"招商银行\":\"bank_cmb\",\n" +
                "\"晋商银行\":\"bank_jsb\",\n" +
                "\"杭州银行\":\"bank_hzcb\",\n" +
                "\"汉口银行\":\"bank_hkb\",\n" +
                "\"江苏江阴农村商业银行\":\"bank_jrcb\",\n" +
                "\"江苏省农村信用联合社\":\"bank_jsrcu\",\n" +
                "\"江苏银行\":\"bank_jsbank\",\n" +
                "\"江西银行\":\"bank_nccb\",\n" +
                "\"河北银行\":\"bank_bhb\",\n" +
                "\"浙商银行\":\"bank_czbank\",\n" +
                "\"渣打银行\":\"bank_scb\",\n" +
                "\"渤海银行\":\"bank_bohaib\",\n" +
                "\"温州银行\":\"bank_wzcb\",\n" +
                "\"盛京银行\":\"bank_sjbank\",\n" +
                "\"绍兴银行\":\"bank_sxcb\",\n" +
                "\"网商银行\":\"bank_antbank\",\n" +
                "\"苏州银行\":\"bank_bosz\",\n" +
                "\"营口银行\":\"bank_boyk\",\n" +
                "\"赣州银行\":\"bank_gzb\",\n" +
                "\"邢台银行\":\"bank_xtb\",\n" +
                "\"重庆农村商业银行\":\"bank_cqrc\",\n" +
                "\"重庆银行\":\"bank_cqbank\",\n" +
                "\"青海银行\":\"bank_boqh\",\n" +
                "\"齐鲁银行\":\"bank_qlbank\"}";
        public static String bankinfo_color_json = "{\"南京银行\":[\"#e75b65\",\"#e95882\"],\"华夏银行\":[\"#e75b65\",\"#e95882\"],\"交通银行\":[\"#4e81bd\",\"#3d5faa\"],\"杭州银行\":[\"#65b1f1\",\"#2b7dc9\"],\"重庆银行\":[\"#37c39c\",\"#37bdc0\"],\"中国银行\":[\"#e75b65\",\"#e95882\"],\"浦发银行\":[\"#4e81bd\",\"#3d5faa\"],\"上海银行\":[\"#4e81bd\",\"#3d5faa\"],\"邮政银行\":[\"#279759\",\"#258829\"],\"邮储银行\":[\"#279759\",\"#258829\"],\"工商银行\":[\"#e75b65\",\"#e95882\"],\"平安银行\":[\"#f68c2d\",\"#f5833e\"],\"兴业银行\":[\"#4e81bd\",\"#3d5faa\"],\"招商银行\":[\"#e75b65\",\"#e95882\"],\"中信银行\":[\"#4e81bd\",\"#3d5faa\"],\"江苏银行\":[\"#4e81bd\",\"#3d5faa\"],\"农业银行\":[\"#37c39c\",\"#37bdc0\"],\"广发银行\":[\"#e75b65\",\"#e95882\"],\"建设银行\":[\"#4e81bd\",\"#3d5faa\"],\"宁波银行\":[\"#f68c2d\",\"#f5833e\"],\"包商银行\":[\"#f68c2d\",\"#f5833e\"],\"光大银行\":[\"#7d4ab7\",\"#6343bf\"],\"北京银行\":[\"#e75b65\",\"#e95882\"],\"渤海银行\":[\"#4e81bd\",\"#3d5faa\"],\"恒生银行\":[\"#e75b65\",\"#e95882\"],\"华润银行\":[\"#e75b65\",\"#e95882\"],\"民生银行\":[\"#37c39c\",\"#37bdc0\"]}";
        public static JSONObject bankinfo_color_jsonObj;
        public static JSONObject bankinfo_info_jsonObj;
        private static GradientDrawable drawable_color;

        public static Drawable getBankColor(String cardName) {
            int[] color;
            try {
                if (null == bankinfo_color_jsonObj) {
                    bankinfo_color_jsonObj = new JSONObject(bankinfo_color_json);
                }
                JSONArray array = bankinfo_color_jsonObj.getJSONArray(cardName);
                if (array == null || array.length() != 2) {
                    color = new int[]{Color.parseColor("#3796e6"), Color.parseColor("3c75d4")};
                } else {
                    color = new int[]{Color.parseColor((String) array.get(0)), Color.parseColor((String) array.get(1))};
                }
            } catch (Exception e) {
                color = new int[]{Color.parseColor("#6699FF"), Color.parseColor("#3366CC")};
            }
            drawable_color = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, color);
            drawable_color.setStroke(1, Color.parseColor("#000000"));
            drawable_color.setCornerRadius(5);
            return drawable_color;
        }

        public static Drawable getBankLogo(String cardName) {
            try {
                if (null == bankinfo_info_jsonObj) {
                    bankinfo_info_jsonObj = new JSONObject(bankinfo_logo_json);
                }
                String resname = bankinfo_info_jsonObj.getString(cardName);
                int picid = mContext.getResources().getIdentifier(resname, "mipmap", mContext.getPackageName());
                return mContext.getResources().getDrawable(picid);
            } catch (Exception e) {
                String resname = "bank_default";
                int picid = mContext.getResources().getIdentifier(resname, "mipmap", mContext.getPackageName());
                return mContext.getResources().getDrawable(picid);
            }
        }

    }

    /**
     * 软键盘
     */
    public static class SoftKeyBoard {
        public static void showSoftInput(Context context, View view) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            //imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }

        public static void hideSoftInput(Context context, View view) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        }

        public static boolean isShowSoftInput(Context context) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            //获取状态信息
            return imm.isActive();//true 打开
        }

        public static void disabledEdiTextShowInputMethod(EditText et)
        {
            et.setInputType(1); // disable soft input
            et.setFocusableInTouchMode(false);
            et.clearFocus();
        }

        public static void enabledEdiTextShowInputMethod(EditText et,boolean isdisable)
        {
            if(isdisable)
            {
                et.setInputType(1); // disable soft input
                et.setFocusableInTouchMode(false);
                et.clearFocus();
            }else
            {
                et.setInputType(InputType.TYPE_CLASS_TEXT); // disable soft input
                et.setFocusableInTouchMode(true);
            }

        }
    }


    public static class NumberUtils {
        /**
         * 格式化为指定位小数的数字,返回未使用科学计数法表示的具有指定位数的字符串。
         * 该方法舍入模式：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。
         * <pre>
         *  "3.1415926", 1          --> 3.1
         *  "3.1415926", 3          --> 3.142
         *  "3.1415926", 4          --> 3.1416
         *  "3.1415926", 6          --> 3.141593
         *  "1234567891234567.1415926", 3   --> 1234567891234567.142
         * </pre>
         *
         * @param
         * @param precision     小数精确度总位数,如2表示两位小数
         * @return 返回数字格式化后的字符串表示形式(注意返回的字符串未使用科学计数法)
         */
        public static String keepPrecision(String number, int precision) {
            BigDecimal bg = new BigDecimal(number);
            return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).toPlainString();
        }

        /**
         * 格式化为指定位小数的数字,返回未使用科学计数法表示的具有指定位数的字符串。
         * 该方法非四舍五入
         *
         * @param
         * @param precision 小数精确度总位数,如2表示两位小数
         * @return 返回数字格式化后的字符串表示形式(注意返回的字符串未使用科学计数法)
         */
        public static String keepPrecision_noUp(String number, int precision) {
            BigDecimal bg = new BigDecimal(number);
            return bg.setScale(precision, BigDecimal.ROUND_UNNECESSARY).toPlainString();
        }

        /**
         * 格式化为指定位小数的数字,返回未使用科学计数法表示的具有指定位数的字符串。<br>
         * 该方法舍入模式：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。<br>
         * 如果给定的数字没有小数，则转换之后将以0填充；例如：int 123  1 --> 123.0<br>
         * <b>注意：</b>如果精度要求比较精确请使用 keepPrecision(String number, int precision)方法
         *
         * @param String类型的数字对象
         * @param precision     小数精确度总位数,如2表示两位小数
         * @return 返回数字格式化后的字符串表示形式(注意返回的字符串未使用科学计数法)
         */
        public static String keepPrecision(Number number, int precision) {
            return keepPrecision(String.valueOf(number), precision);
        }

        /**
         * 对double类型的数值保留指定位数的小数。<br>
         * 该方法舍入模式：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。<br>
         * <b>注意：</b>如果精度要求比较精确请使用 keepPrecision(String number, int precision)方法
         *
         * @param number    要保留小数的数字
         * @param precision 小数位数
         * @return double 如果数值较大，则使用科学计数法表示
         */
        public static double keepPrecision(double number, int precision) {
            BigDecimal bg = new BigDecimal(number);
            return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        public static double keepPrecision_noUp(double number, int precision) {
            BigDecimal bg = new BigDecimal(number);
            return bg.setScale(precision, BigDecimal.ROUND_DOWN).doubleValue();
        }

        /**
         * 对float类型的数值保留指定位数的小数。<br>
         * 该方法舍入模式：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。<br>
         * <b>注意：</b>如果精度要求比较精确请使用 keepPrecision(String number, int precision)方法
         *
         * @param number    要保留小数的数字
         * @param precision 小数位数
         * @return float 如果数值较大，则使用科学计数法表示
         */
        public static float keepPrecision(float number, int precision) {
            BigDecimal bg = new BigDecimal(number);
            return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).floatValue();
        }

        public static float keepPrecision_noUp(float number, int precision) {
            BigDecimal bg = new BigDecimal(number);
            return bg.setScale(precision, BigDecimal.ROUND_UNNECESSARY).floatValue();
        }

    }

    public static class DialogUtils {


        public static AlertView showDeleteDialog(String msg, OnItemClickListener lis) {

            return showDefaulStyleDialog(null, Text.isEmpty(msg) ? "确认删除吗？" : msg, "取消", "确认", lis);
        }

        public static AlertDialog showDeleteDialog(View view) {

            return new AlertDialog.Builder(mContext, R.style.default_dialog_theme)
                    .setView(view)
                    .create();
        }

        /**
         * @param message   提示的文本
         * @param cancel    返回的文本
         * @param determine 确定的文本
         * @param lis       按钮监听
         * @return
         */
        public static AlertView showDefaulStyleDialog(String message, String cancel, String determine, OnItemClickListener lis) {
            return showDefaulStyleDialog(null, message, cancel, determine, lis);
        }

        public static AlertView showDefaulStyleDialog(String message, OnItemClickListener lis) {
            return showDefaulStyleDialog(null, message, "返回", "确定", lis);
        }

        public static AlertView showDefaulStyleDialog(String message, String determine, OnItemClickListener lis) {
            return showDefaulStyleDialog(null, message, null, determine, lis);
        }

        public static AlertView showDefaulStyleDialog(String title, String message, String cancel, String determine, OnItemClickListener lis) {
            String[] other = null;
            if (Text.isEmpty(cancel)) {
                other = new String[1];
                other[0] = determine;
            } else if (!Text.isEmpty(cancel) && !Text.isEmpty(determine)) {
                other = new String[2];
                other[0] = cancel;
                other[1] = determine;
            }

            return new AlertView(title, message, null, null, other, AppManager.getCurrentActivity(), AlertView.Style.Alert, lis);
        }


    }

    public static class CameraUtils {
        private static AlertView mAlertViewSelectPhoto;

        public static AlertView showSelectPhotoModeAlert(OnItemClickListener lis) {

            return new AlertView(null, null, "取消", null, new String[]{"拍摄", "从手机相册选择"},
                    AppManager.getCurrentActivity(), AlertView.Style.ActionSheet, lis);
        }

        public static AlertView showSelectPhotoModeAlert(Context context,OnItemClickListener lis) {

            return new AlertView(null, null, "取消", null, new String[]{"拍摄", "从手机相册选择"}, context, AlertView.Style.ActionSheet, lis);
        }


        public static String getCameraPhotoSavePath(String basePath) {
            if (!Text.isEmpty(basePath)) {
                basePath = basePath + File.separator + Utils.FileUtil.generateJpgPictureName();
                Utils.FileUtil.createOrExistsFile(basePath);
            }
            return basePath;
        }
    }
}
