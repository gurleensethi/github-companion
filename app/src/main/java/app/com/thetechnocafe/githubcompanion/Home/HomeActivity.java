package app.com.thetechnocafe.githubcompanion.Home;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.thetechnocafe.githubcompanion.R;
import app.com.thetechnocafe.githubcompanion.Utilities.GlideUtility;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    private HomeContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GlideUtility.initGlide(this);

        ButterKnife.bind(this);

        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public void initViews() {
        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
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
                    mTabText.setText("Search");
                    mTabImage.setImageResource(R.drawable.ic_search);
                    break;
                }
                case 1: {
                    mTabText.setText("Git Commands");
                    mTabImage.setImageResource(R.drawable.ic_terminal);
                    break;
                }
                case 2: {
                    mTabText.setText("Trending Repos");
                    mTabImage.setImageResource(R.drawable.ic_flame);
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
