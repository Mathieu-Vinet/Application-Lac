package com.example.applicationlac;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String heure;
    int jour;
    int mois;
    float temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BdAdapter releveBdd = new BdAdapter(this);

        //Pour afficher la date du jour et l'heure actuelle.
        Date now = new Date();
        DateFormat dateformatter = DateFormat.getDateInstance(DateFormat.SHORT);
        DateFormat timeformatter = DateFormat.getTimeInstance(DateFormat.SHORT);
        TextView date = (TextView) findViewById(R.id.textViewDate);
        date.setText(dateformatter.format(now)+" "+timeformatter.format(now));

        //On creer nos 3 spinners.
        final Spinner spinnerJour = (Spinner) findViewById(R.id.spinnerJour);
        final Spinner spinnerMois = (Spinner) findViewById(R.id.spinnerMois);
        final Spinner spinnerHeure = (Spinner) findViewById(R.id.spinnerHeures);

        //On créer des listes que l'ont affecte a nos spinner.
        //Liste des jours de 1 à 31
        List<String> listeJour = new ArrayList<>();
        String JourAffecté;
        for(int i = 1 ; i < 32 ; i++)
        {
            listeJour.add(""+i);
        }
        ArrayAdapter<String> adapterListeJour = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeJour);
        adapterListeJour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJour.setAdapter(adapterListeJour);

        //Liste des mois de 1 à 12
        List<String> listeMois = new ArrayList<>();
        for(int e = 1 ; e < 13 ; e++){ listeMois.add(""+e); }
        ArrayAdapter<String> adapterListeMois = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeMois);
        adapterListeMois.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMois.setAdapter(adapterListeMois);

        //Liste des heures
        List<String> listeHeures = new ArrayList<>();
        listeHeures.add("4h");listeHeures.add("8h");listeHeures.add("12h");listeHeures.add("16h");listeHeures.add("20h");listeHeures.add("00h");
        ArrayAdapter<String> adapterListeHeures = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeHeures);
        adapterListeHeures.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHeure.setAdapter(adapterListeHeures);

        //On déclare nos 3 boutons qui nous servent à naviguer entre les pages (C'est notre menu).
        Button btnQuitter = (Button) findViewById(R.id.buttonQuitter);
        Button btnMoyenneTemp = (Button) findViewById(R.id.buttonTempMoyenneMois);
        Button btnJourTemp = (Button) findViewById(R.id.buttonTempDuJour);
        Button btnValider = (Button) findViewById(R.id.buttonValider);

        spinnerJour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jour = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "Vous devez sélectionner un jour", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerMois.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mois = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "Vous devez sélectionner un mois", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerHeure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                heure = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "Vous devez sélectionner une Heure", Toast.LENGTH_SHORT).show();
            }
        });

        View.OnClickListener ecouteurSaisieDesTemp = new View.OnClickListener() {
            @Override
            public void onClick(View y) {
                switch (y.getId()) {
                    //Si le bouton est cliqué on passe à la page TempDuJourActivity
                    case R.id.buttonTempDuJour:
                        Intent intent = new Intent (MainActivity.this, TempDuJourActivity.class);
                        startActivity(intent);
                        break;
                    //Si le bouton est cliqué on passe à la page MoyenneTempDuMois
                    case R.id.buttonTempMoyenneMois:
                        Intent intente = new Intent (MainActivity.this, MoyenneTempDuMois.class);
                        startActivity(intente);
                        break;
                    //Si le bouton est cliqué l'application se ferme.
                    case R.id.buttonQuitter:
                        finish();
                        break;
                    //Quand le bouton valider est cliqué on ajoute le relevé dans la BDD.
                    case R.id.buttonValider:
                        EditText txtTemp = (EditText) findViewById(R.id.editTextTemp);
                        temp = Float.parseFloat(txtTemp.getText().toString());
                        releveBdd.open();
                        Releve releve = new Releve(jour,mois,temp,heure);
                        releveBdd.insererTempérature(releve);
                        releveBdd.close();
                        Toast.makeText(MainActivity.this, "Un Releve a été ajouter à la BDD", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        //On place l'écouteur sur nos bontons.
        btnQuitter.setOnClickListener(ecouteurSaisieDesTemp);
        btnMoyenneTemp.setOnClickListener(ecouteurSaisieDesTemp);
        btnJourTemp.setOnClickListener(ecouteurSaisieDesTemp);
        btnValider.setOnClickListener(ecouteurSaisieDesTemp);
    }
}