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

        TextView tonic2 = view.findViewById(R.id.base_i);
        TextView superTonic2 = view.findViewById(R.id.superTonic2);
        TextView mediant2 = view.findViewById(R.id.mediant2);
        TextView subDominant2 = view.findViewById(R.id.subDominant2);
        TextView dominant2 = view.findViewById(R.id.dominant2);
        TextView subMediant2 = view.findViewById(R.id.subMediant2);
        TextView leadingTone2 = view.findViewById(R.id.leadingTone2);

        final TextView[] chords = {tonic2, superTonic2, mediant2, subDominant2, dominant2, subMediant2, leadingTone2};

        modeSpinner.setAdapter(adapter1);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinner.getSelectedItem().toString();
                String[] majorScaleFormula = getResources().getStringArray(R.array.majorFormula);
                String[] newScaleFormula = Builders.shift(majorScaleFormula, modeSpinner.getSelectedItemPosition());
                String[] notes = Builders.chooseNotes(text, getContext());

                final String[] scale = new String[7];
                final String[] suffix = getResources().getStringArray(R.array.suffixes);
                final String[] numerals = getResources().getStringArray(R.array.romanNumerals);
                int scalePos = Builders.find(notes, text);
                int mode = modeSpinner.getSelectedItemPosition();
                scale[0] = notes[scalePos];

                Builders.shift(suffix, mode);
                Builders.buildScale(notes, newScaleFormula, scale, scalePos);
                System.out.println(Builders.isInOrder(scale));
                Builders.updateNotes(getView(), scale);
                Builders.updateChords(getView(), scale, suffix, chords);
                Builders.updateNumerals(getView(), numerals);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinner.getSelectedItem().toString();
                int mode = modeSpinner.getSelectedItemPosition();
                int scalePos;

                String[] majorScaleFormula = getResources().getStringArray(R.array.majorFormula);
                String[] newScaleFormula = Builders.shift(majorScaleFormula, mode);

                String[] notes = Builders.chooseNotes(text, getContext());

                scalePos = Builders.find(notes, text);


                final String[] scale = new String[7];
                final String[] suffix = getResources().getStringArray(R.array.suffixes);
                final String[] numerals = getResources().getStringArray(R.array.romanNumerals);
                scale[0] = notes[scalePos];

                Builders.shift(suffix, mode);

                Builders.buildScale(notes, newScaleFormula, scale, scalePos);
                System.out.println(Builders.isInOrder(scale));
                Builders.updateNotes(getView(), scale);
                Builders.updateChords(getView(), scale, suffix, chords);
                Builders.updateNumerals(getView(), numerals);


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

                        switch (modeSpinner.getSelectedItemPosition()) {
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
