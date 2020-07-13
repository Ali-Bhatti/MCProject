package pk.edu.pucit.mcproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Fragment {


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
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        DatabaseHelper db;
        db = new DatabaseHelper(getContext());
        final SessionManagement sessionManagement=new SessionManagement(getContext());
        final String useremail=sessionManagement.getSession();
        String uname=db.getName(useremail);
        TextView myTextView1 =  view.findViewById (R.id.profNameBox);
        myTextView1.setText(uname);
        TextView myTextView2 =  view.findViewById (R.id.profEmailBox);
        myTextView2.setText(useremail);
        Button logoutBtn = view.findViewById(R.id.profLogoutButton);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManagement.deleteSession();
                Intent i = new Intent(getContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        final TextView myPassword =  view.findViewById (R.id.editTextTextPassword);
        //String userPass=db.getPassword(useremail);
        //myPassword.setText(userPass);
        ImageButton changePassBtn= view.findViewById(R.id.profImageBtnPassChange);
        final EditText newPasswordBox =  view.findViewById (R.id.editTextTextPassword2);
        final ImageButton confirmPassBtn= view.findViewById(R.id.confirmChangePassBtn);
        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPasswordBox.setVisibility(View.VISIBLE);
                confirmPassBtn.setVisibility(View.VISIBLE);
            }
        });
        confirmPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable newPass = newPasswordBox.getText();
                DatabaseHelper db;
                if (newPass.toString().equals("") ) {
                    Toast.makeText(getContext(), "Fields Are Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    db = new DatabaseHelper(getContext());
                    db.changePassword(useremail,newPass.toString());
                    //String userPass=db.getPassword(useremail);
                    //myPassword.setText(userPass);
                    newPasswordBox.setText("");
                    newPasswordBox.setVisibility(View.INVISIBLE);
                    confirmPassBtn.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "New Password Saved!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


}