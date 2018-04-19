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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author V
 */
public class FXMLDatosAlumnoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private GridPane gridDatosAlumno;
    @FXML
    private ImageView gridDatosAlumnoImgFotografia;
    @FXML
    private Label gridDatosAlumnoLblNombreCabecera;
    @FXML
    private Label gridDatosAlumnoLblDniCabecera;
    @FXML
    private Label gridDatosAlumnoLblDireccionCabecera;
    @FXML
    private Label gridDatosAlumnoLblEdadCabecera;
    @FXML
    private Label gridDatosAlumnoLblCursosMatriculadosCabecera;
    @FXML
    private Label gridDatosAlumnoLblNombre;
    @FXML
    private Label gridDatosAlumnoLblDireccion;
    @FXML
    private Label gridDatosAlumnoLblDni;
    @FXML
    private Label gridDatosAlumnoLblEdad;
    @FXML
    private TableView<?> tablaCursosMatriculados;
    @FXML
    private TableColumn<?, ?> tablaCursosMatriculadosColumnaCurso;
    @FXML
    private TableColumn<?, ?> tablaCursosMatriculadosColumnaProfesor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
