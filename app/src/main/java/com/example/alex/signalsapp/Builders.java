package com.example.alex.signalsapp;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

class Builders {

    //finds index of a certain target.
    static int find(String[] a, String target) {
        for (int i = 0; i < a.length; i++)
            if (a[i].equals(target)) {
                return i;
            }

        return -1;
    }

    static String[] shift(String[] array, int position) {
        for (int i = 0; i < position; i++) {
            String temp = array[0];
            for (int j = 0; j < array.length - 1; j++) {
                array[j] = array[j + 1];
            }
            array[6] = temp;
        }
        return array;
    }

    static void buildScale(String[] notes, String[] formula, String[] scale, int scalePos) {
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

    static void updateNotes(View view, String[] scale){
        TextView tonic = view.findViewById(R.id.base_i);
        tonic.setText(scale[0]);
        TextView superTonic = view.findViewById(R.id.superTonic);
        superTonic.setText(scale[1]);
        TextView mediant = view.findViewById(R.id.mediant);
        mediant.setText(scale[2]);
        TextView subDominant = view.findViewById(R.id.subDominant);
        subDominant.setText(scale[3]);
        TextView dominant = view.findViewById(R.id.dominant);
        dominant.setText(scale[4]);
        TextView subMediant = view.findViewById(R.id.subMediant);
        subMediant.setText(scale[5]);
        TextView leadingTone = view.findViewById(R.id.leadingTone);
        leadingTone.setText(scale[6]);
    }
    static void updateChords(View view, String[] scale, String[] suffix, TextView[] textViews){
        // sets all the chord names on the user interface
        for(int i = 0; i < textViews.length; i++){
            textViews[i].setText(scale[i] + suffix[i]);
        }
    }

    static void updateNumerals(View view, String[] numerals){
        TextView chordi = view.findViewById(R.id.chordi);
        TextView chordii = view.findViewById(R.id.chordii);
        TextView chordiii = view.findViewById(R.id.chordiii);
        TextView chordiv = view.findViewById(R.id.chordiv);
        TextView chordv = view.findViewById(R.id.chordv);
        TextView chordvi = view.findViewById(R.id.chordvi);
        TextView chordvii = view.findViewById(R.id.chordvii);

        chordi.setText(numerals[0]);
        chordii.setText(numerals[1]);
        chordiii.setText(numerals[2]);
        chordiv.setText(numerals[3]);
        chordv.setText(numerals[4]);
        chordvi.setText(numerals[5]);
        chordvii.setText(numerals[6]);


    }

    static String[] chooseNotes(String text, Context context){
        String[] notes;
        switch (text) {
            case "A":
            case "B":
            case "C":
            case "D":
            case "E":
            case "G":
                notes = context.getResources().getStringArray(R.array.sharps);
                break;
            case "B♭":
            case "D♭":
            case "E♭":
            case "F":
            case "A♭":
                notes = context.getResources().getStringArray(R.array.flats);
                break;
            case "F#":
            case "C#":
                notes = context.getResources().getStringArray(R.array.eSharps);
                break;
            default:
                notes = context.getResources().getStringArray(R.array.eFlats);
                break;

        }
        return notes;
    }

    static boolean isInOrder(String[] notes){
        String temp;
        boolean isDuplicate = false;
        for(int i = 0; i < notes.length - 1; i++){
            if(notes[i].length() == 1){
                temp = notes[i];
            }
            else {
                temp = notes[i].substring(0, 1);
            }

            if(temp.equals(notes[i+1].substring(0,1)) && i + 1 < notes.length){
                isDuplicate = true;
            }
        }
        return isDuplicate;
    }

}
