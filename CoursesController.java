// File: bantu_teman/src/main/java/com/sikatu/bantu_teman/controller/CoursesController.java

package com.sikatu.bantu_teman.controller;

import com.sikatu.bantu_teman.util.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.stream.Collectors;

public class CoursesController {

    private static class CourseItem {
        private final String name;
        private final String category;
        private final String color;

        public CourseItem(String name, String category, String color) {
            this.name = name;
            this.category = category;
            this.color = color;
        }

        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getColor() { return color; }
    }

    @FXML private FontIcon backButton;
    @FXML private TextField searchField;
    @FXML private ListView<CourseItem> coursesListView;

    @FXML private HBox homeButton;
    @FXML private HBox calendarButton;
    @FXML private HBox createTaskButton;
    @FXML private HBox settingsButton;

    private final ObservableList<CourseItem> allCourses = FXCollections.observableArrayList(
            new CourseItem("Java for Beginners", "Programming", "#A0C878"),
            new CourseItem("Advanced Java Programming", "Programming", "#A0C878"),
            new CourseItem("Data Structures in Python", "Programming", "#A0C878"),
            new CourseItem("Web Development with Spring", "Web Dev", "#FFEE93"),
            new CourseItem("Introduction to Machine Learning", "AI", "#FFDAB9"),
            new CourseItem("Project Management Fundamentals", "Business", "#ADD8E6"),
            new CourseItem("Agile and Scrum Basics", "Business", "#ADD8E6"),
            new CourseItem("UI/UX Design Principles", "Design", "#D8BFD8"),
            new CourseItem("SQL Database Essentials", "Database", "#B0C4DE"),
            new CourseItem("Android App Development", "Mobile Dev", "#A0C878")
    );

    @FXML
    public void initialize() {
        backButton.setOnMouseClicked(this::handleBack);

        setupListViewCellFactory();

        coursesListView.setItems(allCourses);
        coursesListView.setPlaceholder(new Label("No courses found."));

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchCourses(newValue);
        });

        setupBottomNavigation();
    }

    private void setupListViewCellFactory() {
        coursesListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(CourseItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    VBox card = new VBox(5);
                    card.setStyle("-fx-background-color: " + item.getColor() + "; -fx-padding: 15; -fx-background-radius: 15;");

                    Label courseName = new Label(item.getName());
                    courseName.setFont(Font.font("System Bold", 16));
                    courseName.setStyle("-fx-text-fill: #000000;");

                    Label category = new Label(item.getCategory());
                    category.setFont(Font.font("System", 12));
                    category.setStyle("-fx-text-fill: #404040;");

                    card.getChildren().addAll(courseName, category);
                    setGraphic(card);
                    setStyle("-fx-background-color: transparent; -fx-padding: 0 0 10 0;");
                }
            }
        });
    }

    private void searchCourses(String query) {
        if (query == null || query.isEmpty()) {
            coursesListView.setItems(allCourses);
        } else {
            ObservableList<CourseItem> filteredList = allCourses.stream()
                    .filter(course -> course.getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            coursesListView.setItems(filteredList);
        }
    }

    private void setupBottomNavigation() {
        homeButton.setOnMouseClicked(e -> navigateTo(homeButton, new SceneManager((Stage) homeButton.getScene().getWindow())::switchToDashboard));
        calendarButton.setOnMouseClicked(e -> navigateTo(calendarButton, new SceneManager((Stage) calendarButton.getScene().getWindow())::switchToCalendar));
        createTaskButton.setOnMouseClicked(e -> navigateTo(createTaskButton, new SceneManager((Stage) createTaskButton.getScene().getWindow())::switchToCreateTask));
        settingsButton.setOnMouseClicked(e -> navigateTo(settingsButton, new SceneManager((Stage) settingsButton.getScene().getWindow())::switchToSettings));
    }

    private void handleBack(MouseEvent event) {
        navigateTo(backButton, new SceneManager((Stage) backButton.getScene().getWindow())::switchToDashboard);
    }

    private void navigateTo(Node node, Runnable navigationMethod) {
        navigationMethod.run();
    }
}