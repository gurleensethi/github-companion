package app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.RepositoriesSearch.RepositoriesSearchFragment;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class SearchFragmentPagerAdapter extends FragmentPagerAdapter {

    private String mSearchKeyword;

    public SearchFragmentPagerAdapter(FragmentManager fragmentManager, String searchKeyword) {
        super(fragmentManager);
        mSearchKeyword = searchKeyword;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return RepositoriesSearchFragment.getInstance(mSearchKeyword);
            }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
