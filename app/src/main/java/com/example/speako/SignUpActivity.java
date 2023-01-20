package com.example.speako;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    TextView btn;
    EditText in_username,in_mail,in_pass,in_cnfrmpass;
    String username,password,mail,cnfrmpass;
    Button btnSignUp;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btn=findViewById(R.id.loginRedirect);
        in_username=findViewById(R.id.Username);
        in_mail=findViewById(R.id.Mail);
        in_pass=findViewById(R.id.Password);
        in_cnfrmpass=findViewById(R.id.ConfirmPassword);
        btnSignUp=findViewById(R.id.signUpBtn);
        db = new DBHelper(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCredentials()){
                    username=in_username.getText().toString();
                    mail=in_mail.getText().toString();
                    password=in_pass.getText().toString();
                    Boolean checkMail=db.checkmail(mail);
                    if(checkMail==false){

                        Boolean insert=db.insertData(mail,username,password);
                        if(insert==true){
                            Toast.makeText(SignUpActivity.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                        }else{
                            Toast.makeText(SignUpActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });




        btn.setOnClickListener(new View.OnClickListener(){

                                   @Override
                                   public void onClick(View v) {

                                       startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                   }
                               }
        );
    }

    private boolean checkCredentials() {

        username=in_username.getText().toString();
        mail=in_mail.getText().toString();
        password=in_pass.getText().toString();
        cnfrmpass=in_cnfrmpass.getText().toString();

        if(username.isEmpty() || username.length()<7){

            showError(in_username,"Invalid Username!");
        }
        else if(mail.isEmpty() || !mail.contains("@")){
            showError(in_mail,"Invalid Email!");
        }
        else if(password.isEmpty() || password.length()<8){
            showError(in_pass,"Invalid Password! must be 8 digits");
        }
        else if(cnfrmpass.isEmpty() || !cnfrmpass.equals(password)){
            showError(in_cnfrmpass,"Passwords not match!");
        }
        else{
            return true;
        }

        return false;
    }

    private void showError(EditText in_fields, String s) {
        in_fields.setError(s);
        in_fields.requestFocus();
    }
}