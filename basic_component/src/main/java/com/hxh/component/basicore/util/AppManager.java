package com.hxh.component.basicore.util;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.Toast;

import com.hxh.component.basicore.net.BaseAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

/**
 * App的工具类，管理 Actiivty 和 其他功能
 * 当功能是全局性的，而且不和业务相关的时候，请放入此类中
 */
public class AppManager {
    private static Stack<AppCompatActivity> mActivitys;

    /**
     * 向Activity集合中添加一个Activity
     * @param activity
     */
    public static void addActivity(AppCompatActivity activity)
    {
        if(null == mActivitys)
        {
            mActivitys = new Stack<>();
        }
        mActivitys.add(activity);
    }

    /**
     * 得到当前的Activity
     * 因为Activity栈顶的是当前Activity，但是集合添加是从 上至下 ，所以最后一个就是当前
     * @return
     */
    public static AppCompatActivity getCurrentActivity()
    {

        if(null != mActivitys)
        {
            return mActivitys.lastElement();
        }
        return null;
    }




    public static void removeActivity(AppCompatActivity activity)
    {
        mActivitys.remove(activity);
    }

    /**
     * 借宿掉当前的Activity
     */
    public static void finshCurrActivity()
    {
        mActivitys.lastElement().finish();
    }

    public static void finshAllActivity()
    {
        if(null != mActivitys)
        {
            for (Activity item : mActivitys) {
                if(null != item)
                {
                    item.finish();
                }
            }
            //BaseAPI.getInstance().unRegister();
        }
    }

    /**
     * 退出所有Activity，但是需要多次点击
     */
    private static long lastExitTime;  //上次点击回退键时间
    public static void finshAllActivityButNeedMutilClick()
    {
        if ((System.currentTimeMillis() - lastExitTime) > 2000) {
            Toast.makeText(getCurrentActivity(), "再次点击后退出", Toast.LENGTH_SHORT).show();
            lastExitTime = System.currentTimeMillis();
        } else {
            finshAllActivity();
        }

    }

    /**
     * 退出App
     */
    public static void exitApp()
    {
        finshAllActivity();
        System.exit(0);
        Process.killProcess(Process.myPid());
    }

    /**
     * 后退
     */
    public static void back()
    {
        new Utils.ThreadPool(Utils.ThreadPool.SingleThread,0)
        .execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                } catch (Exception e) {
                    System.out.println("Exception when onBack" + e.toString());
                }
            }
        });
    }
    //判断是否重复点击
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


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }




}
