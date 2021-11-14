package at.jstrillinger.strillinger_uc_aufgabe_03;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private Playground playground;
    private Spinner sizeSpinner;

    Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        sizeSpinner = (Spinner) findViewById(R.id.sizeSpinner);
        startButton = findViewById(R.id.startButton);

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startButton.setFocusedByDefault(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean changeActivity(){
        if(sizeSpinner != null){
            String spinnerContent = sizeSpinner.getSelectedItem().toString();

            if(spinnerContent.equals("Choose")) {
                return false;
            }
            Intent changeActivityIntent = new Intent(this, MemoryActivity.class);
            changeActivityIntent.putExtra("gameSize", sizeSpinner.getSelectedItem().toString());
            startActivity(changeActivityIntent);
            this.finish();
            return true;
        }else{
            System.out.println("ERROR: Spinner is NULL!");
            return false;
        }
    }
}
