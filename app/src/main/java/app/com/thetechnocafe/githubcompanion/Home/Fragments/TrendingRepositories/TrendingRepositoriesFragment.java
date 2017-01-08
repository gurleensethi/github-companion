package app.com.thetechnocafe.githubcompanion.Home.Fragments.TrendingRepositories;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import app.com.thetechnocafe.githubcompanion.Models.RepositoriesModel;
import app.com.thetechnocafe.githubcompanion.R;
import app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.RepositoriesSearch.RepositoriesRecyclerAdapter;
import app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.RepositoriesSearch.RepositoriesSearchFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 08/01/17.
 */

public class TrendingRepositoriesFragment extends Fragment implements TrendingRepositoriesContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRepositoriesRecyclerView;
    @BindView(R.id.error_layout)
    LinearLayout mErrorLinearLayout;
    @BindView(R.id.progress_layout)
    LinearLayout mProgressLinearLayout;
    @BindView(R.id.retry_button)
    Button mRetryButton;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.content_layout)
    LinearLayout mContentLinearLayout;

    private static final String TAG = RepositoriesSearchFragment.class.getSimpleName();
    private TrendingRepositoriesContract.Presenter mPresenter;
    private RepositoriesRecyclerAdapter mRepositoriesRecyclerAdapter;

    public static Fragment getInstance() {
        //Create arguments bundle
        Bundle args = new Bundle();

        //Create fragment and apply arguments
        Fragment fragment = new TrendingRepositoriesFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trending_repositories, container, false);

        ButterKnife.bind(this, root);

        mPresenter = new TrendingRepositoriesPresenter();
        mPresenter.attachView(this);
        mPresenter.loadRepositories("weekly");

        return root;
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void initViews() {
        mRepositoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRetryButton.setOnClickListener(view -> mPresenter.loadRepositories("weekly"));

        mProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void showRepositories(List<RepositoriesModel> list) {
        setUpOrRefreshRecyclerView(list);
    }

    @Override
    public void showErrorLayout() {
        mErrorLinearLayout.setVisibility(View.VISIBLE);
        mProgressLinearLayout.setVisibility(View.GONE);
        mContentLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showProgressLayout() {
        mProgressLinearLayout.setVisibility(View.VISIBLE);
        mErrorLinearLayout.setVisibility(View.GONE);
        mContentLinearLayout.setVisibility(View.GONE);
    }

    //Set up recycler view (create new adapter if already not created, else refresh it)
    private void setUpOrRefreshRecyclerView(List<RepositoriesModel> list) {
        if (mRepositoriesRecyclerAdapter == null) {
            mRepositoriesRecyclerAdapter = new RepositoriesRecyclerAdapter(getContext(), list);
            mRepositoriesRecyclerView.setAdapter(mRepositoriesRecyclerAdapter);
        } else {
            mRepositoriesRecyclerAdapter.notifyDataSetChanged();
        }

        //Show the content layout
        mContentLinearLayout.setVisibility(View.VISIBLE);
        mProgressLinearLayout.setVisibility(View.GONE);
        mErrorLinearLayout.setVisibility(View.GONE);
    }
}
