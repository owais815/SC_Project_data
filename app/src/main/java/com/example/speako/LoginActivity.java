package com.example.speako;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    TextView btn;
    EditText in_mail,in_pass;
    Button btnLogin;
    DBHelper db;
    String mail,password;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        btnGoogle=findViewById(R.id.btnGoogle);

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signin();
            }
        });

        btn=findViewById(R.id.signUpRedirect);
        in_mail=findViewById(R.id.Mail);
        in_pass=findViewById(R.id.password);
        btnLogin=findViewById(R.id.loginBtn);
        db=new DBHelper(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCredentials()){
                    mail=in_mail.getText().toString();
                    password=in_pass.getText().toString();
                    Boolean checkLogin=db.checkLogin(mail,password);
                    if(checkLogin==true){
                        Toast.makeText(LoginActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener(){

                                   @Override
                                   public void onClick(View v) {
                                       startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                                   }
                               }
        );

    }

    private boolean checkCredentials() {


        mail=in_mail.getText().toString();
        password=in_pass.getText().toString();

        if(mail.isEmpty() || !mail.contains("@")){
            showError(in_mail,"Invalid Email!");
        }
        else if(password.isEmpty() || password.length()<8){
            showError(in_mail,"Invalid Password! must be 8 digits");
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

     void signin(){
        Intent signInIntent =gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToHome();
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void navigateToHome() {
        finish();
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }
}