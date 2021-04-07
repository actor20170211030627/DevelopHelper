package com.develophelper.android5.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.develophelper.android5.R;

/**
 * 兼容性
 */
public class SupportFragment extends BaseFragment {

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_support, container, false);
	}

	public String getUrl() {
		return "file:///android_asset/support.html";
	}
}
