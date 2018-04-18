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
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Curso;

/**
 * FXML Controller class
 *
 * @author V
 */
public class FXMLListaCursosController implements Initializable {

    private ObservableList<Curso> listaCursos = null; // Coleccion vinculada a la vista.
    private AccesoaBD baseDatos;
    
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
    private Label lblTitulo;
    @FXML
    private Button btnAtras;
    @FXML
    private Label lblModificacionLista;
    @FXML
    private ImageView imgLupa;
    @FXML
    private TextField textFiltrar;
    @FXML
    private TableView<Curso> tablaListaCursos;
    @FXML
    private TableColumn<Curso, String> tablaListaCursosColumnaCurso;
    @FXML
    private TableColumn<Curso, String> tablaListaCursosColumnaProfesor;
    @FXML
    private GridPane gridBotonera;
    @FXML
    private Button btnAnyadir;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAlumnosMatriculados;

    
    private Stage primaryStage;
    private Scene escenaAnterior; 
    private String tituloAnterior;
    private boolean anyadido;
    
    public void initStage(Stage stage) { 
        primaryStage = stage;
        escenaAnterior = stage.getScene();
        tituloAnterior = stage.getTitle();
        primaryStage.setTitle("Lista cursos");
    }
    
    public void initStage(Stage stage, boolean anyadidoExito) { 
        primaryStage = stage;
        escenaAnterior = stage.getScene();
        tituloAnterior = stage.getTitle();
        primaryStage.setTitle("Lista cursosx");
        anyadido = anyadidoExito;
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AccesoaBD baseDatos = new AccesoaBD();
        ArrayList<Curso> cursos = (ArrayList<Curso>) baseDatos.getCursos();
        listaCursos = FXCollections.observableArrayList(cursos);
        tablaListaCursos.setItems(listaCursos); //vincular la vista y el modelo
        
        //asignar el estilo a las celdas
        tablaListaCursosColumnaCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulodelcurso()));
        tablaListaCursosColumnaProfesor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfesorAsignado()));
        
        // inhabilitar botones al arrancar.
        btnAlumnosMatriculados.setDisable(true);
        btnEliminar.setDisable(true);
        
        //listener para que cuando se pulse una celdase activen los botones de eliminar y ver alumonsMatriculados
        tablaListaCursos.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (tablaListaCursos.isFocused()) {
                    btnEliminar.setDisable(false);
                    btnAlumnosMatriculados.setDisable(false);
                }
            }
        });
        
        tablaListaCursos.setRowFactory(tableRow -> {
            TableRow<Curso> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    System.out.println("0");
                }
            });
            return row;
        });
    }    

    @FXML
    private void pulsarMenuAlumnosAnyadir(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLAnyadirAlumno.fxml"));
        Parent root = (Parent) loader.load();
        
        Scene scene = new Scene(root);
        
        Image icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
        stage.getIcons().add(icon);
        
        loader.<FXMLAnyadirCursoController> getController().initStage(stage, primaryStage); 
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void pulsarMenuAlumnosEliminar(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLEliminarAlumno.fxml"));
        Parent root = (Parent) loader.load();
        
        Scene scene = new Scene(root);
        
        Image icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
        stage.getIcons().add(icon);
        
        loader.<FXMLAnyadirCursoController> getController().initStage(stage, primaryStage); 
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
        
        loader.<FXMLAnyadirCursoController> getController().initStage(stage, primaryStage); 
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
        
        loader.<FXMLAnyadirCursoController> getController().initStage(stage, primaryStage); 
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
        abrirMatricular();
    }

    @FXML
    private void pulsarMenuMatriculacionesDesmatricular(ActionEvent event) throws IOException {
        abrirMatricular();
    }

    @FXML
    private void pulsarRatonBtnAtras(MouseEvent event) {
        ventanaAnterior();
    }

    @FXML
    private void pulsarTecladoBtnAtras(KeyEvent event) throws IOException{
        if (event.getCode().equals(KeyCode.ENTER)) {
            ventanaAnterior();
        }
    }

    @FXML
    private void pulsarRatonBtnAnyadir(MouseEvent event) {
    }

    @FXML
    private void pulsarTecladoBtnAnyadir(KeyEvent event) {
    }

    @FXML
    private void pulsarRatonBtnEliminar(MouseEvent event) {
    }

    @FXML
    private void pulsarTecladoBtnEliminar(KeyEvent event) {
    }

    @FXML
    private void pulsarRatonBtnAlumnosMatriculados(MouseEvent event) {
    }

    @FXML
    private void pulsarTecladoBtnAlumnosMatriculados(KeyEvent event) {
    }
    
    private void abrirListadoAlumnos() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaAlumnos.fxml"));
        Parent root = (Parent) loader.load();
        
        FXMLListaAlumnosController controllerListaAlumnos = loader.<FXMLListaAlumnosController> getController();
        controllerListaAlumnos.initStage(primaryStage);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void abrirListadoCursos() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaCursos.fxml"));
        Parent root = (Parent) loader.load();
        
        FXMLListaCursosController controllerListaCursos = loader.<FXMLListaCursosController> getController();
        controllerListaCursos.initStage(primaryStage);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void abrirMatricular() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLMatriculaciones.fxml"));
        Parent root = (Parent) loader.load();
        
        FXMLMatriculacionesController controllerMatriculaciones = loader.<FXMLMatriculacionesController> getController();
        controllerMatriculaciones.initStage(primaryStage);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void ventanaAnterior() {
        primaryStage.setTitle(tituloAnterior);
        primaryStage.setScene(escenaAnterior);
    }
}
