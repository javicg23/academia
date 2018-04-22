/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.Matricula;

/**
 * FXML Controller class
 *
 * @author V
 */
public class FXMLEliminarAlumnoController implements Initializable {

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
    private TableView<Alumno> tablaAlumnos;
    @FXML
    private TableColumn<Alumno, Image> tablaAlumnosColumnaFotografia;
    @FXML
    private TableColumn<Alumno, String> tablaAlumnosColumnaNombre;
    @FXML
    private TableColumn<Alumno, String> tablaAlumnosColumnaDni;
    @FXML
    private Label lblEliminarModificado;

    private AccesoaBD baseDatos = new AccesoaBD();
    private ObservableList<Alumno> listaAlumnos = null;
    private boolean[] arrayBooleans = new boolean[8];
    private Stage primaryStage, emergenteStage;
    private Boolean vengoDeStageConMenu = false, vengoDesdeListaAlumnos = false;
    private Stage stage;

    public void initStage(Stage stageEmergente, Stage stage) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Eliminar alumno");
        primaryStage = stage;
    }

    public void initStage(Stage stageEmergente, Stage stage, Boolean conMenu) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Eliminar alumno");
        primaryStage = stage;
        vengoDeStageConMenu = conMenu;
    }

    public void initStage(Stage stageEmergente, Stage stage, Boolean conMenu, Boolean vengoDesdeLAlumnos) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Eliminar alumno");
        primaryStage = stage;
        vengoDeStageConMenu = conMenu;
        vengoDesdeListaAlumnos = vengoDesdeLAlumnos;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarTabla();

        //sentencia para aplicar el filtro a la lista de alumnos
        textFiltrar.textProperty().addListener((observable, oldValue, newValue) -> {
            baseDatos = new AccesoaBD();
            ArrayList<Alumno> alumnosTotal = (ArrayList<Alumno>) baseDatos.getAlumnos();
            Set<Alumno> alumnosFiltro = new HashSet();
            for (int i = 0; i < alumnosTotal.size(); i++) {
                Alumno alumno = alumnosTotal.get(i);
                String[] alumnoPartesNombre = alumno.getNombre().split(" ");
                for (int j = 0; j < alumnoPartesNombre.length; j++) {
                    if (quitarAcentos(alumnoPartesNombre[j].toLowerCase()).startsWith(quitarAcentos(newValue.toLowerCase()))) {
                        alumnosFiltro.add(alumno);
                    }
                }
                if (quitarAcentos(alumno.getNombre().toLowerCase()).startsWith(quitarAcentos(newValue.toLowerCase()))) {
                    alumnosFiltro.add(alumno);
                }
            }
            listaAlumnos = FXCollections.observableArrayList(alumnosFiltro);
            tablaAlumnos.setItems(listaAlumnos); //vincular la vista y el modelo
        });

        //el mensaje de la tabla vacia
        tablaAlumnos.setPlaceholder(new Label("No hay alumnos que mostrar"));

        //listener para que cuando se pulse una celdase activen los botones de eliminar y ver los datos del alumno
        btnEliminar.disableProperty().bind(Bindings.equal(-1, tablaAlumnos.getSelectionModel().selectedIndexProperty()));
    }

    @FXML
    private void pulsarRatonBtnCancelar(MouseEvent event) throws IOException {
        if (vengoDesdeListaAlumnos) {
            voyListaAlumnosFalseBoolean();
        }
        if (vengoDeStageConMenu) {
            stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        } else {
            voyListaAlumnosFalseBoolean();
        }
    }

    @FXML
    private void pulsarTecladoBtnCancelar(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (vengoDesdeListaAlumnos) {
                voyListaAlumnosFalseBoolean();
            }
            if (vengoDeStageConMenu) {
                stage = (Stage) btnCancelar.getScene().getWindow();
                stage.close();
            } else {
                voyListaAlumnosFalseBoolean();
            }
        }
    }

    @FXML
    private void pulsarRatonBtnEliminar(MouseEvent event) {
        confirmacionEliminarAlumno();
    }

    @FXML
    private void pulsarTecladoBtnEliminar(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            confirmacionEliminarAlumno();
        }
    }

    private void voyListaAlumnosFalseBoolean() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaAlumnos.fxml"));
        Parent root = (Parent) loader.load();

        FXMLListaAlumnosController controllerListaAlumnos = loader.<FXMLListaAlumnosController>getController();
        controllerListaAlumnos.initStage(primaryStage, false);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
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

    private void inicializarTabla() {
        baseDatos = new AccesoaBD();
        ArrayList<Alumno> alumnos = (ArrayList<Alumno>) baseDatos.getAlumnos();
        if (alumnos != null) {
            listaAlumnos = FXCollections.observableArrayList(alumnos);
            tablaAlumnos.setItems(listaAlumnos); //vincular la vista y el modelo
            //asignar el estilo a las celdas
            tablaAlumnosColumnaDni.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
            tablaAlumnosColumnaDni.setStyle("-fx-alignment: CENTER;");
            tablaAlumnosColumnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
            tablaAlumnosColumnaNombre.setStyle("-fx-alignment: CENTER;");
            tablaAlumnosColumnaFotografia.setCellValueFactory(cel -> new SimpleObjectProperty<Image>(cel.getValue().getFoto()));
            tablaAlumnosColumnaFotografia.setCellFactory(c -> new ImageTableCell<>());
            tablaAlumnosColumnaFotografia.setStyle("-fx-alignment: CENTER;");
        }
    }

    private void confirmacionEliminarAlumno() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar alumno");
        alert.setHeaderText(null);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/icon.png"));
        alert.setContentText("¿Está seguro de que desea eliminar el alumno de forma permanente?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            eliminarAlumno();
        }
    }

    private void eliminarAlumno() {
        baseDatos = new AccesoaBD();
        List<Alumno> alumnosSeleccionados = tablaAlumnos.getSelectionModel().getSelectedItems();
        ArrayList<Alumno> alumnos = (ArrayList<Alumno>) baseDatos.getAlumnos();

        ArrayList<Matricula> matriculasLista = (ArrayList<Matricula>) baseDatos.getMatriculas();
        ArrayList<Matricula> matriculasListaFinal = new ArrayList<>();

        for (int i = 0; i < alumnosSeleccionados.size(); i++) {
            Alumno alumno = alumnosSeleccionados.get(i);
            for (int j = 0; j < matriculasLista.size(); j++) {
                if (!matriculasLista.get(j).getAlumno().getDni().equals(alumno.getDni())) {
                    matriculasListaFinal.add(matriculasLista.get(j));
                }
            }
            alumnos.remove(alumno);
        }
        matriculasLista.clear();
        for (int i = 0; i < matriculasListaFinal.size(); i++) {
            matriculasLista.add(matriculasListaFinal.get(i));
        }
        baseDatos.salvar();

        lblEliminarModificado.setStyle("-fx-text-fill: red;");
        lblEliminarModificado.setText("Alumno eliminado correctamente");
        inicializarTabla();
    }
}
