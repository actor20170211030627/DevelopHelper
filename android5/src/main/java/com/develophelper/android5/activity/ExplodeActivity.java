package com.develophelper.android5.activity;

import android.os.Bundle;

import com.develophelper.android5.R;

public class ExplodeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//      getWindow().setAllowEnterTransitionOverlap(true);
//      getWindow().setAllowReturnTransitionOverlap(true);
//        Explode explode = new Explode();
//        explode.setDuration(500);
//        getWindow().setEnterTransition(explode);
//        getWindow().setExitTransition(explode);

        setContentView(R.layout.activity_animation);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finishAfterTransition();
    }
}
