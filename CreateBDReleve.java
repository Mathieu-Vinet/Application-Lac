package com.example.applicationlac;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateBDReleve extends SQLiteOpenHelper {
        private static final String TABLE_RELEVE = "table_releve";
        public static  final String COL_ID = "_id";
        public static final String COL_JOUR = "JOUR";
        public static final String COL_MOIS = "MOIS";
        public static final String COL_HEURE = "HEURE";
        public static final String COL_TEMPERATURE = "TEMPERATURE";

        private static final String CREATE_BDD = "CREATE TABLE " + TABLE_RELEVE + " ("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COL_JOUR + " TEXT NOT NULL, " + COL_MOIS + " TEXT NOT NULL, " + COL_HEURE + " TEXT NOT NULL," + COL_TEMPERATURE + " REAL NOT NULL);";

        //constructeur paramétré
        public CreateBDReleve(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //méthodes d'instance permettant la gestion de l'objet
        public void onCreate(SQLiteDatabase db){
            // appelée lorsqu’aucune base n’existe et que la classe utilitaire doit en créer une
            //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
            db.execSQL(CREATE_BDD);
        }
        // appelée si la version de la base a changé
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //On peut  supprimer la table et de la recréer
            db.execSQL("DROP TABLE " + TABLE_RELEVE + ";");
            onCreate(db);
        }

    }




