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

import app.com.thetechnocafe.githubcompanion.Dialogs.TrendingFilterOptionsDialogFragment;
import app.com.thetechnocafe.githubcompanion.Models.RepositoriesModel;
import app.com.thetechnocafe.githubcompanion.R;
import app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.RepositoriesSearch.RepositoriesRecyclerAdapter;
import app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.RepositoriesSearch.RepositoriesSearchFragment;
import app.com.thetechnocafe.githubcompanion.Utilities.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;


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
    @BindView(R.id.filter_linear_layout)
    LinearLayout mFilterLinearLayout;

    private static final String TAG = RepositoriesSearchFragment.class.getSimpleName();
    private static final String FILTER_DIALOG_TAG = "filterdialog";
    private TrendingRepositoriesContract.Presenter mPresenter;
    private RepositoriesRecyclerAdapter mRepositoriesRecyclerAdapter;
    private static int DIALOG_SELECTED_OPTION = 0;

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

        mFilterLinearLayout.setOnClickListener(view -> {
            //Show the trending filters dialog fragment
            TrendingFilterOptionsDialogFragment dialogFragment = TrendingFilterOptionsDialogFragment.getInstance(DIALOG_SELECTED_OPTION);
            dialogFragment.setFragmentCallback(selectedTime -> {
                DIALOG_SELECTED_OPTION = selectedTime;
                mPresenter.loadRepositories(Constants.TIME_OPTIONS[selectedTime]);
            });
            dialogFragment.show(getActivity().getSupportFragmentManager(), FILTER_DIALOG_TAG);
        });
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
