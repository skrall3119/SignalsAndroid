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

public class BorrowedFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_borrowed, container, false);

        final MyKeyboard keyboard = view.findViewById(R.id.keyboard);

        final EditText mainEditText = view.findViewById(R.id.mainEditText);
        final EditText dialogEditText = view.findViewById(R.id.userInput);
        final LinearLayout dimLayout = view.findViewById(R.id.dim_layout);

        TextView maj_i = view.findViewById(R.id.majTonic);
        TextView maj_ii = view.findViewById(R.id.majSuperTonic);
        TextView maj_iii = view.findViewById(R.id.majMediant);
        TextView maj_iv = view.findViewById(R.id.majSubDominant);
        TextView maj_v = view.findViewById(R.id.majDominant);
        TextView maj_vi = view.findViewById(R.id.majSubtonic);
        TextView maj_vii = view.findViewById(R.id.majLeadingTone);

        TextView dor_i = view.findViewById(R.id.dorTonic);
        TextView dor_ii = view.findViewById(R.id.dorSuperTonic);
        TextView dor_iii = view.findViewById(R.id.dorMediant);
        TextView dor_iv = view.findViewById(R.id.dorSubDominant);
        TextView dor_v = view.findViewById(R.id.dorDominant);
        TextView dor_vi = view.findViewById(R.id.dorSubtonic);
        TextView dor_vii = view.findViewById(R.id.dorLeadingTone);

        TextView phr_i = view.findViewById(R.id.phrTonic);
        TextView phr_ii = view.findViewById(R.id.phrSuperTonic);
        TextView phr_iii = view.findViewById(R.id.phrMediant);
        TextView phr_iv = view.findViewById(R.id.phrSubDominant);
        TextView phr_v = view.findViewById(R.id.phrDominant);
        TextView phr_vi = view.findViewById(R.id.phrSubtonic);
        TextView phr_vii = view.findViewById(R.id.phrLeadingTone);

        TextView lyd_i = view.findViewById(R.id.lydTonic);
        TextView lyd_ii = view.findViewById(R.id.lydSuperTonic);
        TextView lyd_iii = view.findViewById(R.id.lydMediant);
        TextView lyd_iv = view.findViewById(R.id.lydSubDominant);
        TextView lyd_v = view.findViewById(R.id.lydDominant);
        TextView lyd_vi = view.findViewById(R.id.lydSubtonic);
        TextView lyd_vii = view.findViewById(R.id.lydLeadingTone);

        TextView mix_i = view.findViewById(R.id.mixTonic);
        TextView mix_ii = view.findViewById(R.id.mixSuperTonic);
        TextView mix_iii = view.findViewById(R.id.mixMediant);
        TextView mix_iv = view.findViewById(R.id.mixSubDominant);
        TextView mix_v = view.findViewById(R.id.mixDominant);
        TextView mix_vi = view.findViewById(R.id.mixSubtonic);
        TextView mix_vii = view.findViewById(R.id.mixLeadingTone);

        TextView min_i = view.findViewById(R.id.minTonic);
        TextView min_ii = view.findViewById(R.id.minSuperTonic);
        TextView min_iii = view.findViewById(R.id.minMediant);
        TextView min_iv = view.findViewById(R.id.minSubDominant);
        TextView min_v = view.findViewById(R.id.minDominant);
        TextView min_vi = view.findViewById(R.id.minSubtonic);
        TextView min_vii = view.findViewById(R.id.minLeadingTone);

        TextView loc_i = view.findViewById(R.id.locTonic);
        TextView loc_ii = view.findViewById(R.id.locSuperTonic);
        TextView loc_iii = view.findViewById(R.id.locMediant);
        TextView loc_iv = view.findViewById(R.id.locSubDominant);
        TextView loc_v = view.findViewById(R.id.locDominant);
        TextView loc_vi = view.findViewById(R.id.locSubtonic);
        TextView loc_vii = view.findViewById(R.id.locLeadingTone);

        final TextView major[] = {maj_i, maj_ii, maj_iii, maj_iv, maj_v, maj_vi, maj_vii};
        TextView dorian[] = {dor_i, dor_ii, dor_iii, dor_iv, dor_v, dor_vi, dor_vii};
        TextView phrygian[] = {phr_i, phr_ii, phr_iii, phr_iv, phr_v, phr_vi, phr_vii};
        TextView lydian[] = {lyd_i, lyd_ii, lyd_iii, lyd_iv, lyd_v, lyd_vi, lyd_vii};
        TextView mixolyidan[] = {mix_i, mix_ii, mix_iii, mix_iv, mix_v, mix_vi, mix_vii};
        TextView minor[] = {min_i, min_ii, min_iii, min_iv, min_v, min_vi, min_vii};
        TextView locrian[] = {loc_i, loc_ii, loc_iii, loc_iv, loc_v, loc_vi, loc_vii};

        final TextView[] modes[] = {major, dorian, phrygian, lydian, mixolyidan, minor, locrian};

        updateView("C", modes);

        dialogEditText.setInputType(InputType.TYPE_NULL);
        dialogEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);

        keyboard.setVisibility(View.INVISIBLE);

        InputConnection ic = dialogEditText.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);

        mainEditText.setFocusable(false);

        mainEditText.setText("C");
        mainEditText.setInputType(InputType.TYPE_NULL);
        mainEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboard.setVisibility(View.VISIBLE);
                dimLayout.setVisibility(View.VISIBLE);
                keyboard.bringToFront();
            }
        });

        keyboard.buttonEnter = keyboard.findViewById(R.id.button_enter);
        keyboard.buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String note = dialogEditText.getText().toString();
                if(note.equals("")){
                    mainEditText.setText("C");
                    note = "C";
                }

                updateView(note, modes);
                mainEditText.setText(note);
                keyboard.setVisibility(View.INVISIBLE);
                dimLayout.setVisibility(View.INVISIBLE);

            }
        });


        return view;

    }

    public void updateView(String note, TextView[] modes[]) {
        for(int i = 0; i < 7; i++) {
            final String[] suffixes = getResources().getStringArray(R.array.suffixes);
            final String[] majorScaleFormula = getResources().getStringArray(R.array.majorFormula);

            String[] formula = Builders.shift(majorScaleFormula, i);
            String[] suffix = Builders.shift(suffixes, i);
            String[] scale = Builders.buildScale(note, formula);
            Builders.updateChords(scale, suffix, modes[i]);
        }
    }
}
