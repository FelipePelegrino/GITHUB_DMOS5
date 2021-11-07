package br.edu.ifsp.arq.dmos5_2021s1.github_dmos5.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifsp.arq.dmos5_2021s1.github_dmos5.R;

public class ItemGitAdapter extends RecyclerView.Adapter<ItemGitAdapter.ViewHolder> {

    private List<RepoName> mData;

    public ItemGitAdapter(List<RepoName> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemGitAdapter.ViewHolder holder, int position) {
        holder.mTextName.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.text_name);
        }
    }

    public void setmData(List<RepoName> repoNames) {
        this.mData = repoNames;
    }

}
