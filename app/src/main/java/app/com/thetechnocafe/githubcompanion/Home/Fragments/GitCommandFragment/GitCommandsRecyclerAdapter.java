package app.com.thetechnocafe.githubcompanion.Home.Fragments.GitCommandFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.com.thetechnocafe.githubcompanion.Models.GitCommandModel;
import app.com.thetechnocafe.githubcompanion.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class GitCommandsRecyclerAdapter extends RecyclerView.Adapter<GitCommandsRecyclerAdapter.GitCommandsRecyclerViewHolder> {

    private List<GitCommandModel> mList;
    private Context mContext;

    public GitCommandsRecyclerAdapter(Context context, List<GitCommandModel> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public GitCommandsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_git_command, parent, false);
        return new GitCommandsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GitCommandsRecyclerViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class GitCommandsRecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.command_text_view)
        TextView mCommandTextView;
        @BindView(R.id.description_text_view)
        TextView mDescriptionTextView;

        GitCommandsRecyclerViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

        //Get data to data binding
        void bindData(int position) {
            mCommandTextView.setText(mList.get(position).getCommand());
            mDescriptionTextView.setText(mList.get(position).getDescription());
        }
    }
}
