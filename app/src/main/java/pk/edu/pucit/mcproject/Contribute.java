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

public class Contribute extends Fragment {


    public Contribute() {
        // Required empty public constructor
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
            /*public Add_Image_Fragment newInstance(String Place_Name) {
                Add_Image_Fragment fragment = new Add_Image_Fragment();
                Bundle args = new Bundle();
                args.putString(placeName.getText().toString(), Place_Name);
                fragment.setArguments(args);
                return fragment;
            }*/

        });


        return view;
    }
}