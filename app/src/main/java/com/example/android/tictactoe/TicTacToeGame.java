package com.example.android.tictactoe;

import java.util.ArrayList;
import java.util.Random;

public class TicTacToeGame {

    public static final int GAME_TYPE_SINGLE = 0;
    public static final int GAME_TYPE_TWO = 1;
    public static final int PLAYER_X = 1;
    public static final String PLAYER_X_STRING = "x";
    public static final int PLAYER_O = 2;
    public static final String PLAYER_O_STRING = "o";
    public static final int PLAYER_NONE = 0;
    public static final String PLAYER_NONE_STRING = "";

    private static Random random = new Random(System.currentTimeMillis());
    private int gameType;
    private int playerPlaying = PLAYER_X;
    private int[][] gameBoxes = {
            {PLAYER_NONE, PLAYER_NONE, PLAYER_NONE},
            {PLAYER_NONE, PLAYER_NONE, PLAYER_NONE},
            {PLAYER_NONE, PLAYER_NONE, PLAYER_NONE}
    };
    private int moveNo = 0;

    public TicTacToeGame(int typeOfGame) {
        gameType = typeOfGame;
    }

    /*public static final String LOG_TAG = "TIC_TAC_TOE_GAME";
    private void log(String message) {
        Log.v(LOG_TAG, message);
    }*/

    public ArrayList<Integer> play(int row, int column) {
        if (gameBoxes[row][column] == PLAYER_NONE) {
            gameBoxes[row][column] = playerPlaying;
            moveNo++;
            if (playerPlaying == PLAYER_X) playerPlaying = PLAYER_O;
            else if (playerPlaying == PLAYER_O) playerPlaying = PLAYER_X;
            ArrayList<Integer> arrayList = isCompleted();
            if(arrayList.size() == 0 && gameType == GAME_TYPE_SINGLE) {
                computerPlay();
                moveNo++;
                arrayList = isCompleted();
            }
            return arrayList;
        }
        return new ArrayList<>();
    }

    public String getPlayerPlaying() {
        if (playerPlaying == PLAYER_X) return PLAYER_X_STRING;
        else if (playerPlaying == PLAYER_O) return PLAYER_O_STRING;
        else return PLAYER_NONE_STRING;
    }

    public String getPlayerAt(int row, int column) {
        int player = gameBoxes[row][column];
        if (player == PLAYER_X) return PLAYER_X_STRING;
        else if (player == PLAYER_O) return PLAYER_O_STRING;
        else return PLAYER_NONE_STRING;
    }

    public void computerFirst() {
        computerPlay();
        moveNo++;
    }

    public int getGameType() {
        return gameType;
    }

    private int getOtherPlayer() {
        if (playerPlaying == PLAYER_X) return PLAYER_O;
        else if (playerPlaying == PLAYER_O) return PLAYER_X;
        return PLAYER_NONE;
    }

    private void computerPlay() {
        // log(String.valueOf(moveNo));
        int playingBox = -1;
        int[] gameChoice = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        if (moveNo == 1) {
            if (gameBoxes[1][1] == getOtherPlayer()) {
                do {
                    playingBox = random.nextInt(9);
                    int r = playingBox / 3;
                    int c = playingBox % 3;
                    if (gameBoxes[r][c] == PLAYER_NONE && playingBox%2 == 0) {
                        break;
                    }
                } while (true);
            }
            else if (gameBoxes[0][0] == getOtherPlayer()
                    || gameBoxes[0][2] == getOtherPlayer()
                    || gameBoxes[2][0] == getOtherPlayer()
                    || gameBoxes[2][2] == getOtherPlayer()){
                playingBox = 4;
            }
            else {
                for(int i=0; i<3; i++) {
                    for (int j=0; j<3; j++) {
                        if(gameBoxes[i][j] == getOtherPlayer()) {
                            gameChoice[3 *((i+1)%3) + ((j+1)%3)] = -1;
                            gameChoice[3 *((i+1)%3) + ((j+2)%3)] = -1;
                            gameChoice[3 *((i+2)%3) + ((j+1)%3)] = -1;
                            gameChoice[3 *((i+2)%3) + ((j+2)%3)] = -1;
                            break;
                        }
                    }
                }
            }
        }
        if (moveNo == 2) {
            int initPosition = -1;
            int otherPlayerPosition = -1;
            for (int i=0; i<9; i++) {
                if (gameBoxes[i/3][i%3] == playerPlaying) {
                    initPosition = i;
                }
                if (gameBoxes[i/3][i%3] == getOtherPlayer()) {
                    otherPlayerPosition = i;
                }
            }
            if (initPosition%2 == 0) {
                if (initPosition != 4 && otherPlayerPosition != 4) {
                    int ri = initPosition/3;
                    int ci = initPosition%3;
                    int ro = otherPlayerPosition/3;
                    int co = otherPlayerPosition%3;
                    if (otherPlayerPosition%2 == 1) {
                        if (Math.abs(ri-ro) == 0) {
                            for (int i=0; i<3; i+=2) {
                                if (gameBoxes[i][ci] == PLAYER_NONE) playingBox = 3*i+ci;
                            }
                        }
                        else if (Math.abs(ci-co) == 0) {
                            for (int i=0; i<3; i+=2) {
                                if (gameBoxes[ri][i] == PLAYER_NONE) playingBox = 3*ri+i;
                            }
                        }
                        else {
                            int randomInt = random.nextInt(1000);
                            if (randomInt%2 == 0) {
                                for (int i=0; i<3; i+=2) {
                                    if (gameBoxes[i][ci] == PLAYER_NONE) playingBox = 3*i+ci;
                                }
                            }
                            else {
                                for (int i=0; i<3; i+=2) {
                                    if (gameBoxes[ri][i] == PLAYER_NONE) playingBox = 3*ri+i;
                                }
                            }
                        }
                    }
                }
            }
            else {
                if (initPosition == 1) {
                    if (otherPlayerPosition == 3 || otherPlayerPosition == 6) playingBox = 0;
                    if (otherPlayerPosition == 5 || otherPlayerPosition == 8) playingBox = 2;
                }
                if (initPosition == 3) {
                    if (otherPlayerPosition == 1 || otherPlayerPosition == 2) playingBox = 0;
                    if (otherPlayerPosition == 7 || otherPlayerPosition == 8) playingBox = 6;
                }
                if (initPosition == 5) {
                    if (otherPlayerPosition == 0 || otherPlayerPosition == 1) playingBox = 2;
                    if (otherPlayerPosition == 6 || otherPlayerPosition == 7) playingBox = 8;
                }
                if (initPosition == 7) {
                    if (otherPlayerPosition == 0 || otherPlayerPosition == 3) playingBox = 6;
                    if (otherPlayerPosition == 2 || otherPlayerPosition == 5) playingBox = 8;
                }
            }
        }
        // log("R "+String.valueOf(playingBox));
        // if (moveNo == 1) playingBox = 8;
        if (playingBox == -1) {
            for (int i = 0; i < 9; i++) {
                int r = i / 3;
                int c = i % 3;
                if (gameBoxes[r][c] == PLAYER_NONE) {
                    if (gameBoxes[r][(c + 1) % 3] == playerPlaying
                            && gameBoxes[r][(c + 1) % 3] == gameBoxes[r][(c + 2) % 3]) {
                        playingBox = i;
                        break;
                    }
                    if (gameBoxes[(r + 1) % 3][c] == playerPlaying
                            && gameBoxes[(r + 1) % 3][c] == gameBoxes[(r + 2) % 3][c]) {
                        playingBox = i;
                        break;
                    }
                    if (r == c
                            && gameBoxes[(r + 1) % 3][(c + 1) % 3] == playerPlaying
                            && gameBoxes[(r + 1) % 3][(c + 1) % 3] == gameBoxes[(r + 2) % 3][(c + 2) % 3]) {
                        playingBox = i;
                        break;
                    }
                    if (r + c == 2
                            && gameBoxes[(r + 1) % 3][(c + 2) % 3] == playerPlaying
                            && gameBoxes[(r + 1) % 3][(c + 2) % 3] == gameBoxes[(r + 2) % 3][(c + 1) % 3]) {
                        playingBox = i;
                        break;
                    }
                }
            }
        }
        // log("1 "+String.valueOf(playingBox));
        if (playingBox == -1) {
            for (int i=0; i<9; i++) {
                int r = i/3;
                int c = i%3;
                if (gameBoxes[r][c] == PLAYER_NONE) {
                    if (gameBoxes[r][(c+1)%3] == getOtherPlayer()
                            && gameBoxes[r][(c+1)%3] == gameBoxes[r][(c+2)%3]) {
                        playingBox = i;
                        break;
                    }
                    if (gameBoxes[(r+1)%3][c] == getOtherPlayer()
                            && gameBoxes[(r+1)%3][c] == gameBoxes[(r+2)%3][c]) {
                        playingBox = i;
                        break;
                    }
                    if(r == c
                            && gameBoxes[(r+1)%3][(c+1)%3] == getOtherPlayer()
                            && gameBoxes[(r+1)%3][(c+1)%3] == gameBoxes[(r+2)%3][(c+2)%3]) {
                        playingBox = i;
                        break;
                    }
                    if(r+c == 2
                            && gameBoxes[(r+1)%3][(c+2)%3] == getOtherPlayer()
                            && gameBoxes[(r+1)%3][(c+2)%3] == gameBoxes[(r+2)%3][(c+1)%3]) {
                        playingBox = i;
                        break;
                    }
                }
            }
        }
        // log("2 "+String.valueOf(playingBox));
        if (playingBox == -1) {
            int[] gameChoices = {0, 0, 0, 0, 0, 0, 0, 0, 0};
            for (int i=0; i<9; i++) {
                int r = i / 3;
                int c = i % 3;
                if (gameBoxes[r][c] == PLAYER_NONE) {
                    if (gameBoxes[r][(c+1)%3] == PLAYER_NONE
                            && gameBoxes[r][(c+2)%3] == playerPlaying) {
                        gameChoices[i]++;
                    }
                    if (gameBoxes[r][(c+2)%3] == PLAYER_NONE
                            && gameBoxes[r][(c+1)%3] == playerPlaying) {
                        gameChoices[i]++;
                    }
                    if (gameBoxes[(r+1)%3][c] == PLAYER_NONE
                            && gameBoxes[(r+2)%3][c] == playerPlaying) {
                        gameChoices[i]++;
                    }
                    if (gameBoxes[(r+2)%3][c] == PLAYER_NONE
                            && gameBoxes[(r+1)%3][c] == playerPlaying) {
                        gameChoices[i]++;
                    }
                    if (r == c
                            && gameBoxes[(r+1)%3][(c+1)%3] == PLAYER_NONE
                            && gameBoxes[(r+2)%3][(c+2)%3] == playerPlaying)  {
                        gameChoices[i]++;
                    }
                    if (r == c
                            && gameBoxes[(r+2)%3][(c+2)%3] == PLAYER_NONE
                            && gameBoxes[(r+1)%3][(c+1)%3] == playerPlaying)  {
                        gameChoices[i]++;
                    }
                    if (r+c == 2
                            && gameBoxes[(r+1)%3][(c+2)%3] == PLAYER_NONE
                            && gameBoxes[(r+2)%3][(c+1)%3] == playerPlaying)  {
                        gameChoices[i]++;
                    }
                    if (r+c == 2
                            && gameBoxes[(r+2)%3][(c+1)%3] == PLAYER_NONE
                            && gameBoxes[(r+1)%3][(c+2)%3] == playerPlaying)  {
                        gameChoices[i]++;
                    }
                }
            }
            int maxIndex = indexOfMax(gameChoices);
            if(gameChoices[maxIndex] >= 2) {
                playingBox = maxIndex;
            }
        }
        // log("3 "+String.valueOf(playingBox));
        if (playingBox == -1) {
            int[] gameChoices = {0, 0, 0, 0, 0, 0, 0, 0, 0};
            for (int i=0; i<9; i++) {
                int r = i / 3;
                int c = i % 3;
                if (gameBoxes[r][c] == PLAYER_NONE) {
                    if (gameBoxes[r][(c+1)%3] == PLAYER_NONE
                            && gameBoxes[r][(c+2)%3] == getOtherPlayer()) {
                        gameChoices[i]++;
                    }
                    if (gameBoxes[r][(c+2)%3] == PLAYER_NONE
                            && gameBoxes[r][(c+1)%3] == getOtherPlayer()) {
                        gameChoices[i]++;
                    }
                    if (gameBoxes[(r+1)%3][c] == PLAYER_NONE
                            && gameBoxes[(r+2)%3][c] == getOtherPlayer()) {
                        gameChoices[i]++;
                    }
                    if (gameBoxes[(r+2)%3][c] == PLAYER_NONE
                            && gameBoxes[(r+1)%3][c] == getOtherPlayer()) {
                        gameChoices[i]++;
                    }
                    if (r == c
                            && gameBoxes[(r+1)%3][(c+1)%3] == PLAYER_NONE
                            && gameBoxes[(r+2)%3][(c+2)%3] == getOtherPlayer())  {
                        gameChoices[i]++;
                    }
                    if (r == c
                            && gameBoxes[(r+2)%3][(c+2)%3] == PLAYER_NONE
                            && gameBoxes[(r+1)%3][(c+1)%3] == getOtherPlayer())  {
                        gameChoices[i]++;
                    }
                    if (r+c == 2
                            && gameBoxes[(r+1)%3][(c+2)%3] == PLAYER_NONE
                            && gameBoxes[(r+2)%3][(c+1)%3] == getOtherPlayer())  {
                        gameChoices[i]++;
                    }
                    if (r+c == 2
                            && gameBoxes[(r+2)%3][(c+1)%3] == PLAYER_NONE
                            && gameBoxes[(r+1)%3][(c+2)%3] == getOtherPlayer())  {
                        gameChoices[i]++;
                    }
                }
            }
            // log(l);
            int maxIndex = indexOfMax(gameChoices);
            if(gameChoices[maxIndex] >= 2) {
                do {

                    int randomInt = random.nextInt(9);
                    if (gameChoices[randomInt] == gameChoices[maxIndex]) {
                        playingBox = randomInt;
                    }
                } while (playingBox == -1);
            }
        }
        // log("4 "+String.valueOf(playingBox));
        if (playingBox == -1) {
            for (int j=0; j<9; j++) {
                int rj = j/3;
                int cj = j%3;
                if (gameBoxes[rj][cj] == PLAYER_NONE) {
                    gameBoxes[rj][cj] = playerPlaying;
                    int[] gameChoices = {0, 0, 0, 0, 0, 0, 0, 0, 0};
                    for (int i=0; i<9; i++) {
                        int r = i / 3;
                        int c = i % 3;
                        if (gameBoxes[r][c] == PLAYER_NONE) {
                            if (gameBoxes[r][(c+1)%3] == PLAYER_NONE
                                    && gameBoxes[r][(c+2)%3] == getOtherPlayer()) {
                                gameChoices[i]++;
                            }
                            if (gameBoxes[r][(c+2)%3] == PLAYER_NONE
                                    && gameBoxes[r][(c+1)%3] == getOtherPlayer()) {
                                gameChoices[i]++;
                            }
                            if (gameBoxes[(r+1)%3][c] == PLAYER_NONE
                                    && gameBoxes[(r+2)%3][c] == getOtherPlayer()) {
                                gameChoices[i]++;
                            }
                            if (gameBoxes[(r+2)%3][c] == PLAYER_NONE
                                    && gameBoxes[(r+1)%3][c] == getOtherPlayer()) {
                                gameChoices[i]++;
                            }
                            if (r == c
                                    && gameBoxes[(r+1)%3][(c+1)%3] == PLAYER_NONE
                                    && gameBoxes[(r+2)%3][(c+2)%3] == getOtherPlayer())  {
                                gameChoices[i]++;
                            }
                            if (r == c
                                    && gameBoxes[(r+2)%3][(c+2)%3] == PLAYER_NONE
                                    && gameBoxes[(r+1)%3][(c+1)%3] == getOtherPlayer())  {
                                gameChoices[i]++;
                            }
                            if (r+c == 2
                                    && gameBoxes[(r+1)%3][(c+2)%3] == PLAYER_NONE
                                    && gameBoxes[(r+2)%3][(c+1)%3] == getOtherPlayer())  {
                                gameChoices[i]++;
                            }
                            if (r+c == 2
                                    && gameBoxes[(r+2)%3][(c+1)%3] == PLAYER_NONE
                                    && gameBoxes[(r+1)%3][(c+2)%3] == getOtherPlayer())  {
                                gameChoices[i]++;
                            }
                        }
                    }
                    gameBoxes[rj][cj] = PLAYER_NONE;
                    int maxIndex = indexOfMax(gameChoices);
                    if(gameChoices[maxIndex] > 1) {
                        gameChoice[j] = -1;
                    }
                }
            }
        }
        // log("5 "+String.valueOf(playingBox));
        if (playingBox == -1) {
            for (int j=0; j<9; j++) {
                int rj = j/3;
                int cj = j%3;
                if (gameBoxes[rj][cj] == PLAYER_NONE && gameChoice[rj*3+cj] != -1) {
                    gameBoxes[rj][cj] = playerPlaying;
                    int[] gameChoices = {0, 0, 0, 0, 0, 0, 0, 0, 0};
                    for (int i=0; i<9; i++) {
                        int r = i / 3;
                        int c = i % 3;
                        if (gameBoxes[r][c] == PLAYER_NONE) {
                            if (gameBoxes[r][(c+1)%3] == PLAYER_NONE
                                    && gameBoxes[r][(c+2)%3] == playerPlaying) {
                                gameChoices[i]++;
                            }
                            if (gameBoxes[r][(c+2)%3] == PLAYER_NONE
                                    && gameBoxes[r][(c+1)%3] == playerPlaying) {
                                gameChoices[i]++;
                            }
                            if (gameBoxes[(r+1)%3][c] == PLAYER_NONE
                                    && gameBoxes[(r+2)%3][c] == playerPlaying) {
                                gameChoices[i]++;
                            }
                            if (gameBoxes[(r+2)%3][c] == PLAYER_NONE
                                    && gameBoxes[(r+1)%3][c] == playerPlaying) {
                                gameChoices[i]++;
                            }
                            if (r == c
                                    && gameBoxes[(r+1)%3][(c+1)%3] == PLAYER_NONE
                                    && gameBoxes[(r+2)%3][(c+2)%3] == playerPlaying)  {
                                gameChoices[i]++;
                            }
                            if (r == c
                                    && gameBoxes[(r+2)%3][(c+2)%3] == PLAYER_NONE
                                    && gameBoxes[(r+1)%3][(c+1)%3] == playerPlaying)  {
                                gameChoices[i]++;
                            }
                            if (r+c == 2
                                    && gameBoxes[(r+1)%3][(c+2)%3] == PLAYER_NONE
                                    && gameBoxes[(r+2)%3][(c+1)%3] == playerPlaying)  {
                                gameChoices[i]++;
                            }
                            if (r+c == 2
                                    && gameBoxes[(r+2)%3][(c+1)%3] == PLAYER_NONE
                                    && gameBoxes[(r+1)%3][(c+2)%3] == playerPlaying)  {
                                gameChoices[i]++;
                            }
                        }
                    }
                    gameBoxes[rj][cj] = PLAYER_NONE;
                    int maxIndex = indexOfMax(gameChoices);
                    if(gameChoices[maxIndex] > 1) {
                        if(gameChoice[j] != -1) {
                            gameChoice[j] = maxIndex - 1;
                        }
                    }
                }
            }
        }
        // log("7 "+String.valueOf(playingBox));
        if (playingBox == -1) {
            if(gameChoice[0] != -1
                    && gameChoice[1] != -1
                    && gameChoice[2] != -1
                    && gameChoice[3] != -1
                    && gameChoice[4] != -1
                    && gameChoice[5] != -1
                    && gameChoice[6] != -1
                    && gameChoice[7] != -1
                    && gameChoice[8] != -1) {
                int maxIndex = indexOfMax(gameChoice);
                if(maxIndex != 0) {
                    do {
                        playingBox = random.nextInt(9);
                        int r = playingBox / 3;
                        int c = playingBox % 3;
                        if (gameBoxes[r][c] == PLAYER_NONE && gameChoice[playingBox] == gameChoice[maxIndex]) {
                            // log("RRR " + String.valueOf(playingBox));
                            break;
                        }
                    } while (true);
                }
                else {
                    do {
                        playingBox = random.nextInt(9);
                        int r = playingBox / 3;
                        int c = playingBox % 3;
                        if (gameBoxes[r][c] == PLAYER_NONE) {
                            // log("RRR " + String.valueOf(playingBox));
                            break;
                        }
                    } while (true);
                }
            }
            else {
                do {
                    playingBox = random.nextInt(9);
                    int r = playingBox / 3;
                    int c = playingBox % 3;
                    if (gameBoxes[r][c] == PLAYER_NONE && gameChoice[3*r+c] != -1) {
                        break;
                    }
                } while (true);
            }
        }
        // log("RR "+String.valueOf(playingBox));
        int r = playingBox/3;
        int c = playingBox%3;
        gameBoxes[r][c] = playerPlaying;
        playerPlaying = getOtherPlayer();
    }

    private ArrayList<Integer> isCompleted() {
        ArrayList<Integer> show = new ArrayList<>();
        for (int i=0; i<3; i++) {
            if(gameBoxes[i][i] != PLAYER_NONE) {
                if(gameBoxes[i][0] == gameBoxes[i][1] && gameBoxes[i][0] == gameBoxes[i][2]) {
                    playerPlaying = PLAYER_NONE;
                    show.add(i * 3);
                    show.add(1 + i*3);
                    show.add(2 + i*3);
                }
                if(gameBoxes[0][i] == gameBoxes[1][i] && gameBoxes[0][i] == gameBoxes[2][i]) {
                    playerPlaying = PLAYER_NONE;
                    show.add(i);
                    show.add(i+3);
                    show.add(i+6);
                }
            }
        }
        if (gameBoxes[1][1] != PLAYER_NONE) {
            if (gameBoxes[0][0] == gameBoxes[1][1] && gameBoxes[1][1] == gameBoxes[2][2]) {
                playerPlaying = PLAYER_NONE;
                show.add(0);
                show.add(4);
                show.add(8);
            }
            if (gameBoxes[0][2] == gameBoxes[1][1] && gameBoxes[1][1] == gameBoxes[2][0]) {
                playerPlaying = PLAYER_NONE;
                show.add(2);
                show.add(4);
                show.add(6);
            }
        }
        if (show.size() == 0
                && gameBoxes[0][0] != PLAYER_NONE
                && gameBoxes[0][1] != PLAYER_NONE
                && gameBoxes[0][2] != PLAYER_NONE
                && gameBoxes[1][0] != PLAYER_NONE
                && gameBoxes[1][1] != PLAYER_NONE
                && gameBoxes[1][2] != PLAYER_NONE
                && gameBoxes[2][0] != PLAYER_NONE
                && gameBoxes[2][1] != PLAYER_NONE
                && gameBoxes[2][2] != PLAYER_NONE) {
            show.add(-1);
        }
        return show;
    }

    private int indexOfMax(int[] array) {
        int in = 0;
        for(int i=0; i<array.length; i++) {
            if(array[i] > array[in]) {
                in = i;
            }
        }
        return in;
    }
}
