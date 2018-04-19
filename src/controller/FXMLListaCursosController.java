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
import java.util.ResourceBundle;
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
import javafx.scene.control.Button;
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
public class FXMLListaCursosController implements Initializable {

    
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
    private TableColumn<Curso, String> tablaListaCursosColumnaHora;
    @FXML
    private GridPane gridBotonera;
    @FXML
    private Button btnAnyadir;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAlumnosMatriculados;

    
    private Boolean anyadido = false;
    private Stage primaryStage;
    private ObservableList<Curso> listaCursos = null; // Coleccion vinculada a la vista.
    private AccesoaBD baseDatos;
    
    public void initStage(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Lista cursos");
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
        
        //listener para que cuando se pulse una celdase activen los botones de eliminar y ver alumonsMatriculados
        btnEliminar.disableProperty().bind(Bindings.equal(-1, tablaListaCursos.getSelectionModel().selectedIndexProperty()));
        btnAlumnosMatriculados.disableProperty().bind(Bindings.equal(-1, tablaListaCursos.getSelectionModel().selectedIndexProperty()));
        
        //metodo para abrir los alumnos matriculados en un curso al pulsar dos veces en un curso de la tabla
        tablaListaCursos.setRowFactory(tableRow -> {
            TableRow<Curso> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    try {
                        abrirAlumnosMatriculados();
                    } catch (IOException e) {
                    }
                }
            });
            return row;
        });
        
        //sentencia para aplicar el filtro a la lista de cursos
        textFiltrar.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
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
            tablaListaCursos.setItems(listaCursos); //vincular la vista y el modelo
        });
        
        
        //aplicar el poder seleccionar diferentes filas
        tablaListaCursos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
        abrirMatricular();
    }

    @FXML
    private void pulsarMenuMatriculacionesDesmatricular(ActionEvent event) throws IOException {
        abrirMatricular();
    }

    @FXML
    private void pulsarRatonBtnAtras(MouseEvent event) throws IOException {
        ventanaAnterior();
    }

    @FXML
    private void pulsarTecladoBtnAtras(KeyEvent event) throws IOException{
        if (event.getCode().equals(KeyCode.ENTER)) {
            ventanaAnterior();
        }
    }

    @FXML
    private void pulsarRatonBtnAnyadir(MouseEvent event) throws IOException {
        abrirAnyadirCurso();
    }

    @FXML
    private void pulsarTecladoBtnAnyadir(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            abrirAnyadirCurso();
        }
    }

    @FXML
    private void pulsarRatonBtnEliminar(MouseEvent event) {
        eliminarCurso();
    }

    @FXML
    private void pulsarTecladoBtnEliminar(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            eliminarCurso();
        }
    }

    @FXML
    private void pulsarRatonBtnAlumnosMatriculados(MouseEvent event) throws IOException {
        abrirAlumnosMatriculados();
    }

    @FXML
    private void pulsarTecladoBtnAlumnosMatriculados(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            abrirAlumnosMatriculados();
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
    
    private void abrirMatricular() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLMatriculaciones.fxml"));
        Parent root = (Parent) loader.load();

        FXMLMatriculacionesController controllerMatriculaciones = loader.<FXMLMatriculacionesController>getController();
        controllerMatriculaciones.initStage(primaryStage);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    private void abrirAnyadirCurso() throws IOException {
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
    
    private void abrirAlumnosMatriculados() throws IOException {
        Curso curso = tablaListaCursos.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLAlumnosMatriculados.fxml"));
        Parent root = (Parent) loader.load();
        loader.<FXMLAlumnosMatriculadosController> getController().initStage(stage, primaryStage, curso); 
        Scene scene = new Scene(root);
        
        Image icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
        stage.getIcons().add(icon);
        
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    
    }
    
    private void eliminarCurso() {
        baseDatos = new AccesoaBD();
        List<Curso> cursosSeleccionados = tablaListaCursos.getSelectionModel().getSelectedItems();
        for (int i = 0; i < cursosSeleccionados.size(); i++) {
            Curso curso = cursosSeleccionados.get(i);
            ArrayList<Matricula> matriculasCurso = (ArrayList<Matricula>) baseDatos.getMatriculasDeCurso(curso);
            matriculasCurso.clear();
            baseDatos.getCursos().remove(curso);
        }
        baseDatos.salvar();
        lblModificacionLista.setText("Curso/s eliminado/s correctamente");
        inicializarTabla();
        
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
    
    private void inicializarTabla() {
        baseDatos = new AccesoaBD();
        ArrayList<Curso> cursos = (ArrayList<Curso>) baseDatos.getCursos();
        listaCursos = FXCollections.observableArrayList(cursos);
        tablaListaCursos.setItems(listaCursos); //vincular la vista y el modelo
        //asignar el estilo a las celdas
        tablaListaCursosColumnaCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulodelcurso()));
        tablaListaCursosColumnaProfesor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfesorAsignado()));
        tablaListaCursosColumnaHora.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getHora().toString()));
        
    
    }
    
    private void comprobarAnyadido() {
        if (anyadido) {
            lblModificacionLista.setText("Curso añadido correctamente");
        } else {
            lblModificacionLista.setText("");
        }
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
}
