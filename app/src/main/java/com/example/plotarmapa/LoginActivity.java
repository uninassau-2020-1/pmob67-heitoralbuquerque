package com.example.plotarmapa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plotarmapa.Database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText txtEmail, txtSenha ;
    Button btnLogin, btnCadastro;
    Boolean permissao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(this);

        txtEmail = (EditText)findViewById(R.id.txtEmailLogin);
        txtSenha = (EditText)findViewById(R.id.txtSenhaLogin);
        btnLogin = (Button)findViewById(R.id.btnEntrar);
        btnCadastro  = (Button)findViewById(R.id.btnCadastrarLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String  senha = txtSenha.getText().toString();

                permissao = db.Autentication(email,  senha);

               if (permissao == true){
                   Toast.makeText(getApplicationContext(), "Login realizado com sucesso!",  Toast.LENGTH_SHORT).show();
               }else{
                    Toast.makeText(getApplicationContext(),  "Login não realizado",  Toast.LENGTH_SHORT).show();
               }

               if (permissao ==  true){
                   Intent intent = new Intent(LoginActivity.this, informarCepActivity.class);
                   startActivity(intent);
               }
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new  Intent(LoginActivity.this,  TelaInicial.class);

                startActivity(intent);
            }
        });
    }
}
