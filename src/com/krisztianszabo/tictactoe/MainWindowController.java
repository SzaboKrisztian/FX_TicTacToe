package com.krisztianszabo.tictactoe;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
  private final XOGame model = XOGame.getInstance();
  @FXML private MenuItem menuUndo;
  @FXML private GridPane gameGrid;
  @FXML private Label scoreLabel;
  private final Image IMG_TRANSPARENT = new Image("com/krisztianszabo/tictactoe/resources/transparent.png");
  private final Image IMG_X = new Image("com/krisztianszabo/tictactoe/resources/x.png");
  private final Image IMG_O = new Image("com/krisztianszabo/tictactoe/resources/o.png");

  public void initialize(URL location, ResourceBundle resources) {
    int i = 1;
    for (Node child: gameGrid.getChildren()) {
      if (child instanceof javafx.scene.image.ImageView) {
        ((ImageView) child).setPreserveRatio(true);
        ((ImageView) child).setFitHeight(100);
        ((ImageView) child).setFitWidth(100);
        resetBoardImages();
        child.setOnMouseClicked(new EventHandler<MouseEvent>() {
          private int cell;

          @Override
          public void handle(MouseEvent event) {
            if (model.getGameState() == 0) {
              // The XOGame.makeMove(int) method returns false if the attempted move was
              // in any way illegal and could not be, thus, performed. Hence, the board
              // didn't change.
              if (model.makeMove(cell)) {
                // But if he move was indeed performed, we should first change the ImagesViews to
                // the appropriate images, to reflect the change in the game model, and then check
                // if the game state changed, and display the appropriate alert if it so did.
                updateInterface();
                if (model.getGameState() != 0) {
                  if (model.getGameState() != 3) {
                    PlayerManager.getInstance()
                        .getPlayer(model.getGameState()).increaseScore();
                  }
                  updateInterface();
                  showGameOverAlert();
                  model.startNewGame(model.getStartingPlayer() ==
                      1 ? 2 : 1);
                  updateInterface();
                }
              }
            }
            event.consume();
          }

          private EventHandler<MouseEvent> setCell(int num) {
            this.cell = num;
            return this;
          }
        }.setCell(i));
        child.setPickOnBounds(true);
      }
      i++;
    }

    updateInterface();
  }

  private void showGameOverAlert() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Game over!");
    alert.setHeaderText(null);
    switch (model.getGameState()) {
      case 3:
        alert.setContentText("It's a tie!");
        break;
      case 1:
        alert.setContentText("Congratulations, " +
            PlayerManager.getInstance().getPlayer(1).getName() +
            ", you win!");
        break;
      case 2:
        alert.setContentText("Congratulations, " +
            PlayerManager.getInstance().getPlayer(2).getName() +
            ", you win!");
        break;
      default:
        alert.setContentText("What just happened!?");
    }
    alert.showAndWait();
  }

  public void quitProgram() {
    Platform.exit();
  }

  public void showSettingsScene() throws IOException {
    Stage stage = new Stage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingsWindow.fxml"));
    Parent root = loader.load();

    stage.setTitle("Game Settings");
    stage.setScene(new Scene(root, 300, 325));
    stage.setResizable(false);

    stage.setOnHiding(windowEvent -> updateInterface());
    stage.show();

  }

  private void resetBoardImages() {
    int i = 0;
    String boardRep = model.getBoardState();
    for (Node child: gameGrid.getChildren()) {
      if (child instanceof javafx.scene.image.ImageView) {
        Image appropriateImage;
        switch (boardRep.charAt(i)) {
          case '1':
            appropriateImage = IMG_X;
            break;
          case '2':
            appropriateImage = IMG_O;
            break;
          default:
            appropriateImage = IMG_TRANSPARENT;
        }
        ((ImageView) child).setImage(appropriateImage);
      }
      i++;
    }
  }

  public void undoLastMove() {
    model.undoLastMove();
    updateInterface();
  }

  public void startNewGame() {
    PlayerManager.getInstance().resetScores();
    model.startNewGame();
    updateInterface();
  }

  private void updateInterface() {
    menuUndo.setDisable(model.getBoardState().equals("000000000"));
    resetBoardImages();
    String text = PlayerManager.getInstance().getPlayer(1).getName() + ": " +
        PlayerManager.getInstance().getPlayer(1).getScore() + "     " +
        PlayerManager.getInstance().getPlayer(2).getName() + ": " +
        PlayerManager.getInstance().getPlayer(2).getScore();
    if (model.getGameState() == 0) {
      if (model.getCurrentPlayer() == 1) {
        text = "->  " + text;
      } else {
        text = text + "  <-";
      }
    }
    scoreLabel.setText(text);
  }
}
