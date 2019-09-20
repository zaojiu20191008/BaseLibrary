package com.niubility.demo.modules.keyboard;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.niubility.demo.R;
import com.niubility.library.widget.keyboard.NumericKeyboard;

/**
 * A placeholder fragment containing a simple view.
 */
public class KeyboardActivityFragment extends Fragment {

    View fragView;
    EditText editText;
    NumericKeyboard numericKeyboard;

    public KeyboardActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_keyboard, container, false);

        editText = fragView.findViewById(R.id.editText);

        numericKeyboard = fragView.findViewById(R.id.numericKeyboard);
        numericKeyboard.setEditText(editText);

        //new KeyboardUtil(getActivity().getApplicationContext(), editText, fragView);






        return fragView;

    }
}
