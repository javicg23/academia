/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author V
 */
public class FXMLAnyadirAlumnoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private GridPane gridAnyadirAlumno;
    @FXML
    private ImageView gridAnyadirAlumnoImgFotografia;
    @FXML
    private Label gridAnyadirAlumnoLblNombreCabecera;
    @FXML
    private TextField gridAnyadirAlumnoTextNombre;
    @FXML
    private Label gridAnyadirAlumnoLblDni;
    @FXML
    private TextField gridAnyadirAlumnoTextDni;
    @FXML
    private Label gridAnyadirAlumnoLblDireccion;
    @FXML
    private TextField gridAnyadirAlumnoTextDireccion;
    @FXML
    private Label gridAnyadirAlumnoLblEdad;
    @FXML
    private Spinner<?> gridAnyadirAlumnoSpnEdad;
    @FXML
    private Button gridAnyadirAlumnoBtnExaminar;

    private Stage primaryStage, emergenteStage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void initStage(Stage stageEmergente, Stage stage) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Añadir curso");
        primaryStage = stage;
    }
    
    @FXML
    private void pulsarRatonBtnCancelar(MouseEvent event) {
    }

    @FXML
    private void pulsarTecladoBtnCancelar(KeyEvent event) {
    }

    @FXML
    private void pulsarRatonBtnGuardar(MouseEvent event) {
    }

    @FXML
    private void pulsarTecladoBtnGuardar(KeyEvent event) {
    }

    @FXML
    private void pulsarRatonBtnExaminar(MouseEvent event) {
    }

    @FXML
    private void pulsarTecladoBtnExaminar(KeyEvent event) {
    }
    
}
