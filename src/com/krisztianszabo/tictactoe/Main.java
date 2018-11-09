package com.krisztianszabo.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
    primaryStage.setTitle("Tic Tac Toe");
    Scene scene = new Scene(root, 300, 345);
    scene.getStylesheets().add(getClass().getResource("MainWindow.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.getIcons().add(new Image("com/krisztianszabo/tictactoe/resources/icon.png"));
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}
