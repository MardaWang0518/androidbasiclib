package com.hxh.component.basicore.imageLoader;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * 图片加载
 */
public class GliderLoader implements IImageLoader {

    @Override
    public void init(Context context) {

    }

    @Override
    public void loadFormNet(ImageView iv, String url, Options options) {
        load(getRequestManager(iv.getContext()).load(url),iv,options,null,true);
    }

    @Override
    public void loadFormNet(ImageView iv, String url, Options options, CallBack callback) {
        load(getRequestManager(iv.getContext()).load(url),iv,options,callback,true);
    }

    @Override
    public void loadFormNet(ImageView iv, String url, Options options, boolean enableCache) {
        load(getRequestManager(iv.getContext()).load(url),iv,options,null,false);
    }

    @Override
    public void loadResource(ImageView iv, int resId, Options options) {
        load(getRequestManager(iv.getContext()).load(resId),iv,options,null,true);
    }

    @Override
    public void loadAssest(ImageView iv, String assestName, Options options) {
        load(getRequestManager(iv.getContext()).load("file:///android_asset/"+assestName),iv,options,null,true);
    }

    @Override
    public void loadFile(ImageView iv, File file, Options options) {
        load(getRequestManager(iv.getContext()).load(file),iv,options,null,true);
    }

    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache(Context context) {
        Glide.getPhotoCacheDir(context).deleteOnExit();
    }

    @Override
    public void resume(Context context) {
        getRequestManager(context).resumeRequests();
    }

    @Override
    public void pause(Context context) {
        getRequestManager(context).pauseRequests();
    }

    /**
     * 获取RequestManager
     * @param context
     * @return
     */
    private RequestManager getRequestManager(Context context)
    {
        if(context instanceof Activity)
        {
            return Glide.with(((Activity) context));
        }
        return Glide.with(context);
    }

    private void load(DrawableTypeRequest request,ImageView target,Options options,final CallBack callback,boolean enableCache) {
        if (options == null) options = Options.defaultOptions();

        request

                .error(options.loadErrorResId);

        DrawableRequestBuilder builder = setScaleType(request, options)
                .crossFade();
        if (enableCache)
        {
            builder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
        }else
        {
            builder.diskCacheStrategy( DiskCacheStrategy.NONE );//禁用磁盘缓存
            builder.skipMemoryCache(true);
        }

        if(null != callback)
        {
            builder
                    .listener(new RequestListener() {
                        @Override
                        public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                            callback.onError(e.getMessage());
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    });
        }


        builder.into(target);


    }

    private DrawableTypeRequest setScaleType(DrawableTypeRequest request,Options option)
    {
        if(option != null && option.scaleType!= null)
        {
            switch (option.scaleType)
            {
                case MATRIX:
                    break;

                case FIT_XY:
                    break;

                case FIT_START:
                    break;

                case FIT_END:
                    break;

                case CENTER:
                    break;

                case CENTER_INSIDE:
                    break;

                case FIT_CENTER:
                    request.fitCenter();
                    break;

                case CENTER_CROP:
                    request.centerCrop();
                    break;
            }
        }
        return request;
    }


}
