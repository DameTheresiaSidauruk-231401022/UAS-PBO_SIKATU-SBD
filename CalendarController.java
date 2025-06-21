package com.sikatu.bantu_teman.controller;

import com.sikatu.bantu_teman.db.TaskDAO;
import com.sikatu.bantu_teman.model.Task;
import com.sikatu.bantu_teman.model.User;
import com.sikatu.bantu_teman.util.SceneManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CalendarController {

    @FXML private FontIcon backButton;
    @FXML private Label monthYearLabel;
    @FXML private FontIcon prevMonthButton;
    @FXML private FontIcon nextMonthButton;
    @FXML private GridPane calendarGrid;
    @FXML private ListView<Task> tasksListView;

    private YearMonth currentYearMonth;
    private LocalDate selectedDate;
    private final TaskDAO taskDAO = new TaskDAO();
    private final User currentUser = User.getCurrentUser();

    @FXML
    public void initialize() {
        currentYearMonth = YearMonth.now();
        selectedDate = LocalDate.now();
        drawCalendar();
        loadTasksForDate(selectedDate);

        backButton.setOnMouseClicked(this::handleBack);
        prevMonthButton.setOnMouseClicked(e -> changeMonth(-1));
        nextMonthButton.setOnMouseClicked(e -> changeMonth(1));

        setupTaskListView();
    }

    private void drawCalendar() {
        calendarGrid.getChildren().clear();
        monthYearLabel.setText(currentYearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")));

        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int dayOfWeekOfFirst = firstOfMonth.getDayOfWeek().getValue(); // 1=Mon, 7=Sun

        int day = 1;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (row == 0 && col < dayOfWeekOfFirst - 1) {
                    continue; // Kosongkan sel sebelum tanggal 1
                }
                if (day > currentYearMonth.lengthOfMonth()) {
                    break;
                }

                LocalDate date = currentYearMonth.atDay(day);
                VBox dayCell = new VBox();
                dayCell.setAlignment(Pos.CENTER);
                dayCell.setPrefSize(45, 45);
                dayCell.getChildren().add(new Label(String.valueOf(day)));
                dayCell.setStyle("-fx-background-radius: 25;");

                // Style untuk hari ini
                if (date.equals(LocalDate.now())) {
                    dayCell.setStyle("-fx-border-color: #A0C878; -fx-border-width: 2; -fx-border-radius: 25;");
                }
                // Style untuk hari yang dipilih
                if (date.equals(selectedDate)) {
                    dayCell.setStyle("-fx-background-color: #A0C878; -fx-background-radius: 25;");
                }

                dayCell.setOnMouseClicked(e -> {
                    selectedDate = date;
                    drawCalendar(); // Gambar ulang untuk update style
                    loadTasksForDate(date);
                });

                calendarGrid.add(dayCell, col, row);
                day++;
            }
        }
    }

    private void loadTasksForDate(LocalDate date) {
        if (currentUser == null) return;
        try {
            List<Task> tasks = taskDAO.getTasksForDate(currentUser.getId(), date);
            tasksListView.setItems(FXCollections.observableArrayList(tasks));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void changeMonth(int amount) {
        currentYearMonth = currentYearMonth.plusMonths(amount);
        drawCalendar();
    }

    private void setupTaskListView() {
        tasksListView.setPlaceholder(new Label("No tasks for this date."));
        tasksListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getTaskName());
                    setStyle("-fx-background-color: #A0C878; -fx-padding: 10; -fx-background-radius: 10; -fx-font-weight: bold;");
                }
            }
        });
    }

    private void handleBack(MouseEvent event) {
        new SceneManager((Stage) ((Node) event.getSource()).getScene().getWindow()).switchToDashboard();
    }
}
