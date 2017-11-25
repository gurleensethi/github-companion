package app.com.thetechnocafe.githubcompanion.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import app.com.thetechnocafe.githubcompanion.R;
import app.com.thetechnocafe.githubcompanion.Utilities.GlideUtility;
import app.com.thetechnocafe.githubcompanion.firebase.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    private HomeContract.Presenter mPresenter;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                    finish();
                }
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
            }
        });
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
        mViewPager.setOffscreenPageLimit(3);

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
                case 3: {
                    mTabText.setText("Trending Devs");
                    mTabImage.setImageResource(R.drawable.ic_progress_report);
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
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu , menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout){
            auth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }
}
