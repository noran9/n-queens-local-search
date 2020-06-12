package sample;

public class Queen{
    private int row;
    private int column;

    public Queen(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void move() {
        row++;
    }

    public void moveBack(){
        row--;
    }

    public boolean isInConflict(Queen q){
        //  Check rows and columns
        if(row == q.getRow() || column == q.getColumn())
            return true;
            //  Check diagonals
        else if(Math.abs(column - q.getColumn()) == Math.abs(row - q.getRow()))
            return true;
        return false;
    }


}
