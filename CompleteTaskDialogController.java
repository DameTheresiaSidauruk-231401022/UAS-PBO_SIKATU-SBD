
package com.sikatu.bantu_teman.controller;

import com.sikatu.bantu_teman.model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CompleteTaskDialogController {

    @FXML private Label taskNameLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label deadlineLabel;
    @FXML private Button cancelButton;
    @FXML private Button completeButton;

    private Task task;
    private boolean confirmed = false;

    public void setTask(Task task) {
        this.task = task;
        taskNameLabel.setText("Complete '" + task.getTaskName() + "'?");
        descriptionLabel.setText("Description: " + (task.getDescription().isEmpty() ? "No description." : task.getDescription()));
        deadlineLabel.setText("Deadline: " + task.getEndTime());
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    @FXML
    void handleComplete(ActionEvent event) {
        confirmed = true;
        closeStage();
    }

    @FXML
    void handleCancel(ActionEvent event) {
        confirmed = false;
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) completeButton.getScene().getWindow();
        stage.close();
    }
}