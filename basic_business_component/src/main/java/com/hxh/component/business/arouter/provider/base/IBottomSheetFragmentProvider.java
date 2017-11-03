package com.hxh.component.business.arouter.provider.base;

import android.support.design.widget.BottomSheetDialogFragment;

/**
 * 适用于 BottomSheetFragment
 * @param <T>
 */
public interface IBottomSheetFragmentProvider<T> extends IBaseProvider{

    BottomSheetDialogFragment newInstance(T... args);
}
