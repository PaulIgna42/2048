package com.example.lastminute.gui;

import com.example.lastminute.Direction;
import javafx.application.Application;
import com.example.lastminute.NumberGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class NumberGui extends Application {

    private static VBox fullPane;
    private static HBox header;
    private static GridPane myGrid;
    private static NumberGame game;
    private static StackPane[][] myGridStackPane;
    private static Text points;

    @Override
    public void init() {
        fullPane = new VBox();
        fullPane.setStyle("-fx-background-color: DAE7F8;");

        header = new HBox();
        header.setPadding(new Insets(10));
        header.setSpacing(10);

        Text pointsLable = new Text("Points:");
        pointsLable.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        points = new Text("" + game.getPoints());
        points.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        header.getChildren().addAll(pointsLable, points);

        myGrid = new GridPane();
        myGrid.setPadding(new Insets(10));
        myGrid.setStyle("-fx-background-color: DAE6F3;");
        myGrid.setHgap(6);
        myGrid.setVgap(6);
        myGridStackPane = new StackPane[game.getWidth()][game.getHeight()];
        for (int x = 0; x < game.getWidth(); x++) {
            for (int y = 0; y < game.getHeight(); y++) {
                myGridStackPane[x][y] = new StackPane();
                Rectangle r = new Rectangle(100, 100);
                r.setFill(Color.WHITE);
                myGridStackPane[x][y].getChildren().add(r);
                myGrid.add(myGridStackPane[x][y], x, y);
            }
        }

        fullPane.getChildren().addAll(header, myGrid);
        updateGrid();

    }

    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(fullPane, 438, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("2048");

        addKeyHandler(scene);

        primaryStage.show();
    }

    private void addKeyHandler(Scene scene) {
        scene.setOnKeyPressed(ke -> {
            KeyCode keyCode = ke.getCode();
            if (keyCode.isArrowKey()) {
                Direction dir = null;
                if (keyCode.equals(KeyCode.UP))
                    dir = Direction.UP;
                if (keyCode.equals(KeyCode.DOWN))
                    dir = Direction.DOWN;
                if (keyCode.equals(KeyCode.LEFT))
                    dir = Direction.LEFT;
                if (keyCode.equals(KeyCode.RIGHT))
                    dir = Direction.RIGHT;
                moveHandler(dir);
            }
        });
    }

    public void moveHandler(Direction dir) {
        if (game.canMove(dir)) {
            game.move(dir);
            game.addRandomTile();
            updateGrid();
            updatePoints();

            if (!game.canMove()) {
                showGameOverOverlay();
            }
        }
    }

    public void showGameOverOverlay() {
        // Create a semi-transparent overlay
        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);"); // Semi-transparent black background

        // Add the "out of moves" message
        Label gameOverLabel = new Label("No more moves!");
        gameOverLabel.setFont(new Font(20));
        gameOverLabel.setPadding(new Insets(10));


        overlay.getChildren().add(gameOverLabel);

        // Add the overlay to the root pane (fullPane)
        fullPane.getChildren().add(overlay);

    }

    public void updatePoints() {
        header.getChildren().remove(1);
        points = new Text("" + game.getPoints());
        points.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        header.getChildren().add(points);
    }

    public void updateGrid() {
        int value;
        for (int x = 0; x < game.getWidth(); x++) {
            for (int y = 0; y < game.getHeight(); y++) {
                value = game.get(x, y);
                updateStackPane(myGridStackPane[x][y], value);
            }
        }
    }

    public void updateStackPane(StackPane p, int value) {
        Rectangle r;
        switch (value) {
            case 0:
                p.getChildren().clear();
                r = new Rectangle(100, 100);
                r.setFill(Color.WHITE);
                p.getChildren().add(r);
                break;

            default:
                p.getChildren().clear();
                r = new Rectangle(100, 100);
                setFillForValue(r, value);
                Text valueText = new Text("" + value);
            /* Uncomment here to set tiles with "2048" to hearts
      if (value==2048) {
                valueText.setText("\u2661");
            }
      */
                valueText.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
                valueText.setFill(Color.WHITE);
                valueText.setStroke(Color.BLACK);
                p.getChildren().addAll(r, valueText);
                break;
        }
    }

    public void setFillForValue(Rectangle r, int value) {
        switch (value) {
            case 2:
                r.setFill(Color.BEIGE);
                break;
            case 4:
                r.setFill(Color.BURLYWOOD);
                break;
            case 8:
                r.setFill(Color.SANDYBROWN);
                break;
            case 16:
                r.setFill(Color.YELLOW);
                break;
            case 32:
                r.setFill(Color.YELLOWGREEN);
                break;
            case 64:
                r.setFill(Color.SEAGREEN);
                break;
            case 128:
                r.setFill(Color.STEELBLUE);
                break;
            case 256:
                r.setFill(Color.CORNFLOWERBLUE);
                break;
            case 512:
                r.setFill(Color.SKYBLUE);
                break;
            case 1024:
                r.setFill(Color.THISTLE);
                break;
            case 2048:
                r.setFill(Color.VIOLET);
                break;
            default:
                r.setFill(Color.BLACK);
        }
    }

    @Override
    public void stop() {

    }

    public static void main(String[] args) {
        game = new NumberGame(4, 4, 2);
        launch(args);
    }
}