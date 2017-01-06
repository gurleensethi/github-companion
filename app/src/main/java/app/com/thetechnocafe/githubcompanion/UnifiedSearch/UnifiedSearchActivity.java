package app.com.thetechnocafe.githubcompanion.UnifiedSearch;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.thetechnocafe.githubcompanion.R;
import app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.SearchFragmentPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UnifiedSearchActivity extends AppCompatActivity implements UnifiedSearchContract.View {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private UnifiedSearchContract.Presenter mPresenter;
    public static final String EXTRA_SEARCH_KEYWORD = "search_keyword";
    private static String SEARCH_KEYWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unified_search);

        ButterKnife.bind(this);

        //Get the search keyword from intent
        SEARCH_KEYWORD = getIntent().getStringExtra(EXTRA_SEARCH_KEYWORD);

        mPresenter = new UnifiedSearchPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public void initViews() {
        //Set up the toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(SEARCH_KEYWORD);

        //Set up the fragment page adapter for view pager
        SearchFragmentPagerAdapter adapter = new SearchFragmentPagerAdapter(getSupportFragmentManager(), SEARCH_KEYWORD);
        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);

        //Set custom icon and text for each tab
        for (int count = 0; count < adapter.getCount(); count++) {
            //Create new tab and set corresponding image and text
            View view = LayoutInflater.from(this).inflate(R.layout.item_tab_layout, mTabLayout, false);

            //Get text view and image view
            TextView mTabText = (TextView) view.findViewById(R.id.tab_text_view);
            ImageView mTabImage = (ImageView) view.findViewById(R.id.tab_image_view);

            switch (count) {
                case 0: {
                    mTabText.setText("Repositories");
                    mTabImage.setImageResource(R.drawable.ic_search);
                    break;
                }
            }

            //Get the tab at a position
            TabLayout.Tab tab = mTabLayout.getTabAt(count);
            //Set the custom view
            tab.setCustomView(view);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
