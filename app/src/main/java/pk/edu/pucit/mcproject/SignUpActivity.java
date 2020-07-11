package pk.edu.pucit.mcproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText e1, e2, e3, e4;
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = new DatabaseHelper(this);
        b1 = (Button) findViewById(R.id.Register);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                e1 = (EditText) findViewById(R.id.Name);
                e2 = (EditText) findViewById(R.id.Email);
                e3 = (EditText) findViewById(R.id.Password);
                e4 = (EditText) findViewById(R.id.ConfirmPassword);
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                String s3 = e3.getText().toString();
                String s4 = e4.getText().toString();
                if (s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields Are Empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (s3.equals(s4)) {
                        boolean chkemail = db.checkEmail(s2);
                        if (chkemail) {
                            boolean insert = db.insert(s1, s2, s3);
                            if (insert) {
                                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SignUpActivity.this, LogInActivity.class);
                                startActivity(i);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Email Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password Does Not match", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

}