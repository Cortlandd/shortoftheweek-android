package com.app.shortoftheweek.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.shortoftheweek.R;
import com.bumptech.glide.Glide;
import com.vimeo.networking.model.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BraxtonN on 3/13/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private ArrayList<Video> mVideos;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mVideoThumbnail;
        public TextView mVideoTitle;
        public TextView mVideoDescription;
        public View mView;

        public ViewHolder(View view) {
            super(view);

            mView = view;
            mVideoThumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);
            mVideoTitle = (TextView)itemView.findViewById(R.id.video_title);
            mVideoDescription = (TextView)itemView.findViewById(R.id.video_description);
        }
    }

    public VideoAdapter(ArrayList<Video> videos) {
        mVideos = videos;
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        /**
         * Be sure to check if the current video contains images and pick the correct size
         * ideally 640x360 so that the image can automatically fit to scale.
         *
         * i.e. if(mVideos.get(position).pictures) != null) { GET CORRECT LINK IMAGE }
         */
        Glide.with(holder.mView.getContext())
                .load(mVideos.get(position).pictures.sizes.get(4).link)
                .error(R.drawable.shortoftheweek_logo)
                .into(holder.mVideoThumbnail);

        holder.mVideoTitle.setText(mVideos.get(position).name);
        holder.mVideoDescription.setText(mVideos.get(position).description);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}