package com.example.android.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// Updated to compile this activity
public class MainActivity extends AppCompatActivity {

    TicTacToeGame ticTacToeGame;
    GridView gridView;
    ArrayAdapter<String> gameAdapter;
    TextView playerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerTextView = (TextView) findViewById(R.id.player);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose the type of Game");
        builder.setCancelable(false);
        builder.setPositiveButton("Single Player", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ticTacToeGame = new TicTacToeGame(TicTacToeGame.GAME_TYPE_SINGLE);
                afterCreate();
            }
        }).setNegativeButton("Two Player", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ticTacToeGame = new TicTacToeGame(TicTacToeGame.GAME_TYPE_TWO);
                afterCreate();
            }
        }).create().show();
    }

    private void afterCreate() {
        if(ticTacToeGame.getGameType() == TicTacToeGame.GAME_TYPE_SINGLE) {
            AlertDialog.Builder builderC = new AlertDialog.Builder(this);
            builderC.setTitle("Who plays first?");
            builderC.setCancelable(false);
            builderC.setPositiveButton("Computer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ticTacToeGame.computerFirst();
                    displayBoxes();
                }
            }).setNegativeButton("Me", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    displayBoxes();
                }
            }).create().show();
        }
        gridView = (GridView) findViewById(R.id.tictactoe_gridview);
        String[] gameBoxesArray = {TicTacToeGame.PLAYER_NONE_STRING, TicTacToeGame.PLAYER_NONE_STRING, TicTacToeGame.PLAYER_NONE_STRING, TicTacToeGame.PLAYER_NONE_STRING, TicTacToeGame.PLAYER_NONE_STRING, TicTacToeGame.PLAYER_NONE_STRING, TicTacToeGame.PLAYER_NONE_STRING, TicTacToeGame.PLAYER_NONE_STRING, TicTacToeGame.PLAYER_NONE_STRING};
        gameAdapter = new ArrayAdapter<>(this, R.layout.layout_game_box, gameBoxesArray);
        gridView.setAdapter(gameAdapter);
        playerTextView.setText(ticTacToeGame.getPlayerPlaying());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int r = position/3;
                int c = position%3;
                String player = ticTacToeGame.getPlayerPlaying();
                ArrayList<Integer> afterPlay = ticTacToeGame.play(r, c);
                if(afterPlay.size() == 1 && afterPlay.get(0) == -1) {
                    Toast.makeText(getApplicationContext(), "Its a draw.", Toast.LENGTH_SHORT).show();
                }
                else if(afterPlay.size() >= 3 && !player.equals("")) {
                    Toast.makeText(getApplicationContext(), ticTacToeGame.getPlayerAt(afterPlay.get(0)/3, afterPlay.get(0)%3) + " won.", Toast.LENGTH_SHORT).show();
                    for(int i=0; i<afterPlay.size(); i++) {
                        ((TextView) gridView.getChildAt(afterPlay.get(i))).setTextColor(Color.RED);
                    }
                }
                displayBoxes();
                playerTextView.setText(ticTacToeGame.getPlayerPlaying());
            }
        });
    }

    private void displayBoxes() {
        for(int i=0; i<9; i++) {
            ((TextView) gridView.getChildAt(i)).setText(ticTacToeGame.getPlayerAt(i/3, i%3));
        }
    }

    public void reload(View view) {
        onDestroy();
        onCreate(null);
    }

}