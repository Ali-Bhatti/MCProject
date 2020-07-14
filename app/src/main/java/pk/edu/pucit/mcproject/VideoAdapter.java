package pk.edu.pucit.mcproject;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.util.List;

class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
    public SimpleExoPlayer exoplayer;


    public VideoAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }
    public  VideoAdapter()
    {};

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

        Uri myUri = Uri.parse(uploadCurrent.getImageUrl());
       /* holder.videoView.setVideoURI(myUri);
        holder.videoView.requestFocus();
        holder.videoView.start();*/
        try {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(mContext).build();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoplayer = (SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(mContext);
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("Videos");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(myUri, dataSourceFactory, extractorsFactory, null, null);
            holder.playerView.setPlayer(exoplayer);
            exoplayer.prepare(mediaSource);
            exoplayer.setPlayWhenReady(false);


        } catch (Exception e) {
            Log.e("ViewHolder2", "exoplayer error" + e.toString());

        }

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public TextView placeName;
        public TextView category;
        public TextView details;
        public TextView UserName;
        public  PlayerView playerView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.Place_Name);
            category = itemView.findViewById(R.id.Category);
            details = itemView.findViewById(R.id.details);
            playerView = itemView.findViewById(R.id.video_View);
            UserName = itemView.findViewById(R.id.User_Name);

        }
    }

    public void stopPlayback() {

        if (exoplayer != null) {
            exoplayer.setPlayWhenReady(false);
            exoplayer.stop();
            exoplayer.release();
            exoplayer.seekTo(0);
        }
       // exoplayer.setPlayWhenReady(false);
    }
}
