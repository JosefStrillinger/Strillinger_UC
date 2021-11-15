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

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Playground playground;
    private Spinner sizeSpinner;

    private Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startButton = (Button)findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });
    }

    private void changeActivity(){
        if(sizeSpinner != null){
            String spinnerContent = sizeSpinner.getSelectedItem().toString();

            if(spinnerContent.equals("Choose")) {

                String message = "Bitte wählen Sie eine der Größen";
                showSnackbar(startButton, message, 1000);
            }
            Intent changeActivityIntent = new Intent(this, MemoryActivity.class);
            changeActivityIntent.putExtra("gameSize", sizeSpinner.getSelectedItem().toString());
            startActivity(changeActivityIntent);
            this.finish();
        }else{
            System.out.println("ERROR: Spinner is NULL!");
            String message = "Bitte wählen Sie eine der Größen";
            showSnackbar(startButton, message, 1000);
        }
    }

    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }
}
