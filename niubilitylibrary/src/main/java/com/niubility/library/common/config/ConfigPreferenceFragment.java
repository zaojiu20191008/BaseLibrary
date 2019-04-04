package com.niubility.library.common.config;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.niubility.library.R;

/**
 * 配置Fragment
 */
public class ConfigPreferenceFragment extends PreferenceFragmentCompat {

    public final String TAG = getClass().getSimpleName();
    public static final String mSharedPreferencesName = Config.sp_config;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        getPreferenceManager().setSharedPreferencesName(mSharedPreferencesName);
        addPreferencesFromResource(R.xml.global_config);
    }

}
