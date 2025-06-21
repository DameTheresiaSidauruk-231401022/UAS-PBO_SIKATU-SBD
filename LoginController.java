package com.sikatu.bantu_teman.controller;

import com.sikatu.bantu_teman.db.UserDAO;
import com.sikatu.bantu_teman.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.sql.SQLException;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Email and password cannot be empty.");
            return;
        }

        try {
            boolean isLoggedIn = userDAO.login(email, password);
            if (isLoggedIn) {
                // Login sukses, pindah ke dashboard
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                SceneManager sceneManager = new SceneManager(stage);
                sceneManager.switchToDashboard();
            } else {
                errorLabel.setText("Invalid email or password.");
            }
        } catch (SQLException e) {
            errorLabel.setText("Database error. Please try again later.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignUpLink(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.switchToSignUp();
    }
}