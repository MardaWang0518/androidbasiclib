package com.hxh.component.business.arouter.provider.base;

import android.support.design.widget.BottomSheetDialogFragment;


/**
 * Created by hxh on 2017/5/12.
 */
public interface IBottomSheetFragmentProvider1 extends IBaseProvider{

    BottomSheetDialogFragment newInstance(String... args);

}