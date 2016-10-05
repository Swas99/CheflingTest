package com.archer.recipeupload.modules.recipe_upload;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.archer.recipeupload.modules.recipe_upload.tab_fragments.RecipeUploadPage1;
import com.archer.recipeupload.modules.recipe_upload.tab_fragments.RecipeUploadPage2;
import com.archer.recipeupload.modules.recipe_upload.tab_fragments.RecipeUploadPage3;


/**
 * Created by Swastik on 22-03-2016.
 */
public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager fm ) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return RecipeUploadPage1.newInstance();
            case 1:
                return RecipeUploadPage2.newInstance();
            case 2:
                return RecipeUploadPage3.newInstance();
        }
        return RecipeUploadPage1.newInstance();
    }

    @Override
    public int getCount() {
        return 3;
    }

}
