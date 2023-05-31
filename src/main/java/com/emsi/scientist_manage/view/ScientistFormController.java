package com.emsi.scientist_manage.view;

import com.emsi.scientist_manage.entities.Scientist;
import com.emsi.scientist_manage.service.ScientistService;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ScientistFormController extends Application {

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
    private Button btnAdd;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ScientistFormAdd.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 320, 240);
        stage.setScene(scene);
        stage.setTitle("Add a Scientist");
        stage.show();
    }

    @FXML
    public void AddScientist(ActionEvent event) throws IOException {
        Scientist scientist = getScientist();
        ScientistController.getScientistList().add(scientist);
        ScientistService.save(scientist);
        clearFields();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ScientistTable-View.fxml"));
        Parent root = loader.load();

        // Close the current form stage
        Stage formStage = (Stage) btnAdd.getScene().getWindow();
        formStage.close();

        // Show the scientist table view
        Scene scene = new Scene(root);
        Stage tableStage =(Stage) btnAdd.getScene().getWindow();
        tableStage.setTitle("Scientist TableView");
        tableStage.setScene(scene);
        tableStage.show();
    }

    public Scientist getScientist() {

        return new Scientist(null, name.getText(),
                field.getText(),
                Integer.parseInt(years.getText()),
                research.getText(),
                Integer.parseInt(pub.getText()),
                Integer.parseInt(awards.getText()));
    }

    public void clearFields() {
        name.setText(null);
        field.setText(null);
        years.setText(null);
        research.setText(null);
        pub.setText(null);
        awards.setText(null);
    }


}
