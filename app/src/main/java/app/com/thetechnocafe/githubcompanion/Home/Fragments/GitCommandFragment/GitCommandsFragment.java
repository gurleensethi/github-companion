package app.com.thetechnocafe.githubcompanion.Home.Fragments.GitCommandFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.com.thetechnocafe.githubcompanion.Models.GitCommandModel;
import app.com.thetechnocafe.githubcompanion.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 05/01/17.
 */

public class GitCommandsFragment extends Fragment implements GitCommandsContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private GitCommandsContract.Presenter mPresenter;
    private GitCommandsRecyclerAdapter mGitCommandsRecyclerAdapter;

    public static Fragment getInstance() {
        return new GitCommandsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_git_commands, container, false);

        ButterKnife.bind(this, root);

        mPresenter = new GitCommandsPresenter();
        mPresenter.attachView(this);

        return root;
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setUpOrRefreshRecyclerView(List<GitCommandModel> list) {
        if (mGitCommandsRecyclerAdapter == null) {
            mGitCommandsRecyclerAdapter = new GitCommandsRecyclerAdapter(getContext(), list);
            mRecyclerView.setAdapter(mGitCommandsRecyclerAdapter);
        } else {
            mGitCommandsRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showCommandsList(List<GitCommandModel> list) {
        setUpOrRefreshRecyclerView(list);
    }
}
