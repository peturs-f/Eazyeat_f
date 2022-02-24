package com.example.eazyeat;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.core.ImageProxy;

import android.provider.MediaStore;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.eazyeat.databinding.ActivityScrollingBinding;
import com.google.mlkit.vision.common.InputImage;

public class ScrollingActivity extends AppCompatActivity {

    private ActivityScrollingBinding binding;
    private final int pic_id = 123;
    private InputImage immagine = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        EditText codP = (EditText) findViewById(R.id.etxCodiceProdotto);
        WebView srcProdotto = (WebView) findViewById(R.id.wevPaginaProdotto);                       // WebView per la visualizzazione del Browser integrata
        srcProdotto.setVisibility(View.INVISIBLE);
        srcProdotto.setWebViewClient(new WebViewClient());

        FloatingActionButton fab = binding.fabCerca;                                                // Pulsante per accedere alla schermata di scansione del codice a barre
        Button cerca = (Button) findViewById(R.id.btnCerca);                                        // Pulsante che permette la ricerca del codice del prodotto
        Button ricette = (Button) findViewById(R.id.btnRicette);                                    // Pulsante che farà visualizzare le ricette simili al prodotto

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backed = getIntent();
                boolean flag = backed.hasExtra("logged");

                if(flag){
                    Snackbar.make(view, "Utente Loggato", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    ScannerActivity test = new ScannerActivity();

                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camera_intent, pic_id);
                    test.analyze((ImageProxy) immagine);
                    Intent cod = getIntent();

                    Snackbar.make(view, "Codice - " + cod.getStringExtra("codice"), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    /**/
                    srcProdotto.loadUrl("https://it.openfoodfacts.org/product/" + cod.getStringExtra("codice"));
                    codP.setText(cod.getStringExtra("codice"));
                    srcProdotto.setVisibility(View.VISIBLE);
                    /**/
                }
                else {
                    Snackbar.make(view, "E' necessario aver effettuato il Login per poter scansionare il prodotto ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(codP.getText().toString().equals(""))
                    Snackbar.make(view, "Inserire un codice del prodotto", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else {
                    srcProdotto.loadUrl("https://it.openfoodfacts.org/product/" + codP.getText().toString());       // Ricerca il prodotto sul Database e lo carica sulla WebView
                    srcProdotto.setVisibility(View.VISIBLE);
                }
            }
        });

        ricette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // srcProdotto.loadUrl("https://it.openfoodfacts.org/product/" + codP.getText().toString() + "/");
                if(srcProdotto.getUrl().toString().equals("")) {
                    srcProdotto.loadUrl("https://www.google.com/search?q=" + codP.getText().toString() + " ricette");
                    srcProdotto.setVisibility(View.VISIBLE);
                }
                else {                                                                              // Ricerca di ricette simili al prodotto su Google
                    srcProdotto.loadUrl("https://www.google.com/search?q=" + srcProdotto.getUrl().substring(51).replace('-', ' ') + " ricette");
                    srcProdotto.setVisibility(view.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Creazione del menu con i pulsanti 'Registrazione' e 'Login'
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {                                           // Menu collocato in alto a destra
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.login) {                                                                     // Reindirizzamento alla pagina di 'Login'
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }else if(id == R.id.registrazione) {                                                        // Reindirizzamento alla pagina di 'Registrazione'
            Intent r = new Intent(getApplicationContext(), RegisterActivity.class);
            Toast reg = new Toast(getApplicationContext());

            reg.setText("Passaggio alla schermata di Registrazione");
            reg.show();
            startActivity(r);

        }else
            return super.onOptionsItemSelected(item);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id) {
            immagine = (InputImage) data.getExtras().get("data");
        }
    }
}