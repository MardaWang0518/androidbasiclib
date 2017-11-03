package com.hxh.component.business.arouter.provider.base;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 适用于 ISupportFragment
 */
public interface ISupportFragmentProvider extends IBaseProvider {
    ISupportFragment newInstance(Object... args);

}
