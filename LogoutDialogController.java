// File: bantu_teman/src/main/java/com/sikatu/bantu_teman/controller/LogoutDialogController.java

package com.sikatu.bantu_teman.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LogoutDialogController {

    @FXML private Button noButton;
    @FXML private Button yesButton;

    private boolean confirmed = false;

    public boolean isConfirmed() {
        return confirmed;
    }

    @FXML
    void handleYes(ActionEvent event) {
        confirmed = true;
        closeStage();
    }

    @FXML
    void handleNo(ActionEvent event) {
        confirmed = false;
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) yesButton.getScene().getWindow();
        stage.close();
    }
}