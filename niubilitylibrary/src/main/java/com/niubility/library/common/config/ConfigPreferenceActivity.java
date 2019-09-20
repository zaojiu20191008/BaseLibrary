package com.niubility.library.common.config;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

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
    protected void onPause() {
        super.onPause();
        BaseConfig.readConfig(ConfigPreferenceActivity.this);
    }
}
