package com.emsi.scientist_manage.view;

import com.emsi.scientist_manage.entities.Scientist;
import com.emsi.scientist_manage.service.ScientistService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ScientistController extends Application {

    @FXML
    public TableView<Scientist> table;

    @FXML
    private TableColumn<Scientist, Integer> id;
    @FXML
    private TableColumn<Scientist, String> name;
    @FXML
    private TableColumn<Scientist, Integer> yearsOfExperience;
    @FXML
    private TableColumn<Scientist, String> fieldOfStudy;
    @FXML
    private TableColumn<Scientist, String> researchInterests;
    @FXML
    private TableColumn<Scientist, String> publications;
    @FXML
    private TableColumn<Scientist, String> awardsAndHonors;
    private ScientistUpdateForm updateForm;

    public static void main(String[] args) {
        launch(args);
    }

    public void RefreshTable() {
       table.refresh();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ScientistTable-View.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Scientist TableView");
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(visualBounds.getMinX());
        stage.setY(visualBounds.getMinY());
        stage.setWidth(visualBounds.getWidth());
        stage.setHeight(visualBounds.getHeight());
        stage.show();
    }

    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        yearsOfExperience.setCellValueFactory(new PropertyValueFactory<>("yearsOfExperience"));
        fieldOfStudy.setCellValueFactory(new PropertyValueFactory<>("fieldOfStudy"));
        researchInterests.setCellValueFactory(new PropertyValueFactory<>("researchInterests"));
        publications.setCellValueFactory(new PropertyValueFactory<>("publications"));
        awardsAndHonors.setCellValueFactory(new PropertyValueFactory<>("awardsAndHonors"));

        fillTableView();
    }

    public void fillTableView() {
        table.getColumns().clear();
        table.getColumns().addAll(id, name, fieldOfStudy, yearsOfExperience, researchInterests, publications, awardsAndHonors);
        table.setItems(getScientistList());
    }

    public static ObservableList<Scientist> getScientistList() {
        // Retrieve data from your data source or service
        List<Scientist> scientists = ScientistService.findAll();

        // Convert the list to an ObservableList and return it
        return FXCollections.observableArrayList(scientists);
    }

    @FXML
    public void DeleteScientist(ActionEvent event) {
        TableView.TableViewSelectionModel<Scientist> tsm = table.getSelectionModel();
        if (tsm.isEmpty()) {
            System.out.println("Please select a row to delete.");
        }

        ObservableList<Integer> list = tsm.getSelectedIndices();
        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);
        Scientist deletedScientist = null;

        Arrays.sort(selectedIndices);

        for (int i = selectedIndices.length - 1; i >= 0; i--) {
            tsm.clearSelection(selectedIndices[i].intValue());
            deletedScientist = table.getItems().remove(selectedIndices[i].intValue());
        }
        ScientistService.remove(deletedScientist);
    }

    @FXML
    public void RestoreRows(ActionEvent event) {
        table.getItems().clear();
        table.getItems().addAll(getScientistList());
    }

    @FXML
    public void AddScientist(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ScientistFormAdd.fxml"));
        Parent root = loader.load();

        // Close the current form stage
        Stage formStage = (Stage) table.getScene().getWindow();
        formStage.close();

        // Show the scientist table view
        Scene scene = new Scene(root);
        Stage tableStage = (Stage) table.getScene().getWindow();
        tableStage.setTitle("Add a Scientist");
        tableStage.setScene(scene);
        tableStage.show();
    }
    @FXML
    private void UpdateScientist() {
        Scientist selectedScientist = table.getSelectionModel().getSelectedItem();
        if (selectedScientist != null) {
            updateForm.setScientist(selectedScientist);
        }
    }
    @FXML
    public void UpdateScientist(ActionEvent event) throws IOException {
        Scientist selectedScientist = table.getSelectionModel().getSelectedItem();
        if (selectedScientist != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScientistUpdateForm.fxml"));
            Parent root = loader.load();

            ScientistUpdateForm updateForm = loader.getController();
            updateForm.setScientist(selectedScientist);

            // Close the current form stage
            Stage formStage = (Stage) table.getScene().getWindow();
            formStage.close();

            // Open the update form stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update a Scientist");
            stage.show();
        } else {
            System.out.println("Please select a scientist to update.");
        }
    }

    @FXML
    public void Import(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Data File");
        File file = fileChooser.showOpenDialog(table.getScene().getWindow());
        if (file != null) {
            String filePath = ((File) file).getPath();
            String fileExtension = getFileExtension(filePath);
            try {
                switch (fileExtension) {
                    case "txt":
                        ScientistService.readFromTxtAndInsertToDatabase(filePath);
                        break;
                    case "xlsx":
                        ScientistService.readFromExcelFileToDB(filePath);
                        break;
                    case "json":
                        ScientistService.readFromJsonFileToDB(filePath);
                        break;
                    default:
                        System.out.println("Unsupported file format.");
                }
                table.getItems().clear();
                table.getItems().addAll(getScientistList());
                System.out.println("Data imported from file to the database successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < filePath.length() - 1) {
            return filePath.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }


    @FXML
    public void Export(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Data");
        FileChooser.ExtensionFilter filterJson = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        FileChooser.ExtensionFilter filterTxt = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter filterExcel = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().addAll(filterJson, filterTxt, filterExcel);
        File file = fileChooser.showSaveDialog(table.getScene().getWindow());

        if (file != null) {
            String filePath = file.getPath();
            String fileExtension = getFileExtension(filePath);

            try {
                switch (fileExtension) {
                    case "json":
                        ScientistService.exportToJson(filePath);
                        showAlert("Data exported to JSON successfully.", Alert.AlertType.INFORMATION);
                        break;
                    case "txt":
                        ScientistService.exportToTxt(filePath);
                        showAlert("Data exported to TXT successfully.", Alert.AlertType.INFORMATION);
                        break;
                    case "xlsx":
                        ScientistService.exportToExcel(filePath);
                        showAlert("Data exported to Excel successfully.", Alert.AlertType.INFORMATION);
                        break;
                    default:
                        showAlert("Unsupported file format.", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                showAlert("An error occurred while exporting data: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Export Data");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
