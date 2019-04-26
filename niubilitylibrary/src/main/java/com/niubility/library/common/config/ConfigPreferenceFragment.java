package com.niubility.library.common.config;

import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.niubility.library.R;
import com.niubility.library.utils.RegexUtils;

import java.time.temporal.ValueRange;

/**
 * 配置Fragment
 */
public class ConfigPreferenceFragment extends PreferenceFragmentCompat {

    public final String TAG = getClass().getSimpleName();
    public static final String mSharedPreferencesName = BaseConfig.sp_config;
    private ListPreference lp_environment;
    private EditTextPreference et_ip;
    private String ip_port;


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        getPreferenceManager().setSharedPreferencesName(mSharedPreferencesName);
        addPreferencesFromResource(R.xml.global_config);

    }

    @Override
    public void onStart() {
        super.onStart();

        setCurrentEnvironment();
        setVisibility();
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

        et_ip = (EditTextPreference) findPreference(getString(R.string.key_ip_port));
        ip_port = et_ip.getText();
        et_ip.setSummary(ip_port);
        et_ip.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {

                String change = (String) o;
                if(!TextUtils.isEmpty(ip_port) && !ip_port.equals(change)) {
                    if(!RegexUtils.match(RegexUtils.regex_ip_port, change)) {
                        Toast.makeText(getActivity(), "Ip/Port格式错误", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }

                et_ip.setSummary(change);
                ip_port = change;
                return true;
            }
        });


    }

    public void setVisibility() {
        if(BaseConfig.hideKeys == null) {
            return;
        }
        Preference preference;
        for (String key : BaseConfig.hideKeys) {
            preference = findPreference(key);
            if(preference != null) {
                preference.setVisible(false);
            }
        }
    }
}
