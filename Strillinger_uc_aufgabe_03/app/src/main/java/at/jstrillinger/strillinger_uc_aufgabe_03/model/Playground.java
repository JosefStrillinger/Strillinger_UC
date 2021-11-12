package at.jstrillinger.strillinger_uc_aufgabe_03.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Playground {

    public int whosOnTurn;
    public int[] score = new int[]{};
    Card[][] cards;

    public Playground(int x, int y){

    }

    public void init(){
        //values für die verschiedenen Karten zuweisen, dabei ist zu beachten, das Pärchen immer die selben values haben
        //Idee zum Vorgehen: values in eine ArrayList/ ein Array geben, dann mit Random werte generieren lassen.
        //Zu beachten: Man braucht nur halb so viele Werte, wie man Karten hat, weil es ja Pärchen sind
        //Lösungswerg: Überprüfen, ob das bestimmte value bereits existiert

        Random rand = new Random();
        ArrayList<Integer> helpVal = new ArrayList<Integer>();
        for(int i = 1; i < getNrPairs(); i++){
            int help = rand.nextInt(getNrPairs()/2);
            if(!helpVal.contains(help)){
                helpVal.add(help);
            }
        }


        //Position: Herausfinden, wie man Position setzt und wie man prüft, ob dort schon was liegt
        //Lösung: vermutlich die equals von Position



        
        

    }

    private int getNrPairs(){
        int numAnz = cards.length/2;
        return numAnz;
    }

}
