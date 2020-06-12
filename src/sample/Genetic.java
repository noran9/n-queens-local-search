package sample;


import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Genetic {
    private int n;
    private int populationSize;
    private double mutationProbability;
    private int numOfGenerations;
    private General g;

    public Genetic(int n, int populationSize, int mutationProbability, int numOfGenerations){
        this.n = n;
        this.populationSize = populationSize;
        this.mutationProbability = mutationProbability / 100.0;
        this.numOfGenerations = numOfGenerations;
        g = new General(n);
    }

    public Queen[] solve() {

        populationSize = populationSize - (populationSize % 2); // each one should get a mate.

        //Generate random population
        List<State> population = generatePopulation(n, populationSize);

        //Looping trough all generations
        for (int x = 0; x < numOfGenerations; x++) {

            //Keeping the population sorted by heuristics
            population = getSelectedPopulation(population);

            //Calling crossover function
            population = handleCrossovers(population, n);

            //Looping trough each state
            for (int i = 0; i < populationSize; i++) {

                //Checking for a solution
                if (g.getHeuristic(population.get(i).getQueens()) == 0)
                    return population.get(i).getQueens();

                //Adding mutation and checking for solution again
                population.set(i,  tryToMutate(population.get(i), mutationProbability));
                if (g.getHeuristic(population.get(i).getQueens()) == 0)
                    return population.get(i).getQueens();

            }

        }

        //Return the best state if no solution is found
        return population.get(0).getQueens();
    }

    private List<State> handleCrossovers(List<State> population, int n) {
        for (int i = 0; i < population.size(); i += 2) {

            //Random selection of position for crossover
            int crossoverPos = (int) (Math.random() * (n-1));
            for (int j = 0; j < crossoverPos; j++) {
                Queen tmp = population.get(i).getQueens()[j];
                population.get(i).setQueen(population.get(i + 1).getQueens()[j].getRow(), j);
                population.get(i + 1).setQueen(tmp.getRow(), j);
            }
        }

        return population;
    }

    //Sort the population by comparing heuristics
    private List<State> getSelectedPopulation(List<State> population) {
        return population.stream().sorted().collect(Collectors.toList());
    }

    //Mutation
    private State tryToMutate(State r, double mutationProbability) {
        Queen[] queens = r.getQueens();
        //Moving a random queen
        if (mutationProbability >= Math.random()) {
            int rnd = (int) (Math.random() * queens.length);
            if(queens[rnd].getRow() < (n-1))
                queens[rnd].move();
            else
                queens[rnd].moveBack();
        }

        return new State(queens);
    }


    //Generating random states for the population
    private List<State> generatePopulation(int n, int populationSize) {
        List<State> p = new LinkedList<>();

        General g = new General(n);
        for (int i = 0; i < populationSize; i++)
            p.add(new State(g.randomBoard(n)));

        return p;
    }
}
