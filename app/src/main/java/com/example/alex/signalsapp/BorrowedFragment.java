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
import android.widget.Spinner;
import android.widget.TextView;

public class BorrowedFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrowed, container, false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.choices, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(view.getContext(), R.array.modes, android.R.layout.simple_spinner_item);

        final Spinner spinner = view.findViewById(R.id.baseChoices);
        final Spinner modeSpinner = view.findViewById(R.id.borrowedMode);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        TextView base_i = view.findViewById(R.id.base_i);
        TextView base_ii = view.findViewById(R.id.base_ii);
        TextView base_iii = view.findViewById(R.id.base_iii);
        TextView base_iv = view.findViewById(R.id.base_iv);
        TextView base_v = view.findViewById(R.id.base_v);
        TextView base_vi = view.findViewById(R.id.base_vi);
        TextView base_vii = view.findViewById(R.id.base_vii);

        TextView borrowed_i = view.findViewById(R.id.borrowed_i);
        TextView borrowed_ii = view.findViewById(R.id.borrowed_ii);
        TextView borrowed_iii = view.findViewById(R.id.borrowed_iii);
        TextView borrowed_iv = view.findViewById(R.id.borrowed_iv);
        TextView borrowed_v = view.findViewById(R.id.borrowed_v);
        TextView borrowed_vi = view.findViewById(R.id.borrowed_vi);
        TextView borrowed_vii = view.findViewById(R.id.borrowed_vii);

        final TextView chordText[] = {base_i, base_ii, base_iii, base_iv, base_v, base_vi, base_vii};
        final TextView borrowedChords[] = {borrowed_i, borrowed_ii, borrowed_iii, borrowed_iv, borrowed_v,
                borrowed_vi, borrowed_vii};

        spinner.setAdapter(adapter);
        modeSpinner.setAdapter(adapter1);

        spinner.setSelection(5);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String majorScaleFormula[] = view.getResources().getStringArray(R.array.majorFormula);
                String text = spinner.getSelectedItem().toString();
                String[] notes = Builders.chooseNotes(text, getContext());
                int scalePos = Builders.find(notes, text);
                final String[] suffix = getResources().getStringArray(R.array.suffixes);
                final String[] scale = new String[7];
                scale[0] = notes[scalePos];
                Builders.buildScale(notes, majorScaleFormula, scale, scalePos);
                Builders.updateChords(view, scale, suffix, chordText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int mode = modeSpinner.getSelectedItemPosition();

                String majorScaleFormula[] = view.getResources().getStringArray(R.array.majorFormula);
                String newFormula[] = Builders.shift(majorScaleFormula, mode);
                String text = spinner.getSelectedItem().toString();
                String[] notes = Builders.chooseNotes(text, getContext());

                int scalePos = Builders.find(notes, text);

                final String[] suffix = getResources().getStringArray(R.array.suffixes);
                final String[] scale = new String[7];
                scale[0] = notes[scalePos];
                Builders.shift(suffix, mode);
                Builders.buildScale(notes, newFormula, scale, scalePos);
                Builders.updateChords(view, scale, suffix, borrowedChords);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}
