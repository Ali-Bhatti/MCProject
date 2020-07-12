package pk.edu.pucit.mcproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contribute, container, false);
    }
}