package com.krisztianszabo.tictactoe;

import com.krisztianszabo.tictactoe.Board.GameState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
  private final XOGame model = XOGame.getInstance();
  @FXML private MenuItem menuUndo;
  @FXML private Canvas drawArea;
  @FXML private Label scoreLabel;
  private final Image IMG_TRANSPARENT = new Image("com/krisztianszabo/tictactoe/resources/transparent.png");
  private final Image IMG_X = new Image("com/krisztianszabo/tictactoe/resources/x.png");
  private final Image IMG_O = new Image("com/krisztianszabo/tictactoe/resources/o.png");

  public void initialize(URL location, ResourceBundle resources) {
    updateInterface();

    drawArea.setOnMouseClicked(mouseEvent -> {
      if (model.getGameState() == GameState.ONGOING) {
        double canvasWidth = drawArea.getWidth();
        double canvasHeight = drawArea.getHeight();
        int cellClicked = (int)(mouseEvent.getY() / (canvasHeight / 3)) * 3 +
            (int)(mouseEvent.getX() / (canvasWidth / 3)) + 1;
        if (model.makeMove(cellClicked)) {
          updateInterface();
          if (model.getGameState() != GameState.ONGOING) {
            if (model.getGameState() != GameState.TIE) {
              int winner = model.getGameState() == GameState.P1WON ? 1 : 2;
              PlayerManager.getInstance()
                  .getPlayer(winner).increaseScore();
            }
            updateInterface();
            showGameOverAlert();
            model.startNewGame(model.getStartingPlayer() ==
                1 ? 2 : 1);
            updateInterface();
          } else {
            Player currentPlayer = PlayerManager.getInstance().
                getPlayer(XOGame.getInstance().getCurrentPlayer());
            if (currentPlayer.isCPU()) {
              model.makeMove(currentPlayer.getMove());
              updateInterface();
            }
          }
        }
      }
    });

    Player currentPlayer = PlayerManager.getInstance().getPlayer(XOGame.getInstance().getCurrentPlayer());
    if (currentPlayer.isCPU()) {
      model.makeMove(currentPlayer.getMove());
      updateInterface();
    }
  }

  private void drawSymbols(Canvas target) {
    GraphicsContext draw = target.getGraphicsContext2D();
    double canvasWidth = target.getWidth();
    double canvasHeight = target.getHeight();
    int index = 0;
    char[] board = XOGame.getInstance().getBoardState().toCharArray();
    for (int j = 0; j < 3; j++) {
      for (int i = 0; i < 3; i++) {
        double imageOriginX = i * (canvasWidth / 3);
        double imageOriginY = j * (canvasHeight / 3);
        double imageWidth = canvasWidth / 3;
        double imageHeigth = canvasHeight / 3;
        Image imageContent;
        switch (board[index]) {
          case '1':
            imageContent = IMG_X;
            break;
          case '2':
            imageContent = IMG_O;
            break;
          default:
            imageContent = IMG_TRANSPARENT;
            break;
        }
        draw.drawImage(imageContent, imageOriginX, imageOriginY, imageWidth, imageHeigth);
        index++;
      }
    }
  }

  private void showGameOverAlert() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Game over!");
    alert.setHeaderText(null);
    switch (model.getGameState()) {
      case TIE:
        alert.setContentText("It's a tie!");
        break;
      case P1WON:
        alert.setContentText("Congratulations, " +
            PlayerManager.getInstance().getPlayer(1).getName() +
            ", you win!");
        break;
      case P2WON:
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
    Scene scene = new Scene(root, 300, 160);
    scene.getStylesheets().add(getClass().getResource("SettingsWindow.css").toExternalForm());
    stage.setScene(scene);
    stage.getIcons().add(new Image("com/krisztianszabo/tictactoe/resources/icon.png"));
    stage.setResizable(false);

    stage.setOnHiding(windowEvent -> updateInterface());
    stage.show();

  }

  private void drawBackground(Canvas drawArea) {
    double areaHeight = drawArea.getHeight();
    double areaWidth = drawArea.getWidth();
    GraphicsContext draw = drawArea.getGraphicsContext2D();
    draw.setFill(Color.WHITE);
    draw.fillRect(0, 0, areaWidth, areaHeight);
    draw.setStroke(Color.BLACK);
    draw.setLineWidth(5);
    double padding = 10;
    draw.strokeLine(areaWidth / 3, padding, areaWidth / 3, areaHeight - padding);
    draw.strokeLine((areaHeight / 3) * 2, padding, (areaWidth / 3) * 2, areaHeight - padding);
    draw.strokeLine(padding, areaHeight / 3, areaWidth - padding, areaHeight / 3);
    draw.strokeLine(padding, (areaHeight / 3) * 2, areaWidth - padding, (areaHeight / 3) * 2);
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
    drawBackground(drawArea);
    drawSymbols(drawArea);
    if (model.getWinningPattern() != Board.WinPattern.NONE) {
      drawWinLine(drawArea);
    }
    String text = PlayerManager.getInstance().getPlayer(1).getName() + ": " +
        PlayerManager.getInstance().getPlayer(1).getScore() + "     " +
        PlayerManager.getInstance().getPlayer(2).getName() + ": " +
        PlayerManager.getInstance().getPlayer(2).getScore();
    if (model.getGameState() == GameState.ONGOING) {
      if (model.getCurrentPlayer() == 1) {
        text = "->  " + text;
      } else {
        text = text + "  <-";
      }
    }
    scoreLabel.setText(text);
  }

  private void drawWinLine(Canvas drawArea) {
    double cellWidth = drawArea.getWidth() / 3;
    double cellHeight = drawArea.getHeight() / 3;
    double padding = 25;
    double lineStartX = 0, lineStartY = 0, lineEndX = 0, lineEndY = 0;
    switch (model.getWinningPattern()) {
      case COL_ONE:
        lineStartX = 0.5 * cellWidth;
        lineStartY = padding;
        lineEndX = 0.5 * cellWidth;
        lineEndY = drawArea.getHeight() - padding;
        break;
      case COL_TWO:
        lineStartX = 1.5 * cellWidth;
        lineStartY = padding;
        lineEndX = 1.5 * cellWidth;
        lineEndY = drawArea.getHeight() - padding;
        break;
      case COL_THREE:
        lineStartX = 2.5 * cellWidth;
        lineStartY = padding;
        lineEndX = 2.5 * cellWidth;
        lineEndY = drawArea.getHeight() - padding;
        break;
      case ROW_ONE:
        lineStartX = padding;
        lineStartY = 0.5 * cellHeight;
        lineEndX = drawArea.getWidth() - padding;
        lineEndY = 0.5 * cellHeight;
        break;
      case ROW_TWO:
        lineStartX = padding;
        lineStartY = 1.5 * cellHeight;
        lineEndX = drawArea.getWidth() - padding;
        lineEndY = 1.5 * cellHeight;
        break;
      case ROW_THREE:
        lineStartX = padding;
        lineStartY = 2.5 * cellHeight;
        lineEndX = drawArea.getWidth() - padding;
        lineEndY = 2.5 * cellHeight;
        break;
      case DIAG_ONE:
        lineStartX = padding;
        lineStartY = padding;
        lineEndX = drawArea.getWidth() - padding;
        lineEndY = drawArea.getHeight() - padding;
        break;
      case DIAG_TWO:
        lineStartX = drawArea.getWidth() - padding;
        lineStartY = padding;
        lineEndX = padding;
        lineEndY = drawArea.getHeight() - padding;
        break;
      default:
        break;
    }
    GraphicsContext draw = drawArea.getGraphicsContext2D();
    draw.setStroke(Color.RED);
    draw.setLineWidth(5);
    draw.strokeLine(lineStartX, lineStartY, lineEndX, lineEndY);
  }
}
