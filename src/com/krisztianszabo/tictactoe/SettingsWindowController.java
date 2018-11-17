package com.krisztianszabo.tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingsWindowController implements Initializable {
  @FXML private TextField p1name;
  @FXML private TextField p2name;
  @FXML private CheckBox CPUEnabled;
  @FXML private RadioButton CPU_P1;
  @FXML private RadioButton CPU_P2;

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
    if (result.isPresent() && result.get() == ButtonType.OK){
      if (CPUEnabled.isSelected()) {
        PlayerManager.getInstance().setPlayer(1, CPU_P1.isSelected() ?
            new ComputerPlayer() : new HumanPlayer(p1name.getText()));
        PlayerManager.getInstance().setPlayer(2, CPU_P2.isSelected() ?
            new ComputerPlayer() : new HumanPlayer(p2name.getText()));
      } else {
        PlayerManager.getInstance().setPlayer(1, new HumanPlayer(p1name.getText()));
        PlayerManager.getInstance().setPlayer(2, new HumanPlayer(p2name.getText()));
      }
      XOGame.getInstance().startNewGame();
    }
    closeStage();
  }

  private void closeStage() {
    ((Stage)p1name.getScene().getWindow()).close();
  }

  public void checkBoxClicked() {
    if (CPUEnabled.isSelected()) {
      CPU_P1.setDisable(false);
      CPU_P2.setDisable(false);
      p1name.setDisable(CPU_P1.isSelected());
      p2name.setDisable(CPU_P2.isSelected());
    } else {
      CPU_P1.setDisable(true);
      CPU_P2.setDisable(true);
      p1name.setDisable(false);
      p2name.setDisable(false);
    }
  }

  public void radioButtonsClicked() {
    p1name.setDisable(CPU_P1.isSelected());
    p2name.setDisable(CPU_P2.isSelected());
  }
}
