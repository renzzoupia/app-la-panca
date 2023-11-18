package com.braxly.lapancaproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.braxly.lapancaproject.activity.LoginActivity;
import com.braxly.lapancaproject.activity.MainActivity;
import com.braxly.lapancaproject.conexionApi.ConexionApi;

public class StartApp extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);

        Thread timeTread = new Thread(){
            public void run(){
                try{
                    sleep(2700);
                    //fillArrays();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent i = new Intent(StartApp.this, MainActivity.class);
                    startActivity(i);



                }
            }
        };
        timeTread.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

}