package COM.XENONSTAR.CONNECT4;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public  Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("GAME.fxml"));
        GridPane rootnode=loader.load();
        MenuBar mbar=createMenuBar();
       // mbar.setStyle("-fx-text-fill: #FF0000");


        mbar.prefWidthProperty().bind(primaryStage.widthProperty());
        Pane pane=(Pane)rootnode.getChildren().get(0);


        pane.getChildren().add(0,mbar);
       Controller controller=loader.getController();
       controller.createPlayground();

        Scene scene=new Scene(rootnode);
        primaryStage.setScene(scene);
        primaryStage.setTitle("CONNECT--FOUR");
        primaryStage.show();
       New.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               controller.newGame();
           }
       });
       Reset.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
          controller.resetGame();
           }
       });
       Undo.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
          // controller.undo();
           }
       });
    }

    MenuItem New=new MenuItem("NEW GAME");
    MenuItem Reset =new MenuItem("RESET GAME");
    MenuItem Undo=new MenuItem("UNDO");
    public MenuBar createMenuBar()
    {
    MenuBar bar=new MenuBar();
    Menu file=new Menu("FILE");


    Menu help=new Menu("HELP");


    MenuItem about =new MenuItem("ABOUT CONNECT_4");
        MenuItem aboutme =new MenuItem("ABOUT-ME");
    SeparatorMenuItem sep=new SeparatorMenuItem();
        SeparatorMenuItem sep1=new SeparatorMenuItem();

        file.getItems().addAll(New,sep,Reset);
        help.getItems().addAll(about,aboutme);
        bar.getMenus().addAll(file,help);

        about.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            createAboutApp();
        }
    });
        aboutme.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                creatAboutMe();
            }
        });
        return bar;
}

    public void createAboutApp()
    {   Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("CONNECT-4 BY RAHUL_LOUR");
        alert.setTitle("ABOUT THIS APP");
        alert.setContentText("Connect Four (also known as Four Up, Plot Four, Find Four, Four in a Row, Four in a Line, Drop Four, and Gravitrips (in Soviet Union)) is a two-player connection  board game in which the players first choose a color and then take turns dropping one colored disc from the top into a seven-column, six-row vertically suspended grid. The pieces fall straight down, occupying the lowest available space within the column. The objective of the game is to be the first to form a  horizontal, vertical, or diagonal line of four of one's own discs.\n Connect Four is a solved game. The first player can always win by playing the right moves.\n" +
                "--YOU ARE SUGGESTED NOT TO COPY AND SHARE THIS PROJECT--");
        alert.setWidth(400);
        alert.setHeight(400);
        alert.show();
    }
    public void creatAboutMe()
    { Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("CONNECT-4 BY RAHUL_LOUR");
        alert.setTitle("ABOUT THE DEVELOPER");
        alert.setContentText("I LOVE TO CODE , YOUR SUGGESTION ARE MOST WELCOME \n " +
                "FROM DEVELOPER SIDE--HAPPY GAMING :)-(: \n" +
                "Developer's Contact -- rahulbidawas@gmail.com ");

        alert.setWidth(400);
        alert.setHeight(300);
        alert.show();


    }



}
