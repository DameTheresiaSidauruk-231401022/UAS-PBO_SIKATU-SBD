package com.sikatu.bantu_teman.controller;

import com.sikatu.bantu_teman.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SplashScreenController {

    @FXML
    private void handleLetsStart(ActionEvent event) {
        // Dapatkan stage dari tombol yang diklik
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Gunakan SceneManager untuk berpindah
        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.switchToLogin();
    }
}