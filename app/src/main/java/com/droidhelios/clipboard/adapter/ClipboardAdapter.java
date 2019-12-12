package com.droidhelios.clipboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.droidhelios.clipboard.AppApplication;
import com.droidhelios.clipboard.R;
import com.droidhelios.clipboard.database.Clipboard;
import com.droidhelios.clipboard.util.ClipboardUtil;

import java.util.List;

public class ClipboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Clipboard> clipboard;

    public ClipboardAdapter(List<Clipboard> clipboardModels) {
        this.clipboard = clipboardModels;
    }


    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int var2) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clipboard, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            Clipboard contact = clipboard.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.tvText.setText(contact.getText());
        }
    }

    @Override
    public int getItemCount() {
        return clipboard.size();
    }


    private class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvText;

        UserViewHolder(View view) {
            super(view);
            tvText = view.findViewById(R.id.tv_text);
            view.findViewById(R.id.iv_delete).setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.iv_delete){
                int pos = getAdapterPosition();
                AppApplication.databaseManager.delete(clipboard.get(getAdapterPosition()));
                clipboard.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, clipboard.size()-1);
            }else {
                ClipboardUtil.copy(itemView.getContext(), clipboard.get(getAdapterPosition()).getText());
            }
        }
    }
}