package com.example.crimeslostsreport.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crimeslostsreport.Interfaces.RecyclerViewInterface;
import com.example.crimeslostsreport.Models.Announcement;
import com.example.crimeslostsreport.Models.User;
import com.example.crimeslostsreport.R;

import java.util.ArrayList;
import java.util.Objects;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder> {

    User currentUser;
    Context context;
    String[] status_photos;
    ArrayList<Announcement> announcements;
    private final RecyclerViewInterface recyclerViewInterface;

    public AnnouncementAdapter(User user, Context context, ArrayList<Announcement> announcements,
                               RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.announcements = announcements;
        this.recyclerViewInterface = recyclerViewInterface;
        currentUser= user;
        status_photos = context.getResources().getStringArray(R.array.statuses);
    }

    @NonNull
    @Override
    public AnnouncementAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        //context.getResources().getStringArray(R.array.statuses);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.announcement_layout, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view, recyclerViewInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.MyViewHolder holder, int position) {
        holder.title.setText(announcements.get(position).getTitle());
        String type = "CRIME";
        if (!announcements.get(position).getIs_crime())
            type = "LOST";
        holder.announcementType.setText(type);
        holder.date.setText(String.valueOf(announcements.get(position).getDate()));
        if (Objects.equals(currentUser.getUserRole().getRoleName(), "user")) {
            if (announcements.get(position).getUser_id() != currentUser.getUserId()) {
                holder.editButton.setVisibility(View.INVISIBLE);
                holder.deleteButton.setVisibility(View.INVISIBLE);
            }
        }
        holder.status.setImageResource(
                context.getResources().getIdentifier(
                        status_photos[announcements.get(position).getStatus().getStatusId() - 1],
                        "drawable", context.getPackageName()));

        byte[] byteArr = announcements.get(position).getImageUrl();
        if (byteArr != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
            if (bmp != null)
                holder.imageView.setImageBitmap(bmp);
        }
    }
    public void filterList(ArrayList<Announcement> filterlist) {
        announcements = filterlist;
        notifyDataSetChanged();
    }

    public void updateList(ArrayList<Announcement> newlist) {
        announcements=newlist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView, status;
        TextView title, date, announcementType;
        ImageButton editButton, deleteButton;
        RecyclerViewInterface recyclerViewInterface;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            status = itemView.findViewById(R.id.status);
            title = itemView.findViewById(R.id.titleTextView);
            date = itemView.findViewById(R.id.dateTextView);
            announcementType = itemView.findViewById(R.id.typeTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
            this.recyclerViewInterface=recyclerViewInterface;
        }

        @Override
        public void onClick(View v) {
            if (recyclerViewInterface != null) {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (v == editButton)
                        recyclerViewInterface.onEditClick(pos);
                    else if (v == deleteButton)
                        recyclerViewInterface.onDeleteClick(pos);
                }
            }
        }
    }
}
