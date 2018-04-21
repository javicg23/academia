/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.Curso;
import modelo.Dias;

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
    private TableView<Alumno> tablaAlumnos;
    @FXML
    private TableColumn<Alumno, Image> tablaAlumnosColumnaFotografia;
    @FXML
    private TableColumn<Alumno, String> tablaAlumnosColumnaNombre;
    @FXML
    private TableColumn<Alumno, String> tablaAlumnosColumnaDni;
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

    private Stage primaryStage, emergenteStage;
    private Curso curso;
    private AccesoaBD baseDatos;
    private ObservableList<Alumno> listaAlumnos = null; // Coleccion vinculada a la vista.

    public void initStage(Stage stageEmergente, Stage stage, Curso c) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Alumnos matriculados");
        primaryStage = stage;
        this.curso = c;
        initializeAll();
    }

    private void initializeAll() {
        //poner los datos del curso
        gridDatosCursoLblAula.setText(curso.getAula());
        gridDatosCursoLblHora.setText(curso.getHora().toString());
        lblCurso.setText(curso.getTitulodelcurso());

        //introoducir los dias que se imparten las clases en el label
        ArrayList<Dias> dias = curso.getDiasimparte();
        int r = 0;
        String diasString = "";
        do {
            diasString += dias.get(r);
            if (r < dias.size() - 1) {
                diasString += ", ";
            }
            r++;
            if (r % 2 == 0) {
                diasString += "\n";
            }
        } while (r < dias.size());
        gridDatosCursoLblDias.setText(diasString);

        //introducir el nombre del profesor
        String nombreProfesor = curso.getProfesorAsignado();
        String nombreProfesorFinal = "";
        List<String> listaNombreString = new ArrayList<String>(Arrays.asList(nombreProfesor.split(" ")));
        for (int i = 0; i < listaNombreString.size(); i++) {
            String cadenaNombre = listaNombreString.get(i);
            nombreProfesorFinal += cadenaNombre;
            if (nombreProfesorFinal.length() + cadenaNombre.length() > 16) {
                nombreProfesorFinal += "\n";
            }
        }
        gridDatosCursoLblProfesor.setText(nombreProfesorFinal);

        //tablaView
        baseDatos = new AccesoaBD();
        ArrayList<Alumno> alumnos = (ArrayList<Alumno>) baseDatos.getAlumnosDeCurso(curso);
        if (alumnos != null) {
            listaAlumnos = FXCollections.observableArrayList(alumnos);
            tablaAlumnos.setItems(listaAlumnos); //vincular la vista y el modelo

            tablaAlumnosColumnaDni.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
            tablaAlumnosColumnaDni.setStyle("-fx-alignment: CENTER;");
            tablaAlumnosColumnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
            tablaAlumnosColumnaNombre.setStyle("-fx-alignment: CENTER;");
            tablaAlumnosColumnaFotografia.setCellValueFactory(cel -> new SimpleObjectProperty<Image>(cel.getValue().getFoto()));
            tablaAlumnosColumnaFotografia.setCellFactory(c -> new ImageTableCell<>());
            tablaAlumnosColumnaFotografia.setStyle("-fx-alignment: CENTER;");

        }
        //sentencia para aplicar el filtro a la lista de cursos
        textFiltrar.textProperty().addListener((observable, oldValue, newValue) -> {
            baseDatos = new AccesoaBD();
            ArrayList<Alumno> alumnoTotal = (ArrayList<Alumno>) baseDatos.getAlumnosDeCurso(curso);
            ArrayList<Alumno> alumnosFiltro = new ArrayList();
            for (int i = 0; i < alumnoTotal.size(); i++) {
                Alumno alumno = alumnoTotal.get(i);
                if (quitarAcentos(alumno.getNombre().toLowerCase()).startsWith(quitarAcentos(newValue.toLowerCase()))) {
                    alumnosFiltro.add(alumno);
                }
            }
            listaAlumnos = FXCollections.observableArrayList(alumnosFiltro);
            tablaAlumnos.setItems(listaAlumnos); //vincular la vista y el modelo
        });

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private String quitarAcentos(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case 'á':
                    c = 'a';
                    break;
                case 'à':
                    c = 'a';
                    break;
                case 'é':
                    c = 'e';
                    break;
                case 'è':
                    c = 'e';
                    break;
                case 'í':
                    c = 'i';
                    break;
                case 'ó':
                    c = 'o';
                    break;
                case 'ò':
                    c = 'o';
                    break;
                case 'ú':
                    c = 'u';
                    break;
                default:
            }
            res = res.concat(c + "");
        }
        return res;
    }

}
