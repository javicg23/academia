/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author V
 */
public class FXMLAnyadirCursoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private GridPane gridAnyadirCurso;
    @FXML
    private Label gridAnyadirCursoLblTitulo;
    @FXML
    private TextField gridAnyadirCursoTextTitulo;
    @FXML
    private Label gridAnyadirCursoLblProfesor;
    @FXML
    private TextField gridAnyadirCursoTextProfesor;
    @FXML
    private Label gridAnyadirCursoLblNMax;
    @FXML
    private Spinner<?> gridAnyadirCursoSpnNMax;
    @FXML
    private Label gridAnyadirCursoLblHora;
    @FXML
    private TextField gridAnyadirCursoTextHora;
    @FXML
    private Label gridAnyadirCursoLblInicio;
    @FXML
    private DatePicker gridAnyadirCursoDateInicio;
    @FXML
    private Label gridAnyadirCursoLblFin;
    @FXML
    private DatePicker gridAnyadirCursoDateFin;
    @FXML
    private Label gridAnyadirCursoLblAula;
    @FXML
    private ComboBox<?> gridAnyadirCursoCmbAula;
    @FXML
    private Label gridAnyadirCursoLblDias;
    @FXML
    private CheckBox gridAnyadirCursoCheckLunes;
    @FXML
    private CheckBox gridAnyadirCursoCheckMartes;
    @FXML
    private CheckBox gridAnyadirCursoCheckMiercoles;
    @FXML
    private CheckBox gridAnyadirCursoCheckJueves;
    @FXML
    private CheckBox gridAnyadirCursoCheckViernes;

    private AccesoaBD acceso = new AccesoaBD();
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnGuardar.setDisable(true);
        
        gridAnyadirCursoTextProfesor.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"-0123456789/n".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        
        //gridAnyadirCursoSpnNMax.getValueFactory().setValue(0);
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
    
    private void comprobarCampos() {
        
    }
}
