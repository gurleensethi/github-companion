package app.com.thetechnocafe.githubcompanion.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import app.com.thetechnocafe.githubcompanion.R;
import app.com.thetechnocafe.githubcompanion.Utilities.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 12/01/17.
 */

public class TrendingFilterOptionsDialogFragment extends android.support.v4.app.DialogFragment {
    //Interface for callback
    public interface OnOptionsSelected {
        void onOptionsFiltered(int selectedTime);
    }

    @BindView(R.id.button_cancel)
    Button mCancelButton;
    @BindView(R.id.button_save)
    Button mSaveButton;
    @BindView(R.id.time_option_spinner)
    Spinner mTimeOptionsSpinner;

    private OnOptionsSelected mCallback;
    private static final String ARGS_TIME_OPTION = "timeoption";
    private int SELECTED_TIME_OPTION = 0;

    //Instance method
    public static TrendingFilterOptionsDialogFragment getInstance(int selectedTimeOption) {
        //Create bundle arguments and attach to dialog fragment
        Bundle args = new Bundle();
        args.putInt(ARGS_TIME_OPTION, selectedTimeOption);

        TrendingFilterOptionsDialogFragment fragment = new TrendingFilterOptionsDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    //Set the callback
    public void setFragmentCallback(OnOptionsSelected callback) {
        mCallback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //inflate custom view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_filter, null);

        ButterKnife.bind(this, view);

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        SELECTED_TIME_OPTION = getArguments().getInt(ARGS_TIME_OPTION);

        initViews();

        return dialog;
    }


    //Initialize the event listeners
    private void initViews() {
        mCancelButton.setOnClickListener(view -> getDialog().dismiss());

        mSaveButton.setOnClickListener(view -> {
            //Check if callback is attached or not
            if (mCallback != null) {
                mCallback.onOptionsFiltered(SELECTED_TIME_OPTION);
            }
            getDialog().dismiss();
        });

        //Initialize Spinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Constants.TIME_OPTIONS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTimeOptionsSpinner.setAdapter(adapter);

        mTimeOptionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SELECTED_TIME_OPTION = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                SELECTED_TIME_OPTION = 0;
            }
        });

        mTimeOptionsSpinner.setSelection(SELECTED_TIME_OPTION);
    }
}
