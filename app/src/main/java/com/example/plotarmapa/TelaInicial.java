package com.example.plotarmapa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plotarmapa.Database.DatabaseHelper;

public class TelaInicial extends AppCompatActivity {
    DatabaseHelper db;
    EditText txtEmail, txtSenha, txtConfSenha ;
    Button btnCadastrar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        db = new DatabaseHelper(this);

        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtSenha = (EditText)findViewById(R.id.txtSenha);
        txtConfSenha = (EditText)findViewById(R.id.txtConfSenha);
        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();
                String confSenha = txtConfSenha.getText().toString();

                if (email.equals("") || senha.equals("") || confSenha.equals("")){
                    Toast.makeText(getApplicationContext(), "Algum campo necessita ser preenchido", Toast.LENGTH_SHORT).show();
                }else{
                    if(senha.equals(confSenha)){
                        Boolean chkEmail = db.checkEmail(email);
                        if(chkEmail == true){
                            Boolean insert  = db.Insert(email, senha);
                            if(insert == true){
                                Toast.makeText(getApplicationContext(), "Cadastrado com sucesso!!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Email já cadastrado", Toast.LENGTH_SHORT ).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Senhas não conferem", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
    }
}

