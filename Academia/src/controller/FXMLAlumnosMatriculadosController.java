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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author V
 */
public class FXMLAlumnosMatriculadosController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblCurso;
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
    private GridPane gridDatosCurso;
    @FXML
    private Label gridDatosCursoLblProfesorCabecera;
    @FXML
    private Label gridDatosCursoLblHoraCabecera;
    @FXML
    private Label gridDatosCursoLblDiasCabecera;
    @FXML
    private Label gridDatosCursoLblAulaCabecera;
    @FXML
    private Label gridDatosCursoLblProfesor;
    @FXML
    private Label gridDatosCursoLblHora;
    @FXML
    private Label gridDatosCursoLblDias;
    @FXML
    private Label gridDatosCursoLblAula;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
