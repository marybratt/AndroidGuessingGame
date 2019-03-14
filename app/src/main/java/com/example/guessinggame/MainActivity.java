package com.example.guessinggame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static android.widget.TextView.*;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber;
    private Button btnGuess;
    private Button btnHint;
    private TextView tvResult;
    private int theNumber;
    private Button btnPlayAgain;
    private int count;


    public void checkGuess() {
        String  guessText = etNumber.getText().toString();
        String message = " ";
        try
        {
            int guess = Integer.parseInt(guessText);
            count++;
            if (guess == 9999)// backdoor if need to see what the random number was
            {
                message = theNumber + " is the random number";

            }
            else if (guess < theNumber)
                message = guess + " is too low. Try again.";
            else if (guess > theNumber)
                message = guess + " is too high. Try again.";
            else {
                message = guess +
                        " is correct. You win! \nIt took you " + count + " tries";
                //sets up and calls the popup when correct number is entered
                Intent i = new Intent(getApplicationContext(), PopActivity.class);
                startActivity(i);
            }
        } catch (Exception e) {
            message = "Enter a whole number between 1 and 1000.";
        } finally {
            //display message and sets up for the next number
            tvResult.setText(message);
            etNumber.setText("");
            etNumber.requestFocus();
            etNumber.selectAll();
        }
    }

    // get random number and sets the counter for number of tries
    public void newGame()
    {
        //get random number
        int noOfNumbers = 1000;
        int firstNumber = 1;
        theNumber = (int)(Math.random() * noOfNumbers + firstNumber);
        count = 0;// set to count the number of tries

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        etNumber = findViewById(R.id.etNumber);
        btnGuess = findViewById(R.id.btnGuess);
        tvResult = findViewById(R.id.tvResult);
        btnHint = findViewById(R.id.btnHint);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);

        newGame();

        //set up the event listener for the input field
        etNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                checkGuess();
                return true;
            }
        });



        // handle the click on the Guess button
        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            checkGuess();
            }

        });

        // handle the click on the Play Again button
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
                checkGuess();
            }
        });

    }
}
