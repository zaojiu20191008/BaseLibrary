package com.niubility.demo.modules.preference;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toolbar;
import com.niubility.demo.R;

import java.util.List;

public class DemoPreferenceActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setTheme(android.R.style.Theme_DeviceDefault_Light_DarkActionBar);

        setActionBar(new Toolbar(this));
        getActionBar().setCustomView(R.layout.toolbar_preference);


        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SinglePreferenceFragment())
                .commit();

    }



    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);

        //loadHeadersFromResource(R.xml.preference_demo_headers, target);

    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
//        return super.isValidFragment(fragmentName);

        return true;
    }
}
