package com.emsi.scientist_manage.view;
import com.emsi.scientist_manage.entities.Scientist;
import com.emsi.scientist_manage.service.ScientistService;
import com.emsi.scientist_manage.view.ScientistController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ScientistUpdateForm extends Application {
    @FXML
    private TextField id;

    @FXML
    private TextField name;
    @FXML
    private TextField field;
    @FXML
    private TextField research;
    @FXML
    private TextField years;
    @FXML
    private TextField pub;
    @FXML
    private TextField awards;
    @FXML
    private Button btnUpdate;

    private Scientist selectedScientist;
    private ScientistController scientistController;

    public ScientistUpdateForm() {
    }

    public void setScientist(Scientist scientist) {
        selectedScientist = scientist;
        populateFields();
    }

    private void populateFields() {
        id.setText(String.valueOf(selectedScientist.getId()));
        name.setText(selectedScientist.getName());
        field.setText(selectedScientist.getFieldOfStudy());
        research.setText(selectedScientist.getResearchInterests());
        years.setText(String.valueOf(selectedScientist.getYearsOfExperience()));
        pub.setText(String.valueOf(selectedScientist.getPublications()));
        awards.setText(String.valueOf(selectedScientist.getAwardsAndHonors()));
    }

    @FXML
    private void updateScientist() throws IOException {
        int idField = Integer.parseInt(id.getText());
        String nameField = name.getText();
        String fieldField = field.getText();
        String researchField = research.getText();
        int experienceField = Integer.parseInt(years.getText());
        int publicationsField = Integer.parseInt(pub.getText());
        int awardsField = Integer.parseInt(awards.getText());

        // Update the selected scientist's data
        selectedScientist.setId(idField);
        selectedScientist.setName(nameField);
        selectedScientist.setFieldOfStudy(fieldField);
        selectedScientist.setResearchInterests(researchField);
        selectedScientist.setYearsOfExperience(experienceField);
        selectedScientist.setPublications(publicationsField);
        selectedScientist.setAwardsAndHonors(awardsField);

        // Perform the update logic
        ScientistService.update(selectedScientist);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ScientistTable-View.fxml"));
        Parent root = loader.load();

        // Close the current form stage
        Stage formStage = (Stage) btnUpdate.getScene().getWindow();
        formStage.close();

        // Show the scientist table view
        Scene scene = new Scene(root);
        Stage tableStage =(Stage) btnUpdate.getScene().getWindow();
        tableStage.setTitle("Scientist TableView");
        tableStage.setScene(scene);
        tableStage.show();

    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ScientistUpdateForm.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Update a Scientist");
        stage.show();
    }


}
