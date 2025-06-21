// File: bantu_teman/src/main/java/com/sikatu/bantu_teman/controller/SettingsController.java

package com.sikatu.bantu_teman.controller;

import com.sikatu.bantu_teman.model.User;
import com.sikatu.bantu_teman.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class SettingsController {

    @FXML private FontIcon backButton;
    @FXML private HBox editProfileButton;
    @FXML private HBox logoutButton;

    // Toggle Buttons
    @FXML private ToggleButton reminderD3Toggle;
    @FXML private ToggleButton reminderD1Toggle;
    @FXML private ToggleButton lightModeToggle;
    @FXML private ToggleButton darkModeToggle;

    // Bottom Navigation
    @FXML private HBox homeButton;
    @FXML private HBox calendarButton;
    @FXML private HBox createTaskButton;
    @FXML private HBox coursesButton;

    @FXML
    public void initialize() {
        // Setup Event Handlers
        backButton.setOnMouseClicked(this::handleBack);
        editProfileButton.setOnMouseClicked(this::handleEditProfile);
        logoutButton.setOnMouseClicked(this::handleLogout);

        // Setup Theme Toggles to be mutually exclusive
        ToggleGroup themeToggleGroup = new ToggleGroup();
        lightModeToggle.setToggleGroup(themeToggleGroup);
        darkModeToggle.setToggleGroup(themeToggleGroup);
        lightModeToggle.setSelected(true); // Default Light Mode on

        // Setup Bottom Navigation
        homeButton.setOnMouseClicked(e -> navigateTo(homeButton, new SceneManager((Stage) homeButton.getScene().getWindow())::switchToDashboard));
        calendarButton.setOnMouseClicked(e -> navigateTo(calendarButton, new SceneManager((Stage) calendarButton.getScene().getWindow())::switchToCalendar));
        createTaskButton.setOnMouseClicked(e -> navigateTo(createTaskButton, new SceneManager((Stage) createTaskButton.getScene().getWindow())::switchToCreateTask));
        coursesButton.setOnMouseClicked(e -> navigateTo(coursesButton, new SceneManager((Stage) coursesButton.getScene().getWindow())::switchToCourses));
    }

    private void handleBack(MouseEvent event) {
        navigateTo(backButton, new SceneManager((Stage) backButton.getScene().getWindow())::switchToDashboard);
    }

    private void handleEditProfile(MouseEvent event) {
        navigateTo(editProfileButton, new SceneManager((Stage) editProfileButton.getScene().getWindow())::switchToProfile);
    }

    private void handleLogout(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LogoutDialog.fxml"));
            Parent root = loader.load();

            LogoutDialogController controller = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            dialogStage.setScene(scene);

            Stage ownerStage = (Stage) logoutButton.getScene().getWindow();
            dialogStage.initOwner(ownerStage);

            dialogStage.showAndWait();

            if (controller.isConfirmed()) {
                User.logout(); //
                new SceneManager((Stage) logoutButton.getScene().getWindow()).switchToSplashScreen();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateTo(Node node, Runnable navigationMethod) {
        navigationMethod.run();
    }
}