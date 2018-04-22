/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academia;

import controller.FXMLAcademiaController;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author V
 */
public class Academia extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLAcademia.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 850, 600);
        stage.setScene(scene);

        Image icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
        stage.getIcons().add(icon);

        stage.setTitle("Academia");
        FXMLAcademiaController controllerAcademia = loader.<FXMLAcademiaController>getController();
        controllerAcademia.initStage(stage);
        
        stage.setOnCloseRequest((WindowEvent event) -> {
            event.consume();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cerrar aplicación");
            alert.setHeaderText(null);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/icon.png"));
            alert.setContentText("¿Está seguro de que desea cerrar la aplicación?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Platform.exit();
            }
        });
        stage.setMinWidth(750);
        stage.setMinHeight(500);
        stage.setMaxWidth(850);
        stage.setMaxHeight(600);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
