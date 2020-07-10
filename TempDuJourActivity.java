package com.example.applicationlac;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TempDuJourActivity extends AppCompatActivity {

    int jour;
    int mois;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_du_jour);

        Button btnRechercher = (Button) findViewById(R.id.buttonRechercher);

        final Spinner spinnerJour = (Spinner) findViewById(R.id.spinnerJour);
        final Spinner spinnerMois = (Spinner) findViewById(R.id.spinnerMois);



        //On créer des listes que l'ont affecte a nos spinner.
        //Liste des jours de 1 à 31
        List<String> listeJour = new ArrayList<>();
        for(int i = 1 ; i < 32 ; i++)
        {
            listeJour.add(""+i);
        }
        ArrayAdapter<String> adapterListeJour = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeJour);
        adapterListeJour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJour.setAdapter(adapterListeJour);

        //Liste des mois de 1 à 12
        List<String> listeMois = new ArrayList<>();
        for(int e = 1 ; e < 13 ; e++)
        {
            listeMois.add(""+e);
        }
        ArrayAdapter<String> adapterListeMois = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeMois);
        adapterListeMois.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMois.setAdapter(adapterListeMois);

        spinnerJour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jour = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(TempDuJourActivity.this, "Vous devez sélectionner un jour", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerMois.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mois = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(TempDuJourActivity.this, "Vous devez sélectionner un mois", Toast.LENGTH_SHORT).show();
            }
        });

        View.OnClickListener ecouteurSaisieDesTemp = new View.OnClickListener() {
            @Override
            public void onClick(View y) {
                switch (y.getId()) {
                    case R.id.buttonRechercher:
                        afficherReleve(jour,mois);
                        break;
                }
            }
        };
        //On place l'écouteur sur nos bontons.
        btnRechercher.setOnClickListener(ecouteurSaisieDesTemp);
    }

    private void afficherReleve(int jour, int mois) {

        ListView listeTempJour = (ListView) findViewById(R.id.ListeTempJour);
        BdAdapter releveBdd = new BdAdapter(this);
        releveBdd.open();
        Cursor c = releveBdd.getReleveWithDate(jour,mois);
        Toast.makeText(getApplicationContext(), "il y a " + String.valueOf(c.getCount()) + " articles dans la BD", Toast.LENGTH_LONG).show();
        // colonnes à afficher
        String[] columns = new String[]{BdAdapter.COL_JOUR, BdAdapter.COL_MOIS, BdAdapter.COL_HEURE, BdAdapter.COL_TEMPERATURE};
        // champs dans lesquelles afficher les colonnes
        int[] to = new int[]{R.id.textViewJour, R.id.textViewMois, R.id.textViewHeure, R.id.textViewTemp};
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this, R.layout.activity_list_temp, c, columns, to,0);
        // Assign adapter to ListView
        listeTempJour.setAdapter(dataAdapter);
        releveBdd.close();
    }

}

