package pk.edu.pucit.mcproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class Contribute extends Fragment {

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SessionManagement sessionManagement=new SessionManagement(getContext());
        String useremail=sessionManagement.getSession();
        if(useremail==null)
            moveToLoginActivity();
    }


    private void moveToLoginActivity(){
        Intent intent = new Intent(getContext(),LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment;
        View view = inflater.inflate(R.layout.fragment_contribute, container, false);


        Button btn_Add_Image = view.findViewById(R.id.btn_Add_Image);
        btn_Add_Image.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Add_Image_Fragment.class);
                startActivity(intent);

            }
            /*public Add_Image_Fragment newInstance(String Place_Name) {
                Add_Image_Fragment fragment = new Add_Image_Fragment();
                Bundle args = new Bundle();
                args.putString(placeName.getText().toString(), Place_Name);
                fragment.setArguments(args);
                return fragment;
            }*/

        });
        Button btn_Add_Video = view.findViewById(R.id.btn_Add_Video);
        btn_Add_Video.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Add_Video.class);
                startActivity(intent);
            }
        });
        Button btn_uploaded_images = view.findViewById(R.id.btn_uploaded_Images);
        btn_uploaded_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Show_Uploaded_Images.class);
                startActivity(intent);
            }
        });
        Button btn_uploaded_videos = view.findViewById(R.id.btn_uploaded_Videos);
        btn_uploaded_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),show_uploaded_videos.class);
                startActivity(intent);
            }
        });



        return view;
    }
    /*void showUploadedItems(View view){
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_uploaded_Images:
                intent = new Intent(getContext(),Show_Uploaded_Images.class);
                break;
            case R.id.btn_uploaded_Videos:
                intent = new Intent(getContext(),show_uploaded_videos.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
        startActivity(intent);
    }*/
}