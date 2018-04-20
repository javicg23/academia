/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.Curso;

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

    private Stage primaryStage, emergenteStage;
    private Alumno alumno;
    private AccesoaBD baseDatos = new AccesoaBD();
    private ObservableList<Curso> listaCursos = null;
    
    
    public void initStage(Stage stageEmergente, Stage stage, Alumno alumno) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Datos Alumno");
        primaryStage = stage;
        this.alumno = alumno;
        initializeAll();
    }
    
    private void initializeAll() {
    
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
