package com.archer.recipeupload.modules.recipe_upload.tab_fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.archer.recipeupload.R;

/**
 * Created by Swastik on 24-03-2016.
 */
public class RecipeUploadPage2
        extends Fragment
        implements View.OnClickListener {


    View RootView;
    int selectedScopeCode;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedScopeCode", selectedScopeCode);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null)
        {
            selectedScopeCode = savedInstanceState.getInt("selectedScopeCode");
        }
        else
            selectedScopeCode = 0;
    }

    public static RecipeUploadPage2 newInstance( ) {
        return new RecipeUploadPage2();
    }

    public RecipeUploadPage2() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_upload_p2, container, false);
        init(rootView);
        return rootView;
    }


    private void init(View rootView) {
        View logo = rootView.findViewById(R.id.logo);
        long anim_duration = 630;
        logo.animate().setDuration(anim_duration);
        logo.animate().setInterpolator(new LinearInterpolator());
        new CountDownTimer(10000000, anim_duration + 10) {
            int dir = 1;
            @Override
            public void onTick(long l) {

                if(getView()==null)
                {
                    this.cancel();
                    return;
                }
                View logo = getView().findViewById(R.id.logo);
                if(logo!=null)
                    logo.animate().rotation(20*dir);
                else
                    this.cancel();
                dir*=-1;
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        }
    }

}