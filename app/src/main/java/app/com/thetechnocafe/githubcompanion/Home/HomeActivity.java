package app.com.thetechnocafe.githubcompanion.Home;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import app.com.thetechnocafe.githubcompanion.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    @BindView(R.id.search_edit_text)
    EditText mSearchEditText;
    @BindView(R.id.search_image_button)
    ImageButton mSearchImageButton;

    private HomeContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        mPresenter = new HomePresenter();
        mPresenter.attachView(this);

        initViews();
    }

    @Override
    public void startSearchResultActivity(String searchKeyword) {

    }

    @Override
    public void showError(int messageID) {
        Toast.makeText(this, messageID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    public void initViews() {
        //Configure Button properties
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mSearchImageButton.setElevation(8);
            mSearchImageButton.setTranslationZ(8);
        }

        mSearchImageButton.setOnClickListener(view -> mPresenter.onSearch(mSearchEditText.getText().toString()));
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
