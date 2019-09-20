package com.niubility.demo.modules.preference;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

import com.niubility.demo.R;

/**
 * A simple {@link Fragment} subclass.
 *
 *
 */
public class SecondPreferenceFragment extends PreferenceFragmentCompat { //todo  PreferenceFragmentCompat 用法有待研究


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        //getPreferenceManager().setSharedPreferencesName("MyPreferenceDemo");//mysetting就是修改后的配置名
        addPreferencesFromResource(R.xml.preference_demo_fragment_two);//必须放在修改配置名之后
    }

}
