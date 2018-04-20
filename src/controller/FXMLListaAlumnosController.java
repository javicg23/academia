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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
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
import modelo.Alumno;
import modelo.Curso;
import modelo.Matricula;

/**
 * FXML Controller class
 *
 * @author V
 */
public class FXMLListaAlumnosController implements Initializable {

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
    private TableView<Alumno> tablaAlumnos;
    @FXML
    private TableColumn<Alumno, Image> tablaAlumnosColumnaFotografia;
    @FXML
    private TableColumn<Alumno, String> tablaAlumnosColumnaNombre;
    @FXML
    private TableColumn<Alumno, String> tablaAlumnosColumnaDni;
    @FXML
    private GridPane gridBotonera;
    @FXML
    private Button btnAnyadir;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnVisualizar;

    private Boolean anyadido = false;
    private Stage primaryStage;
    private ObservableList<Alumno> listaAlumnos = null; // Coleccion vinculada a la vista.
    private AccesoaBD baseDatos;

    public void initStage(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Lista alumnos");
    }

    public void initStage(Stage stage, Boolean anyadidoExito) {
        initStage(stage);
        anyadido = anyadidoExito;
        comprobarAnyadido();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        baseDatos = new AccesoaBD();
        inicializarTabla();
        
        //listener para que cuando se pulse una celdase activen los botones de eliminar y ver los datos del alumno
        btnEliminar.disableProperty().bind(Bindings.equal(-1, tablaAlumnos.getSelectionModel().selectedIndexProperty()));
        btnVisualizar.disableProperty().bind(Bindings.equal(-1, tablaAlumnos.getSelectionModel().selectedIndexProperty()));
        
        //sentencia para aplicar el filtro a la lista de alumnos
        textFiltrar.textProperty().addListener((observable, oldValue, newValue) -> {
            baseDatos = new AccesoaBD();
            ArrayList<Alumno> alumnosTotal = (ArrayList<Alumno>) baseDatos.getAlumnos();
            ArrayList<Alumno> alumnosFiltro = new ArrayList();
            for (int i = 0; i < alumnosTotal.size(); i++) {
                Alumno alumno = alumnosTotal.get(i);
                if (quitarAcentos(alumno.getNombre().toLowerCase()).startsWith(quitarAcentos(newValue.toLowerCase()))) {
                    alumnosFiltro.add(alumno);
                }
            }
            listaAlumnos = FXCollections.observableArrayList(alumnosFiltro);
            tablaAlumnos.setItems(listaAlumnos); //vincular la vista y el modelo
        });
        
        //metodo para abrir los alumnos matriculados en un curso al pulsar dos veces en un curso de la tabla
        tablaAlumnos.setRowFactory(tableRow -> {
            TableRow<Alumno> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    try {
                        abrirVisualizar();
                    } catch (IOException e) {
                    }
                }
            });
            return row;
        });
        
        
        //aplicar el poder seleccionar diferentes filas
        tablaAlumnos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void pulsarMenuAlumnosAnyadir(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLAnyadirAlumno.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);

        Image icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
        stage.getIcons().add(icon);

        loader.<FXMLAnyadirAlumnoController>getController().initStage(stage, primaryStage, true, true);

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

        loader.<FXMLEliminarAlumnoController>getController().initStage(stage, primaryStage, true, true);

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
        ventanaAnterior();
    }

    @FXML
    private void pulsarTecladoBtnAtras(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            ventanaAnterior();
        }
    }

    @FXML
    private void pulsarRatonBtnAnyadir(MouseEvent event) throws IOException {
        abrirAnyadirAlumno();
    }

    @FXML
    private void pulsarTecladoBtnAnyadir(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            abrirAnyadirAlumno();
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

    @FXML
    private void pulsarRatonBtnVisualizacion(MouseEvent event) throws IOException{
        abrirVisualizar();
    }

    @FXML
    private void pulsarTecladoBtnVisualizacion(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            abrirVisualizar();
        }
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
    
    private void ventanaAnterior() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLAcademia.fxml"));
        Parent root = (Parent) loader.load();

        FXMLAcademiaController controllerAcademia = loader.<FXMLAcademiaController> getController();
        controllerAcademia.initStage(primaryStage);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Academia");
        primaryStage.setScene(scene);
        primaryStage.show();
    
    }
    
    private void abrirAnyadirAlumno() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLAnyadirAlumno.fxml"));
        Parent root = (Parent) loader.load();

        Scene scene = new Scene(root);
        
        Image icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
        stage.getIcons().add(icon);
        
        loader.<FXMLAnyadirAlumnoController> getController().initStage(stage, primaryStage); 
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    private void abrirVisualizar() throws IOException {
        Alumno alumno = tablaAlumnos.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLDatosAlumno.fxml"));
        Parent root = (Parent) loader.load();
        loader.<FXMLDatosAlumnoController> getController().initStage(stage, primaryStage, alumno); 
        Scene scene = new Scene(root);
        
        Image icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
        stage.getIcons().add(icon);
        
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
    
    private void comprobarAnyadido() {
        if (anyadido) {
            lblModificacionLista.setStyle("-fx-text-fill: green;");
            lblModificacionLista.setText("Alumnno añadido correctamente");
        } else {
            lblModificacionLista.setText("");
        }
    }
    
    private void confirmacionEliminarAlumno() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("Eliminar alumno/s");
        alert.setContentText("¿Esta seguro que desea eliminar/los de forma permanente?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            eliminarAlumno();
        }
    }
    
    private void eliminarAlumno() {
        baseDatos = new AccesoaBD();
        List<Alumno> alumnosSeleccionados = tablaAlumnos.getSelectionModel().getSelectedItems();
        for (int i = 0; i < alumnosSeleccionados.size(); i++) {
            Alumno alumno = alumnosSeleccionados.get(i);
            ArrayList<Matricula> matriculas = (ArrayList<Matricula>) baseDatos.getMatriculas();
            for (int j = 0; j < matriculas.size(); j++) {
                Matricula matricula = matriculas.get(j);
                if (alumno.equals(matricula.getAlumno())) {
                    matriculas.remove(matricula);
                    
                }
            }
            baseDatos.getAlumnos().remove(alumno);
        }
        baseDatos.salvar();
        lblModificacionLista.setStyle("-fx-text-fill: red;");
        lblModificacionLista.setText("Alumno/s eliminado/s correctamente");
        inicializarTabla();
    }
    
    private void inicializarTabla() {
        baseDatos = new AccesoaBD();
        ArrayList<Alumno> alumnos = (ArrayList<Alumno>) baseDatos.getAlumnos();
        if (alumnos != null) { 
            listaAlumnos = FXCollections.observableArrayList(alumnos);
            tablaAlumnos.setItems(listaAlumnos); //vincular la vista y el modelo
            //asignar el estilo a las celdas
            tablaAlumnosColumnaDni.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
            tablaAlumnosColumnaNombre.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getNombre()));
            
//            tablaAlumnosColumnaFotografia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoto()));
        }
    
    }
    private String quitarAcentos(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case 'á': c = 'a';break;
                case 'à': c = 'a';break;
                case 'é': c = 'e';break;
                case 'è': c = 'e';break;
                case 'í': c = 'i';break;
                case 'ó': c = 'o';break;
                case 'ò': c = 'o';break;
                case 'ú': c = 'u';break;
                default: 
            }
            res = res.concat(c+"");
        }
        return res;
    }
    
}
