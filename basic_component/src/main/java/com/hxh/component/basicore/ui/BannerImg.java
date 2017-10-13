package com.hxh.component.basicore.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hxh.component.basicore.R;
import com.hxh.component.basicore.imageLoader.IImageFactory;
import com.hxh.component.basicore.imageLoader.IImageLoader;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015-12-30.
 */
public class BannerImg extends FrameLayout {

    public BannerImg(Context context) {
        this(context, null);
    }

    public BannerImg(Context context, AttributeSet attrs) {

        this(context, attrs, 0);

    }

    public BannerImg(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init(context);
    }


    private final static boolean isAutoPlay = true;
    private List<String> imageUris;
    private List<Integer> imageUris_int;
    private List<ImageView> imageViewsList;
    private List<ImageView> dotViewsList;
    private LinearLayout mLinearLayout;
    private ViewPager mViewPager;
    private int currentItem = 0;
    private IImageLoader.Options mOptions;
    private ScheduledExecutorService scheduledExecutorService;
    private OnImageClickListener mOnlistener;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (isInEditMode())
            {
                return;
            }
            super.handleMessage(msg);
            mViewPager.setCurrentItem(currentItem);
        }
    };

    private void init(Context context)
    {
        if (!isInEditMode())
        {
            mOptions = IImageLoader.Options.defaultOptions();

            initUI(context);
            if (!(imageUris.size() <= 0)) {
                setImageUris(imageUris);
            }
            if (isAutoPlay) {
                startPlay();
            }
        }
    }

    private void initUI(Context context) {
        if(!isInEditMode()){
            imageViewsList = new ArrayList<ImageView>();
            dotViewsList = new ArrayList<ImageView>();
            imageUris = new ArrayList<String>();
            imageUris_int   = new ArrayList<Integer>();
            LayoutInflater.from(context).inflate(R.layout.layout_bannerimg, this, true);
            mLinearLayout = (LinearLayout) findViewById(R.id.dotsImg);
            mViewPager = (ViewPager) findViewById(R.id.imagePager);

        }

    }

    public void clearUris()
    {
        if (imageUris != null) {
            imageUris.clear();
        }
    }

    public void setImageUris(List<String> imageuris) {
        for (int i = 0; i < imageuris.size(); i++) {
            imageUris.add(imageuris.get(i));
        }
        for (int i = 0; i < imageUris.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);//铺满屏幕
            imageViewsList.add(imageView);
            IImageFactory.getLoader().loadFormNet(imageView,imageuris.get(i),mOptions);

            ImageView viewDot = new ImageView(getContext());
            RelativeLayout.LayoutParams params =  new RelativeLayout.LayoutParams(20,20);
            params.leftMargin =6;
            viewDot.setLayoutParams(params);
            if (i == 0) {
                viewDot.setBackgroundResource(R.mipmap.dot_focus);
            } else {
                viewDot.setBackgroundResource(R.mipmap.dot);
            }
            dotViewsList.add(viewDot);
            mLinearLayout.addView(viewDot);
            imageView.setOnClickListener(v->{
                if(null != mOnlistener)
                {
                    mOnlistener.onClick(currentItem);
                }
            });
        }
        mViewPager.setFocusable(true);
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());

    }

    public void setImageUris_Resource(List<Integer> imageuris) {
        for (int i = 0; i < imageuris.size(); i++) {
            imageUris_int.add(imageuris.get(i));
        }
        for (int i = 0; i < imageUris_int.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(15,15);
            layoutParams.leftMargin  = 10;
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);//铺满屏幕
            imageViewsList.add(imageView);
            IImageFactory.getLoader().loadResource(imageView,imageuris.get(i),mOptions);

            ImageView viewDot = new ImageView(getContext());
            LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(20,20);
            params.leftMargin =6;
            viewDot.setLayoutParams(params);
            if (i == 0) {
                viewDot.setBackgroundResource(R.mipmap.dot_focus);
            } else {
                viewDot.setBackgroundResource(R.mipmap.dot);
            }
            dotViewsList.add(viewDot);

            mLinearLayout.addView(viewDot);
            imageView.setOnClickListener(v->{
                if(null != mOnlistener)
                {
                    mOnlistener.onClick(currentItem);
                }
            });
        }
        mViewPager.setFocusable(true);
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());

    }


    public void setOnClickListener(OnImageClickListener lis)
    {
        this.mOnlistener = lis;
    }


    public interface OnImageClickListener
    {
       void onClick(int position);
    }


    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new BannerTask(), 3, 2, TimeUnit.SECONDS);
    }

    @SuppressWarnings("unused")
    private void stopPlay() {
        scheduledExecutorService.shutdown();
    }

    /**
     * 设置选中的圆点的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < dotViewsList.size(); i++) {
            if (i == selectItems) {
                dotViewsList.get(i).setBackgroundResource(R.mipmap.dot_focus);
            } else {
                dotViewsList.get(i).setBackgroundResource(R.mipmap.dot);
            }
        }
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public void destroyItem(View container, int position, Object object) {
            // TODO Auto-generated method stub
            //((ViewPag.er)container).removeView((View)object);
            ((ViewPager) container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            // TODO Auto-generated method stub
            ((ViewPager) container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub
        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }
    }


    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
                case 0:
                    if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        mViewPager.setCurrentItem(0);
                    }
                    //如果滑到头就从尾开始
                    else if (mViewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 1);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int pos) {
            // TODO Auto-generated method stub
            setImageBackground(pos);
        }
    }


    private class BannerTask implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (mViewPager) {
                currentItem = (currentItem + 1) % imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }
}