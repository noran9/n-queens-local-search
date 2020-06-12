package sample;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;


public class Main extends Application implements EventHandler<ActionEvent>{

    public static void main(String[] args) {
        launch(args);
    }

    //Declaration of the gui elements
    GridPane board = new GridPane();
    VBox userControls;
    static int size = 4;

    Button start;
    Button generateRandom;
    Rectangle[][] squares = new Rectangle[size][size];
    Queen[] state = new Queen[size];
    RadioButton hill, simulated, beam, genetic;

    //parameters for simulated annealing
    TextField temperature, cooling;

    //parameters for local beam search
    TextField beams;

    //Parameters for genetic
    TextField population, generation, mutation;

    @Override
    public void start(Stage primaryStage){
        //Setting the window title
        primaryStage.setTitle("Local Search Algorithms");

        //Setting the main view
        userControls = new VBox();
        VBox main = new VBox(board, userControls);


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
                board.add(square, col, row);
                squares[row][col] = square;
            }
        }

        //Selection area
        userControls.setAlignment(Pos.CENTER);
        userControls.setSpacing(10);

        //Creating comboBox for selecting number of queens
        ObservableList<Integer> options = FXCollections.observableArrayList(4,5,6,7,8,9,10,11,12);
        ComboBox number = new ComboBox(options);
        number.setPromptText("Select number of queens");
        number.setOnAction(e -> {

            //Generating a new board based on the number selected
            size = Integer.valueOf(number.getSelectionModel().getSelectedItem().toString());
            squares = new Rectangle[size][size];
            GuiScene gui = new GuiScene();
            this.squares = gui.generateSquares(size);

            board = new GridPane();
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++)
                    board.add(squares[i][j], i , j);
            }
            main.getChildren().set(0, board);
        });

        //Adding a label which will be set to show current heuristic
        userControls.getChildren().add(new Label("\n\n"));
        userControls.getChildren().add(number);

        //Algorithm selection area
        userControls.getChildren().add(new Label("Select an Algorithm\n\n"));

        VBox selection = new VBox();
        selection.setSpacing(4);
        hill = new RadioButton("Hill climbing");
        simulated = new RadioButton("Simulated annealing");
        beam = new RadioButton("Local beam search");
        genetic = new RadioButton("Genetic");

        //Creating a toggle group for the algorithms
        ToggleGroup algorithm = new ToggleGroup();
        hill.setToggleGroup(algorithm);
        simulated.setToggleGroup(algorithm);
        beam.setToggleGroup(algorithm);
        genetic.setToggleGroup(algorithm);
        selection.setSpacing(5);
        selection.getChildren().addAll(hill,simulated,beam,genetic);

        VBox parameters = new VBox();
        parameters.getChildren().addAll(new Label(), new Label(), new Label(), new Label(), new Label(), new Label());
        //parameters for simulated
        simulated.setOnAction(e -> {
            parameters.getChildren().set(0, new Label("Please add temperature"));
            temperature = new TextField();
            parameters.getChildren().set(1, temperature);

            parameters.getChildren().set(2, new Label("Please add cooling factor"));
            cooling = new TextField();
            parameters.getChildren().set(3, cooling);
            parameters.getChildren().set(4, new Label());
            parameters.getChildren().set(5, new Label());
        });

        //parameter for local beam search
        beam.setOnAction(e -> {
            parameters.getChildren().set(0, new Label("Please add number of beams"));
            beams = new TextField();
            parameters.getChildren().set(1, beams);
            parameters.getChildren().set(2, new Label());
            parameters.getChildren().set(3, new Label());
            parameters.getChildren().set(4, new Label());
            parameters.getChildren().set(5, new Label());
        });

        //parameters for genetic algorithm
        genetic.setOnAction(e -> {
            parameters.getChildren().set(0, new Label("Please add size of population"));
            population = new TextField();
            parameters.getChildren().set(1, population);

            parameters.getChildren().set(2, new Label("Please add size of generation"));
            generation = new TextField();
            parameters.getChildren().set(3, generation);

            parameters.getChildren().set(4, new Label("Please rate of mutation"));
            mutation = new TextField();
            parameters.getChildren().set(5, mutation);
        });


        //Adding the elements to the main view
        HBox controls = new HBox();
        controls.setSpacing(20);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().addAll(selection, parameters);

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);

        //Buttons for random board and start of an algorithm
        generateRandom = new Button("Generate Random");
        generateRandom.setOnAction(this);
        start = new Button("Start");
        start.setOnAction(this::handle);
        buttons.getChildren().addAll(generateRandom, start);
        userControls.getChildren().addAll(controls,buttons);

        // place splitPane as center
        BorderPane borderPane = new BorderPane(main);

        //load and show scene
        primaryStage.setScene(new Scene(borderPane, 350, 650));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    //Function to handle button events
    @Override
    public void handle(ActionEvent event) {

        //Generating a random board if the button is clicked
        if (event.getSource() == generateRandom) {
            General g = new General(size);
            state = g.randomBoard(size);
        }

        //Solving with hill climbing
        if(hill.isSelected()) {
            HillClimbing hc = new HillClimbing(size);
            System.out.println(size);
            if (event.getSource() == start) {
                Queen[] random = state;
                state = hc.solve(random);
            }
        }

        //Solving with simulated annealing
        if(simulated.isSelected()){
            if (event.getSource() == start) {
                float t = Float.valueOf(temperature.getText());
                int c = Integer.valueOf(cooling.getText());
                SimulatedAnnealing sa = new SimulatedAnnealing(size, t, c);
                Queen[] random = state;
                state = sa.solve(random);
            }
        }

        //Solving with local beam search
        if(beam.isSelected()){
            if(event.getSource() == start){
                int b = Integer.valueOf(beams.getText());
                LocalBeamSearch lb = new LocalBeamSearch(b, size);
                Queen[] random = state;
                state = lb.solve(random);
            }
        }

        //Solving with genetic
        if(genetic.isSelected()){
            if(event.getSource() == start){
                int p = Integer.valueOf(population.getText());
                int m = Integer.valueOf(mutation.getText());
                int gen = Integer.valueOf(generation.getText());
                Genetic g = new Genetic(size, p, m, gen);
                state = g.solve();
            }
        }

        //Displaying the solution
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Color color;
                if ((i + j) % 2 == 0) color = Color.BURLYWOOD;
                else color = Color.BROWN;
                squares[i][j].setFill(color);
            }
        }

        //Drawing the queens on the board
        Image queen = new Image("chess-queen.png");
        for (int i = 0; i < size; i++)
            this.squares[state[i].getColumn()][state[i].getRow()].setFill(new ImagePattern(queen));

        General g = new General(size);
        userControls.getChildren().set(0, new Label("\nCurrent state heuristics: " + g.getHeuristic(state) + "\n"));
    }


}
