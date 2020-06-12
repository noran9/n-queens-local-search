package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//Class for generating a new board every time
public class GuiScene {
    static int size = 4;


    //Method for creating the board
    public Rectangle[][] generateSquares(int size) {
        this.size = size;
        Rectangle[][] squares = new Rectangle[size][size];

        //Generating the chess board colors
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Rectangle square = new Rectangle();
                Color color;
                if ((row + col) % 2 == 0) color = Color.BURLYWOOD;
                else color = Color.BROWN;
                square.setFill(color);
                square.setWidth(350.0 / size);
                square.setHeight(350.0 / size);
                squares[row][col] = square;
            }
        }
        return squares;
    }
}