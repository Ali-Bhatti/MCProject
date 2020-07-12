package pk.edu.pucit.mcproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class Add_Image_Fragment extends Fragment {


    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private Button mButtonShowUploads;
    private String category;
    private String UserNAme = "Usman";
    private String UserEmail = "Usman@gmail.com";
    private EditText PlaceName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri Image_uri;
    private EditText aboutPlace;
    String GeneratedFilePath = "";


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;


    public Add_Image_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add__image_, container, false);

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

        mButtonChooseImage = view.findViewById(R.id.btn_Browse_Image);
        mButtonUpload = view.findViewById(R.id.btn_Upload);
        PlaceName = view.findViewById(R.id.Place_Name);
        mImageView = view.findViewById(R.id.imageView);
        mProgressBar = view.findViewById(R.id.progressBar);
        aboutPlace = view.findViewById(R.id.txtWriteSomething);
        mButtonShowUploads = view.findViewById(R.id.btn_show_uploads);
        category = Spinner.getSelectedItem().toString();

        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Images");


        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Show_Uploaded_Images.class);
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


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Image_uri = data.getData();
            Picasso.get().load(Image_uri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();//msla tha get activity check
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile() {
        if (Image_uri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(Image_uri));
            mUploadTask = fileReference.putFile(Image_uri)
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
                                            Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}