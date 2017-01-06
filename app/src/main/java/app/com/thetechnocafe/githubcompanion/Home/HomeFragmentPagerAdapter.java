package app.com.thetechnocafe.githubcompanion.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.com.thetechnocafe.githubcompanion.Home.Fragments.GitCommandFragment.GitCommandsFragment;
import app.com.thetechnocafe.githubcompanion.Home.Fragments.SearchFragment.SearchFragment;

/**
 * Created by gurleensethi on 05/01/17.
 */

public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {

    public HomeFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return SearchFragment.getInstance();
            }
            case 1: {
                return GitCommandsFragment.getInstance();
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: {
                return "Search";
            }
            default:
                return "Git Commands";
        }
    }
}
