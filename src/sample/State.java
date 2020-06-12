package sample;

//Class that encapsulates one state

public class State implements Comparable{
    private Queen[] queens;

    public State(Queen[] q){
        queens = new Queen[q.length];
        for (int i = 0; i <  q.length; i++)
            this.queens[i] = q[i];
    }

    public void setQueen(int i, int value){
        queens[i] = new Queen(i, value);
    }

    public Queen[] getQueens(){
        return queens;
    }

    @Override
    public int compareTo(Object o) {
        State s = (State) o;
        General g = new General(queens.length);
        return Integer.compare(g.getHeuristic(queens), g.getHeuristic(s.getQueens()));
    }

    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < queens.length; i++)
            s += queens[i].getRow() + " " + queens[i].getColumn() + "////";
        return s + "/n";
    }
}
