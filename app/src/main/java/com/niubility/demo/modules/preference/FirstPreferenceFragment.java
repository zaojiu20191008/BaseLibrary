package com.niubility.demo.modules.preference;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

import com.niubility.demo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstPreferenceFragment extends PreferenceFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference_demo_fragment_one);
    }
}
