package app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.UsersSearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.com.thetechnocafe.githubcompanion.Models.UsersSearchModel;
import app.com.thetechnocafe.githubcompanion.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gurleensethi on 08/01/17.
 */

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UsersViewHolder> {

    private Context mContext;
    private List<UsersSearchModel> mList;

    public UsersRecyclerAdapter(Context context, List<UsersSearchModel> list) {
        mContext = context;
        mList = list;
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar_circle_image_view)
        CircleImageView mAvatarImageView;
        @BindView(R.id.login_text_view)
        TextView mLoginTextView;

        UsersViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

        void bindData(int position) {
            UsersSearchModel user = mList.get(position);

            //Load the avatar image with glide
            Glide.with(mContext)
                    .load(user.getAvatarUrl())
                    .into(mAvatarImageView);

            mLoginTextView.setText(user.getLogin());
        }
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_users, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
