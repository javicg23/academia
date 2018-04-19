/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author V
 */
public class FXMLMatriculacionesController implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menuAlumnos;
    @FXML
    private MenuItem menuAlumnosAnyadir;
    @FXML
    private MenuItem menuAlumnosEliminar;
    @FXML
    private MenuItem menuAlumnosListado;
    @FXML
    private Menu menuCursos;
    @FXML
    private MenuItem menuCursosAnyadir;
    @FXML
    private MenuItem menuCursosEliminar;
    @FXML
    private MenuItem menuCursosListado;
    @FXML
    private Menu menuMatriculaciones;
    @FXML
    private MenuItem menuMatriculacionesMatricular;
    @FXML
    private MenuItem menuMatriculacionesDesmatricular;
    @FXML
    private Tab tabMatricular;
    @FXML
    private Label lblMatricularTitulo;
    @FXML
    private Button btnMatricularAtras;
    @FXML
    private ImageView imgMatricularLupaCursos;
    @FXML
    private TextField textMatricularFiltrarCursos;
    @FXML
    private ImageView imgMatricularLupaAlumnos;
    @FXML
    private TextField textMatricularFiltrarAlumnos;
    @FXML
    private TableView<?> tablaMatricularCursos;
    @FXML
    private TableColumn<?, ?> tablaMatricularCursosColumnaCurso;
    @FXML
    private TableColumn<?, ?> tablaMatricularCursosColumnaProfesor;
    @FXML
    private TableView<?> tablaMatricularAlumnos;
    @FXML
    private TableColumn<?, ?> tablaMatricularAlumnosColumnaFotografia;
    @FXML
    private TableColumn<?, ?> tablaMatricularAlumnosColumnaAlumno;
    @FXML
    private TableColumn<?, ?> tablaMatricularAlumnosColumnaDni;
    @FXML
    private Tab tabDesmatricular;
    @FXML
    private Label lblDesmatricularTitulo;
    @FXML
    private Button btnDesmatricularAtras;
    @FXML
    private Button btnDesmatricular;
    @FXML
    private ImageView imgDesmatricularLupa;
    @FXML
    private TextField textDesmatricularFiltrarCursos;
    @FXML
    private TextField textDesmatricularFiltrarAlumnos;
    @FXML
    private TableView<?> tablaDesmatricularCursos;
    @FXML
    private TableColumn<?, ?> tablaDesmatricularCursosColumnaCurso;
    @FXML
    private TableColumn<?, ?> tablaDesmatricularCursosColumnaProfesor;
    @FXML
    private TableView<?> tablaDesmatricularAlumnos;
    @FXML
    private TableColumn<?, ?> tablaDesmatricularAlumnosColumnaFotografía;
    @FXML
    private TableColumn<?, ?> tablaDesmatricularAlumnosColumnaAlumno;
    @FXML
    private TableColumn<?, ?> tablaDesmatricularAlumnosColumnaDni;

    private Stage primaryStage;
    private Scene escenaAnterior; 
    private String tituloAnterior;
    @FXML
    private Button btnMatricular;
    @FXML
    private TableColumn<?, ?> tablaMatricularColumnaHora;
    @FXML
    private TableColumn<?, ?> tablaDesmatricularColumnaHora;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void initStage(Stage stage) { 
        primaryStage = stage;
        escenaAnterior = stage.getScene();
        tituloAnterior = stage.getTitle();
        primaryStage.setTitle("Matriculaciones");
    }
    
    @FXML
    private void pulsarMenuAlumnosAnyadir(ActionEvent event) {
    }

    @FXML
    private void pulsarMenuAlumnosEliminar(ActionEvent event) {
    }

    @FXML
    private void pulsarMenuAlumnosListado(ActionEvent event) {
    }

    @FXML
    private void pulsarMenuCursosAnyadir(ActionEvent event) {
    }

    @FXML
    private void pulsarMenuCursosEliminar(ActionEvent event) {
    }

    @FXML
    private void pulsarMenuCursosListado(ActionEvent event) {
    }

    @FXML
    private void pulsarMenuMatriculacionesMatricular(ActionEvent event) {
    }

    @FXML
    private void pulsarMenuMatriculacionesDesmatricular(ActionEvent event) {
    }

    @FXML
    private void pulsarRatonBtnAtras(MouseEvent event) {
        primaryStage.setTitle(tituloAnterior);
        primaryStage.setScene(escenaAnterior);
    }

    @FXML
    private void pulsarTecladoBtnAtras(KeyEvent event) {
    }


    @FXML
    private void pulsarRatonBtnDesmatricular(MouseEvent event) {
    }

    @FXML
    private void pulsarTecladoBtnDesmatricular(KeyEvent event) {
    }


    @FXML
    private void pulsarTecladoBtnMatricular(KeyEvent event) {
    }
    
}
