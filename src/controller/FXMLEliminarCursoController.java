/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Curso;

/**
 * FXML Controller class
 *
 * @author V
 */
public class FXMLEliminarCursoController implements Initializable {

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
    private TableView<?> tablaCursos;
    @FXML
    private TableColumn<?, ?> tablaCursosColumnaCurso;
    @FXML
    private TableColumn<?, ?> tablaCursosColumnaProfesor;
    @FXML
    private TableColumn<?, ?> tablaCursosColumnaHora;

    private AccesoaBD acceso = new AccesoaBD();
    private ArrayList<Curso> listaCursos = (ArrayList<Curso>) acceso.getCursos();
    private boolean[] arrayBooleans = new boolean[8];
    private Stage primaryStage, emergenteStage;
    private Boolean vengoDeStageConMenu = false;

    public void initStage(Stage stageEmergente, Stage stage) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Eliminar curso");
        primaryStage = stage;
    }

    public void initStage(Stage stageEmergente, Stage stage, Boolean conMenu) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Eliminar curso");
        primaryStage = stage;
        vengoDeStageConMenu = conMenu;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void pulsarRatonBtnCancelar(MouseEvent event) {
    }

    @FXML
    private void pulsarTecladoBtnCancelar(KeyEvent event) {
    }

    @FXML
    private void pulsarRatonBtnEliminar(MouseEvent event) {
    }

    @FXML
    private void pulsarTecladoBtnEliminar(KeyEvent event) {
    }

}