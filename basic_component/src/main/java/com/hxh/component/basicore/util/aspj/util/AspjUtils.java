package com.hxh.component.basicore.util.aspj.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by hxh on 2017/7/6.
 */
public class AspjUtils {

    //region 获取Context方式
    private static Context mContext;
    /**
     * 通过正常方式获取（先调用init方法）
     * @return
     */
    public static Context getApplicationContext() {
        if(null == mContext)//说明没有进行初始化
        {
            mContext = getApplicationContext_reflection();
        }
        if (null == mContext) {
            throw new IllegalStateException("you first call Utils.init()...");
        }
        return mContext;
    }

    /**
     * 通过反射方式获取
     * @return
     */
    private static Context getApplicationContext_reflection()
    {
        if(null == CURRENT && null == mContext)
        {
            try {
                Object activityThread = getActivityThread();
                Object app = activityThread.getClass().getMethod("getApplication").invoke(activityThread);
                CURRENT = (Application) app;

            } catch (Throwable e) {
                throw new IllegalStateException("Can not access Application context by magic code, boom!", e);
            }
        }
        return CURRENT;
    }

    @SuppressLint("StaticFieldLeak")
    private static Application CURRENT;

    private static Object getActivityThread() {
        Object activityThread = null;
        try {
            Method method = Class.forName("android.app.ActivityThread").getMethod("currentActivityThread");
            method.setAccessible(true);
            activityThread = method.invoke(null);
        } catch (final Exception e) {
            Log.w("hxh", e);
        }
        return activityThread;
    }
    //endregion

    public static class App
    {

        private static long lastClickTime;
        private final static int SPACE_TIME = 500;
        public static boolean isDoubleClick() {
            long currentTime = System.currentTimeMillis();
            boolean isClick2;
            if (currentTime - lastClickTime >
                    SPACE_TIME) {
                isClick2 = false;
            } else {
                isClick2 = true;
            }
            lastClickTime = currentTime;
            return isClick2;
        }

        public static boolean isDoubleClick(int spacetime) {
            long currentTime = System.currentTimeMillis();
            boolean isClick2;
            if (currentTime - lastClickTime >
                    spacetime) {
                isClick2 = false;
            } else {
                isClick2 = true;
            }
            lastClickTime = currentTime;
            return isClick2;
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
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(key, value);
            return editor.commit();
        }

        public static SharedPreferences.Editor editor() {
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
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
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
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
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
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
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
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
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
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
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
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
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
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
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
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
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
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
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            return settings.getBoolean(key, defaultValue);
        }
    }

    public static class FileCache
    {
        /**
         * Created by Tony Shen on 16/2/4.
         */
        private static final int MAX_SIZE = 1000 * 1000 * 50; // 50 mb
        private static final int MAX_COUNT = Integer.MAX_VALUE; // 不限制存放数据的数量
        private static Map<String, FileCache> mInstanceMap = new HashMap<String, FileCache>();
        private CacheManager cacheManager;

        /**
         * 默认的缓存名称为Cache
         * @param context
         * @return
         */
        public static FileCache get(Context context) {
            return get(context, "Cache");
        }

        /**
         *
         * @param context
         * @param cacheName 缓存的名称
         * @return
         */
        public static FileCache get(Context context, String cacheName) {
            File f = new File(context.getCacheDir(), cacheName);
            return get(f, MAX_SIZE, MAX_COUNT);
        }

        public static FileCache get(File cacheDir, long max_zise, int max_count) {
            FileCache manager = mInstanceMap.get(cacheDir.getAbsoluteFile() + "_" + android.os.Process.myPid());
            if (manager == null) {
                manager = new FileCache(cacheDir, max_zise, max_count);
                mInstanceMap.put(cacheDir.getAbsolutePath() + "_" + android.os.Process.myPid(), manager);
            }
            return manager;
        }

        private FileCache(File cacheDir, long max_size, int max_count) {
            if (!cacheDir.exists() && !cacheDir.mkdirs()) {
                throw new RuntimeException("can't make dirs in "
                        + cacheDir.getAbsolutePath());
            }
            cacheManager = new CacheManager(cacheDir, max_size, max_count);
        }

        /**--------------String相关操作--------------*/

        /**
         * 保存 String数据 到 缓存中
         *
         * @param key
         * @param value
         */
        public void put(String key, String value) {
            File file = cacheManager.newFile(key);
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new FileWriter(file), 1024);
                out.write(value);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                cacheManager.put(file);
            }
        }

        /**
         * 保存String数据到缓存中
         *
         * @param key
         * @param value
         * @param saveTime 保存的时间，单位：秒
         */
        public void put(String key, String value, int saveTime) {
            put(key, CacheUtils.newStringWithDateInfo(saveTime, value));
        }

        /**
         * 获取String数据
         *
         * @param key
         * @return String 数据
         */
        public String getString(String key) {
            File file = cacheManager.get(key);
            if (!file.exists())
                return null;

            boolean removeFile = false;
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(file));
                String readString = "";
                String currentLine;
                while ((currentLine = in.readLine()) != null) {
                    readString += currentLine;
                }
                if (!CacheUtils.isDue(readString)) {
                    return CacheUtils.clearDateInfo(readString);
                } else {
                    removeFile = true;
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (removeFile)
                    remove(key);
            }
        }

        /**--------------JSONObject相关操作--------------*/

        /**
         * 保存 JSONObject数据 到 缓存中
         *
         * @param key
         * @param value
         */
        public void put(String key, JSONObject value) {
            put(key, value.toString());
        }

        /**
         * 保存 JSONObject数据 到 缓存中
         *
         * @param key
         * @param value
         * @param saveTime 保存的时间，单位：秒
         */
        public void put(String key, JSONObject value, int saveTime) {
            put(key, value.toString(), saveTime);
        }

        /**
         * 获取JSONObject数据
         *
         * @param key
         * @return JSONObject数据
         */
        public JSONObject getJSONObject(String key) {
            String JSONString = getString(key);
            try {
                JSONObject obj = new JSONObject(JSONString);
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**--------------JSONArray相关操作--------------*/

        /**
         * 保存 JSONArray数据 到 缓存中
         *
         * @param key
         * @param value
         */
        public void put(String key, JSONArray value) {
            put(key, value.toString());
        }

        /**
         * 保存 JSONArray数据 到 缓存中
         *
         * @param key
         * @param value
         * @param saveTime 保存的时间，单位：秒
         */
        public void put(String key, JSONArray value, int saveTime) {
            put(key, value.toString(), saveTime);
        }

        /**
         * 读取JSONArray数据
         *
         * @param key
         * @return JSONArray数据
         */
        public JSONArray getJSONArray(String key) {
            String JSONString = getString(key);
            try {
                JSONArray obj = new JSONArray(JSONString);
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**--------------byte[]相关操作--------------*/

        /**
         * 保存 byte数据 到 缓存中
         *
         * @param key
         * @param value
         */
        public void put(String key, byte[] value) {
            File file = cacheManager.newFile(key);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                out.write(value);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                cacheManager.put(file);
            }
        }

        /**
         * 保存 byte数据 到 缓存中
         *
         * @param key
         * @param value
         * @param saveTime 保存的时间，单位：秒
         */
        public void put(String key, byte[] value, int saveTime) {
            put(key, CacheUtils.newByteArrayWithDateInfo(saveTime, value));
        }

        /**
         * 获取 byte 数据
         *
         * @param key
         * @return byte 数据
         */
        public byte[] getBytes(String key) {
            RandomAccessFile RAFile = null;
            boolean removeFile = false;
            try {
                File file = cacheManager.get(key);
                if (!file.exists())
                    return null;
                RAFile = new RandomAccessFile(file, "r");
                byte[] byteArray = new byte[(int) RAFile.length()];
                RAFile.read(byteArray);
                if (!CacheUtils.isDue(byteArray)) {
                    return CacheUtils.clearDateInfo(byteArray);
                } else {
                    removeFile = true;
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (RAFile != null) {
                    try {
                        RAFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (removeFile)
                    remove(key);
            }
        }

        /**--------------Serializable相关操作--------------*/

        /**
         * 保存序列化的数据 到 缓存中
         *
         * @param key
         * @param value
         */
        public void put(String key, Serializable value) {
            put(key, value, -1);
        }

        /**
         * 保存 Serializable数据到 缓存中
         *
         * @param key
         * @param value
         * @param saveTime 保存的时间，单位：秒
         */
        public void put(String key, Serializable value, int saveTime) {
            ByteArrayOutputStream baos = null;
            ObjectOutputStream oos = null;
            try {
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(value);
                byte[] data = baos.toByteArray();
                if (saveTime != -1) {
                    put(key, data, saveTime);
                } else {
                    put(key, data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
        }

        /**
         * 获取可序列化的数据
         *
         * @param key
         * @return Serializable 数据
         */
        public Object getObject(String key) {
            byte[] data = getBytes(key);
            if (data != null) {
                ByteArrayInputStream bais = null;
                ObjectInputStream ois = null;
                try {
                    bais = new ByteArrayInputStream(data);
                    ois = new ObjectInputStream(bais);
                    Object reObject = ois.readObject();
                    return reObject;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    try {
                        if (bais != null)
                            bais.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (ois != null)
                            ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        /**--------------Parcelable相关操作--------------*/

        /**
         * 保存序列化的数据 到 缓存中
         *
         * @param key
         * @param value
         */
        public void put(String key, Parcelable value) {
            put(key, value, -1);
        }

        /**
         * 保存 Parcelable数据到 缓存中
         *
         * @param key
         * @param value
         * @param saveTime 保存的时间，单位：秒
         */
        public void put(String key, Parcelable value, int saveTime) {

            try {
                byte[] data = ParcelableUtils.marshall(value);
                if (saveTime != -1) {
                    put(key, data, saveTime);
                } else {
                    put(key, data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 获取Parcel，如果要转换成相应的class，则
         * Parcel parcel = cache.getParcelObject(key)
         * MyClass myclass = new MyClass(parcel); // Or MyClass.CREATOR.createFromParcel(parcel).
         *
         * @param key
         * @return Parcel 数据
         */
        public Parcel getParcelObject(String key) {
            byte[] data = getBytes(key);
            if (data != null) {
                return ParcelableUtils.unmarshall(data);
            }
            return null;
        }

        /**
         * 获取可序列化的数据
         * MyClass myclass = cache.getObject(key, MyClass.CREATOR);
         * @param key
         * @param creator
         * @param <T>
         * @return
         */
        public <T> T getObject(String key,Parcelable.Creator<T> creator) {
            byte[] data = getBytes(key);
            if (data != null) {
                return ParcelableUtils.unmarshall(data,creator);
            }
            return null;
        }

        /**
         * 删除某个key
         *
         * @param key
         * @return 是否删除成功
         */
        public boolean remove(String key) {
            return cacheManager.remove(key);
        }

        /**
         * 清除所有数据
         */
        public void clear() {
            cacheManager.clear();
        }

        public class CacheManager {
            private final AtomicLong cacheSize;
            private final AtomicInteger cacheCount;
            private final long sizeLimit;
            private final int countLimit;
            private final Map<File, Long> lastUsageDates = Collections.synchronizedMap(new HashMap<File, Long>());
            protected File cacheDir;

            private CacheManager(File cacheDir, long sizeLimit, int countLimit) {
                this.cacheDir = cacheDir;
                this.sizeLimit = sizeLimit;
                this.countLimit = countLimit;
                cacheSize = new AtomicLong();
                cacheCount = new AtomicInteger();
                calculateCacheSizeAndCacheCount();
            }

            /**
             * 计算 cacheSize和cacheCount
             */
            private void calculateCacheSizeAndCacheCount() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int size = 0;
                        int count = 0;
                        File[] cachedFiles = cacheDir.listFiles();
                        if (cachedFiles != null) {
                            for (File cachedFile : cachedFiles) {
                                size += calculateSize(cachedFile);
                                count += 1;
                                lastUsageDates.put(cachedFile,
                                        cachedFile.lastModified());
                            }
                            cacheSize.set(size);
                            cacheCount.set(count);
                        }
                    }
                }).start();
            }

            private void put(File file) {
                int curCacheCount = cacheCount.get();
                while (curCacheCount + 1 > countLimit) {
                    long freedSize = removeNext();
                    cacheSize.addAndGet(-freedSize);

                    curCacheCount = cacheCount.addAndGet(-1);
                }
                cacheCount.addAndGet(1);

                long valueSize = calculateSize(file);
                long curCacheSize = cacheSize.get();
                while (curCacheSize + valueSize > sizeLimit) {
                    long freedSize = removeNext();
                    curCacheSize = cacheSize.addAndGet(-freedSize);
                }
                cacheSize.addAndGet(valueSize);

                Long currentTime = System.currentTimeMillis();
                file.setLastModified(currentTime);
                lastUsageDates.put(file, currentTime);
            }

            private File get(String key) {
                File file = newFile(key);
                Long currentTime = System.currentTimeMillis();
                file.setLastModified(currentTime);
                lastUsageDates.put(file, currentTime);

                return file;
            }

            private File newFile(String key) {
                return new File(cacheDir, key.hashCode() + "");
            }

            private boolean remove(String key) {
                File file = get(key);
                return file.delete();
            }

            private void clear() {
                lastUsageDates.clear();
                cacheSize.set(0);
                File[] files = cacheDir.listFiles();
                if (files != null) {
                    for (File f : files) {
                        f.delete();
                    }
                }
            }

            /**
             * 移除旧的文件
             *
             * @return
             */
            private long removeNext() {
                if (lastUsageDates.isEmpty()) {
                    return 0;
                }

                Long oldestUsage = null;
                File mostLongUsedFile = null;
                Set<Map.Entry<File, Long>> entries = lastUsageDates.entrySet();
                synchronized (lastUsageDates) {
                    for (Map.Entry<File, Long> entry : entries) {
                        if (mostLongUsedFile == null) {
                            mostLongUsedFile = entry.getKey();
                            oldestUsage = entry.getValue();
                        } else {
                            Long lastValueUsage = entry.getValue();
                            if (lastValueUsage < oldestUsage) {
                                oldestUsage = lastValueUsage;
                                mostLongUsedFile = entry.getKey();
                            }
                        }
                    }
                }

                long fileSize = calculateSize(mostLongUsedFile);
                if (mostLongUsedFile.delete()) {
                    lastUsageDates.remove(mostLongUsedFile);
                }
                return fileSize;
            }

            private long calculateSize(File file) {
                return file.length();
            }
        }

        private static class CacheUtils {

            private static final char mSeparator = ' ';

            /**
             * 判断缓存的String数据是否到期
             *
             * @param str
             * @return true：到期了 false：还没有到期
             */
            private static boolean isDue(String str) {
                return isDue(str.getBytes());
            }

            /**
             * 判断缓存的byte数据是否到期
             *
             * @param data
             * @return true：到期了 false：还没有到期
             */
            private static boolean isDue(byte[] data) {
                String[] strs = getDateInfoFromDate(data);
                if (strs != null && strs.length == 2) {
                    String saveTimeStr = strs[0];
                    while (saveTimeStr.startsWith("0")) {
                        saveTimeStr = saveTimeStr
                                .substring(1, saveTimeStr.length());
                    }
                    long saveTime = Long.parseLong(saveTimeStr);
                    long deleteAfter = Long.parseLong(strs[1]);
                    if (System.currentTimeMillis() > saveTime + deleteAfter * 1000) {
                        return true;
                    }
                }
                return false;
            }

            private static String newStringWithDateInfo(int second, String strInfo) {
                return createDateInfo(second) + strInfo;
            }

            private static byte[] newByteArrayWithDateInfo(int second, byte[] data2) {
                byte[] data1 = createDateInfo(second).getBytes();
                byte[] retdata = new byte[data1.length + data2.length];
                System.arraycopy(data1, 0, retdata, 0, data1.length);
                System.arraycopy(data2, 0, retdata, data1.length, data2.length);
                return retdata;
            }

            private static String clearDateInfo(String strInfo) {
                if (strInfo != null && hasDateInfo(strInfo.getBytes())) {
                    strInfo = strInfo.substring(strInfo.indexOf(mSeparator) + 1,
                            strInfo.length());
                }
                return strInfo;
            }

            private static byte[] clearDateInfo(byte[] data) {
                if (hasDateInfo(data)) {
                    return copyOfRange(data, indexOf(data, mSeparator) + 1,
                            data.length);
                }
                return data;
            }

            private static boolean hasDateInfo(byte[] data) {
                return data != null && data.length > 15 && data[13] == '-'
                        && indexOf(data, mSeparator) > 14;
            }

            private static String[] getDateInfoFromDate(byte[] data) {
                if (hasDateInfo(data)) {
                    String saveDate = new String(copyOfRange(data, 0, 13));
                    String deleteAfter = new String(copyOfRange(data, 14,
                            indexOf(data, mSeparator)));
                    return new String[] { saveDate, deleteAfter };
                }
                return null;
            }

            private static int indexOf(byte[] data, char c) {
                for (int i = 0; i < data.length; i++) {
                    if (data[i] == c) {
                        return i;
                    }
                }
                return -1;
            }

            private static byte[] copyOfRange(byte[] original, int from, int to) {
                int newLength = to - from;
                if (newLength < 0)
                    throw new IllegalArgumentException(from + " > " + to);
                byte[] copy = new byte[newLength];
                System.arraycopy(original, from, copy, 0,
                        Math.min(original.length - from, newLength));
                return copy;
            }

            private static String createDateInfo(int second) {
                String currentTime = System.currentTimeMillis() + "";
                while (currentTime.length() < 13) {
                    currentTime = "0" + currentTime;
                }
                return currentTime + "-" + second + mSeparator;
            }
        }
    }

    public static class ParcelableUtils
    {
        public static byte[] marshall(Parcelable parceable) {
            Parcel parcel = Parcel.obtain();
            parceable.writeToParcel(parcel, 0);
            byte[] bytes = parcel.marshall();
            parcel.recycle();
            return bytes;
        }

        public static Parcel unmarshall(byte[] bytes) {
            Parcel parcel = Parcel.obtain();
            parcel.unmarshall(bytes, 0, bytes.length);
            parcel.setDataPosition(0); // This is extremely important!
            return parcel;
        }

        public static <T> T unmarshall(byte[] bytes, Parcelable.Creator<T> creator) {
            Parcel parcel = unmarshall(bytes);
            T result = creator.createFromParcel(parcel);
            parcel.recycle();
            return result;
        }
    }

}
