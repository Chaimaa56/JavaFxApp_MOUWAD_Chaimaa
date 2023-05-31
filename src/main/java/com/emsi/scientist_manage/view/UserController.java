package com.emsi.scientist_manage.view;

import com.emsi.scientist_manage.service.UserService;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UserController extends Application {

    @FXML
    private TextField name;
    @FXML
    private PasswordField password;
    @FXML
    private Button btnSignIn;
    @FXML
    public void Connect(ActionEvent event) {
        String username = name.getText();
        String passwd = password.getText();

        // Verify the password
        boolean loginSuccess = UserService.verifyPassword(username, passwd);

        if (loginSuccess) {
            // Navigate to TableView page
            try {

                FXMLLoader loader = new FXMLLoader(ScientistController.class.getResource("ScientistTable-View.fxml"));
                Parent root = loader.load();
                System.out.println("Scientist TABLE VIEW");
                // Close the current login stage
                Stage loginStage = (Stage) btnSignIn.getScene().getWindow();
                loginStage.close();
                // Close the current login stage
                Scene scene = new Scene(root, 700, 600);
                Stage primaryStage=new Stage();
                primaryStage.setTitle("Scientist Table View");
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Display error
            showErrorMessage("Invalid username or password");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserController.class.getResource("UserView.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
