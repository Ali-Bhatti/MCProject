package pk.edu.pucit.mcproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class show_uploaded_videos extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private VideoAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_uploaded_videos);
        mRecyclerView = findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);
        //GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 3);
        //mRecyclerView.setLayoutManager(gridLayoutManager);
        mUploads = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Videos");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter = new VideoAdapter(show_uploaded_videos.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(show_uploaded_videos.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

        });
        mAdapter=new VideoAdapter();
    }


   /* @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopPlayback();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mAdapter.exoplayer != null) {
            mAdapter.exoplayer.setPlayWhenReady(false);
            mAdapter.exoplayer.stop();
            mAdapter.exoplayer.seekTo(0);
        }


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //mAdapter.stopPlayback();
            /*if (mAdapter.exoplayer != null) {
                mAdapter.exoplayer.setPlayWhenReady(false);
                mAdapter.exoplayer.stop();
                mAdapter.exoplayer.seekTo(0);
                finish();
            }
            mAdapter.stopPlayback();
            finish();
        }
        return true;
    }*/
    @Override
    protected void onPause() {

        super.onPause();
        //pausePlayer(mAdapter.exoplayer);
        mAdapter.stopPlayback();

    }

    @Override
    protected void onStop() {

        super.onStop();
      //  pausePlayer(mAdapter.exoplayer);
        mAdapter.stopPlayback();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
       // releaseExoPlayer(mAdapter.exoplayer);
    }

    @Override
    protected void onResume() {

        super.onResume();
       // startPlayer(mAdapter.exoplayer);
    }
    public static void startPlayer(SimpleExoPlayer exoPlayer) {

        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(true);

        }
    }

    public static void pausePlayer(SimpleExoPlayer exoPlayer) {

        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false);

        }
    }

    public static void releaseExoPlayer(SimpleExoPlayer exoPlayer) {

        if (exoPlayer != null) {
            exoPlayer.release();

        }

    }


}