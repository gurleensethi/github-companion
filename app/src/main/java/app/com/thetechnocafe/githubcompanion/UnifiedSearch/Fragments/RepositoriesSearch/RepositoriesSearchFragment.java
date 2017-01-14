package app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.RepositoriesSearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
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
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class RepositoriesSearchFragment extends Fragment implements RepositoriesSearchContract.View {

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
    private static final String ARG_SEARCH_KEYWORD = "search_keyword";
    private RepositoriesSearchContract.Presenter mPresenter;
    private RepositoriesRecyclerAdapter mRepositoriesRecyclerAdapter;

    public static Fragment getInstance(String searchKeyword) {
        //Create arguments bundle
        Bundle args = new Bundle();
        args.putString(ARG_SEARCH_KEYWORD, searchKeyword);

        //Create fragment and apply arguments
        Fragment fragment = new RepositoriesSearchFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_repositories_search, container, false);

        ButterKnife.bind(this, root);

        mPresenter = new RepositoriesSearchPresenter();
        mPresenter.attachView(this);
        mPresenter.loadRepositories(getArguments().getString(ARG_SEARCH_KEYWORD));

        return root;
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void initViews() {
        mRepositoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRetryButton.setOnClickListener(view -> mPresenter.loadRepositories(getArguments().getString(ARG_SEARCH_KEYWORD)));

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

            //Add click listener for a single item
            mRepositoriesRecyclerAdapter.setOnRepoClickListener(repository -> {
                //TODO:Remove this from production code
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(repository.getHtmlUrl()));
                startActivity(intent);
            });
        } else {
            mRepositoriesRecyclerAdapter.notifyDataSetChanged();
        }

        //Show the content layout
        mContentLinearLayout.setVisibility(View.VISIBLE);
        mProgressLinearLayout.setVisibility(View.GONE);
        mErrorLinearLayout.setVisibility(View.GONE);
    }
}
