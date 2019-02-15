package com.example.alex.signalsapp;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Simple method for finding index of a certain target.
    public static int find(String[] a, String target)
    {
        for (int i = 0; i < a.length; i++)
            if (a[i].equals(target)) {
                return i;
            }

        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // sets "spinner" or dropdown menu options and adapter.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner1);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = findViewById(R.id.spinner1);
        //Given notes, how to build any major scale in alphabetical order.
        String[] majorScaleFormula = new String[]{"W", "W", "H", "W", "W", "W"};
        String[] notes;
        String text = spinner.getSelectedItem().toString();
        int scalePos;
        int newPos;

        // Determines which set of notes to use. In music, some sharps and flats mean the same sound.
        // Example: E♭ can also be referred to as D#.
        // these keys use sharps instead of flats
        if(text.equals("A") || text.equals("B") || text.equals("C") || text.equals("D") || text.equals("E") || text.equals("G")){
            notes = getResources().getStringArray(R.array.sharps);
            scalePos = find(notes, text);
        }
        // These keys use flats instead of sharps
        else if(text.equals("B♭") || text.equals("D♭") || text.equals("E♭") || text.equals("F") || text.equals("A♭")){
            notes = getResources().getStringArray(R.array.flats);
            scalePos = find(notes, text);
        }
        // Some keys have odd notation. In theory the notes B & C as well as E & F are separated by
        // a distance of a half step. All other letters are separated by a whole step. the sharps
        // and flats are what is in between. When writing a scale, each letter must be in alphabetical
        // order, and never repeated. So for the key of F#, the note F is noted as E# because an F
        // is already used as the root note of the key (F#).
        else if(text.equals("F#") || text.equals("C#")){
            notes = getResources().getStringArray(R.array.eSharps);
            scalePos = find(notes, text);
        }
        else
        {
            notes = getResources().getStringArray(R.array.eFlats);
            scalePos = find(notes, text);
        }

        // Creates a new scale based on the formula for creating a major scale.
        // using the specified notes, it takes the index of the user-selected note and does modular
        // math to determine the rest of the scale.
        String[] scale = new String[7];
        scale[0] = notes[scalePos];

        for(int i = 1; i < scale.length; i++){
            if(majorScaleFormula[i-1].equals("W")){
                scale[i] = notes[(scalePos + 2) % 12];
                newPos = (scalePos + 2)% 12;
                scalePos = newPos;
            }
            else{
                scale[i] = notes[(scalePos + 1) % 12];
                newPos = (scalePos + 1)% 12;
                scalePos = newPos;
            }

        }
        // sets all the note names on the user interface
        TextView tonic = findViewById(R.id.tonic);
        tonic.setText(scale[0]);
        TextView superTonic = findViewById(R.id.superTonic);
        superTonic.setText(scale[1]);
        TextView mediant = findViewById(R.id.mediant);
        mediant.setText(scale[2]);
        TextView subDominant = findViewById(R.id.subDominant);
        subDominant.setText(scale[3]);
        TextView dominant = findViewById(R.id.dominant);
        dominant.setText(scale[4]);
        TextView subMediant = findViewById(R.id.subMediant);
        subMediant.setText(scale[5]);
        TextView leadingTone = findViewById(R.id.leadingTone);
        leadingTone.setText(scale[6]);

        // sets all the chord names on the user interface
        TextView tonic2 = findViewById(R.id.tonic2);
        tonic2.setText(scale[0]);
        TextView superTonic2 = findViewById(R.id.superTonic2);
        superTonic2.setText(scale[1] + "m");
        TextView mediant2 = findViewById(R.id.mediant2);
        mediant2.setText(scale[2] + "m");
        TextView subDominant2 = findViewById(R.id.subDominant2);
        subDominant2.setText(scale[3]);
        TextView dominant2 = findViewById(R.id.dominant2);
        dominant2.setText(scale[4]);
        TextView subMediant2 = findViewById(R.id.subMediant2);
        subMediant2.setText(scale[5] + "m");
        TextView leadingTone2 = findViewById(R.id.leadingTone2);
        leadingTone2.setText(scale[6] + "°");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
