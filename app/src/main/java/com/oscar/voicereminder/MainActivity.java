package com.oscar.voicereminder;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Código de identificación para la respuesta del micrófono
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    // Declaramos los elementos de la interfaz
    TextView textoReconocido;
    Button btnGrabar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazamos las variables con los componentes del XML
        textoReconocido = findViewById(R.id.textoReconocido);
        btnGrabar = findViewById(R.id.btnGrabar);

        // Configuramos el click del botón
        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escucharVoz();
            }
        });
    }

    private void escucharVoz() {
        // Intent para abrir el reconocimiento de voz de Google
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Dime tu recordatorio...");

        try {
            // Lanzamos la actividad esperando un resultado
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            // Por si el dispositivo no tiene Google Voice instalado
            Toast.makeText(this, "Error: Tu móvil no soporta voz", Toast.LENGTH_SHORT).show();
        }
    }

    // Este método se ejecuta cuando terminamos de hablar
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Comprobamos que el resultado sea de nuestra petición de voz
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            // Extraemos el texto reconocido
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                // Ponemos el texto en el TextView para verlo
                textoReconocido.setText(result.get(0));
            }
        }
    }
}