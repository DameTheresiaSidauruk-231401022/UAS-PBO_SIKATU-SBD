package com.sikatu.bantu_teman.controller;

import com.sikatu.bantu_teman.db.UserDAO;
import com.sikatu.bantu_teman.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SignUpController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleSignUp(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("All fields are required.");
            return;
        }

        // Validasi email sederhana
        if (!email.matches("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$")) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Invalid email format.");
            return;
        }

        try {
            userDAO.signUp(name, email, password);
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("Account created successfully! Redirecting to login...");

            // Tambahkan jeda singkat sebelum pindah agar user bisa baca pesan
            // (Untuk UX yang lebih baik, namun untuk sekarang kita langsung pindah)
            handleSignInLink(event);

        } catch (SQLException e) {
            if (e.getErrorCode() == 19) { // Kode error SQLite untuk UNIQUE constraint violation
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Email is already registered.");
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Database error. Please try again.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleSignInLink(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.switchToLogin();
    }
}