package com.example.eazyeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_actvity);
        Intent r = getIntent(), back = new Intent(getApplicationContext(), ScrollingActivity.class);

        EditText nome = (EditText) findViewById(R.id.extNome);
        EditText mail = (EditText) findViewById(R.id.etxMailR);
        EditText password = (EditText) findViewById(R.id.etxPswR);
        EditText pswConferma = (EditText) findViewById(R.id.etxPswR_C);

        Button register = (Button) findViewById(R.id.btnRegister);                                  // Pulsante per la registrazione dell'utente
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nome.getText().toString().equals(""))
                    Snackbar.make(view, "Il campo 'nome' è obbligatorio", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else if(mail.getText().toString().equals(""))
                    Snackbar.make(view, "Il campo 'email' è obbligatorio", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else if(password.getText().toString().equals(""))
                    Snackbar.make(view, "Il campo 'password' è obbligatorio", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else if(password.getText().length() < 8)
                    Snackbar.make(view, "Password troppo corta, minimo 8 caratteri", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else if(!(password.getText().toString().equals(pswConferma.getText().toString())))
                    Snackbar.make(view, "Le password non combaciano", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else {                                                                              // Accesso al database e caricamento dei dati dell'utente
                    User utente = new User(nome.getText().toString(), mail.getText().toString(), password.getText().toString(), true);
                    DatabaseReference userDB = FirebaseDatabase.getInstance("https://eazyeat-5e189-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

                    userDB.child("User").child(utente.getNome()).setValue(utente);
                    back.putExtra("logged", true);
                    startActivity(back);
                }
            }
        });


    }
}
