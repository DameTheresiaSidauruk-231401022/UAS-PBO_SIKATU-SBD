// File: bantu_teman/src/main/java/com/sikatu/bantu_teman/controller/ProfileController.java

package com.sikatu.bantu_teman.controller;

import com.sikatu.bantu_teman.db.UserDAO;
import com.sikatu.bantu_teman.model.User;
import com.sikatu.bantu_teman.util.SceneManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

public class ProfileController {

    @FXML private FontIcon backButton;
    @FXML private Circle profileImageCircle;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private DatePicker dobPicker;
    @FXML private ComboBox<String> countryComboBox;

    private UserDAO userDAO = new UserDAO();
    private User currentUser = User.getCurrentUser();
    private String currentImagePath = "";

    @FXML
    public void initialize() {
        backButton.setOnMouseClicked(this::handleBack);
        countryComboBox.setItems(FXCollections.observableArrayList("Indonesia", "Malaysia", "Singapore", "United States", "Other"));

        if (currentUser != null) {
            loadUserData();
        }
    }

    private void loadUserData() {
        try {
            Optional<Map<String, String>> userDetails = userDAO.getUserDetails(currentUser.getId());
            userDetails.ifPresent(details -> {
                nameField.setText(details.get("name"));
                emailField.setText(details.get("email"));

                String dob = details.get("dob");
                if (dob != null && !dob.isEmpty()) {
                    dobPicker.setValue(LocalDate.parse(dob));
                }

                countryComboBox.setValue(details.get("country"));
                currentImagePath = details.get("imagePath");

                if (currentImagePath != null && !currentImagePath.isEmpty()) {
                    File imgFile = new File(currentImagePath);
                    if (imgFile.exists()) {
                        Image image = new Image(imgFile.toURI().toString());
                        profileImageCircle.setFill(new ImagePattern(image));
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String dob = (dobPicker.getValue() != null) ? dobPicker.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE) : "";
        String country = countryComboBox.getValue();

        try {
            userDAO.updateProfile(currentUser.getId(), dob, country, currentImagePath);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Profile updated successfully!");
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not update profile.");
            alert.showAndWait();
        }
    }

    /**
     * Metode ini akan dipanggil dari FXML saat Circle diklik.
     */
    @FXML
    private void handleChangePicture(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(profileImageCircle.getScene().getWindow());
        if(selectedFile != null) {
            currentImagePath = selectedFile.getAbsolutePath();
            Image image = new Image(selectedFile.toURI().toString());
            profileImageCircle.setFill(new ImagePattern(image));
        }
    }

    private void handleBack(MouseEvent event) {
        new SceneManager((Stage) ((Node) event.getSource()).getScene().getWindow()).switchToSettings();
    }
}