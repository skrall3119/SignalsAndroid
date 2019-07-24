package com.example.alex.signalsapp;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class ChordsFragment extends Fragment {

    EditText viewEditText;
    EditText dialogEditText;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_chords, container, false);
        final ImageView imgview = view.findViewById(R.id.imgView);

        TextView tonic2 = view.findViewById(R.id.tonic2);
        TextView superTonic2 = view.findViewById(R.id.superTonic2);
        TextView mediant2 = view.findViewById(R.id.mediant2);
        TextView subDominant2 = view.findViewById(R.id.subDominant2);
        TextView dominant2 = view.findViewById(R.id.dominant2);
        TextView subMediant2 = view.findViewById(R.id.subMediant2);
        TextView leadingTone2 = view.findViewById(R.id.leadingTone2);

        final TextView[] chords = {tonic2, superTonic2, mediant2, subDominant2, dominant2, subMediant2, leadingTone2};

        final String[] numerals = getResources().getStringArray(R.array.romanNumerals);

        final Spinner modeSpinner = view.findViewById(R.id.modes);

        viewEditText = view.findViewById(R.id.mainEditText);
        final MyKeyboard keyboard = view.findViewById(R.id.keyboard);
        dialogEditText = view.findViewById(R.id.userInput);
        dialogEditText.setInputType(InputType.TYPE_NULL);
        dialogEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        InputConnection ic = dialogEditText.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);

        viewEditText.setFocusable(false);
        dialogEditText.setFocusable(false);
        viewEditText.setText("C");

        keyboard.setVisibility(keyboard.GONE);

        final LinearLayout dimLayout = view.findViewById(R.id.dim_layout);

        viewEditText.setInputType(InputType.TYPE_NULL);
        viewEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeyboard(keyboard);
                dimLayout.setVisibility(View.VISIBLE);
                keyboard.bringToFront();
            }
        });
        keyboard.buttonEnter = keyboard.findViewById(R.id.button_enter);
        keyboard.buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] suffix = getResources().getStringArray(R.array.suffixes);
                final String[] majorScaleFormula = getResources().getStringArray(R.array.majorFormula);

                String note = dialogEditText.getText().toString();
                if (note.equals("")) {
                    viewEditText.setText("C");
                    note = "C";
                }
                viewEditText.setText(note);
                int mode = modeSpinner.getSelectedItemPosition();
                String newFormula[] = Builders.shift(majorScaleFormula, mode);
                String newSuffix[] = Builders.shift(suffix, mode);
                String scale[] = Builders.buildScale(note, newFormula);
                Builders.updateNotes(view, scale);
                Builders.updateChords(scale, newSuffix, chords);
                keyboard.setVisibility(v.INVISIBLE);
                dimLayout.setVisibility(v.INVISIBLE);

            }
        });

        for (TextView i : chords) {

            final TextView textView = i;

            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Builders.createChart(view, v, textView);
                    imgview.setVisibility(View.VISIBLE);
                }
            });
        }

        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgview.setVisibility(View.INVISIBLE);
            }
        });


        // sets "spinner" or dropdown menu options and adapter.
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(view.getContext(), R.array.modes, android.R.layout.simple_spinner_item);

        modeSpinner.setAdapter(adapter1);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String[] majorScaleFormula = getResources().getStringArray(R.array.majorFormula);
                final String[] suffix = getResources().getStringArray(R.array.suffixes);

                final String text = viewEditText.getText().toString();
                int mode = modeSpinner.getSelectedItemPosition();

                String[] newScaleFormula = Builders.shift(majorScaleFormula, mode);

                Builders.shift(suffix, mode);
                final String scale[] = Builders.buildScale(text, newScaleFormula);

                Builders.updateNotes(getView(), scale);
                Builders.updateChords(scale, suffix, chords);
                Builders.updateNumerals(getView(), numerals);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        Button generator = view.findViewById(R.id.generator);
        generator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String scale[] = Builders.buildScale(viewEditText.getText().toString(), Builders.shift(getResources().getStringArray(R.array.majorFormula), modeSpinner.getSelectedItemPosition()));

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


        return view;
    }


    public static void showKeyboard(View view) {
        view.setVisibility(view.VISIBLE);
    }
}
