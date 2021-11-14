package at.jstrillinger.strillinger_uc_aufgabe_03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Playground {

    private int whosOnTurn;
    private int[] score;
    private Card[][] cards;
    private boolean isInitialized = false;
    public int x;
    public int y;

    public Playground(){}

    public Playground(int x, int y){
        this.x = x;
        this.y = y;
        cards = new Card[x][y];
        if(!isInitialized){
            init();
        }
    }

    public void init(){
        //values für die verschiedenen Karten zuweisen, dabei ist zu beachten, das Pärchen immer die selben values haben
        //Idee zum Vorgehen: values in eine ArrayList/ ein Array geben, dann mit Random werte generieren lassen.
        //Zu beachten: Man braucht nur halb so viele Werte, wie man Karten hat, weil es ja Pärchen sind
        //Lösungswerg: Überprüfen, ob das bestimmte value bereits existiert
        if(isInitialized){
            return;
        }

        Random rand = new Random();
        ArrayList<Integer> helpPics = new ArrayList<Integer>();
        List<Card> helpCard = new ArrayList<Card>();
        int[] picsArr = MemoryActivity.getPicsArray();

        for(int i = 0; i < x*y; i++){
            helpCard.add(new Card());
        }

        int helpVal = 0;

        for(int i = 0; i < helpCard.size(); i+=2){
            while (true) {
                //int help = rand.nextInt(10/2);
                helpVal = rand.nextInt(picsArr.length - 0) + 0;
                if (!helpPics.contains(helpVal)) {
                    helpCard.get(i).setValue(picsArr[helpVal]);
                    helpCard.get(i).setVisible(true);
                    helpCard.get(i + 1).setValue(picsArr[helpVal]);
                    helpPics.add(helpVal);
                    break;
                }
            }
            helpVal = i;
        }

        Collections.shuffle(helpCard);

        int temp = 0;
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                cards[i][j] = helpCard.get(temp);
                temp++;
            }
        }

        isInitialized = true;

        //Position: Herausfinden, wie man Position setzt und wie man prüft, ob dort schon was liegt
        //Lösung: vermutlich die equals von Position

    }

    private Card play(Position pos){
        return cards[pos.x][pos.y];
    }

    boolean finished(){
        for(int i = 0; i < cards.length; i++){
            for(int j = 0; j < cards[i].length; i++){
                if(!cards[i][j].isVisible()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isPair(Position pos1, Position pos2){
        return(cards[pos1.x][pos1.y].getValue() == cards[pos2.x][pos2.y].getValue());
    }

    public Card getCard(Position pos){
        return cards[pos.x][pos.y];
    }

    private int getNrPairs(){
        int numAnz = cards.length/2;
        return numAnz;
    }

    private Card getRandomCard(Random r){
        int randomRow = r.nextInt(x-1)+1;
        int randomCol = r.nextInt(y-1)+1;
        return cards[randomRow-1][randomCol-1];
    }

    @Override
    public String toString() {
        return "Playground{" +
                "whosOnTurn=" + whosOnTurn +
                ", score=" + Arrays.toString(score) +
                ", cards=" + Arrays.toString(cards) +
                ", isInitialized=" + isInitialized +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
