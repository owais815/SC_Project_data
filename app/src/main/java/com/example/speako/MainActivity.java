package com.example.speako;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    TextView txtSpeak;
    Button btnSpeech;
    String input;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        txtSpeak =findViewById(R.id.txtSpeak);
        btnSpeech=findViewById(R.id.btnSpeech);

        btnSpeech.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){

                SpeakNow(view);
            }
        });
    }

    private void SpeakNow(View view){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Start Speaking....");
        startActivityForResult(intent,111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==111 && resultCode== RESULT_OK){
            txtSpeak.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
           input= (String) txtSpeak.getText().toString();
            Toast.makeText(MainActivity.this,"AA g ponka "+input,Toast.LENGTH_SHORT).show();
        }
    }
}