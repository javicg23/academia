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

    private Stage primaryStage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void initStage(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Eliminar Curso");
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
