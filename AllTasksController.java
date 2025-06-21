// File: bantu_teman/src/main/java/com/sikatu/bantu_teman/controller/AllTasksController.java

package com.sikatu.bantu_teman.controller;

import com.sikatu.bantu_teman.db.TaskDAO;
import com.sikatu.bantu_teman.model.Task;
import com.sikatu.bantu_teman.model.User;
import com.sikatu.bantu_teman.util.SceneManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.sql.SQLException;

public class AllTasksController {

    @FXML private FontIcon backButton;
    @FXML private ListView<Task> allTasksListView;

    private final TaskDAO taskDAO = new TaskDAO();
    private final User currentUser = User.getCurrentUser();

    @FXML
    public void initialize() {
        backButton.setOnMouseClicked(this::handleBack);
        loadAllUnfinishedTasks();
        setupTaskListView();
    }

    private void loadAllUnfinishedTasks() {
        if (currentUser == null) return;
        try {
            allTasksListView.setItems(FXCollections.observableArrayList(taskDAO.getAllUnfinishedTasks(currentUser.getId())));
        } catch (SQLException e) {
            e.printStackTrace();
            allTasksListView.setPlaceholder(new Label("Error loading tasks."));
        }
    }

    private void setupTaskListView() {
        allTasksListView.setPlaceholder(new Label("No unfinished tasks. Great job!"));
        allTasksListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                } else {
                    HBox hbox = new HBox(10);
                    hbox.setStyle("-fx-background-color: #FFEE93; -fx-padding: 15; -fx-background-radius: 10; -fx-cursor: hand;");

                    Label taskName = new Label(item.getTaskName());
                    taskName.setFont(Font.font("System Bold", 16));

                    Label deadline = new Label("Due: " + item.getEndTime());
                    deadline.setFont(Font.font("System", 12));

                    HBox taskInfoContainer = new HBox(taskName, new HBox(), deadline);
                    HBox.setHgrow(taskInfoContainer.getChildren().get(1), javafx.scene.layout.Priority.ALWAYS);

                    hbox.getChildren().add(taskInfoContainer);
                    HBox.setHgrow(taskInfoContainer, javafx.scene.layout.Priority.ALWAYS);

                    setGraphic(hbox);
                    setStyle("-fx-background-color: transparent; -fx-padding: 0 0 5 0;");

                    // --- FUNGSI KLIK BARU ---
                    hbox.setOnMouseClicked(event -> {
                        if (item.getStatus().equals("in_progress")) {
                            showCompleteTaskDialog(item);
                        }
                    });
                }
            }
        });
    }

    // --- METODE BARU UNTUK MENAMPILKAN DIALOG KUSTOM ---
    private void showCompleteTaskDialog(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CompleteTaskDialog.fxml"));
            Parent root = loader.load();

            CompleteTaskDialogController controller = loader.getController();
            controller.setTask(task);

            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            dialogStage.setScene(scene);

            dialogStage.showAndWait();

            if (controller.isConfirmed()) {
                taskDAO.updateTaskStatus(task.getId(), "completed");
                // Hapus item dari list setelah selesai
                allTasksListView.getItems().remove(task);
                allTasksListView.refresh();
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setContentText("Could not process the task completion.");
            errorAlert.showAndWait();
        }
    }

    private void handleBack(MouseEvent event) {
        new SceneManager((Stage) ((Node) event.getSource()).getScene().getWindow()).switchToDashboard();
    }
}