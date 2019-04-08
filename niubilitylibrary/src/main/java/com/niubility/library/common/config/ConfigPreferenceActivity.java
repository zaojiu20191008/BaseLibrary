package com.niubility.library.common.config;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.niubility.library.R;

/**
 * 配置Activity
 */
public class ConfigPreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_config);

        ConfigPreferenceFragment fragment = new ConfigPreferenceFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.framelayout, fragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseConfig.readConfig(ConfigPreferenceActivity.this);
    }
}
