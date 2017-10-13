package com.hxh.component.basicore.util;

import android.os.Environment;

import java.io.File;

/**
 * 日志工具类
 */
public class Log {

    private Log()
    {
        throw new IllegalStateException("you can't instance me");
    }

    private static boolean logEnable = false;
    private static boolean logWriteEnable = false;
    private static String logTag;
    private static String logDirPath;
    private static String logFilterChar= "v";


    /**
     * 初始化Log
     * @param logEnable 日志是否开启
     * @param logWriteEnable 日志是否写入文件
     * @param logTag 日志Tag
     */
    public static void init(boolean logEnable,boolean logWriteEnable,String logTag)
    {
        //当前sd卡是否是挂载的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            logDirPath = Utils.getApplicationContext().getExternalCacheDir().getPath()+ File.separator;
        }else
        {
            logDirPath = Utils.getApplicationContext().getCacheDir().getPath()+File.separator;
        }
        Log.logEnable = logEnable;
        Log.logWriteEnable = logWriteEnable;
        Log.logTag = logTag;

    }

    //region 另外提供建造者模式构造
    public static Builder builder()
    {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            logDirPath = Utils.getApplicationContext().getExternalCacheDir().getPath()+ File.separator;
        }else
        {
            logDirPath = Utils.getApplicationContext().getCacheDir().getPath()+File.separator;
        }
        return new Builder();
    }

    static class Builder
    {
        private boolean  logEnable;
        private boolean logWriteEnable;
        private String logTag;
        public Builder setLogEnable(boolean isEnable)
        {
            this.logEnable = isEnable;
            return this;
        }

        public Builder setLogWriteEnable(boolean isEnable)
        {
            this.logWriteEnable = isEnable;
            return this;
        }

        public Builder setLogTag(String tag)
        {
            this.logTag = tag;
            return this;
        }
        public Builder build()
        {
            Log.logEnable = this.logEnable;
            Log.logTag = this.logTag;
            Log.logWriteEnable = this.logWriteEnable;
            return this;
        }
    }
    //endregion


    public static void i(String message)
    {
        log(message,"i");
    }

    public static void i(Throwable tr)
    {
        log(tr.getMessage(),"i");
    }

    public static void d(String message)
    {
        log(message,"d");
    }

    public static void d(Throwable tr)
    {
        log(tr.getMessage(),"d");
    }

    public static void w(String message)
    {
        log(message,"w");
    }

    public static void w(Throwable tr)
    {
        log(tr.getMessage(),"w");
    }

    public static void e(String message)
    {
        log(message,"e");
    }

    public static void e(Throwable tr)
    {
        log(tr.getMessage(),"e");
    }


    private static void log(String message,String type)
    {
        if(logEnable)
        {
            switch (type)
            {
                case "e":
                    android.util.Log.e(Log.logTag,message);
                    break;
                case "d":
                    android.util.Log.d(Log.logTag,message);
                    break;
                case "i":
                    android.util.Log.i(Log.logTag,message);
                    break;
                case "w":
                    android.util.Log.w(Log.logTag,message);
                    break;
            }
        }

        if(logWriteEnable)
        {
            //logWriteToFile();
        }
    }
}
