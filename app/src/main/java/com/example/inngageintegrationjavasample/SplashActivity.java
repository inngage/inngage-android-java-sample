package com.example.inngageintegrationjavasample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inngageintegrationjavasample.sdk.InngageIntentService;

//import br.com.inngage.sdk.InngageIntentService;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Chame o método startHandleNotifications() para capturar as notificações recebidas
        InngageIntentService.startHandleNotifications(this, getIntent());

        new Handler().postDelayed(() -> startActivity(new Intent(getApplicationContext(), MainActivity.class)), 2000);
    }
}