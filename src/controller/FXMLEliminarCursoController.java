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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Curso;
import modelo.Matricula;

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
    private TableView<Curso> tablaCursos;
    @FXML
    private TableColumn<Curso, String> tablaCursosColumnaCurso;
    @FXML
    private TableColumn<Curso, String> tablaCursosColumnaProfesor;
    @FXML
    private TableColumn<Curso, String> tablaCursosColumnaHora;
    @FXML
    private Label lblEliminarModificado;

    private AccesoaBD baseDatos = new AccesoaBD();
    private ObservableList<Curso> listaCursos = null;
    private boolean[] arrayBooleans = new boolean[8];
    private Stage primaryStage, emergenteStage;
    private Boolean vengoDeStageConMenu = false, vengoDesdeListaCursos = false;
    private Stage stage;

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

    public void initStage(Stage stageEmergente, Stage stage, Boolean conMenu, Boolean vengoDesdeLCursos) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Eliminar curso");
        primaryStage = stage;
        vengoDeStageConMenu = conMenu;
        vengoDesdeListaCursos = vengoDesdeLCursos;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarTabla();

        //sentencia para aplicar el filtro a la lista de cursos
        textFiltrar.textProperty().addListener((observable, oldValue, newValue) -> {
            baseDatos = new AccesoaBD();
            ArrayList<Curso> cursosTotal = (ArrayList<Curso>) baseDatos.getCursos();
            ArrayList<Curso> cursosFiltro = new ArrayList();
            for (int i = 0; i < cursosTotal.size(); i++) {
                Curso curso = cursosTotal.get(i);
                if (quitarAcentos(curso.getTitulodelcurso().toLowerCase()).startsWith(quitarAcentos(newValue.toLowerCase()))) {
                    cursosFiltro.add(curso);
                }
            }
            listaCursos = FXCollections.observableArrayList(cursosFiltro);
            tablaCursos.setItems(listaCursos); //vincular la vista y el modelo
        });

        //listener para que cuando se pulse una celdase activen los botones de eliminar y ver alumonsMatriculados
        btnEliminar.disableProperty().bind(Bindings.equal(-1, tablaCursos.getSelectionModel().selectedIndexProperty()));
        //aplicar el poder seleccionar diferentes filas
        tablaCursos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void pulsarRatonBtnCancelar(MouseEvent event) throws IOException {
        if (vengoDesdeListaCursos) {
            voyListaCursosFalseBoolean();
        }
        if (vengoDeStageConMenu) {
            stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        } else {
            voyListaCursosFalseBoolean();
        }
    }

    @FXML
    private void pulsarTecladoBtnCancelar(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (vengoDesdeListaCursos) {
                voyListaCursosFalseBoolean();
            }
            if (vengoDeStageConMenu) {
                stage = (Stage) btnCancelar.getScene().getWindow();
                stage.close();
            } else {
                voyListaCursosFalseBoolean();
            }
        }
    }

    @FXML
    private void pulsarRatonBtnEliminar(MouseEvent event) {
        confirmacionEliminarCurso();
    }

    @FXML
    private void pulsarTecladoBtnEliminar(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            confirmacionEliminarCurso();
        }
    }

    private void voyListaCursosFalseBoolean() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaCursos.fxml"));
        Parent root = (Parent) loader.load();

        FXMLListaCursosController controllerListaCursos = loader.<FXMLListaCursosController>getController();
        controllerListaCursos.initStage(primaryStage, false);
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

    private void confirmacionEliminarCurso() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar curso/s");
        alert.setHeaderText(null);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/icon.png"));
        alert.setContentText("¿Está seguro de que desea eliminar el/los curso/s de forma permanente?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            eliminarCurso();
        }
    }

    private void eliminarCurso() {
        baseDatos = new AccesoaBD();
        List<Curso> cursosSeleccionados = tablaCursos.getSelectionModel().getSelectedItems();
        ArrayList<Curso> cursos = (ArrayList<Curso>) baseDatos.getCursos();
        ArrayList<Matricula> matriculasLista = (ArrayList<Matricula>) baseDatos.getMatriculas();
        ArrayList<Matricula> matriculasListaFinal = new ArrayList<>();
        for (int i = 0; i < cursosSeleccionados.size(); i++) {
            Curso curso = cursosSeleccionados.get(i);
            for (int j = 0; j < matriculasLista.size(); j++) {
                if (!matriculasLista.get(j).getCurso().equals(curso)) {
                    matriculasListaFinal.add(matriculasLista.get(j));
                }
            }
            cursos.remove(curso);
        }
        matriculasLista.clear();
        for (int i = 0; i < matriculasListaFinal.size(); i++) {
            matriculasLista.add(matriculasListaFinal.get(i));
        }
        baseDatos.salvar();

        lblEliminarModificado.setStyle("-fx-text-fill: red;");
        lblEliminarModificado.setText("Curso/s eliminado/s correctamente");
        inicializarTabla();

    }

    private void inicializarTabla() {
        baseDatos = new AccesoaBD();
        ArrayList<Curso> cursos = (ArrayList<Curso>) baseDatos.getCursos();
        if (cursos != null) {
            listaCursos = FXCollections.observableArrayList(cursos);
            tablaCursos.setItems(listaCursos); //vincular la vista y el modelo
            //asignar el estilo a las celdas
            tablaCursosColumnaCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulodelcurso()));
            tablaCursosColumnaCurso.setStyle("-fx-alignment: CENTER;");
            tablaCursosColumnaProfesor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfesorAsignado()));
            tablaCursosColumnaProfesor.setStyle("-fx-alignment: CENTER;");
            tablaCursosColumnaHora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora().toString()));
            tablaCursosColumnaHora.setStyle("-fx-alignment: CENTER;");
        }

    }

}
