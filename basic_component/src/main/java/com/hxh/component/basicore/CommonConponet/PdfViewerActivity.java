package com.hxh.component.basicore.CommonConponet;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.hxh.component.basicore.Base.TopBarBaseActivity;
import com.hxh.component.basicore.Base.topbar.ActionBarConfig;
import com.hxh.component.basicore.R;
import com.hxh.component.basicore.mvp.persenter.IPresenter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 创建者：hxh
 * 时间：  2017/8/4
 * 描述： 一个Pdf的加载界面
 */
public class PdfViewerActivity extends TopBarBaseActivity {

    private String mPdfPath;
    public static final String EXTRA_PDFPATH = "pdfpath";
    public static final String EXTRA_PAGETITLE= "pagetitle";
    public static void startSelf(Context context, String pdfpath,String title)
    {
        Intent intent = new Intent(context,PdfViewerActivity.class);
        intent.putExtra(EXTRA_PDFPATH,pdfpath);
        intent.putExtra(EXTRA_PAGETITLE,title);

        context.startActivity(intent);
    }

    public static void startSelf(Context context, String pdfpath)
    {
        Intent intent = new Intent(context,PdfViewerActivity.class);
        intent.putExtra(EXTRA_PDFPATH,pdfpath);
        context.startActivity(intent);
    }

    private PDFView pdfView;



    @Override
    protected ActionBarConfig setActionBarConfig()
    {

        String title = getExtra_String(EXTRA_PAGETITLE);
        return new ActionBarConfig.Builder()
                .title(isEmpty(title)?"浏览界面":title)
                .backgroundColor(R.color.actionbar_twolevel_color)
                .enableBackView()
                .build();
    }

    private void loadpdf()
    {
        mPdfPath = getExtra_String(EXTRA_PDFPATH);
        PDFView.Configurator config  = null;
        if(!mPdfPath.contains("/"))
        {
            AssetManager manager = getResources().getAssets();
            InputStream stream = null;
            try {
                stream = manager.open(mPdfPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            config = pdfView.fromStream(stream);
            //config = pdfView.fromAsset(mPdfPath);
        }else
        {
            config = pdfView.fromFile(new File(mPdfPath));
        }
        config
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                // allows to draw something on the current page, usually visible in the middle of the screen
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .load();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pdf_view;
    }

    @Override
    public void initData(Bundle saveInstanceState) {
        pdfView = (PDFView) findViewById(R.id.pdfView);
        loadpdf();
    }

    @Override
    public IPresenter newP() {
        return null;
    }
}
