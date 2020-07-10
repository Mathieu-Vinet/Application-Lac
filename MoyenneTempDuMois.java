package com.example.applicationlac;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MoyenneTempDuMois extends AppCompatActivity {

    int mois;
    boolean farhneit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moyennetempdumois);

        RadioButton btnFahrenheit = (RadioButton) findViewById(R.id.radioButtonFahreinheit);
        RadioButton btnCelsius = (RadioButton) findViewById(R.id.radioButtonCelsius);
        Button btnQuitter = (Button) findViewById(R.id.buttonQuitter);
        Button btnTempJour = (Button) findViewById(R.id.buttonTempDuJour);
        Button btnSaisirTemp = (Button) findViewById(R.id.buttonSaisieTemp);
        Button btnRechercher = (Button) findViewById(R.id.buttonRechercher);

        final Spinner spinnerMois = (Spinner) findViewById(R.id.spinnerMois);
        //Liste des mois de 1 à 12
        List<String> listeMois = new ArrayList<>();
        for(int e = 1 ; e < 13 ; e++)
        {
            listeMois.add(""+e);
        }
        ArrayAdapter<String> adapterListeMois = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeMois);
        adapterListeMois.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMois.setAdapter(adapterListeMois);

        spinnerMois.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mois = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MoyenneTempDuMois.this, "Vous devez sélectionner un mois", Toast.LENGTH_SHORT).show();
            }
        });

        View.OnClickListener ecouteurSaisieDesTemp = new View.OnClickListener() {
            @Override
            public void onClick(View y) {
                switch (y.getId()) {
                    //Si le bouton est cliqué on passe à la page MainActivty
                    case R.id.buttonSaisieTemp:
                        Intent intent = new Intent (MoyenneTempDuMois.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    //Si le bouton est cliqué on passe à la page TempDuJour
                    case R.id.buttonTempDuJour:
                        Intent intente = new Intent (MoyenneTempDuMois.this, TempDuJourActivity.class);
                        startActivity(intente);
                        break;
                    //Si le bouton est cliqué l'application se ferme.
                    case R.id.buttonQuitter:
                        finish();
                        break;
                    case R.id.radioButtonFahreinheit:
                        farhneit = true;
                        break;
                    case R.id.radioButtonCelsius:
                        farhneit = false;
                        break;
                    case R.id.buttonRechercher:
                        calculMoyenneMois(mois);
                        break;
                }
            }
        };
        //On place l'écouteur sur nos bontons.
        btnQuitter.setOnClickListener(ecouteurSaisieDesTemp);
        btnSaisirTemp.setOnClickListener(ecouteurSaisieDesTemp);
        btnTempJour.setOnClickListener(ecouteurSaisieDesTemp);
        btnRechercher.setOnClickListener(ecouteurSaisieDesTemp);
        btnFahrenheit.setOnClickListener(ecouteurSaisieDesTemp);
        btnCelsius.setOnClickListener(ecouteurSaisieDesTemp);
    }

    public void calculMoyenneMois (int mois){
        float temperature4h = 0;
        float temperature8h = 0;
        float temperature12h = 0;
        float temperature16h = 0;
        float temperature20h = 0;
        float temperature24h = 0;
        BdAdapter releveBdd = new BdAdapter(this);
        releveBdd.open();
        Cursor H4 = releveBdd.TempByMoisBy4H(mois);
        H4.moveToFirst();
        if(H4 != null && H4.moveToFirst()) {
            do {
                temperature4h = temperature4h + H4.getFloat(0);
            } while (H4.moveToNext());
        }
        temperature4h = temperature4h/H4.getCount();
        Cursor H8 = releveBdd.TempByMoisBy8H(mois);
        H8.moveToFirst();
        if(H8 != null && H4.moveToFirst()) {
            do {
                temperature8h = temperature8h + H8.getFloat(0);
            } while (H8.moveToNext());
        }
        temperature8h = temperature8h/H8.getCount();
        Cursor H12 = releveBdd.TempByMoisBy12H(mois);
        H4.moveToFirst();
        if(H12 != null && H12.moveToFirst()) {
            do {
                temperature12h = temperature12h + H12.getFloat(0);
            } while (H12.moveToNext());
        }
        temperature12h = temperature12h/H12.getCount();
        Cursor H16 = releveBdd.TempByMoisBy16H(mois);
        H16.moveToFirst();
        if(H16 != null && H16.moveToFirst()) {
            do {
                temperature16h = temperature16h + H16.getFloat(0);
            } while (H16.moveToNext());
        }
        temperature16h = temperature16h/H16.getCount();
        Cursor H20 = releveBdd.TempByMoisBy20H(mois);
        H20.moveToFirst();
        if(H20 != null && H20.moveToFirst()) {
            do {
                temperature20h = temperature20h + H20.getFloat(0);
            } while (H20.moveToNext());
        }
        temperature20h = temperature20h/H20.getCount();
        Cursor H24 = releveBdd.TempByMoisBy24H(mois);
        H24.moveToFirst();
        if(H24 != null && H24.moveToFirst()) {
            do {
                temperature24h = temperature24h + H24.getFloat(0);
            } while (H24.moveToNext());
        }
        temperature24h = temperature24h/H24.getCount();
        if(farhneit == true){
            temperature4h = (temperature4h * (180/100)) + 32;
            temperature8h = (temperature8h * (180/100)) + 32;
            temperature12h = (temperature12h * (180/100)) + 32;
            temperature16h = (temperature16h * (180/100)) + 32;
            temperature20h = (temperature20h * (180/100)) + 32;
            temperature24h = (temperature24h * (180/100)) + 32;
        }
        releveBdd.close();
        TextView temp4h = (TextView) findViewById(R.id.textViewTemp4h);
        temp4h.setText(String.valueOf(temperature4h));
        TextView temp8h = (TextView) findViewById(R.id.textViewTemp8h);
        temp8h.setText(String.valueOf(temperature8h));
        TextView temp12h = (TextView) findViewById(R.id.textViewTemp12h);
        temp12h.setText(String.valueOf(temperature12h));
        TextView temp16h = (TextView) findViewById(R.id.textViewTemp16h);
        temp16h.setText(String.valueOf(temperature16h));
        TextView temp20h = (TextView) findViewById(R.id.textViewTemp20h);
        temp20h.setText(String.valueOf(temperature20h));
        TextView temp24h = (TextView) findViewById(R.id.textViewTemp24h);
        temp24h.setText(String.valueOf(temperature24h));
        return;
    }

}

