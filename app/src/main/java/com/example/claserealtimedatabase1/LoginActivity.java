package com.example.claserealtimedatabase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private EditText etUser;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegistrar;

    private SignInButton btnSignIn;
    private Button btnSignOut;
    private Button btnRevoke;
    //CallbackManager callbackManager;

    private GoogleApiClient apiClient;
    private static final int RC_SIGN_IN = 1001;

    private ProgressDialog progressDialog;
    private static final String TAG = "MainActivity";

    //Llamamos al autenticador
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //AccessToken accessToken = AccessToken.getCurrentAccessToken();
        //boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        //Ahora Instanciamos
        auth=FirebaseAuth.getInstance();

        etUser = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userE=etUser.getText().toString();
                String passE=etPassword.getText().toString();
                //Ahora validamos por si uno de los campos esta vacío
                if(TextUtils.isEmpty(userE)){
                    //por si falta correo
                    Toast.makeText(LoginActivity.this,"Inserte correo",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passE)){
                    //por si falta password
                    Toast.makeText(LoginActivity.this,"Inserte contraseña",Toast.LENGTH_SHORT).show();
                    return;
                }

                //Ahora usamos el Auth para que se logee una vez registrado
                auth.signInWithEmailAndPassword(userE,passE).
                        //Le pasamos la clase registro
                                addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(!task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this,"Clave o usuario incorrecto",Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                Toast.makeText(LoginActivity.this,"Bienvenido",Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(LoginActivity.this,producto.class);
                                startActivity(i);
                            }
                        });
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,Registro.class);
                startActivity(i);
            }
        });

        btnSignIn = (SignInButton)findViewById(R.id.sign_in_button);
        btnSignOut = (Button)findViewById(R.id.btnSignOut);
        btnRevoke = (Button)findViewById(R.id.btnRevoke);

        //Google API Client

        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Personalización del botón de login

        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setColorScheme(SignInButton.COLOR_LIGHT);
        btnSignIn.setScopes(gso.getScopeArray());

        //Eventos de los botones

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(apiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                updateUI(false);
                            }
                        });
            }
        });

        btnRevoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.revokeAccess(apiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                updateUI(false);
                            }
                        });
            }
        });

        updateUI(false);

    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            btnRevoke.setVisibility(View.VISIBLE);
        } else {

            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            btnRevoke.setVisibility(View.GONE);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result =
                    Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            handleSignInResult(result);
        }

        //Toast.makeText(this, "No existe el producto con dicho código", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, persona.class );
        startActivity(i);

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            //Usuario logueado --> Mostramos sus datos
            GoogleSignInAccount acct = result.getSignInAccount();
            updateUI(true);
        } else {
            //Usuario no logueado --> Lo mostramos como "Desconectado"
            updateUI(false);
        }
    }
}