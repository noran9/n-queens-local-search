package sample;


import java.util.Random;

public class SimulatedAnnealing {
    private int n;
    private float temperature;
    private int cooling;

    public SimulatedAnnealing(int n, float temperature, int cooling){
        this.n = n;
        this.temperature = temperature;
        this.cooling = cooling;
    }

    General g = new General(n);

    //Function for solving
    public Queen[] solve(Queen[] state){
        //Copy the state in a new curr state

        Queen[] curr = new Queen[n];
        for (int i = 0; i < n; i++)
            curr[i] = new Queen(state[i].getRow(), state[i].getColumn());

        while (true) {
            //Check if solution is found or temperature = 0
            if (g.getHeuristic(curr) == 0 || temperature == 0)
                return curr;

            //Generate random successor
            Queen[] rand = new Queen[n];
            for (int i = 0; i < n; i++)
                rand[i] = new Queen(curr[i].getRow(), curr[i].getColumn());

            Random random = new Random();
            int rnd = random.nextInt(n);

            Queen r = rand[rnd];
            if(r.getRow() < (n-1))
                rand[rnd].move();

            else
                rand[rnd].moveBack();


            //Calculate difference
            int difference = g.getHeuristic(rand) - g.getHeuristic(curr);
            if (difference < 0){
                curr = rand;
            }
            else {

                //Calculate probability
                double probability = Math.min(1, Math.exp (difference / temperature));
                if(Math.random() > probability){
                    System.out.println(probability);
                    curr = rand;
                }
            }
            temperature -= cooling;
        }
    }
}
