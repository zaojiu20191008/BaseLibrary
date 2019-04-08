package com.niubility.library.common.config;

import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.niubility.library.R;

/**
 * 配置Fragment
 */
public class ConfigPreferenceFragment extends PreferenceFragmentCompat {

    public final String TAG = getClass().getSimpleName();
    public static final String mSharedPreferencesName = BaseConfig.sp_config;
    private ListPreference lp_environment;


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        getPreferenceManager().setSharedPreferencesName(mSharedPreferencesName);
        addPreferencesFromResource(R.xml.global_config);

    }

    @Override
    public void onStart() {
        super.onStart();

        setCurrentEnvironment();
    }

    private void setCurrentEnvironment() {
        final String[] environmentArrays = getResources().getStringArray(R.array.attr_environment);

        String key_environment = getString(R.string.key_environment);
        lp_environment = (ListPreference) findPreference(key_environment);
        lp_environment.setSummary("当前运行环境："
                + lp_environment.getEntry().toString());

        lp_environment.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                lp_environment.setSummary("当前运行环境："
                        + environmentArrays[Integer.valueOf((String) o)]);

                return true;
            }
        });
    }
}
