package com.jeeduniya.connect3game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 0:yellow, 1:Red, 2:empty
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int activePlayer = 0;  // yellow player is going to start the game all time
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};  // these no.. 0,1,2--8 are the tag assigned to the box in xml
    boolean gameActive = true;

    public void dropin(View view) {

        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameActive) {  // this means jis place pe phle se chal gya h waha pr phir se press krne pe na chale

            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000);

            if (activePlayer == 0) {  // i.e yellow is playing
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;  //changing to 1 so that our active player be red now
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000).rotation(360).setDuration(300);

            //To check who is winnig
            for (int[] winningPos : winningPositions) {  //winningPos = {0, 1, 2}{ 1st element of winningPositions array and so on this will loop till end element of this array i.e {2, 4, 6}} and then winningPos[0]=0,winningPos[1]=1, winningPos[2]=2
                if (gameState[winningPos[0]] == gameState[winningPos[1]] && gameState[winningPos[1]] == gameState[winningPos[2]] && gameState[winningPos[0]] != 2) {
                    gameActive = false;
                    String winner = "";
                    if (activePlayer == 1) {
                        winner = "Yellow";
                    } else {
                        winner = "Red";
                    }
                    //Toast.makeText(this, winner + " has won", Toast.LENGTH_SHORT).show();

                    TextView winnerTextView = findViewById(R.id.winnerTextView);
                    Button playAgainButton = findViewById(R.id.playAgainButton);

                    winnerTextView.setText(winner + " has won");

                    winnerTextView.setVisibility(View.VISIBLE);
                    playAgainButton.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view) {
        TextView winnerTextView = findViewById(R.id.winnerTextView);
        Button playAgainButton = findViewById(R.id.playAgainButton);
        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);

            imageView.setImageDrawable(null);
        }

        Arrays.fill(gameState, 2);
//        for (int i = 0; i < gameState.length; i++) {
//            gameState[i] = 2;
//        }

        activePlayer = 0;  // yellow player is going to start the game all time
        gameActive = true;
    }
}
