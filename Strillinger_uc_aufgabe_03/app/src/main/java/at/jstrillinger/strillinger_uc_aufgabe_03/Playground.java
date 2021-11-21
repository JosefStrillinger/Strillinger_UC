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

        if(isInitialized)
            return;

        Random rand = new Random();
        ArrayList<Integer> helpPics = new ArrayList<Integer>();
        List<Card> card = new ArrayList<Card>();
        int[] picsArr = MemoryActivity.getPicsArray();

        for(int i = 0; i < getNrPairs(); i++){
            card.add(new Card());
        }

        int helpVal;

        for(int i = 0; i < (card.size()); i+=2){
            while (true) {
                //int help = rand.nextInt(10/2);
                helpVal = rand.nextInt(picsArr.length - 0);
                if (!helpPics.contains(helpVal)) {
                    card.get(i).setValue(picsArr[helpVal]);
                    //card.get(i).setVisible(false);
                    card.get(i + 1).setValue(picsArr[helpVal]);
                    helpPics.add(helpVal);
                    break;
                }
            }

        }

        Collections.shuffle(card);

        int temp = 0;
        for(int i = 0; i < cards.length; i++){
            for(int j = 0; j < cards[0].length; j++){
                cards[i][j] = card.get(temp);
                temp++;
            }
        }

        isInitialized = true;

    }

    private Card play(Position pos){

        return cards[pos.x][pos.y];
    }

    boolean finished(){
        for(int i = 0; i < cards.length; i++){
            for(int j = 0; j < cards[i].length; j++){
                if(!cards[i][j].isVisible()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isPair(Position pos1, Position pos2){
        return(cards[pos1.x][pos1.y].getValue() == cards[pos2.x][pos2.y].getValue());
        //return(getCard(pos1).getValue() == getCard(pos2).getValue());
    }

    public Card getCard(Position pos){

        return cards[pos.x][pos.y];
    }

    public int getNrPairs(){

        return cards.length * cards[0].length;
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
