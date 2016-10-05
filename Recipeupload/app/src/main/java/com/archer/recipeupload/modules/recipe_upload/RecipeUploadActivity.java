package com.archer.recipeupload.modules.recipe_upload;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.archer.recipeupload.R;
import com.archer.recipeupload.utils.CheflingUtils;

import java.util.Locale;

public class RecipeUploadActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    int currentPageIndex;
    PageAdapter pagerAdapter;

    boolean closeFlag;

    ViewPager.OnPageChangeListener pageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initializePageScroller();
        setupViewPager();

    }

    private void init() {
        currentPageIndex=0;
        View page_progress = findViewById(R.id.page_progress);
        float base_width = CheflingUtils.getScreenWidth(getWindowManager().getDefaultDisplay())/3;
        ViewGroup.LayoutParams layoutParams = page_progress.getLayoutParams();
        layoutParams.width = (int)(base_width);
        page_progress.setLayoutParams(layoutParams);

        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnNext).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);


    }
    private void initializePageScroller() {
        pageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                position++;
                View page_progress = findViewById(R.id.page_progress);
                float base_width = CheflingUtils.getScreenWidth(getWindowManager().getDefaultDisplay())/3;
                ViewGroup.LayoutParams layoutParams = page_progress.getLayoutParams();
                layoutParams.width = (int)(base_width*position+positionOffsetPixels/3);
                page_progress.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position)
            {
                currentPageIndex=position;
                TextView tvCurrentPage = (TextView) findViewById(R.id.tvCurrentPage);
                tvCurrentPage.setText(String.format(Locale.ENGLISH,"%d/3", position+1));
                if(currentPageIndex==0)
                    findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
                else
                    findViewById(R.id.btnBack).setVisibility(View.VISIBLE);
                if(currentPageIndex==2)
                    findViewById(R.id.btnNext).setVisibility(View.INVISIBLE);
                else
                    findViewById(R.id.btnNext).setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    public void setupViewPager() {
        // Setup the viewPager
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnBack:
            {
                if(viewPager==null)
                    viewPager = (ViewPager) findViewById(R.id.view_pager);
                viewPager.setCurrentItem(currentPageIndex-1);
                break;
            }
            case R.id.btnNext:
            {
                if(viewPager==null)
                    viewPager = (ViewPager) findViewById(R.id.view_pager);
                viewPager.setCurrentItem(currentPageIndex+1);
                break;
            }
            case R.id.btnCancel:
            {
                finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        if(closeFlag)
        {
            super.onBackPressed();
            return;
        }
        closeFlag=true;
        Snackbar.make(findViewById(R.id.root_view), "Tap again to exit", Snackbar.LENGTH_SHORT).show();
        resetCloseFlagInTwoSeconds();
    }

    private void resetCloseFlagInTwoSeconds() {
        new CountDownTimer(2000, 2000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                closeFlag=false;
            }
        }.start();
    }
}
