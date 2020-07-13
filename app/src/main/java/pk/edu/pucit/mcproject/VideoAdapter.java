package pk.edu.pucit.mcproject;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
    public VideoAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.show_videos, parent, false);
        return new VideoAdapter.VideoViewHolder(v);
    }
    @Override
    public void onBindViewHolder(VideoAdapter.VideoViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.placeName.setText(uploadCurrent.getPlaceName());
        holder.category.setText(uploadCurrent.getCategory());
        holder.details.setText(uploadCurrent.getAboutPlace());
        holder.UserName.setText(uploadCurrent.getUserName());
       /* Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.videoView);*/
        Uri myUri = Uri.parse(uploadCurrent.getImageUrl());
        holder.videoView.setVideoURI(myUri);
        holder.videoView.requestFocus();

        //VideoView.setMediaController(new MediaController(mContext));
        holder.videoView.start();
    }
    @Override
    public int getItemCount() {
        return mUploads.size();
    }
    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public TextView placeName;
        public TextView category;
        public TextView details;
        public VideoView videoView;
        public TextView UserName;
        public VideoViewHolder(View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.Place_Name);
            category = itemView.findViewById(R.id.Category);
            details = itemView.findViewById(R.id.details);
            videoView = itemView.findViewById(R.id.video_View);
            UserName=itemView.findViewById(R.id.User_Name);

        }
    }


}
