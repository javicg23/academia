/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Alumno;

/**
 * FXML Controller class
 *
 * @author V
 */
public class FXMLEliminarAlumnoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgLupa;
    @FXML
    private TextField textFiltrar;
    @FXML
    private TableView<?> tablaAlumnos;
    @FXML
    private TableColumn<?, ?> tablaAlumnosColumnaFotografia;
    @FXML
    private TableColumn<?, ?> tablaAlumnosColumnaNombre;
    @FXML
    private TableColumn<?, ?> tablaAlumnosColumnaDni;

    private AccesoaBD acceso = new AccesoaBD();
    private ArrayList<Alumno> listaAlumnos = (ArrayList<Alumno>) acceso.getAlumnos();
    private boolean[] arrayBooleans = new boolean[8];
    private Stage primaryStage, emergenteStage;
    private Boolean vengoDeStageConMenu = false, vengoDesdeListaAlumnos = false;
    private Stage stage;

    public void initStage(Stage stageEmergente, Stage stage) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Eliminar alumno");
        primaryStage = stage;
    }

    public void initStage(Stage stageEmergente, Stage stage, Boolean conMenu) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Eliminar alumno");
        primaryStage = stage;
        vengoDeStageConMenu = conMenu;
    }
    
    public void initStage(Stage stageEmergente, Stage stage, Boolean conMenu, Boolean vengoDesdeLAlumnos) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Eliminar alumno");
        primaryStage = stage;
        vengoDeStageConMenu = conMenu;
        vengoDesdeListaAlumnos = vengoDesdeLAlumnos;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    @FXML
    private void pulsarRatonBtnCancelar(MouseEvent event) throws IOException {
        if (vengoDesdeListaAlumnos) {
            voyListaAlumnosFalseBoolean();
        }
        if (vengoDeStageConMenu) {
            stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        } else {
            voyListaAlumnosFalseBoolean();
        }
    }

    @FXML
    private void pulsarTecladoBtnCancelar(KeyEvent event) throws IOException {
         if (event.getCode().equals(KeyCode.ENTER)) {
            if (vengoDesdeListaAlumnos) {
                voyListaAlumnosFalseBoolean();
            }
            if (vengoDeStageConMenu) {
                stage = (Stage) btnCancelar.getScene().getWindow();
                stage.close();
            } else {
                voyListaAlumnosFalseBoolean();
            }
        }
    }

    @FXML
    private void pulsarRatonBtnEliminar(MouseEvent event) {
        
    }

    @FXML
    private void pulsarTecladoBtnEliminar(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            
        }
    }
    
    private void voyListaAlumnosFalseBoolean() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaAlumnos.fxml"));
        Parent root = (Parent) loader.load();

        FXMLListaAlumnosController controllerListaAlumnos = loader.<FXMLListaAlumnosController>getController();
        controllerListaAlumnos.initStage(primaryStage, false);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

}
