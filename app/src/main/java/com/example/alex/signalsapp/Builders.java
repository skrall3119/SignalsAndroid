package com.example.alex.signalsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Type;

class Builders {

    private static String letters[] = {"A", "B", "C", "D", "E", "F", "G"};
    private static String notes[] = {"A", "x", "B", "C", "x", "D", "x", "E", "F", "x", "G", "x"};


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

    static String[] buildScale(String note, String[] formula){


        //creates the array
        String scale[] = new String[7];
        scale[0] = note;

        //initialize essential values.
        int offset = sharpFlat(note);
        int currentPos = find(notes, note.substring(0,1)) + offset;
        int nextLetterPos;
        int distance;

        String nextLetter;

        for(int i = 1; i < scale.length; i++){
            if(formula[i-1].equals("W")){
                currentPos = (currentPos + 2) % 12;
                nextLetter = whatIsNextLetter(letters, scale[i-1].substring(0,1));
                nextLetterPos = find(notes, nextLetter);
                distance = calcDistance(currentPos, nextLetterPos);
                nextLetter += addSharpFlat(distance);
                scale[i] = nextLetter;
            } else {
                currentPos = (currentPos + 1) % 12;
                nextLetter = whatIsNextLetter(letters, scale[i-1].substring(0,1));
                nextLetterPos = find(notes, nextLetter);
                distance = calcDistance(currentPos, nextLetterPos);
                nextLetter += addSharpFlat(distance);
                scale[i] = nextLetter;
            }
            if(scale[i].equals("x")){
                nextLetter = whatIsNextLetter(letters, scale[i-1].substring(0,1));
                nextLetterPos = find(notes, nextLetter);
                distance = calcDistance(currentPos, nextLetterPos);
                nextLetter += addSharpFlat(distance);
                scale[i] = nextLetter;
            }
            //console output for debugging.
        }
        return scale;
    }

    // returns a value based on how many sharps/ flats are at the end of the user entered note.
    private static int sharpFlat(String accent){
        int offset = 0;
        for(int i = 1; i < accent.length(); i++){
            if(accent.charAt(i) == '#'){
                offset++;
            } else{
                offset--;
            }
        }
        return offset;
    }

    //based on the distance calculated in calcDistance(), adds sharps if positive and flats if negative.
    private static String addSharpFlat(int distance){
        String accidentals = "";

        for(int i = 0; i < Math.abs(distance); i++){
            if(distance > 0){
                accidentals += "#";
            } else if (distance < 0){
                accidentals += "♭";
            }
        }
        return accidentals;
    }

    // calculates the distance between the next letter, and the current position
    private static int calcDistance(int i1, int i2){
        int i = (i1 - i2) % 12;

        if(i > 6)
            i -= 12;
        else if(i < -6)
            i += 12;

        return i;
    }

    //returns the next letter in the letters[] array.
    private static String whatIsNextLetter(String letters[], String letter){
        String nextLetter;
        int nextLetterPos = (find(letters, letter) + 1) % letters.length;
        nextLetter = letters[nextLetterPos];
        return nextLetter;
    }

    static String[] makeDominants(String[] scale){
        String[] dominants = new String[7];

        for(int i = 0; i < scale.length; i++){
            dominants[i] = notes[(find(notes, scale[i]) + 7) % 12];

        }

        return dominants;

    }

    static void updateNotes(View view, String[] scale){

        TextView tonic = view.findViewById(R.id.tonic);
        TextView superTonic = view.findViewById(R.id.superTonic);;
        TextView mediant = view.findViewById(R.id.mediant);
        TextView subDominant = view.findViewById(R.id.subDominant);
        TextView dominant = view.findViewById(R.id.dominant);
        TextView subMediant = view.findViewById(R.id.subMediant);
        TextView leadingTone = view.findViewById(R.id.leadingTone);

        TextView noteViews[] = {tonic, superTonic, mediant, subDominant, dominant, subMediant, leadingTone};

        float spacing = (float) 0.00000001;
        for (int i = 0; i < noteViews.length; i++){
            noteViews[i].setText(scale[i]);
            if(scale[i].contains("♭")){
                noteViews[i].setLetterSpacing(spacing);
            }
        }


    }
    static void updateChords(String[] scale, String[] suffix, TextView[] textViews){
        // sets all the chord names on the user interface
        float spacing = (float) 0.00000001;
        for(int i = 0; i < textViews.length; i++){
            textViews[i].setText(scale[i] + suffix[i]);
            if(scale[i].contains("♭")){
                textViews[i].setLetterSpacing(spacing);
            }
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

    private static int[] pickPositions(String chord){

        int[] positions = {};

        switch (chord){
            case "A":
                positions = new int[]{0, 0, 2, 2, 2, 0};
                break;
            case "Am":
                positions = new int[]{0, 0, 2, 2, 1, 0};
                break;
            case "A7":
                positions = new int[]{0, 0, 2, 0, 2, 0};
                break;
            case "A°":
                positions = new int[]{0, 0, 1, 2, 1, 0};
                break;
            case "A#":
            case "B♭":
                positions = new int[]{0, 1, 3, 3, 3, 1};
                break;
            case "A#m":
            case "B♭m":
                positions = new int[]{0, 1, 3, 3, 2, 1};
                break;
            case "A#7":
            case "B♭7":
                positions = new int[]{0, 0, 3, 3, 3, 4};
                break;
            case "A#°":
            case "B♭°":
                positions = new int[]{0, 1, 2, 0, 2, 0};
                break;
            case "B":
                positions = new int[]{0, 2, 4, 4, 4, 2};
                break;
            case "Bm":
                positions = new int[]{0, 2, 4, 4, 3, 2};
                break;
            case "B7":
                positions = new int[]{0, 2, 1, 2, 0, 2};
                break;
            case "B°":
                positions = new int[]{0, 2, 3, 4, 3, 0};
                break;
            case "C":
                positions = new int[]{0, 3, 2, 0, 1, 0};
                break;
            case "Cm":
                //needs fret 5
                positions = new int[]{0, 1, 3, 3, 2, 1};
                break;
            case "C7":
                positions = new int[]{0, 3, 2, 3, 1, 0};
                break;
            case "C°":
                positions = new int[]{2, 3, 4, 2, 4, 2};
                break;

            case "C#":
            case "D♭":
                positions = new int[]{0, 4, 3, 1, 2, 1};
                break;
            case "C#m":
            case "D♭m":
                positions = new int[]{0, 4, 2, 1, 2, 0};
                break;
            case "C#7":
            case "D♭7":
                positions = new int[]{0, 3, 2, 3, 1, 0};
                break;
            case "C#°":
            case "D♭°":
                positions = new int[]{0, 0, 1, 2, 1, 2};
                break;

            case "D":
                positions = new int[]{0, 0, 0, 2, 3, 2};
                break;
            case "Dm":
                positions = new int[]{0, 0, 0, 2, 3, 1};
                break;
            case "D7":
                positions = new int[]{0, 0, 0, 2, 1, 2};
                break;
            case "D°":
                positions = new int[]{0, 0, 0, 1, 3, 1};
                break;

            case "D#":
            case "E♭":
                positions = new int[]{0, 0, 1, 3, 4, 3};
                break;
            case "D#m":
            case "E♭m":
                positions = new int[]{0, 0, 1, 3, 4, 2};
                break;
            case "D#7":
            case "E♭7":
                positions = new int[]{0, 0, 1, 0, 2, 3};
                break;
            case "D#°":
            case "E♭°":
                positions = new int[]{0, 0, 1, 2, 4, 2};
                break;

            case "E":
                positions = new int[]{0, 2, 2, 1, 0, 0};
                break;
            case "Em":
                positions = new int[]{0, 2, 2, 0, 0, 0};
                break;
            case "E7":
                positions = new int[]{0, 2, 0, 1, 0, 0};
                break;
            case "E°":
                positions = new int[]{0, 1, 2, 0, 2, 0};
                break;

            case "F":
                positions = new int[]{0, 0, 3, 2, 1, 1};
                break;
            case "Fm":
                positions = new int[]{1, 3, 3, 1, 1, 1};
                break;
            case "F7":
                positions = new int[]{1, 3, 1, 2, 1, 1};
                break;
            case "F°":
                positions = new int[]{1, 2, 3, 1, 0, 0};
                break;

            case "F#":
            case "G♭":
                positions = new int[]{2, 4, 4, 3, 2, 2};
                break;
            case "F#m":
            case "G♭m":
                positions = new int[]{2, 4, 4, 2, 2, 2};
                break;
            case "F#7":
            case "G♭7":
                positions = new int[]{0, 0, 4, 3, 2, 0};
                break;
            case "F#°":
            case "G♭°":
                positions = new int[]{0, 0, 4, 2, 1, 2};
                break;

            case "G":
                positions = new int[]{3, 2, 0, 0, 0, 3};
                break;
            case "Gm":
                //needs fret 3
                positions = new int[]{1, 3, 3, 1, 1, 1};
                break;
            case "G7":
                positions = new int[]{3, 2, 0, 0, 0, 1};
                break;
            case "G°":
                //needs fret 3
                positions = new int[]{1, 2, 3, 1, 0, 0};
                break;

            case "G#":
            case "A♭":
                //needs fret 4
                positions = new int[]{1, 3, 3, 2, 1, 1};
                break;
            case "G#m":
            case "A♭m":
                //needs fret 4
                positions = new int[]{1, 3, 3, 1, 1, 1};
                break;
            case "G#7":
            case "A♭7":
                //needs fret 4
                positions = new int[]{1, 3, 1, 2, 1, 1};
                break;
            case "G#°":
            case "A♭°":
                //4th fret
                positions = new int[]{1, 2, 3, 1, 0, 0};
                break;
        }
        return positions;
    }

    public static void createChart(View view, View v, TextView textView){
        Bitmap bitmap = Bitmap.createBitmap(500, 360, Bitmap.Config.ARGB_8888);
        ImageView imgView = view.findViewById(R.id.imgView);
        Canvas canvas = new Canvas(bitmap);

        Paint strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStrokeWidth(4);
        strokePaint.setAntiAlias(true);

        Paint backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(Color.WHITE);
        backgroundPaint.setAntiAlias(true);

        Paint circleBackground = new Paint();
        circleBackground.setStyle(Paint.Style.FILL);
        circleBackground.setColor(Color.parseColor("#e6e6e6"));
        circleBackground.setAntiAlias(true);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);

        canvas.drawRect(120, 70, 380, 359, backgroundPaint);

        canvas.drawRect(120, 70, 380, 359, strokePaint);
        canvas.drawRect(120, 70, 380, 90, paint);

        canvas.drawRect(120, 150, 380, 155, paint);
        canvas.drawRect(120, 220, 380, 225, paint);
        canvas.drawRect(120, 290, 380, 295, paint);

        canvas.drawRect(172, 70, 173, 360, paint);
        canvas.drawRect(224, 70, 225, 360, paint);
        canvas.drawRect(276, 70, 277, 360, paint);
        canvas.drawRect(328, 70, 329, 360, paint);

        canvas.drawCircle(60, 215, 30, circleBackground);
        canvas.drawLine(45, 215, 65, 200, strokePaint);
        canvas.drawLine(45, 214, 65, 229, strokePaint);

        canvas.drawCircle(440, 215, 30, circleBackground);
        canvas.drawLine(455, 215, 435, 200, strokePaint);
        canvas.drawLine(455, 214, 435, 229, strokePaint);

        canvas.drawCircle(410, 50, 30, circleBackground);
        canvas.drawLine(390, 30, 430, 70, strokePaint);
        canvas.drawLine(390, 70, 430, 30, strokePaint);


//                    canvas.drawCircle(0, 50, 20, paint);
//                    canvas.drawCircle(0, 117, 20, paint);
//                    canvas.drawCircle(0, 185, 20, paint);
//                    canvas.drawCircle(0, 257, 20, paint);


        float x = v.getX();
        float y = v.getY();

        float trueX = x - 250 + (v.getWidth() / 2);
        float trueY = y - 370;

        imgView.setX(trueX);
        imgView.setY(trueY);

        String chord = textView.getText().toString();
        System.out.println(chord);
        int positions[] = Builders.pickPositions(chord);

        placeCircles(canvas, paint, positions);

        imgView.setImageBitmap(bitmap);
        imgView.bringToFront();
    }

    private static void placeCircles(Canvas canvas, Paint paint, int[] positions){
        float xpos = 120;
        for(int i: positions){

            float ypos;

            switch(i){
                case 0:
                    xpos += 52;
                    break;
                case 1:
                    ypos = 120;
                    canvas.drawCircle(xpos, ypos, 20, paint);
                    xpos += 52;
                    break;
                case 2:
                    ypos = 187;
                    canvas.drawCircle(xpos, ypos, 20, paint);
                    xpos += 52;
                    break;
                case 3:
                    ypos = 225;
                    canvas.drawCircle(xpos, ypos, 20, paint);
                    xpos += 52;
                    break;
                case 4:
                    ypos = 327;
                    canvas.drawCircle(xpos, ypos, 20, paint);
                    xpos += 52;
                    break;

            }

        }

    }

}
