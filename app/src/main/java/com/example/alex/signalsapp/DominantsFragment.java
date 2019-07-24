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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DominantsFragment extends Fragment {

    EditText dialogEditText;
    EditText userInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_dominants, container, false);

        TextView baseChordi = view.findViewById(R.id.base_chord_i);
        TextView baseChordii = view.findViewById(R.id.base_chord_ii);
        TextView baseChordiii = view.findViewById(R.id.base_chord_iii);
        TextView baseChordiv = view.findViewById(R.id.base_chord_iv);
        TextView baseChordv = view.findViewById(R.id.base_chord_v);
        TextView baseChordvi = view.findViewById(R.id.base_chord_vi);
        TextView baseChordvii = view.findViewById(R.id.base_chord_vii);

        TextView dominantChordi = view.findViewById(R.id.dominant_chord_i);
        TextView dominantChordii = view.findViewById(R.id.dominant_chord_ii);
        TextView dominantChordiii = view.findViewById(R.id.dominant_chord_iii);
        TextView dominantChordiv = view.findViewById(R.id.dominant_chord_iv);
        TextView dominantChordv = view.findViewById(R.id.dominant_chord_v);
        TextView dominantChordvi = view.findViewById(R.id.dominant_chord_vi);
        TextView dominantChordvii = view.findViewById(R.id.dominant_chord_vii);

        final TextView bases[] = {baseChordi, baseChordii, baseChordiii, baseChordiv, baseChordv, baseChordvi, baseChordvii};
        final TextView dominants[] = {dominantChordi, dominantChordii, dominantChordiii, dominantChordiv, dominantChordv, dominantChordvi, dominantChordvii};
        final String[] suffix = getResources().getStringArray(R.array.suffixes);
        final String[] sevens = getResources().getStringArray(R.array.sevens);

        final String[] majorScaleFormula = getResources().getStringArray(R.array.majorFormula);

        final MyKeyboard myKeyboard = view.findViewById(R.id.keyboard);

        userInput = view.findViewById(R.id.domEdit);
        userInput.setFocusable(false);
        userInput.setInputType(InputType.TYPE_NULL);
        dialogEditText = view.findViewById(R.id.userInput);
        dialogEditText.setInputType(InputType.TYPE_NULL);
        dialogEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);

        InputConnection ic = dialogEditText.onCreateInputConnection(new EditorInfo());
        myKeyboard.setInputConnection(ic);
        userInput.setText("C");

        String note = userInput.getText().toString();

        String scale[] = Builders.buildScale(note, majorScaleFormula);
        String doms[] = Builders.makeDominants(scale);
        Builders.updateChords(scale, suffix, bases);
        Builders.updateChords(doms, sevens, dominants);


        myKeyboard.setVisibility(myKeyboard.GONE);

        final LinearLayout dimLayout = view.findViewById(R.id.dim_layout);

        userInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myKeyboard.setVisibility(View.VISIBLE);
                dimLayout.setVisibility(View.VISIBLE);
                myKeyboard.bringToFront();
            }
        });

        myKeyboard.buttonEnter = myKeyboard.findViewById(R.id.button_enter);
        myKeyboard.buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] majorScaleFormula = getResources().getStringArray(R.array.majorFormula);

                String note = dialogEditText.getText().toString();
                if(note.equals("")){
                    userInput.setText("C");
                    note = "C";
                }
                userInput.setText(note);
                String scale[] = Builders.buildScale(note, majorScaleFormula);
                String doms[] = Builders.makeDominants(scale);
                Builders.updateChords(scale, suffix, bases);
                Builders.updateChords(doms, sevens, dominants);
                myKeyboard.setVisibility(View.INVISIBLE);
                dimLayout.setVisibility(View.INVISIBLE);
            }
        });


        return view;
    }
}
