package app.com.thetechnocafe.githubcompanion.Home.Fragments.SearchFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import app.com.thetechnocafe.githubcompanion.R;
import app.com.thetechnocafe.githubcompanion.UnifiedSearch.UnifiedSearchActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 05/01/17.
 */

public class SearchFragment extends Fragment implements SearchContract.View {

    @BindView(R.id.search_edit_text)
    EditText mSearchEditText;
    @BindView(R.id.search_image_button)
    ImageButton mSearchImageButton;

    private SearchContract.Presenter mPresenter;

    public static Fragment getInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, root);

        mPresenter = new SearchPresenter();
        mPresenter.attachView(this);

        return root;
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void initViews() {
        //Configure Button properties
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mSearchImageButton.setElevation(8);
            mSearchImageButton.setTranslationZ(8);
        }

        mSearchImageButton.setOnClickListener(view -> mPresenter.onSearch(mSearchEditText.getText().toString()));
    }

    @Override
    public void startSearchResultActivity(String searchKeyword) {
        Intent intent = new Intent(getContext(), UnifiedSearchActivity.class);
        intent.putExtra(UnifiedSearchActivity.EXTRA_SEARCH_KEYWORD, searchKeyword);
        startActivity(intent);
    }

    @Override
    public void showError(int messageID) {
        Toast.makeText(getAppContext(), messageID, Toast.LENGTH_SHORT).show();
    }
}
