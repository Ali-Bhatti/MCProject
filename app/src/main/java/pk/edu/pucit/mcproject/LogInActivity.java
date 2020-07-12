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
    public void onBackPressed() {
        moveToMainActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Check If user is already logged in
        //if logged in move to main activity

        SessionManagement sessionManagement=new SessionManagement(LogInActivity.this);
        String useremail=sessionManagement.getSession();
        if(useremail!=null)
        {
            moveToMainActivity();
        }

    }
    private void moveToMainActivity(){
        Intent intent = new Intent(LogInActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        db = new DatabaseHelper(this);
        e1 =  findViewById(R.id.loginEmail);
        e2 =  findViewById(R.id.loginPassword);
        b1 =  findViewById(R.id.loginButton);
        b2 =  findViewById(R.id.loginRegister);
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
                    User user=new User(mail);
                    SessionManagement sessionManagement=new SessionManagement(LogInActivity.this);
                    sessionManagement.saveSession(user);

                    Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogInActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

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