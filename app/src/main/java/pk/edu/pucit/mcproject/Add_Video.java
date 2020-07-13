package pk.edu.pucit.mcproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class Add_Video extends AppCompatActivity {


    private static final int PICK_Video_REQUEST = 1;
    private Button mButtonChooseVideo;
    private Button mButtonUpload;
    private Button mButtonShowUploads;
    private String category;
    private String UserNAme = "Usman";
    private String UserEmail = "Usman@gmail.com";
    private EditText PlaceName;
    private VideoView mVideoView;
    private ProgressBar mProgressBar;
    private Uri Video_uri;
    private EditText aboutPlace;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add__video);
        //spinner
        Spinner Spinner = findViewById(R.id.spinner);


        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.category_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        Spinner.setAdapter(staticAdapter);

        mButtonChooseVideo =findViewById(R.id.btn_Browse_Video);
        mButtonUpload = findViewById(R.id.btn_Upload);
        PlaceName = findViewById(R.id.Place_Name);
        mVideoView =findViewById(R.id.VideoView);
        mProgressBar = findViewById(R.id.progressBar);
        aboutPlace = findViewById(R.id.txtWriteSomething);
        mButtonShowUploads =findViewById(R.id.btn_show_uploads);
        category = Spinner.getSelectedItem().toString();
        // mVideoView.setVisibility(View.VISIBLE);

        mStorageRef = FirebaseStorage.getInstance().getReference("Videos");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Videos");


        mButtonChooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), show_uploaded_videos.class);
                startActivity(intent);
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = " Upload in progress";
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

    }


    /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add__video, container, false);
//spinner
        Spinner Spinner = view.findViewById(R.id.spinner);


        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.category_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        Spinner.setAdapter(staticAdapter);

        mButtonChooseVideo = view.findViewById(R.id.btn_Browse_Video);
        mButtonUpload = view.findViewById(R.id.btn_Upload);
        PlaceName = view.findViewById(R.id.Place_Name);
        mVideoView = view.findViewById(R.id.VideoView);
        mProgressBar = view.findViewById(R.id.progressBar);
        aboutPlace = view.findViewById(R.id.txtWriteSomething);
        mButtonShowUploads = view.findViewById(R.id.btn_show_uploads);
        category = Spinner.getSelectedItem().toString();
       // mVideoView.setVisibility(View.VISIBLE);

        mStorageRef = FirebaseStorage.getInstance().getReference("Videos");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Videos");


        mButtonChooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), show_uploaded_videos.class);
                startActivity(intent);
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = " Upload in progress";
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        return view;
    }
    */


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_Video_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_Video_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Video_uri = data.getData();
            //mVideoView.requestFocus();
           mVideoView.setVideoURI(Video_uri);
            //mVideoView.setBackgroundColor(Color.TRANSPARENT);

           MediaController mediaController=new MediaController(this);
           // mVideoView.setMediaController(mediaController);
          //  mediaController.setAnchorView(mVideoView);
            mVideoView.setMediaController(mediaController);
            mediaController.setAnchorView(mVideoView);
           // mVideoView.setZOrderOnTop(true);
            mVideoView.start();

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getApplicationContext().getContentResolver();//msla tha get activity check
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



    private void uploadFile() {
        if (Video_uri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(Video_uri));
            mUploadTask = fileReference.putFile(Video_uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                    new OnCompleteListener<Uri>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            String fileLink = task.getResult().toString();
                                            //next work with URL
                                            Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_LONG).show();
                                            Upload upload = new Upload(PlaceName.getText().toString().trim(),
                                                    fileLink, category, UserNAme, UserEmail, aboutPlace.getText().toString().trim());

                                            String uploadId = mDatabaseRef.push().getKey();
                                            mDatabaseRef.child(uploadId).setValue(upload);

                                        }
                                    });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}