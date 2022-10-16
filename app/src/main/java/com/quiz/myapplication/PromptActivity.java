package com.quiz.myapplication;

import static com.quiz.myapplication.R.id.CorrectAnswerButton;
import static com.quiz.myapplication.R.id.answerTextView;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class PromptActivity extends AppCompatActivity {

    private boolean correctAnswer;
    private Button showCorrectAnswerButton;
    private TextView answerText;
    public static final String KEY_EXTRA_ANSWER_SHOWN = "com.quiz.myapplication.answerShown";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);
        answerText = findViewById(R.id.answerTextView);
        showCorrectAnswerButton = findViewById(R.id.CorrectAnswerButton);

        showCorrectAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                answerText.setText(answer);
                setAnswerShowResult(true);
            }
        });
    }

    private void setAnswerShowResult(boolean answerShowShown) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerShowShown);
        setResult(RESULT_OK, resultIntent);
    }

    //private void setAnswerShownResult(boolean answerWasShown) {
    //    Intent resultIntent = new Intent();
    //    resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShown);
    //    setResult(RESULT_OK, resultIntent);
    //}

}
