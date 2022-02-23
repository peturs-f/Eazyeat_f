package com.example.eazyeat;


import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

public class ScannerActivity implements ImageAnalysis.Analyzer {

    // Creazione dell'API per la lettura di un barcode in protocollo EAN-13 (etichette ealimentari)
    private BarcodeScannerOptions options = new BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_EAN_13).build();
    private BarcodeScanner scanner = BarcodeScanning.getClient();
    public Intent prodotto = new Intent();

    @Override
    public void analyze(@NonNull ImageProxy image) {                                                // Funzione per la lettura delle immagini prelevate dall'API per la scansione
        Task<List<Barcode>> result = scanner.process((InputImage) image).addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
            @Override
            public void onSuccess(List<Barcode> barcodes) {                                         // In caso di successo
                for (Barcode barcode: barcodes) {
                    Rect bounds = barcode.getBoundingBox();
                    Point[] corners = barcode.getCornerPoints();
                    String rawValue = barcode.getRawValue();

                    switch (barcode.getValueType()) {                                                            // Controllo del valore del codice a barre scansionato
                        case Barcode.TYPE_PRODUCT:
                            prodotto.putExtra("codice", barcode.getDisplayValue());
                            break;
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
