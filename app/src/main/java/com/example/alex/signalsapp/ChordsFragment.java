package com.example.alex.signalsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ChordsFragment extends Fragment {

    // Simple method for finding index of a certain target.
    public static int find(String[] a, String target) {
        for (int i = 0; i < a.length; i++)
            if (a[i].equals(target)) {
                return i;
            }

        return -1;
    }

    public String[] shiftForm(String[] formula, int position) {
        for (int i = 0; i < position; i++) {
            String temp = formula[0];
            for (int j = 0; j < formula.length - 1; j++) {
                formula[j] = formula[j + 1];
            }
            formula[6] = temp;
        }
        return formula;
    }

    public void buildScale(String[] notes, String[] formula, String[] scale, int scalePos) {
        int newPos;
        for (int i = 1; i < scale.length; i++) {
            if (formula[i - 1].equals("W")) {
                scale[i] = notes[(scalePos + 2) % 12];
                newPos = (scalePos + 2) % 12;
                scalePos = newPos;
            } else {
                scale[i] = notes[(scalePos + 1) % 12];
                newPos = (scalePos + 1) % 12;
                scalePos = newPos;
            }

        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chords, container, false);


        // sets "spinner" or dropdown menu options and adapter.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.choices, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(view.getContext(), R.array.modes, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner modeSpinner = view.findViewById(R.id.modes);
        final Spinner spinner = view.findViewById(R.id.keys);

        modeSpinner.setAdapter(adapter1);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] notes;
                String text = spinner.getSelectedItem().toString();
                int scalePos;

                //Given notes, how to build any major scale in alphabetical order.
                String[] majorScaleFormula = {"W", "W", "H", "W", "W", "W", "H"};
                String[] newScaleFormula = shiftForm(majorScaleFormula, modeSpinner.getSelectedItemPosition());


                // Determines which set of notes to use. In music, some sharps and flats mean the same sound.
                // Example: E♭ can also be referred to as D#.
                // these keys use sharps instead of flats
                if (text.equals("A") || text.equals("B") || text.equals("C") || text.equals("D") || text.equals("E") || text.equals("G")) {
                    notes = getResources().getStringArray(R.array.sharps);
                    scalePos = find(notes, text);
                }
                // These keys use flats instead of sharps
                else if (text.equals("B♭") || text.equals("D♭") || text.equals("E♭") || text.equals("F") || text.equals("A♭")) {
                    notes = getResources().getStringArray(R.array.flats);
                    scalePos = find(notes, text);
                }
                // Some keys have odd notation. In theory the notes B & C as well as E & F are separated by
                // a distance of a half step. All other letters are separated by a whole step. the sharps
                // and flats are what is in between. When writing a scale, each letter must be in alphabetical
                // order, and never repeated. So for the key of F#, the note F is noted as E# because an F
                // is already used as the root note of the key (F#).
                else if (text.equals("F#") || text.equals("C#")) {
                    notes = getResources().getStringArray(R.array.eSharps);
                    scalePos = find(notes, text);
                } else {
                    notes = getResources().getStringArray(R.array.eFlats);
                    scalePos = find(notes, text);
                }

                // Creates a new scale based on the formula for creating a major scale.
                // using the specified notes, it takes the index of the user-selected note and does modular
                // math to determine the rest of the scale.
                final String[] scale = new String[7];
                scale[0] = notes[scalePos];


                buildScale(notes, newScaleFormula, scale, scalePos);

                for (int k = 0; k < scale.length; k++) {
                    if (scale[(k + 1) % 7].contains(scale[k])) {
                        System.out.println("true");
                        break;
                    }
                }
                // sets all the note names on the user interface
                TextView tonic = getView().findViewById(R.id.tonic);
                tonic.setText(scale[0]);
                TextView superTonic = getView().findViewById(R.id.superTonic);
                superTonic.setText(scale[1]);
                TextView mediant = getView().findViewById(R.id.mediant);
                mediant.setText(scale[2]);
                TextView subDominant = getView().findViewById(R.id.subDominant);
                subDominant.setText(scale[3]);
                TextView dominant = getView().findViewById(R.id.dominant);
                dominant.setText(scale[4]);
                TextView subMediant = getView().findViewById(R.id.subMediant);
                subMediant.setText(scale[5]);
                TextView leadingTone = getView().findViewById(R.id.leadingTone);
                leadingTone.setText(scale[6]);

                // sets all the chord names on the user interface
                TextView tonic2 = getView().findViewById(R.id.tonic2);
                tonic2.setText(scale[0]);
                TextView superTonic2 = getView().findViewById(R.id.superTonic2);
                superTonic2.setText(scale[1]);
                TextView mediant2 = getView().findViewById(R.id.mediant2);
                mediant2.setText(scale[2]);
                TextView subDominant2 = getView().findViewById(R.id.subDominant2);
                subDominant2.setText(scale[3]);
                TextView dominant2 = getView().findViewById(R.id.dominant2);
                dominant2.setText(scale[4]);
                TextView subMediant2 = getView().findViewById(R.id.subMediant2);
                subMediant2.setText(scale[5]);
                TextView leadingTone2 = getView().findViewById(R.id.leadingTone2);
                leadingTone2.setText(scale[6]);

                TextView chordi = getView().findViewById(R.id.chordi);
                TextView chordii = getView().findViewById(R.id.chordii);
                TextView chordiii = getView().findViewById(R.id.chordiii);
                TextView chordiv = getView().findViewById(R.id.chordiv);
                TextView chordv = getView().findViewById(R.id.chordv);
                TextView chordvi = getView().findViewById(R.id.chordvi);
                TextView chordvii = getView().findViewById(R.id.chordvii);

                System.out.println(modeSpinner.getSelectedItemPosition());
                switch (modeSpinner.getSelectedItemPosition()){
                    case 5:
                        tonic2.setText(scale[0] + "m");
                        superTonic2.setText(scale[1] +"°");
                        mediant2.setText(scale[2]);
                        subDominant2.setText(scale[3] + "m");
                        dominant2.setText(scale[4] + "m");
                        subMediant2.setText(scale[5]);
                        leadingTone2.setText(scale[6]);

                        chordi.setText("i");
                        chordii.setText("ii°");
                        chordiii.setText("III");
                        chordiv.setText("iv");
                        chordv.setText("v");
                        chordvi.setText("VI");
                        chordvii.setText("VII");
                        break;

                    default:
                        superTonic2.setText(scale[1] + "m");
                        mediant2.setText(scale[2] + "m");
                        subMediant2.setText(scale[5] + "m");
                        leadingTone2.setText(scale[6] + "°");

                        chordi.setText("I");
                        chordii.setText("ii");
                        chordiii.setText("iii");
                        chordiv.setText("IV");
                        chordv.setText("V");
                        chordvi.setText("vi");
                        chordvii.setText("vii°");
                        break;
                }


                Button generator = getView().findViewById(R.id.generator);
                generator.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView firstChord = getView().findViewById(R.id.firstChordText);
                        TextView secondChord = getView().findViewById(R.id.secondChordText);
                        TextView thirdChord = getView().findViewById(R.id.thirdChordText);
                        TextView fourthChord = getView().findViewById(R.id.fourthChordText);

                        String[] progression = new String[4];

                        boolean isDifferent = false;

                        int max;
                        int min;

                        int number1 = 0;
                        int number2 = 0;
                        int number3 = 0;

                        switch(modeSpinner.getSelectedItemPosition()) {
                            case 5:
                                max = 6;
                                min = 1;

                                while (!isDifferent) {
                                    if (number2 == number1 || number2 == number3 || number1 == number3) {
                                        number1 = (int) (Math.random() * (max - min) + 1) + min;
                                        number2 = (int) (Math.random() * (max - min) + 1) + min;
                                        number3 = (int) (Math.random() * (4 - 2) + 1) + 2;
                                    } else {
                                        isDifferent = true;
                                    }
                                }

                                progression[0] = scale[0] + "m";
                                if (number1 == 3 || number1 == 4)
                                    progression[1] = scale[number1] + "m";
                                else
                                    progression[1] = scale[number1];
                                if (number2 == 3 || number2 == 4)
                                    progression[2] = scale[number2] + "m";
                                else
                                    progression[2] = scale[number2];
                                progression[3] = scale[number3] + "m";

                                break;

                            default:
                                while (!isDifferent) {
                                    if (number2 == number1 || number2 == number3 || number1 == number3) {
                                        number1 = (int) (Math.random() * (5) + 1);
                                        number2 = (int) (Math.random() * (5) + 1);
                                        number3 = (int) (Math.random() * (4 - 2) + 1) + 2;
                                    } else {
                                        isDifferent = true;
                                    }
                                }

                                progression[0] = scale[0];
                                if (number2 == 1 || number2 == 2 || number2 == 5)
                                    progression[2] = scale[number2] + "m";
                                else
                                    progression[2] = scale[number2];
                                if (number1 == 1 || number1 == 2 || number1 == 5)
                                    progression[1] = scale[number1] + "m";
                                else
                                    progression[1] = scale[number1];
                                progression[3] = scale[number3];
                                break;
                        }
                        firstChord.setText(progression[0]);
                        secondChord.setText(progression[1]);
                        thirdChord.setText(progression[2]);
                        fourthChord.setText(progression[3]);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setSelection(5);


        return view;
    }
}
