package app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.RepositoriesSearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.com.thetechnocafe.githubcompanion.Models.RepositoriesModel;
import app.com.thetechnocafe.githubcompanion.R;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        ImageView mOwnerImageView;
        @BindView(R.id.description_text_view)
        TextView mDescriptionTextView;
        @BindView(R.id.language_text_view)
        TextView mLanguageTextView;
        @BindView(R.id.forks_count_text_view)
        TextView mForksTextView;
        @BindView(R.id.watchers_count_text_view)
        TextView mWatchersTextView;

        public RepositoriesViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

        public void bindData(int position) {
            //Get the repository
            RepositoriesModel repository = mList.get(position);

            mFullNameTextView.setText(repository.getName());
            mDescriptionTextView.setText(repository.getDescription());
            mLanguageTextView.setText(repository.getLanguage());
            mForksTextView.setText(String.valueOf(repository.getForksCount()));
            mWatchersTextView.setText(String.valueOf(repository.getWatchersCount()));

            //Load the user image with glide
            Glide.with(mContext)
                    .load(repository.getOwner().getAvatarUrl())
                    .into(mOwnerImageView);
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
