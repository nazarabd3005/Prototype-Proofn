package com.nazar.prototypeproofn.common.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nazar.prototypeproofn.R;
import com.nazar.prototypeproofn.models.Message;
import com.nazar.prototypeproofn.utils.Config;

import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {
    Context context;
    public List<Message> messages;

    public InboxAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_inbox,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Message messageItem = messages.get(i);
        if (messageItem.selected){
            viewHolder.container.setBackgroundResource(R.color.grey600);
        }else
            viewHolder.container.setBackgroundResource(android.R.color.transparent);

        Glide.with(context)
                .load(Config.URL_IMAGE+messageItem.sender.avatarPathSmall)
                .centerInside()
                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.ic_launcher_foreground)
                .into(viewHolder.avatar);

        viewHolder.name.setText(messageItem.sender.firstName);
        viewHolder.subject.setText(messageItem.subject);
        viewHolder.content.setText(messageItem.contentPreview);
    }
    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View container;
        public ImageView avatar;
        public TextView name,subject,content,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            subject = (TextView) itemView.findViewById(R.id.subject);
            content = (TextView) itemView.findViewById(R.id.content);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
