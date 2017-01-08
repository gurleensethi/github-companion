package app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.RepositoriesSearch;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.com.thetechnocafe.githubcompanion.Models.RepositoriesModel;
import app.com.thetechnocafe.githubcompanion.R;
import app.com.thetechnocafe.githubcompanion.Utilities.LanguageColorUtility;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class RepositoriesRecyclerAdapter extends RecyclerView.Adapter<RepositoriesRecyclerAdapter.RepositoriesViewHolder> {

    private List<RepositoriesModel> mList;
    private Context mContext;

    public RepositoriesRecyclerAdapter(Context context, List<RepositoriesModel> list) {
        mContext = context;
        mList = list;
    }

    class RepositoriesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.full_name_text_view)
        TextView mFullNameTextView;
        @BindView(R.id.owner_image_view)
        CircleImageView mOwnerImageView;
        @BindView(R.id.description_text_view)
        TextView mDescriptionTextView;
        @BindView(R.id.language_text_view)
        TextView mLanguageTextView;
        @BindView(R.id.forks_count_text_view)
        TextView mForksTextView;
        @BindView(R.id.stars_count_text_view)
        TextView mStarsTextView;

        public RepositoriesViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

        public void bindData(int position) {
            //Get the repository
            RepositoriesModel repository = mList.get(position);

            mLanguageTextView.setText(repository.getLanguage());
            mForksTextView.setText(String.valueOf(repository.getForksCount()));
            mStarsTextView.setText(String.valueOf(repository.getStargazersCount()));

            //Configure the language color
            if (repository.getLanguage() != null && !repository.getLanguage().equals("")) {
                String colorInHex = LanguageColorUtility.getInstance(mContext).getColorCode(repository.getLanguage());

                //If color found
                if (colorInHex != null) {
                    mLanguageTextView.setTextColor(Color.parseColor(colorInHex));
                } else {
                    mLanguageTextView.setTextColor(Color.BLACK);
                }
            } else {
                mLanguageTextView.setText("-");
                mLanguageTextView.setTextColor(Color.BLACK);
            }

            //Shorten the description text if its too long
            String description = repository.getDescription();
            if (description != null) {
                if (description.length() > 140) {
                    String finalDescription = description.substring(0, 140) + "...";
                    mDescriptionTextView.setText(finalDescription);
                } else {
                    mDescriptionTextView.setText(description);
                }
            } else {
                mDescriptionTextView.setText("");
            }

            //Set the text
            mFullNameTextView.setText(getStyledSpannableString(repository.getFullName()), TextView.BufferType.SPANNABLE);

            //Load the user image with glide
            Glide.with(mContext)
                    .load(repository.getOwner().getAvatarUrl())
                    .into(mOwnerImageView);
        }

        /**
         * Change the style of name of user in full name by using spannable strings
         * (Example : String format -> "username/reponame", changes --> make username of different color)
         *
         * @param text Text to be changed.
         * @return SpannableString Changed spannable string.
         */
        private SpannableString getStyledSpannableString(String text) {

            //Get the point to which the style has to changed
            int splitPoint = text.indexOf("/");
            //Create new spannable string
            SpannableString spannableString = new SpannableString(text);
            //spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.md_blue_700)), 0, splitPoint + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, splitPoint + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
    }

    @Override
    public RepositoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_repositories, parent, false);
        return new RepositoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepositoriesViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
