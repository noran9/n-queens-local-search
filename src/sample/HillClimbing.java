package sample;

public class HillClimbing {

    private int n;

    public HillClimbing(int n){
        this.n = n;
    }

    General general = new General(n);

    //Function for solving
    public Queen[] solve(Queen[] state){
        while (true){
            Queen[] nextState = lowestHeuristic(state);

            //Return the state if the best possible solution is found
            if(general.getHeuristic(nextState) >= general.getHeuristic(state))
                return state;

            state = nextState;
        }
    }

    //Function to return the state with the lowest heuristic
    public Queen[] lowestHeuristic(Queen[] state){
        Queen[] min = new Queen[n];
        int minHeuristic = general.getHeuristic(state);
        Queen[] curr = new Queen[n];

        //Copy the state in min and curr
        for(int i = 0; i < n; i++){
                min[i] = new Queen(state[i].getRow(), state[i].getColumn());
                curr[i] = new Queen(state[i].getRow(), state[i].getColumn());
        }

        //Iterate all columns
        for(int i = 0; i < n; i++){
            if (i>0)
                curr[i-1] = new Queen (state[i-1].getRow(), state[i-1].getColumn());
            curr[i] = new Queen (0, curr[i].getColumn());
            //Iterate rows
            for(int j = 0; j < n; j++){

                //Check if a minimum is found
                if(general.getHeuristic(curr) < minHeuristic){
                    minHeuristic = general.getHeuristic(curr);

                    for(int k = 0; k < n; k++)
                        min[k] = new Queen(curr[k].getRow(), curr[k].getColumn());
                }

                //Move the queen
                if(curr[i].getRow() != n-1)
                    curr[i].move();
            }
        }
        return min;
    }
}
