/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import modelo.Matricula;

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
    private ImageView imgFotografia;
    @FXML
    private Label gridDatosAlumnoLblNombreCabecera;
    @FXML
    private Label gridDatosAlumnoLblDniCabecera;
    @FXML
    private Label gridDatosAlumnoLblDireccionCabecera;
    @FXML
    private Label gridDatosAlumnoLblEdadCabecera;
    @FXML
    private Label gridDatosAlumnoLblNombre;
    @FXML
    private Label gridDatosAlumnoLblDireccion;
    @FXML
    private Label gridDatosAlumnoLblDni;
    @FXML
    private Label gridDatosAlumnoLblEdad;
    @FXML
    private TableView<Curso> tablaCursosMatriculados;
    @FXML
    private TableColumn<Curso, String> tablaCursosMatriculadosColumnaCurso;
    @FXML
    private TableColumn<Curso, String> tablaCursosMatriculadosColumnaProfesor;
    @FXML
    private TableColumn<Curso, String> tablaCursosMatriculadosColumnaHora;
    @FXML
    private Label lblCursosMatriculadosCabecera;

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
        imgFotografia.setImage(alumno.getFoto());
        gridDatosAlumnoLblDireccion.setText(alumno.getDireccion());
        gridDatosAlumnoLblDni.setText(alumno.getDni());
        gridDatosAlumnoLblEdad.setText(alumno.getEdad() + "");
        gridDatosAlumnoLblNombre.setText(alumno.getNombre());

        //el mensaje de la tabla vacia
        tablaCursosMatriculados.setPlaceholder(new Label("No está matriculado en ningún curso"));

        //inicializar la lista de cursos en los que esta matriculado
        baseDatos = new AccesoaBD();
        ArrayList<Curso> cursos = new ArrayList<>();
        List<Matricula> matriculas = baseDatos.getMatriculas();
        for (int i = 0; i < matriculas.size(); i++) {
            Matricula matricula = matriculas.get(i);
            if (alumno.getDni().equals(matricula.getAlumno().getDni())) {
                cursos.add(matricula.getCurso());
            }
        }
        listaCursos = FXCollections.observableArrayList(cursos);
        tablaCursosMatriculados.setItems(listaCursos); //vincular la vista y el modelo
        //asignar el estilo a las celdas
        tablaCursosMatriculadosColumnaCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulodelcurso()));
        tablaCursosMatriculadosColumnaCurso.setStyle("-fx-alignment: CENTER;");
        tablaCursosMatriculadosColumnaProfesor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfesorAsignado()));
        tablaCursosMatriculadosColumnaProfesor.setStyle("-fx-alignment: CENTER;");
        tablaCursosMatriculadosColumnaHora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora().toString()));
        tablaCursosMatriculadosColumnaHora.setStyle("-fx-alignment: CENTER;");

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
