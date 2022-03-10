package COM.XENONSTAR.CONNECT4;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Controller implements Initializable {
    @FXML
    public GridPane gridPane;
    public Pane menuPane;
    public Pane discPane = new Pane();
    public VBox playerBox;
    public TextField player1, player2, color1, color2;
    public Label labelOne,oneCount,twoCount,oneName,twoName,leadText1,leadText2,slash;
    public Button playButton;
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;
    private static final int SROWS = 7;
    private static final int SCOLUMNS = 8;

    private static final int CIRCLE_DIAMETER = 90;
    public static String discColor1 = "RED";
    public static String discColor2 = "YELLOW";

    public static String proDiscColor1;
    public static String proDiscColor2;


    public static String PLAYER_ONE = "PLAYER ONE";
    public static String PLAYER_TWO = "PLAYER TWO";
    public static String OLD_PLAYER_ONE="";
    public static String OLD_PLAYER_TWO="";


    private boolean isPlayerOneTurn = true;
    private Disc[][] insertedDiscsArray = new Disc[ROWS][COLUMNS];   //For my use;


    public void setNames() {
        PLAYER_ONE = player1.getText();
        PLAYER_ONE = PLAYER_ONE.toUpperCase();
        PLAYER_ONE = PLAYER_ONE.concat("'S");
        PLAYER_TWO = player2.getText();
        PLAYER_TWO = PLAYER_TWO.toUpperCase();
        PLAYER_TWO = PLAYER_TWO.concat("'S");

        if (player1.getText().isBlank()) {
            PLAYER_ONE = "PLAYER ONE'S";


        }
        if (player2.getText().isBlank()) {
            PLAYER_TWO = "PLAYER TWO'S";

        }
        if (player1.getText().isBlank() && player2.getText().isBlank()) {
            PLAYER_ONE = "PLAYER ONE'S";
            PLAYER_TWO = "PLAYER TWO'S";

        }
        labelOne.setText(PLAYER_ONE);
        proDiscColor1 = color1.getText();
        proDiscColor2 = color2.getText();
        proDiscColor1 = proDiscColor1.toUpperCase();
        proDiscColor2 = proDiscColor2.toUpperCase();


    }

    public void createPlayground() {

        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                setNames();
                letsPlayResetGame();
                player1.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        fieldResetGame();


                    }
                });
                player2.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        fieldResetGame();

                    }
                });

            }
        });


        Shape createStructureGrid = createStructure();
        gridPane.add(createStructureGrid, 0, 1);
        List<Rectangle> rectangleList = createClickableColumns();
        for (Rectangle rectangle : rectangleList)
            gridPane.add(rectangle, 0, 1);

    }


    public Shape createStructure() {
        Shape rectangleWithHoles = new Rectangle(SCOLUMNS * CIRCLE_DIAMETER, SROWS * CIRCLE_DIAMETER);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Circle circle = new Circle();
                circle.setSmooth(true);

                circle.setRadius(CIRCLE_DIAMETER / 2);
                circle.setCenterX(CIRCLE_DIAMETER / 2);
                circle.setCenterY(CIRCLE_DIAMETER / 2);


                circle.setTranslateX(j * (CIRCLE_DIAMETER + 5) + (CIRCLE_DIAMETER / 5));
                circle.setTranslateY(i * (CIRCLE_DIAMETER + 5) + (CIRCLE_DIAMETER / 5));
                rectangleWithHoles = Shape.subtract(rectangleWithHoles, circle);


            }
        }


        rectangleWithHoles.setFill(Color.CYAN);
        return rectangleWithHoles;

    }

    public List<Rectangle> createClickableColumns() {
        List<Rectangle> rectangleList = new ArrayList<>();
        for (int i = 0; i < COLUMNS; i++) {
            Rectangle rectangle = new Rectangle(CIRCLE_DIAMETER, SROWS * CIRCLE_DIAMETER);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setTranslateX(i * (CIRCLE_DIAMETER + 5) + (CIRCLE_DIAMETER / 5));
            rectangle.setOnMouseEntered(mouseEvent -> rectangle.setFill(Color.valueOf("#eeeeee56")));
            rectangle.setOnMouseExited(mouseEvent -> rectangle.setFill(Color.TRANSPARENT));
            final int column = i;
            rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    insertDisc(new Disc(isPlayerOneTurn), column);


                }
            });

            rectangleList.add(rectangle);
        }
        return rectangleList;


    }

    public void insertDisc(Disc disc, int COLUMN) {
        int row = ROWS - 1;

        while (row >= 0) {
            if (insertedDiscsArray[row][COLUMN] == null) {
                break;
            }
            row--;
        }

        if (row < 0) {
            return;

        }
        insertedDiscsArray[row][COLUMN] = disc;  //for my use.
        discPane.getChildren().add(disc);


        disc.setTranslateX(COLUMN * (CIRCLE_DIAMETER + 5) + (CIRCLE_DIAMETER / 5));
        int currentRow = row;
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.3), disc);
        translateTransition.setToY(row * (CIRCLE_DIAMETER + 5) + (CIRCLE_DIAMETER / 5));
        ;

        if (gameEnded(currentRow, COLUMN)) {
            gameOver();
        }

        isPlayerOneTurn = !isPlayerOneTurn;
        labelOne.setText(isPlayerOneTurn ? PLAYER_ONE : PLAYER_TWO);


        translateTransition.play();

    }

    public boolean undostatus = false;

    public void undo() {
        undostatus = true;


    }


    private boolean gameEnded(int row, int column) {
        List<Point2D> verticalPoints = IntStream.rangeClosed(row - 3, row + 3)  // If, row = 3, column = 3, then row = 0,1,2,3,4,5,6
                .mapToObj(r -> new Point2D(r, column))  // 0,3  1,3  2,3  3,3  4,3  5,3  6,3 [ Just an example for better understanding ]
                .collect(Collectors.toList());

        List<Point2D> horizontalPoints = IntStream.rangeClosed(column - 3, column + 3)
                .mapToObj(col -> new Point2D(row, col))
                .collect(Collectors.toList());

        Point2D startPoint1 = new Point2D(row - 3, column + 3);
        List<Point2D> diagonal1Points = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> startPoint1.add(i, -i))
                .collect(Collectors.toList());

        Point2D startPoint2 = new Point2D(row - 3, column - 3);
        List<Point2D> diagonal2Points = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> startPoint2.add(i, i))
                .collect(Collectors.toList());

        boolean isEnded = checkCombinations(verticalPoints) || checkCombinations(horizontalPoints)
                || checkCombinations(diagonal1Points) || checkCombinations(diagonal2Points);

        return isEnded;
    }

    private boolean checkCombinations(List<Point2D> points) {

        int chain = 0;

        for (Point2D point : points) {

            int rowIndexForArray = (int) point.getX();
            int columnIndexForArray = (int) point.getY();

            Disc disc = getDiscIfPresent(rowIndexForArray, columnIndexForArray);

            if (disc != null && disc.isPlayerOneMove == isPlayerOneTurn) {  // if the last inserted Disc belongs to the current player

                chain++;
                if (chain == 4) {
                    return true;
                }
            } else {
                chain = 0;
            }
        }

        return false;
    }

    private Disc getDiscIfPresent(int row, int column) {    // To prevent ArrayIndexOutOfBoundException

        if (row >= ROWS || row < 0 || column >= COLUMNS || column < 0)  // If row or column index is invalid
            return null;

        return insertedDiscsArray[row][column];
    }

    private class Disc extends Circle {

        private final boolean isPlayerOneMove;

        public Disc(boolean isPlayerOneMove) {

            this.isPlayerOneMove = isPlayerOneMove;
            setRadius(CIRCLE_DIAMETER / 2);


            if (proDiscColor1.isBlank() && proDiscColor2.isBlank()) {

                setFill(isPlayerOneMove ? Color.valueOf(discColor1) : Color.valueOf(discColor2));
                color1.setText("COLOR SET TO DEFAULT-(RED)");
                color2.setText("COLOR SET TO DEFAULT-(YELLOW)");
                color2.setText("COLOR SET TO DEFAULT-(YELLOW)");

            } else if (proDiscColor1.length() > 0 && proDiscColor2.length() > 0) {
                if (isPlayerOneMove) {
                    try {
                        Color.valueOf(proDiscColor1);
                        setFill(Color.valueOf(proDiscColor1));
                    } catch (Exception e) {
                        setFill(Color.valueOf(discColor1));
                        color1.setAlignment(Pos.CENTER_LEFT);
                        color1.setText("COLOR SET TO DEFAULT-(RED)");
                    }


                } else {
                    try {
                        Color.valueOf(proDiscColor2);
                        setFill(Color.valueOf(proDiscColor2));
                    } catch (Exception e) {
                        setFill(Color.valueOf(discColor2));
                        color2.setAlignment(Pos.CENTER_LEFT);
                        color2.setText("COLOR SET TO DEFAULT-(YELLOW)");
                    }


                }

            } else if (proDiscColor1.isBlank() && proDiscColor2.length() > 0) {
                {
                    if (isPlayerOneMove) {
                        setFill(Color.valueOf(discColor1));
                        color1.setAlignment(Pos.CENTER_LEFT);
                        color1.setText("COLOR SET TO DEFAULT-(RED)");
                    } else {
                        try {
                            Color.valueOf(proDiscColor2);
                            setFill(Color.valueOf(proDiscColor2));
                        } catch (Exception e) {
                            setFill(Color.valueOf(discColor2));
                            color2.setAlignment(Pos.CENTER_LEFT);
                            color2.setText("COLOR SET TO DEFAULT-(YELLOW)");
                        }


                    }

                }

            } else if (proDiscColor1.length() > 0 && proDiscColor2.isBlank()) {
                {
                    if (isPlayerOneMove) {
                        try {
                            setFill(Color.valueOf(proDiscColor1));
                            Color.valueOf(proDiscColor1);

                        } catch (Exception e) {
                            setFill(Color.valueOf(discColor1));
                            color1.setAlignment(Pos.CENTER_LEFT);
                            color1.setText("COLOR SET TO DEFAULT-(RED)");
                        }


                    } else

                        setFill(Color.valueOf(discColor2));
                    color2.setAlignment(Pos.CENTER_LEFT);
                    color2.setText("COLOR SET TO DEFAULT-(YELLOW)");


                }


            }


            setCenterX(CIRCLE_DIAMETER / 2);
            setCenterY(CIRCLE_DIAMETER / 2);
        }
    }
    public int onewincount=0;
    public int twowincount=0;
    private void gameOver() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String winner = isPlayerOneTurn ? PLAYER_TWO : PLAYER_ONE;
                winner = winner.substring(0, winner.length() - 2);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("WINNER");
                alert.setHeaderText(winner + " HAS WON!!");
                alert.setContentText("WANNA PLAY AGAIN!!");
                ButtonType yesBtn = new ButtonType("YES");
                ButtonType noBtn = new ButtonType("NO,EXIT");
                alert.getButtonTypes().setAll(yesBtn, noBtn);

                alert.setWidth(400);
                alert.setHeight(200);
                Optional<ButtonType> btnclicked = alert.showAndWait();
                if (btnclicked.isPresent() && btnclicked.get() == yesBtn) {
                    letsPlayResetGame();
                    String tempPLAYERONE=PLAYER_ONE.substring(0,PLAYER_ONE.length() - 2);
                    String tempPLAYERTWO=PLAYER_TWO.substring(0,PLAYER_TWO.length() - 2);


                    if(winner.contentEquals(tempPLAYERONE))
                    { onewincount=onewincount+1;
                        oneCount.setText(Integer.toString(onewincount));
                        //  System.out.println("playerone"+onewincount);

                    }
                    else if(winner.contentEquals(tempPLAYERTWO))
                    {  twowincount=twowincount+1;
                        twoCount.setText(Integer.toString(twowincount));
                        //  System.out.println("Playertwo"+twowincount);


                    }
                    if(onewincount>twowincount) {
                        leadText1.setText("Leading->");
                        slash.setText("");
                        leadText2.setText("");
                    }
                    else if(twowincount>onewincount) {
                        leadText2.setText("Leading->");
                        slash.setText("");
                        leadText1.setText("");
                    }

                    else {   leadText1.setText("EqualSkills->");
                        leadText2.setText("EqualSkills->");
                        slash.setText("|");
                    }




                } else {
                    Platform.exit();
                    System.exit(0);

                }

            }
        });

    }
    public void letsPlayResetGame()
    {  discPane.getChildren().clear();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                insertedDiscsArray[i][j] = null;
            }

        }
        isPlayerOneTurn = true;
        labelOne.setText(PLAYER_ONE);
        oneName.setText(PLAYER_ONE+" WINS");
        twoName.setText(PLAYER_TWO+" WINS");
        createPlayground();




    }

    public void resetGame() {
        discPane.getChildren().clear();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                insertedDiscsArray[i][j] = null;
            }

        }
        isPlayerOneTurn = true;
        labelOne.setText(PLAYER_ONE);
        oneName.setText("PLAYER ONE"+" WINS");
        twoName.setText("PLAYER TWO"+" WINS");
        createPlayground();
        color1.setText("");
        color2.setText("");

    }
    public void fieldResetGame()
    {
        discPane.getChildren().clear();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                insertedDiscsArray[i][j] = null;
            }

        }
        isPlayerOneTurn = true;
        labelOne.setText("PLAYER ONE'S");
        oneName.setText("PLAYER ONE"+" WINS");
        twoName.setText("PLAYER TWO"+" WINS");
        oneCount.setText("0");
        twoCount.setText("0");
        leadText1.setText("");
        leadText2.setText("");
        slash.setText("");
        onewincount=0;
        twowincount=0;
        PLAYER_ONE="PLAYER ONE'S";
        PLAYER_TWO="PLAYER TWO'S";


        createPlayground();


    }

    public void newGame() {
        discPane.getChildren().clear();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                insertedDiscsArray[i][j] = null;
            }

        }
        isPlayerOneTurn = true;
        labelOne.setText("PLAYER ONE'S");
        player1.setText("");
        player2.setText("");
        color1.setText("");
        color2.setText("");

        oneName.setText("PLAYER ONE"+" WINS");
        twoName.setText("PLAYER TWO"+" WINS");
        oneCount.setText("0");
        twoCount.setText("0");
        leadText1.setText("");
        leadText2.setText("");
        slash.setText("");
        PLAYER_ONE="PLAYER ONE'S";
        PLAYER_TWO="PLAYER TWO'S";
        onewincount=0;
        twowincount=0;


        createPlayground();
        color1.setText("");
        color2.setText("");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
