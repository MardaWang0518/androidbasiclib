package com.hxh.component.business.arouter.provider.base;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by hxh on 2017/5/26.
 */
public interface ISupportFragmentProvider extends IBaseProvider {
    SupportFragment newInstance(Object... args);

}
