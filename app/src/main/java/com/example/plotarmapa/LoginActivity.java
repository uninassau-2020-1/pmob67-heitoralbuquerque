package com.example.plotarmapa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plotarmapa.Database.DatabaseHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText txtEmail, txtSenha ;
    Button btnLogin, btnCadastro;
    Boolean permissao;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
    SignInButton btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(this);

        txtEmail = (EditText)findViewById(R.id.txtEmailLogin);
        txtSenha = (EditText)findViewById(R.id.txtSenhaLogin);
        btnLogin = (Button)findViewById(R.id.btnEntrar);
        btnCadastro  = (Button)findViewById(R.id.btnCadastrarLogin);
        btnGoogle = (SignInButton)findViewById(R.id.btnGoogle);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        btnGoogle.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case  R.id.btnGoogle:
                        signIn();
                    break;
                }
            }
        });


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

   @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account != null){
            Intent intent = new Intent(LoginActivity.this, informarCepActivity.class);
            startActivity(intent);
        }
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(LoginActivity.this, informarCepActivity.class);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Erro", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
