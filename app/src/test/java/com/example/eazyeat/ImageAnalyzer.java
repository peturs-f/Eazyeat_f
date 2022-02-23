package com.example.eazyeat;

import android.media.Image;

import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.mlkit.vision.common.InputImage;

public interface ImageAnalyzer extends ImageAnalysis.Analyzer {                                     // Interfaccia per l'analisi dell'immagine prelvata dalla fotocamera

    @Override
    public default void analyze(ImageProxy imageProxy) {
        Image mediaImage = imageProxy.getImage();
        if (mediaImage != null) {
            InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
        }
    }

}
