package com.krisztianszabo.tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingsWindowController implements Initializable {
  @FXML private Button buttonSave;
  @FXML private TextField p1name;
  @FXML private TextField p2name;

  public void initialize(URL location, ResourceBundle resources) {
    p1name.setText(PlayerManager.getInstance().getPlayer(1).getName());
    p2name.setText(PlayerManager.getInstance().getPlayer(2).getName());
  }

  @FXML
  private void cancelAndClose() {
    closeStage();
  }

  @FXML
  private void saveAndClose() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Start new game?");
    alert.setHeaderText("Both the board and the scores will be reset.");
    alert.setContentText("Are you ok with this?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
      PlayerManager.getInstance().getPlayer(1).setName(p1name.getText());
      PlayerManager.getInstance().getPlayer(2).setName(p2name.getText());
      PlayerManager.getInstance().resetScores();
      XOGame.getInstance().startNewGame();
    }
    closeStage();
  }

  private void closeStage() {
    ((Stage)buttonSave.getScene().getWindow()).close();
  }
}
