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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class FXMLListaAlumnosController implements Initializable {

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
    private Label lblTitulo;
    @FXML
    private Button btnAtras;
    @FXML
    private Label lblModificacionLista;
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
    @FXML
    private GridPane gridBotonera;
    @FXML
    private Button btnAnyadir;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnVisualizar;

    private Stage primaryStage;
    private Scene escenaAnterior; 
    private String tituloAnterior;
    
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
        primaryStage.setTitle("Lista alumnos");
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
    private void pulsarRatonBtnAnyadir(MouseEvent event) {
    }

    @FXML
    private void pulsarTecladoBtnAnyadir(KeyEvent event) {
    }

    @FXML
    private void pulsarRatonBtnEliminar(MouseEvent event) {
    }

    @FXML
    private void pulsarTecladoBtnEliminar(KeyEvent event) {
    }

    @FXML
    private void pulsarRatonBtnVisualizacion(MouseEvent event) {
    }

    @FXML
    private void pulsarTecladoBtnVisualizacion(KeyEvent event) {
    }
    
}
