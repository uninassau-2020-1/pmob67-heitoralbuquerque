package com.example.plotarmapa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plotarmapa.GoogleGeoCoding.GoogleGeoCode;
import com.example.plotarmapa.GoogleGeoCoding.GoogleGeoLatLng;
import com.example.plotarmapa.GoogleGeoCoding.GoogleGeoResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class informarCepActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informar_cep);

        final EditText cep = findViewById(R.id.etMain_cep);
        final String resposta ;
        final Button  btnBuscarCep = findViewById(R.id.btnMain_buscarCep);
        final Button btnDeslogar = findViewById(R.id.btnDeslogar);
        final String[] coordenadas = new String[2];

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 switch(view.getId()){
                     case  R.id.btnDeslogar:
                            signOut();
                     break;
                }
            }
        });

        btnBuscarCep.setOnClickListener(view -> {
            if (cep.getText().length() == 8 && cep.getText().toString() != ""){
                try{
                    GoogleGeoCode geoCode = new HttpService(cep.getText().toString()).execute().get();
                   for( GoogleGeoResult item : geoCode.getResults()){
                       coordenadas[0] =  item.getGeometry().getLocation().getLat();
                       coordenadas[1] =  item.getGeometry().getLocation().getLng();
                   }

                }catch(InterruptedException e){
                    e.printStackTrace();
                }catch(ExecutionException e){
                    e.printStackTrace();
                }

            }

            coordenadas cd = (coordenadas) getApplicationContext();

            cd.setLatitude(Double.parseDouble(coordenadas[0]));
            cd.setLongitude(Double.parseDouble(coordenadas[1]));

            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);

        });

    }
    private void  signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>(){
            public  void onComplete(@NonNull Task<Void> task){

                Toast.makeText(informarCepActivity.this , "Deslogado com sucesso!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

}
