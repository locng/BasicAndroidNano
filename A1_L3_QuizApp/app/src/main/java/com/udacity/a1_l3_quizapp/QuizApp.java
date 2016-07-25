package com.udacity.a1_l3_quizapp;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Random;

public class QuizApp extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private boolean answerQ1 = false;
    private boolean answerQ2 = false;
    private boolean answerQ3 = false;
    private boolean answerQ3_0 = false;
    private boolean answerQ3_1 = false;
    private boolean answerQ3_2 = false;
    private boolean answerQ3_3 = false;
    private int totalCorrectAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_app);
        Resources res = getResources();

        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.rg1);
        String[] q1_option = res.getStringArray(R.array.q1_option);
        createRadioButton(radioGroup1, q1_option);

        RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.rg2);
        String[] q2_option = res.getStringArray(R.array.q2_option);
        createRadioButton(radioGroup2, q2_option);

        LinearLayout view = (LinearLayout) findViewById(R.id.cbGroup3);
        String[] q3_option = res.getStringArray(R.array.q3_option);
        createCheckBox(view, q3_option);

    }

    /*
    * Create radio button
    */
    private void createRadioButton(final RadioGroup radioGroup, String[] data) {
        for (int i = 0; i < 4; i++) {
            final RadioButton radioButton = new RadioButton(this);
            radioButton.setText(data[i]);
            radioButton.setId(i);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "radio: " + view.getId());
                    int id = radioGroup.getId();
                    switch (id) {
                        case R.id.rg1:
                            if (radioGroup.getCheckedRadioButtonId() == 1) {
                                answerQ1 = true;
                                Log.d(TAG, "Q1 OK");
                            } else {
                                answerQ1 = false;
                                Log.d(TAG, "Q1 Not OK");
                            }
                            break;
                        case R.id.rg2:
                            if (radioGroup.getCheckedRadioButtonId() == 0) {
                                answerQ2 = true;
                                Log.d(TAG, "Q2 OK");
                            } else {
                                answerQ2 = false;
                                Log.d(TAG, "Q2 Not OK");
                            }
                            break;
                    }
                }
            });
            radioGroup.addView(radioButton);
        }
    }

    /*
    * Create checkbox
    */
    private void createCheckBox(LinearLayout v, String[] data) {
        for (int i = 0; i < 4; i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(data[i]);

            checkBox.setId(i);
            checkBox.setOnClickListener(new View.OnClickListener() {
                int selected;

                @Override
                public void onClick(View view) {
                    selected = view.getId();
                    final CheckBox cb = (CheckBox) view.findViewById(selected);
                    boolean checked = cb.isChecked();
                    switch (selected) {
                        case 0:
                            if (checked) {
                                Log.d(TAG, "clicked:" + selected);
                                answerQ3_0 = true;
                            } else
                                answerQ3_0 = false;
                            break;
                        case 1:
                            if (checked) {
                                Log.d(TAG, "clicked:" + selected);
                                answerQ3_1 = true;
                            } else
                                answerQ3_1 = false;
                            break;
                        case 2:
                            if (checked) {
                                Log.d(TAG, "clicked:" + selected);
                                answerQ3_2 = true;
                            } else
                                answerQ3_2 = false;
                            break;
                        case 3:
                            if (checked) {
                                Log.d(TAG, "clicked:" + selected);
                                answerQ3_3 = true;
                            } else
                                answerQ3_3 = false;
                            break;
                    }
                    answerQ3 = answerQ3_0 & !answerQ3_1 & answerQ3_2 & answerQ3_3;
                    Log.d(TAG, "answer: " + answerQ3);
                }
            });
            v.addView(checkBox);
        }
    }

    private boolean getResultQ4() {
        EditText editText = (EditText) findViewById(R.id.edittext);
        String input = editText.getText().toString().toLowerCase();
        if (input.equals("operating system"))
            return true;
        else
            return false;
    }

    public void onSubmitClicked(View v) {
        Log.d(TAG, "button clicked");
        if (answerQ1)
            totalCorrectAnswer++;
        if (answerQ2)
            totalCorrectAnswer++;
        if (answerQ3)
            totalCorrectAnswer++;
        if (getResultQ4())
            totalCorrectAnswer++;
        Toast.makeText(getApplication(), "You got " + totalCorrectAnswer + "/4 correct", Toast.LENGTH_SHORT).show();
        //reset value
        totalCorrectAnswer = 0;
    }
}
