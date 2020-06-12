package sample;

import java.util.*;
import java.util.stream.Collectors;

public class LocalBeamSearch {

    int k;
    int size;
    General g = new General(size);

    //Constructor
    public LocalBeamSearch(int k, int n){
        this.k = k;
        this.size = n;
    }

    //Function for solving
    public Queen[] solve(Queen[] s){
        //Creating a list of random states
        List<State> list = new LinkedList<>();
        list.add(new State(s));
        for(int i = 0; i < k - 1; i++)
            list.add(new State(g.randomBoard(size)));

        //Max number of iterations
        int count = 0;
        while (count < 10000) {
            //Keeping the list sorted and taking k best states
            list = list.stream().sorted().limit(k).collect(Collectors.toList());

            //Checking if solution is found
            for (int i = 0; i < k; i++) {
                if (g.getHeuristic(list.get(i).getQueens()) == 0)
                    return list.get(i).getQueens();
            }

            //Expanding every state
            for(int i = 0; i < k; i++){
                //Copy the current state into tmp and state
                Queen[] tmp = new Queen[size];
                Queen[] state = new Queen[size];
                for(int l = 0; l < size; l++) {
                    tmp[l] = new Queen(list.get(i).getQueens()[l].getRow(), list.get(i).getQueens()[l].getColumn());
                    state[l] = new Queen(list.get(i).getQueens()[l].getRow(), list.get(i).getQueens()[l].getColumn());
                }

                //Iterate all columns of a state
                for(int n = 0; n < size; n++){
                    if (n>0)
                        tmp[n-1] = new Queen (state[n-1].getRow(), state[n-1].getColumn());
                    tmp[n] = new Queen (0, tmp[n].getColumn());
                    //Iterate rows
                    for(int m = 0; m < size; m++){

                        //Move the queen and add that new state to the list
                        if(tmp[n].getRow() != size-1)
                            tmp[n].move();

                        list.add(new State(tmp));
                    }
                }

            }
            count++;
        }
        //Return the best option if no solution is found
        return list.get(0).getQueens();
    }
}
