package com.example.applicationlac;

        import java.text.DateFormat;
        import java.util.Calendar;

public class Releve {
    protected int jour;
    protected int mois;
    protected String heure;
    protected float temp;
    //Evolution pour récupérer la date du jour automatiquement.
    //protected DateFormat dateNow = DateFormat.getDateInstance(DateFormat.SHORT);


    //constructeur paramétré
    public Releve(float temperature ,String heure){
        this.jour = Calendar.DAY_OF_WEEK;
        this.mois = Calendar.MONTH;
        this.heure = heure;
        this.temp = temperature;
    }

    //constructeur paramétré
    public Releve(int jour, int mois, float temperature ,String heure){
        this.jour = jour;
        this.mois = mois;
        this.heure = heure;
        this.temp = temperature;
    }


    //les accesseurs
    public int getJour(){
        return jour;
    }
    public void setJour(int jour){
        this.jour = jour;
    }
    public int getMois(){
        return mois;
    }
    public void setMois(int mois) {
        this.mois = mois;
    }
    public String getHeure(){
        return heure;
    }

    //les mutateurs
    public void setHeure(String heure){
        this.heure = heure;
    }

    public float getTemp(){
        return temp;
    }

    public void setTemp(float temp){
        this.temp = temp;
    }
}
