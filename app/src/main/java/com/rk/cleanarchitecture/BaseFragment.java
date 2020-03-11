package com.rk.cleanarchitecture;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.rk.cleanarchitecture.utils.Constants;

public class BaseFragment extends Fragment implements Constants {

    protected Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

