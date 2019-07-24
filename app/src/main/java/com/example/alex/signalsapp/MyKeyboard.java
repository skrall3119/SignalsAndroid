package com.example.alex.signalsapp;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.View;

public class MyKeyboard extends LinearLayout implements View.OnClickListener {

    Button button1, button2, button3, button4, button5, button6, button7,
                    buttonDelete, buttonEnter, buttonSharp, buttonFlat;


    private SparseArray<String> keyValues = new SparseArray<>();
    private InputConnection inputConnection;


    public MyKeyboard(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }
    public MyKeyboard(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);
        button1 = findViewById(R.id.button_1);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.button_2);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.button_3);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.button_4);
        button4.setOnClickListener(this);
        button5 = findViewById(R.id.button_5);
        button5.setOnClickListener(this);
        button6 = findViewById(R.id.button_6);
        button6.setOnClickListener(this);
        button7 = findViewById(R.id.button_7);
        button7.setOnClickListener(this);
        buttonSharp = findViewById(R.id.button_sharp);
        buttonSharp.setOnClickListener(this);
        buttonFlat = findViewById(R.id.button_flat);
        buttonFlat.setOnClickListener(this);

        buttonDelete = findViewById(R.id.button_enter);
        buttonDelete.setOnClickListener(this);
        buttonEnter = findViewById(R.id.button_delete);
        buttonEnter.setOnClickListener(this);

        keyValues.put(R.id.button_1, "A");
        keyValues.put(R.id.button_2, "B");
        keyValues.put(R.id.button_3, "C");
        keyValues.put(R.id.button_4, "D");
        keyValues.put(R.id.button_5, "E");
        keyValues.put(R.id.button_6, "F");
        keyValues.put(R.id.button_7, "G");
        keyValues.put(R.id.button_flat, "♭");
        keyValues.put(R.id.button_sharp, "#");


    }

    @Override
    public void onClick(View v) {
        if (inputConnection == null)
            return;

        if (v.getId() == R.id.button_delete) {
            CharSequence selectedText = inputConnection.getSelectedText(0);

            if (TextUtils.isEmpty(selectedText)) {
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                inputConnection.commitText("", 1);
            }
        } else if(v.getId() == R.id.button_flat || v.getId() == R.id.button_sharp) {
            String value = keyValues.get(v.getId());
            inputConnection.commitText(value, 1);

        } else {
            inputConnection.setSelection(0,1);
            inputConnection.deleteSurroundingText(1, 0);
            String value = keyValues.get(v.getId());
            inputConnection.commitText(value, 1);

        }
    }

    public void setInputConnection(InputConnection ic) {
        inputConnection = ic;
    }
}
