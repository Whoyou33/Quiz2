package com.quiz.myapplication;

import static com.quiz.myapplication.R.id.question_text_view;
import static com.quiz.myapplication.R.id.true_button;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //private static final int REQUET_CODE_PROMPT = 0;
    private static final int REQUEST_CODE_PROMPT = 0;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button promptButton;
    private TextView questionTextView;
    private int currentIndex = 0;
    private static final String TAG = "Quiz";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER = "com.quiz.myapplication.correctAnswer";

    private Question[] questions = new Question[]{
            new Question(R.string.q_activity, true),
            new Question(R.string.q_find_resources,  false),
            new Question(R.string.q_listener,  true),
            new Question(R.string.q_resources,  true),
            new Question(R.string.q_version,  false),
    };
    private Log log;
    private boolean answerWasShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "On Create");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton = findViewById(true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(question_text_view);
        promptButton = findViewById(R.id.prompt_button);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1 )%questions.length;
                answerWasShown = false;
                setNextQuestion();
            }
        });

        promptButton.setOnClickListener(v ->  {
                Intent intent = new Intent( MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);

            });
        setNextQuestion();
    }

   @Override
   public void onDestroy() {
        super.onDestroy();
        log.d(TAG, "On destroy");
   }

   @Override
   public void onPause() {
        super.onPause();
        log.d(TAG, "On pause");
   }

   @Override
   public void onStart() {
        super.onStart();
        log.d(TAG, "On start");
   }

    @Override
    public void onResume() {
        super.onResume();
        log.d(TAG, "On Resume");
    }

    @Override
    public void onStop() {
        super.onStop();
        log.d(TAG, "On stop");
    }

    public void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        log.d(TAG, "Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resutlMessageID = 0;
        if(answerWasShown) {
            resutlMessageID = R.string.answer_was_shown;
        } else {
            if (userAnswer == correctAnswer) {
                resutlMessageID = R.string.correct_answer;
            } else {
                resutlMessageID = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resutlMessageID, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK) { return; }
        if(requestCode == REQUEST_CODE_PROMPT) {
            if(data == null) {return;}
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

}

