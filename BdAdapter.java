package com.example.applicationlac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BdAdapter {

        protected int jour;
        protected int mois;
        static final int VERSION_BDD =1;
        private static final String NOM_BDD = "articles.db";
        static final String TABLE_RELEVE = "table_releve";
        static final String COL_ID = "_id";
        static final int NUM_COL_ID = 0;
        static final String COL_JOUR = "JOUR";
        static final int NUM_COL_JOUR = 1;
        static final String COL_MOIS = "MOIS";
        static final int NUM_COL_MOIS = 2;
        static final String COL_HEURE = "HEURE";
        static final int NUM_COL_HEURE = 3;
        static final String COL_TEMPERATURE = "TEMPERATURE";
        static final int NUM_COL_TEMPERATURE = 4;
        private CreateBDReleve bdReleve;
        private Context context;
        private SQLiteDatabase db;

        //le constructeur
        public BdAdapter  (Context context){
            this.context = context;
            bdReleve = new CreateBDReleve(context, NOM_BDD, null, VERSION_BDD);
        }

        //si la base n’existe pas, l’objet SQLiteOpenHelper exécute la méthode onCreate
        // si la version de la base a changé, la méthode onUpgrade sera lancée
        // dans les 2 cas l’appel à getWritableDatabase ou getReadableDatabase renverra  la base
        // de données en cache, nouvellement ouverte, nouvellement créée ou mise à jour

        //les méthodes d'instance
        public BdAdapter  open(){
            db = bdReleve.getWritableDatabase();
            return this;
        }
        public BdAdapter  close (){
            db.close();
            return null;
        }
        public long insererTempérature (Releve uneTemperature){
            //Création d'un ContentValues (fonctionne comme une HashMap)
            ContentValues values = new ContentValues();
            //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne où on veut mettre la valeur)
            values.put(COL_JOUR, uneTemperature.getJour());
            values.put(COL_MOIS, uneTemperature.getMois());
            values.put(COL_HEURE, uneTemperature.getHeure());
            values.put(COL_TEMPERATURE, uneTemperature.getTemp());
            //on insère l'objet dans la BDD via le ContentValues
            return db.insert(TABLE_RELEVE, null, values);
        }

        private Releve cursorToArticle(Cursor c){ //Cette méthode permet de convertir un cursor en un article
            //si aucun élément n'a été retourné dans la requête, on renvoie null
            if (c.getCount() == 0)
                return null;
            //Sinon
            c.moveToFirst();   //on se place sur le premier élément
            Releve unReleve = new Releve(0,null);  //On créé un article
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            unReleve.setJour(c.getInt(NUM_COL_JOUR));
            unReleve.setMois(c.getInt(NUM_COL_MOIS));
            unReleve.setHeure(c.getString(NUM_COL_HEURE));
            unReleve.setTemp(c.getFloat(NUM_COL_TEMPERATURE));
            c.close();     //On ferme le cursor
            return unReleve;  //On retourne l'article
        }

        public Cursor getReleveWithDate(int jour, int mois){
            //Récupère dans un Cursor les valeurs correspondant à un releve grave a sa date)
            return db.rawQuery("SELECT * FROM TABLE_RELEVE WHERE JOUR = ? AND MOIS = ?", new String[] {String.valueOf(jour),String.valueOf(mois)});

        }

        public Cursor TempByMoisBy4H(int mois){
            //Récupère dans un Cursor les valeurs correspondant à un releve grave a sa date)
            return db.rawQuery("SELECT TEMPERATURE FROM TABLE_RELEVE WHERE MOIS = ? AND HEURE = ?", new String[] {String.valueOf(mois),"4h"});
        }
        public Cursor TempByMoisBy8H(int mois){
            //Récupère dans un Cursor les valeurs correspondant à un releve grave a sa date)
            return db.rawQuery("SELECT TEMPERATURE FROM TABLE_RELEVE WHERE MOIS = ? AND HEURE = ?", new String[] {String.valueOf(mois),"8h"});
        }

        public Cursor TempByMoisBy12H(int mois){
            //Récupère dans un Cursor les valeurs correspondant à un releve grave a sa date)
            return db.rawQuery("SELECT TEMPERATURE FROM TABLE_RELEVE WHERE MOIS = ? AND HEURE = ?", new String[] {String.valueOf(mois),"12h"});
        }

        public Cursor TempByMoisBy16H(int mois){
            //Récupère dans un Cursor les valeurs correspondant à un releve grave a sa date)
            return db.rawQuery("SELECT TEMPERATURE FROM TABLE_RELEVE WHERE MOIS = ? AND HEURE = ?", new String[] {String.valueOf(mois),"16h"});
        }

        public Cursor TempByMoisBy20H(int mois){
            //Récupère dans un Cursor les valeurs correspondant à un releve grave a sa date)
            return db.rawQuery("SELECT TEMPERATURE FROM TABLE_RELEVE WHERE MOIS = ? AND HEURE = ?", new String[] {String.valueOf(mois),"20h"});
        }

        public Cursor TempByMoisBy24H(int mois){
            //Récupère dans un Cursor les valeurs correspondant à un releve grave a sa date)
            return db.rawQuery("SELECT TEMPERATURE FROM TABLE_RELEVE WHERE MOIS = ? AND HEURE = ?", new String[] {String.valueOf(mois),"00h"});
        }

        public int updateArticle(String ref, Releve unReleve){
            //La mise à jour d'un article dans la BDD fonctionne plus ou moins comme une insertion
            //il faut simple préciser quel article on doit mettre à jour grâce à sa référence
            ContentValues values = new ContentValues();
            values.put(COL_MOIS, unReleve.getMois());
            values.put(COL_HEURE, unReleve.getHeure());
            values.put(COL_TEMPERATURE, unReleve.getTemp());
            return db.update(TABLE_RELEVE, values, COL_JOUR + " = \"" +ref+"\"", null);
        }
        public int removeArticleWithRef(String ref){
            //Suppression d'un article de la BDD grâce à sa référence
            return db.delete(TABLE_RELEVE, COL_JOUR + " = \"" +ref+"\"", null);
        }

        public Cursor getData(){
            return db.rawQuery("SELECT * FROM TABLE_RELEVE", null);
        }


    }



