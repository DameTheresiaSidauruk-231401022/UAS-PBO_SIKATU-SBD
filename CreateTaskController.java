package com.sikatu.bantu_teman.controller;

import com.sikatu.bantu_teman.db.TaskDAO;
import com.sikatu.bantu_teman.model.User;
import com.sikatu.bantu_teman.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Controller untuk halaman Create Task.
 * Mengelola input pengguna untuk membuat tugas baru.
 */
public class CreateTaskController {

    // Elemen UI yang di-inject dari FXML
    @FXML private FontIcon backButton;
    @FXML private HBox dateSelectorHBox;
    @FXML private TextField nameField;
    @FXML private TextArea descriptionArea;
    @FXML private ComboBox<String> startTimeCombo;
    @FXML private ComboBox<String> endTimeCombo;
    @FXML private ToggleGroup priorityToggleGroup;
    @FXML private Label errorLabel;

    private final TaskDAO taskDAO = new TaskDAO();
    private LocalDate selectedDate; // Variabel untuk menyimpan tanggal yang dipilih

    /**
     * Metode initialize dipanggil secara otomatis saat FXML dimuat.
     * Digunakan untuk setup awal UI.
     */
    @FXML
    public void initialize() {
        selectedDate = LocalDate.now(); // Default tanggal yang dipilih adalah hari ini
        setupDateSelector();
        setupTimeCombos();
        backButton.setOnMouseClicked(this::handleBack);

        // Listener untuk membersihkan label error saat pengguna mulai mengetik
        nameField.textProperty().addListener((obs, oldText, newText) -> errorLabel.setText(""));
    }

    /**
     * Membuat dan menampilkan pemilih tanggal untuk 7 hari ke depan secara dinamis.
     */
    private void setupDateSelector() {
        dateSelectorHBox.getChildren().clear();
        for (int i = 0; i < 7; i++) {
            LocalDate date = LocalDate.now().plusDays(i);

            // Membuat VBox untuk setiap tanggal
            VBox dayBox = new VBox(5);
            dayBox.setAlignment(Pos.CENTER);
            dayBox.setPrefWidth(45);
            dayBox.getStyleClass().add("date-box"); // Menambahkan style class untuk CSS

            // Label untuk nama hari (Mon, Tue, etc.)
            Label dayName = new Label(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            // Label untuk nomor tanggal (1, 2, etc.)
            Label dayNumber = new Label(String.valueOf(date.getDayOfMonth()));

            dayBox.getChildren().addAll(dayName, dayNumber);

            final int dayIndex = i;
            dayBox.setOnMouseClicked(event -> {
                selectedDate = LocalDate.now().plusDays(dayIndex);
                updateDateSelectorStyles(); // Update style saat tanggal baru dipilih
            });
            dateSelectorHBox.getChildren().add(dayBox);
        }
        updateDateSelectorStyles(); // Terapkan style awal
    }

    /**
     * Memperbarui style visual dari pemilih tanggal untuk menyorot tanggal yang aktif.
     */
    private void updateDateSelectorStyles() {
        for(int i = 0; i < dateSelectorHBox.getChildren().size(); i++){
            VBox dayBox = (VBox) dateSelectorHBox.getChildren().get(i);
            LocalDate date = LocalDate.now().plusDays(i);
            if(date.equals(selectedDate)){
                dayBox.setStyle("-fx-background-color: #FFEE93; -fx-background-radius: 10;");
            } else {
                dayBox.setStyle("-fx-background-color: #E0E0E0; -fx-background-radius: 10;");
            }
        }
    }

    /**
     * Mengisi ComboBox (dropdown) untuk pilihan jam mulai dan selesai.
     */
    private void setupTimeCombos() {
        // Mengisi pilihan jam dari 00:00 hingga 23:30 dengan interval 30 menit
        for (int hour = 0; hour < 24; hour++) {
            for (int min = 0; min < 60; min += 30) {
                String time = String.format("%02d:%02d", hour, min);
                startTimeCombo.getItems().add(time);
                endTimeCombo.getItems().add(time);
            }
        }
        // Atur nilai default
        startTimeCombo.getSelectionModel().select("09:00");
        endTimeCombo.getSelectionModel().select("17:00");
    }

    /**
     * Mendapatkan nilai prioritas dari ToggleButton yang dipilih.
     * @return 1 untuk High, 2 untuk Medium, 3 untuk Low.
     */
    private int getSelectedPriority() {
        ToggleButton selected = (ToggleButton) priorityToggleGroup.getSelectedToggle();
        if (selected == null) return 2; // Default ke Medium jika tidak ada yang dipilih
        switch (selected.getText()) {
            case "High": return 1;
            case "Low": return 3;
            default: return 2; // Medium
        }
    }

    /**
     * Aksi yang dijalankan saat tombol "Create Task" ditekan.
     */
    @FXML
    private void handleCreateTask(ActionEvent event) {
        // Validasi input
        String name = nameField.getText();
        if (name.isEmpty()) {
            errorLabel.setText("Task name cannot be empty.");
            return;
        }

        User currentUser = User.getCurrentUser();
        if (currentUser == null) {
            errorLabel.setText("Error: No user logged in. Please restart the application.");
            return;
        }

        // Mengumpulkan semua data dari form
        String description = descriptionArea.getText();
        int priority = getSelectedPriority();
        // Menggabungkan tanggal dan jam menjadi format DATETIME yang diterima SQLite
        String endTime = selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + endTimeCombo.getValue() + ":00";

        try {
            // Memanggil DAO untuk menyimpan data ke database
            taskDAO.createTask(currentUser.getId(), name, description, endTime, priority);

            // Jika berhasil, kembali ke halaman Dashboard
            handleBack(null);

        } catch (SQLException e) {
            errorLabel.setText("Database error. Could not create task.");
            e.printStackTrace();
        }
    }

    /**
     * Aksi untuk kembali ke halaman sebelumnya (Dashboard).
     */
    private void handleBack(MouseEvent event) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        new SceneManager(stage).switchToDashboard();
    }
}