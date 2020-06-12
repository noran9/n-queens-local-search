package sample;

import java.util.Random;

//Class for often used methods
public class General {
    private int n;

    public General(int n){
        this.n = n;
    }

    //Generate a state of queens all in a random row
    public Queen[] randomBoard(int n){
        Queen[] board = new Queen[n];

        for(int i = 0; i < n; i++){
            Random rand = new Random();
            board[i] = new Queen(rand.nextInt(n), i);
        }
        return board;
    }

    // Method to find Heuristics of a state
    public int getHeuristic (Queen[] state) {
        int heuristic = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j=i+1; j < state.length ; j++ ) {
                if (state[i].isInConflict(state[j])) {
                    heuristic++;
                }
            }
        }
        return heuristic;
    }
}
