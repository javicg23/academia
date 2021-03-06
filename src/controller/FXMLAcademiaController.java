/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author V
 */
public class FXMLAcademiaController implements Initializable {

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
    private ImageView imgLogo;
    @FXML
    private Button btnAlumnos;
    @FXML
    private Button btnCursos;
    @FXML
    private Button btnMatriculaciones;

    private Stage primaryStage;
    @FXML
    private BorderPane borderPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnMatriculaciones.setMinHeight(50);
    }

    public void initStage(Stage stage) {
        primaryStage = stage;
        borderPane.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        borderPane.getStyleClass().add("fondoImagen");
        btnAlumnos.getStyleClass().addAll("boton", "botonAcademia");
        btnCursos.getStyleClass().addAll("boton", "botonAcademia");
        btnMatriculaciones.getStyleClass().addAll("boton", "botonAcademia");
        
    }
    //metodo que se lanza al pulsar en el menu en alumnos - > anyadir
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
        stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2);
        stage.setY(primaryStage.getY() - 20);
        stage.setResizable(false);
        stage.showAndWait();
    }
    //metodo que se lanza al pulsar en el menu en alumnos - > eliminar
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
        stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2);
        stage.setY(primaryStage.getY() - 20);
        stage.setResizable(false);
        stage.showAndWait();
    }
    //metodo que se lanza al pulsar en el menu en alumnos - > listado
    @FXML
    private void pulsarMenuAlumnosListado(ActionEvent event) throws IOException {
        abrirListadoAlumnos();
    }
    //metodo que se lanza al pulsar en el menu en cursos - > anyadir
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
        stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2);
        stage.setY(primaryStage.getY() - 20);
        stage.setResizable(false);
        stage.showAndWait();
    }
    //metodo que se lanza al pulsar en el menu en cursos - > eliminar
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
        stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2);
        stage.setY(primaryStage.getY() - 20);
        stage.setResizable(false);
        stage.showAndWait();
    }
    //metodo que se lanza al pulsar en el menu en cursos - > listado
    @FXML
    private void pulsarMenuCursosListado(ActionEvent event) throws IOException {
        abrirListadoCursos();
    }
    //metodo que se lanza al pulsar en el menu en matriculaciones - > matricular
    @FXML
    private void pulsarMenuMatriculacionesMatricular(ActionEvent event) throws IOException {
        abrirMatricular(false);
    }
    //metodo que se lanzar al pulsar en el menu en matriculaciones - > desmatricular
    @FXML
    private void pulsarMenuMatriculacionesDesmatricular(ActionEvent event) throws IOException {
        abrirMatricular(true);
    }
    //metodo que se lanza al pulsar con el raton en el boton alumnos
    @FXML
    private void pulsarRatonBtnAlumnos(MouseEvent event) throws IOException {
        abrirListadoAlumnos();
    }
    //metodo que se lanza al pulsar con el teclado en el boton alumnos
    @FXML
    private void pulsarTecladoBtnAlumnos(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            abrirListadoAlumnos();
        }
    }
    //metodo que se lanza al pulsar con el raton en el boton cursos
    @FXML
    private void pulsarRatonBtnCursos(MouseEvent event) throws IOException {
        abrirListadoCursos();
    }
    //metodo que se lanza al pulsar con el teclado en el boton cursos
    @FXML
    private void pulsarTecladoBtnCursos(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            abrirListadoCursos();
        }
    }
    //metodo que se lanza al pulsar con el raton en el boton matriculaciones
    @FXML
    private void pulsarRatonBtnMatriculaciones(MouseEvent event) throws IOException {
        abrirMatricular(false);
    }
    //metodo que se lanza al pulsar con el teclado en el boton matriculaciones
    @FXML
    private void pulsarTecladoBtnMatriculaciones(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            abrirMatricular(false);
        }
    }
    //metodo que cambia la stage a listado alumnos
    private void abrirListadoAlumnos() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaAlumnos.fxml"));
        Parent root = (Parent) loader.load();

        FXMLListaAlumnosController controllerListaAlumnos = loader.<FXMLListaAlumnosController>getController();
        controllerListaAlumnos.initStage(primaryStage);
        Scene scene = new Scene(root, primaryStage.getWidth() - 16, primaryStage.getHeight() - 39);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //metodo que cambia la stage a listado cursos
    private void abrirListadoCursos() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaCursos.fxml"));
        Parent root = (Parent) loader.load();

        FXMLListaCursosController controllerListaCursos = loader.<FXMLListaCursosController>getController();
        controllerListaCursos.initStage(primaryStage);
        Scene scene = new Scene(root, primaryStage.getWidth() - 16, primaryStage.getHeight() - 39);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //metodo que cambia la stage a matriculaciones, si el bool es false se abre la pestanya matricular
    //sino es true se abre la pestanya desmatricular
    private void abrirMatricular(Boolean bool) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLMatriculaciones.fxml"));
        Parent root = (Parent) loader.load();

        FXMLMatriculacionesController controllerMatriculaciones = loader.<FXMLMatriculacionesController>getController();
        controllerMatriculaciones.initStage(primaryStage, bool);
        Scene scene = new Scene(root, primaryStage.getWidth() - 16, primaryStage.getHeight() - 39);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
