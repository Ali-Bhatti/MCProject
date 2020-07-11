package pk.edu.pucit.mcproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    EditText e1, e2;
    Button b1, b2;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        db = new DatabaseHelper(this);
        e1 = (EditText) findViewById(R.id.loginEmail);
        e2 = (EditText) findViewById(R.id.loginPassword);
        b1 = (Button) findViewById(R.id.loginButton);
        b2 = (Button) findViewById(R.id.loginRegister);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = e1.getText().toString();
                String pass = e2.getText().toString();
                if (mail.equals("") || pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields Are Empty", Toast.LENGTH_SHORT).show();
                }
                boolean checkmailpass = db.checkEmailPassword(mail, pass);
                if (checkmailpass) {
                    Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Entry", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void btn_signup_activity(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));

    }
}