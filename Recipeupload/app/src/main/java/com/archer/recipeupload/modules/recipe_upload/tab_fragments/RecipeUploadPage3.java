package com.archer.recipeupload.modules.recipe_upload.tab_fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import com.archer.recipeupload.R;

import java.util.Random;

/**
 * Created by Swastik on 24-03-2016.
 */
public class RecipeUploadPage3
        extends Fragment implements View.OnClickListener {

    Random rnd;
    private Dialog dialog;

    public static RecipeUploadPage3 newInstance( ) {
        return new RecipeUploadPage3();
    }

    public RecipeUploadPage3() {
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
        long anim_duration = 540;
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
                    logo.animate().rotation(18*dir);
                else
                    this.cancel();
                dir*=-1;
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public Dialog getDialog(int id)
    {
        if(dialog==null)
        {
            dialog = new AlertDialog.Builder(getContext()).show();
            dialog.setCancelable(true);
//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            Window window = dialog.getWindow();
//            lp.copyFrom(window.getAttributes());
//            lp.width = JojoUtils.getScreenWidth(getWindowManager().getDefaultDisplay())
//                    - JojoUtils.ConvertToPx(getApplicationContext(), 40); //WindowManager.LayoutParams.WRAP_CONTENT;
//            window.setAttributes(lp);
        }
        LayoutInflater inflater = getLayoutInflater(null);
        View view = inflater.inflate(id, null, true);
        dialog.setContentView(view);
        dialog.show();
        dialog.getWindow()
                .clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        }
    }

}