package at.jstrillinger.strillinger_uc_aufgabe_03;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MemoryActivity extends AppCompatActivity {

    private int[] pics;
    private Playground field;
    private Position previousCard;
    private ImageButton[][] buttons;
    private LinearLayout playingField;
    private int pairs = 0;
    private boolean blockSelection = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playingField = (LinearLayout) findViewById(R.id.playingField);

        pics = getPicsArray();

        receiveMessage();
    }

    @Override
    public void onBackPressed(){
        Intent homeScreen = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(homeScreen);

    }

    private void generateGrid(int nrCol, int nrRows){

        buttons = new ImageButton[nrRows][nrCol];
        ArrayList<LinearLayout> playingRows = new ArrayList<LinearLayout>();

        for(int i = 0; i < nrRows; i++){
            playingRows.add(new LinearLayout(this));
            playingRows.get(playingRows.size()-1).setOrientation(LinearLayout.HORIZONTAL);
            playingRows.get(playingRows.size()-1).setLayoutParams(new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            playingRows.get(playingRows.size()-1).setGravity(Gravity.CENTER);

            playingField.addView(playingRows.get(playingRows.size()-1));
            for(int j = 0; j < nrCol; j++){
                buttons[i][j] = generateButton(new Position());

                playingRows.get(playingRows.size()-1).addView(buttons[i][j]);
            }
        }
    }

    private void generateAndAddRows(int row, int nrCols){

    }

    private ImageButton generateButton(Position pos){
        ImageButton button = new ImageButton(this);
        button.setImageResource(R.drawable.back);
        button.setLayoutParams(new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setForegroundGravity(Gravity.CENTER);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onButtonClick(view);
            }
        });

        ViewGroup.LayoutParams params = button.getLayoutParams();

        params.height = 150;
        params.width = 100;

        return button;
    }

    public static int[] getPicsArray() {
        int[] c = new int[20];

        c[0] = R.drawable.i000;
        c[1] = R.drawable.i001;
        c[2] = R.drawable.i002;
        c[3] = R.drawable.i003;
        c[4] = R.drawable.i004;
        c[5] = R.drawable.i005;
        c[6] = R.drawable.i006;
        c[7] = R.drawable.i007;
        c[8] = R.drawable.i008;
        c[9] = R.drawable.i009;
        c[10] = R.drawable.i010;
        c[11] = R.drawable.i011;
        c[12] = R.drawable.i012;
        c[13] = R.drawable.i013;
        c[14] = R.drawable.i014;
        c[15] = R.drawable.i015;
        c[16] = R.drawable.i016;
        c[17] = R.drawable.i017;
        c[18] = R.drawable.i018;
        c[19] = R.drawable.i019;
        c[20] = R.drawable.i020;
        c[21] = R.drawable.i021;
        c[22] = R.drawable.i022;
        c[23] = R.drawable.i023;
        c[24] = R.drawable.i024;
        c[25] = R.drawable.i025;
        c[26] = R.drawable.i026;
        c[27] = R.drawable.i027;
        c[28] = R.drawable.i028;
        c[29] = R.drawable.i029;
        c[30] = R.drawable.i030;
        c[31] = R.drawable.i031;
        c[32] = R.drawable.i032;
        c[33] = R.drawable.i033;
        c[34] = R.drawable.i034;
        c[35] = R.drawable.i035;
        c[36] = R.drawable.i036;
        c[37] = R.drawable.i037;
        c[38] = R.drawable.i038;
        c[39] = R.drawable.i039;
        c[40] = R.drawable.i040;
        c[41] = R.drawable.i041;
        c[42] = R.drawable.i042;
        c[43] = R.drawable.i043;
        c[44] = R.drawable.i044;
        c[45] = R.drawable.i045;
        c[46] = R.drawable.i046;
        c[47] = R.drawable.i047;
        c[48] = R.drawable.i048;
        c[49] = R.drawable.i049;
        c[50] = R.drawable.i050;
        c[51] = R.drawable.i051;
        c[52] = R.drawable.i052;
        c[53] = R.drawable.i053;
        c[54] = R.drawable.i054;
        c[55] = R.drawable.i055;
        c[56] = R.drawable.i056;
        c[57] = R.drawable.i057;
        c[58] = R.drawable.i058;
        c[59] = R.drawable.i059;
        c[60] = R.drawable.i060;
        c[61] = R.drawable.i061;
        c[62] = R.drawable.i062;
        c[63] = R.drawable.i063;
        return c;
    }


    public void onClick(View view){

    }

    public void onButtonClick(View view){
        /*
        if(blockSelection)
            return;
        */
        ImageButton button = (ImageButton) view;
        Position pos = null;
        for(int i = 0; i < buttons.length; i++){
            for(int j = 0; j < buttons[i].length; j++){
                if(buttons[i][j] == button){
                    pos = new Position(i, j);
                    break;
                }
            }
        }

        if(pos == null)
            return;

        if(field.getCard(pos).isVisible())
            return;

        int value = field.getCard(pos).getValue();

        button.setImageResource(pics[value-110]);

        if(previousCard == null){
            previousCard = pos;
        }else{
            blockSelection = true;
            if(!field.isPair(previousCard, pos)){
                closeCards(previousCard, pos);
            }else{
                blockSelection = false;
                pairs++;
                previousCard = null;
                field.getCard(pos).setVisible(true);
                if(pairs >= (field.x*field.y)/4){
                    if(field.finished())
                        showVictory();
                }
            }
        }
    }

    public void closeCards(Position pos1, Position pos2){
        class CloseTask extends TimerTask
        {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    buttons[pos1.x][pos1.y].setImageResource(R.drawable.back);
                    buttons[pos2.x][pos2.y].setImageResource(R.drawable.back);
                    previousCard = null;
                    blockSelection = false;
                });
            }
        }

        Timer timer = new Timer();
        timer.schedule(new CloseTask(),1000);
    }

    private void receiveMessage(){
        String fieldSize = getIntent().getStringExtra("gameSize");

        String[] sizes = fieldSize.split("x");

        int x = Integer.parseInt(sizes[0]);
        int y = Integer.parseInt(sizes[1]);


        field = new Playground(x, y);
        field.init();

        generateGrid(y, x);
    }


    private void showVictory(){
    }

}
