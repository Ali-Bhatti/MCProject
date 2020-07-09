package pk.edu.pucit.mcproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void btn_signup_activity(View view) {
        startActivity(new Intent(getApplicationContext() , SignUpActivity.class));

    }
}