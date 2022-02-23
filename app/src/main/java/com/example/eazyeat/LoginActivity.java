package com.example.eazyeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    // Database contenete gli utenti registrati nell'applicazione e la relativa connessione
    private DatabaseReference userDB = FirebaseDatabase.getInstance("https://eazyeat-5e189-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_2);
        Intent i = getIntent(), back = new Intent(getApplicationContext(), ScrollingActivity.class);
        final User[] utDB = {new User()};

        EditText email = (EditText) findViewById(R.id.etxEmail);
        EditText password = (EditText) findViewById(R.id.etxPassword);

        Button accesso = (Button) findViewById(R.id.btnAccedi);
        accesso.setOnClickListener(new View.OnClickListener() {                                     // Pulsante per l'accesso dell'utente e permettergli di scansionare il codice a barre
            @Override
            public void onClick(View view) {

                // back.putExtra("logged", true);
                Query queryMail = userDB.child("User").orderByChild("email").equalTo(email.getText().toString());  // Query effettuato sulla email inserita nel database
                queryMail.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren())
                            utDB[0] = singleSnapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                if(email.getText().toString().equals(""))                                           // Controllo sull'input dell'utente
                    Snackbar.make(view, "Il campo 'email' è obbligatorio", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else if(password.getText().toString().equals(""))
                    Snackbar.make(view, "Il campo 'password' è obbligatorio", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else if(!(password.getText().toString().equals(utDB[0].getPassword())) && !(email.getText().toString().equals(utDB[0].getEmail())))
                    Snackbar.make(view, "Password Errata", Snackbar.LENGTH_LONG)                // Confronto della password presente nel DB e quella inserita dall'utente
                            .setAction("Action", null).show();
                else if(password.getText().toString().equals(utDB[0].getPassword()) && email.getText().toString().equals(utDB[0].getEmail())){
                    back.putExtra("logged", true);
                    startActivity(back);
                }
             }
        });

        Button registrazione = (Button) findViewById(R.id.btnRegisterLogin);
        registrazione.setOnClickListener(new View.OnClickListener() {                               // Pullsante che reindirizza alla schermata di Registrazione
            @Override
            public void onClick(View view) {
                Intent r = new Intent(getApplicationContext(), RegisterActivity.class);
                Toast reg = new Toast(getApplicationContext());

                reg.setText("Passaggio alla schermata di Registrazione");
                reg.show();
                startActivity(r);
            }
        });
    }
}
