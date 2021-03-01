package com.example.uchihavssenju;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    int activePlayer = 0;
    boolean gameActive = true;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {2, 4, 6}, {0, 4, 8},};


     private boolean gameHasOpenMoves(int[] _gameState) {
            boolean emptySpace = false;
         for( int position : _gameState) {
             if(position == 2) {
                 emptySpace = true;
                 break;
             }
         }
        return emptySpace;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBoardTap(View view) {
        ImageView img = (ImageView) view;

        int tappedBlock = Integer.parseInt(img.getTag().toString());

        if((gameState[tappedBlock] == 2) && gameActive) {
            gameState[tappedBlock] = activePlayer;
            img.setTranslationY(-1500);

            if (activePlayer == 0) {
                img.setImageResource(R.drawable.uchiha);
                activePlayer = 1;
            } else {
                img.setImageResource(R.drawable.senju);
                activePlayer = 0;
            }

            img.animate().translationYBy(1500).setDuration(300);
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    gameActive = false;

                    String winner = "";

                    if (activePlayer == 1) {
                        winner = "Kamui Shuriken! Uchiha";

                        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sharingan);

                        mediaPlayer.start();

                        ImageView winnerImageView = (ImageView) findViewById(R.id.winnerImageView);

                        winnerImageView.setImageResource(R.drawable.madara);

                        winnerImageView.animate().alpha(1).setDuration(4000);
                    } else {
                        winner = "Moktun no Jutsu! Senju";

                        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.woodstyle);

                        mediaPlayer.start();

                        ImageView winnerImageView = (ImageView) findViewById(R.id.winnerImageView);

                        winnerImageView.setImageResource(R.drawable.hashirama);

                        winnerImageView.animate().alpha(1).setDuration(4000);

                    }

                    Toast.makeText(this, winner + " clan has won!", Toast.LENGTH_LONG).show();

                    Button restartButton = (Button) findViewById(R.id.restartGameButton);

                    restartButton.setVisibility(View.VISIBLE);
                }


                if(!gameHasOpenMoves(gameState) && gameActive) {
                    Toast.makeText(this, "Neither clan is superior. It's a draw!", Toast.LENGTH_SHORT).show();

                    Button restartButton = (Button) findViewById(R.id.restartGameButton);

                    restartButton.setVisibility(View.VISIBLE);
                }

            }
        }

    }

    public void restartGame(View v) {
        Button restartBtn = (Button) findViewById(R.id.restartGameButton);

        restartBtn.setVisibility(View.INVISIBLE);

        ImageView winnerImageView = (ImageView) findViewById(R.id.winnerImageView);

        winnerImageView.animate().alpha(0).setDuration(500);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView child = (ImageView)  gridLayout.getChildAt(i);

            child.setImageDrawable(null);
        }

        activePlayer = 0;

        gameActive = true;

        gameState = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}