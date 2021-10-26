package com.example.inngageintegrationjavasample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.inngage.sdk.InngageIntentService;
import br.com.inngage.sdk.InngageUtils;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "INNGAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Para fazer a integração padrão utilize o método handleSubscription()
        // Para fazer a integração utlizando campos customizáveis utilize o método handleSubscriptionWithCustomFields()
        handleSubscription();

        //handleSubscriptionWithCustomFields();
    }

    /**
     * Método responsável por fazer a integração padrão (sem campos customizados)
     */
    private void handleSubscription() {

        String myInngageAppToken = InngageConstants.inngageAppToken;
        String myInngageEnvironment = InngageConstants.inngageEnvironment;
        String myGoogleMessageProvider = InngageConstants.googleMessageProvider;

        InngageIntentService.startInit(
                this,
                myInngageAppToken,
                "userIdentifierExample", //Seu identificador
                myInngageEnvironment,
                myGoogleMessageProvider);


        InngageUtils.handleNotification(this, getIntent(), myInngageAppToken, myInngageEnvironment);
    }

    /**
     * Método responsável por fazer a integração utilizando campos customizáveis
     * Altere os campos conforme sua necessidade
     */
    private void handleSubscriptionWithCustomFields() {

        String myInngageAppToken = InngageConstants.inngageAppToken;
        String myInngageEnvironment = InngageConstants.inngageEnvironment;
        String myGoogleMessageProvider = InngageConstants.googleMessageProvider;

        JSONObject jsonCustomField = new JSONObject();

        try {
            jsonCustomField.put("nome", "jean");
            jsonCustomField.put("email", "jean@gmail.com");
            jsonCustomField.put("telefone", "");
            jsonCustomField.put("dataRegistro", "");
            jsonCustomField.put("dataNascimento", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Adicione o "jsonCustomField" como na chamada abaixo
        InngageIntentService.startInit(
                this,
                myInngageAppToken,
                "IdentifierWithCustomFields", //Seu identificador
                myInngageEnvironment,
                myGoogleMessageProvider,
                jsonCustomField);

        InngageUtils.handleNotification(this, getIntent(), myInngageAppToken, myInngageEnvironment);
    }
}