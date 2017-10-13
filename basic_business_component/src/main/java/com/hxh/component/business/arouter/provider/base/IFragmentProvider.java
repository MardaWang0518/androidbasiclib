package com.hxh.component.business.arouter.provider.base;

import android.support.v4.app.Fragment;

/**
 * Fragmetn 的Router  Provider
 */
public interface IFragmentProvider extends IBaseProvider {

    Fragment newInstance(Object... args);

}
