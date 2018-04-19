/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Curso;

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
    @FXML
    private Button btnMatricular;
    @FXML
    private TableColumn<?, ?> tablaMatricularColumnaHora;
    @FXML
    private TableColumn<?, ?> tablaDesmatricularColumnaHora;
    @FXML
    private TabPane tabPane;
    
    private Boolean seleccionarTab = false;
    private Stage primaryStage;
    private ObservableList<Curso> listaAlumnos = null; // Coleccion vinculada a la vista.
    private AccesoaBD baseDatos;

    public void initStage(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Matriculaciones");
    }

    public void initStage(Stage stage, Boolean selTab) {
        initStage(stage);
        seleccionarTab = selTab;
        if (seleccionarTab) {
            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
            selectionModel.select(tabDesmatricular);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void pulsarMenuAlumnosAnyadir(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLAnyadirAlumno.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);

        Image icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
        stage.getIcons().add(icon);

        loader.<FXMLAnyadirAlumnoController>getController().initStage(stage, primaryStage, true);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void pulsarMenuAlumnosEliminar(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLEliminarAlumno.fxml"));
        Parent root = (Parent) loader.load();

        Image icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
        stage.getIcons().add(icon);

        Scene scene = new Scene(root);

        loader.<FXMLEliminarAlumnoController>getController().initStage(stage, primaryStage, true);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void pulsarMenuAlumnosListado(ActionEvent event) throws IOException {
        abrirListadoAlumnos();
    }

    @FXML
    private void pulsarMenuCursosAnyadir(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLAnyadirCurso.fxml"));
        Parent root = (Parent) loader.load();

        Scene scene = new Scene(root);

        Image icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
        stage.getIcons().add(icon);

        loader.<FXMLAnyadirCursoController>getController().initStage(stage, primaryStage, true);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void pulsarMenuCursosEliminar(ActionEvent event) throws IOException {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLEliminarCurso.fxml"));
        Parent root = (Parent) loader.load();

        Scene scene = new Scene(root);

        Image icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
        stage.getIcons().add(icon);

        loader.<FXMLEliminarCursoController>getController().initStage(stage, primaryStage, true);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    @FXML
    private void pulsarMenuCursosListado(ActionEvent event) throws IOException {
        abrirListadoCursos();
    }

    @FXML
    private void pulsarMenuMatriculacionesMatricular(ActionEvent event) throws IOException {
        abrirMatricular(false);
    }

    @FXML
    private void pulsarMenuMatriculacionesDesmatricular(ActionEvent event) throws IOException {
        abrirMatricular(true);
    }

    @FXML
    private void pulsarRatonBtnAtras(MouseEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLAcademia.fxml"));
            Parent root = (Parent) loader.load();

            FXMLAcademiaController controllerAcademia = loader.<FXMLAcademiaController> getController();
            controllerAcademia.initStage(primaryStage);
            Scene scene = new Scene(root);
            primaryStage.setTitle("Academia");
            primaryStage.setScene(scene);
            primaryStage.show();
    }

    @FXML
    private void pulsarTecladoBtnAtras(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLAcademia.fxml"));
            Parent root = (Parent) loader.load();

            FXMLAcademiaController controllerAcademia = loader.<FXMLAcademiaController> getController();
            controllerAcademia.initStage(primaryStage);
            Scene scene = new Scene(root);
            primaryStage.setTitle("Academia");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    @FXML
    private void pulsarRatonBtnDesmatricular(MouseEvent event) throws IOException {
        abrirMatricular(false);
    }

    @FXML
    private void pulsarTecladoBtnDesmatricular(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            abrirMatricular(true);
        }
    }

    @FXML
    private void pulsarTecladoBtnMatricular(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            abrirMatricular(false);
        }
    }

    @FXML
    private void pulsarRatonBtnMatricular(MouseEvent event) throws IOException {
        abrirMatricular(false);
    }

    private void abrirListadoAlumnos() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaAlumnos.fxml"));
        Parent root = (Parent) loader.load();

        FXMLListaAlumnosController controllerListaAlumnos = loader.<FXMLListaAlumnosController>getController();
        controllerListaAlumnos.initStage(primaryStage);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void abrirListadoCursos() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaCursos.fxml"));
        Parent root = (Parent) loader.load();

        FXMLListaCursosController controllerListaCursos = loader.<FXMLListaCursosController>getController();
        controllerListaCursos.initStage(primaryStage);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void abrirMatricular(Boolean bool) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLMatriculaciones.fxml"));
        Parent root = (Parent) loader.load();

        FXMLMatriculacionesController controllerMatriculaciones = loader.<FXMLMatriculacionesController>getController();
        controllerMatriculaciones.initStage(primaryStage, bool);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
