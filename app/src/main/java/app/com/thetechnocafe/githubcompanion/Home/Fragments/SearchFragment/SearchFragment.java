package app.com.thetechnocafe.githubcompanion.Home.Fragments.SearchFragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import app.com.thetechnocafe.githubcompanion.R;
import app.com.thetechnocafe.githubcompanion.UnifiedSearch.UnifiedSearchActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class SearchFragment extends Fragment implements SearchContract.View {

    @BindView(R.id.search_edit_text)
    EditText mSearchEditText;
    @BindView(R.id.search_image_button)
    ImageButton mSearchImageButton;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private SearchContract.Presenter mPresenter;

    public static Fragment getInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);


        ButterKnife.bind(this, root);

        btnSpeak = (ImageButton)root.findViewById(R.id.mic_button);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

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
    private void promptSpeechInput() {
        Log.d(TAG, "promptSpeechInput: in");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
            
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getAppContext().getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG, "promptSpeechInput: out");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: in");
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mPresenter.onSearch(result.get(0));

                }
                break;
            }

        }
        Log.d(TAG, "onActivityResult: out");
    }
}
