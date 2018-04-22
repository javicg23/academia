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

        //el mensaje de la tabla vacia
        tablaAlumnos.setPlaceholder(new Label("No hay alumnos que mostrar"));

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

    }
    //metodo que se ejecuta al pulsar en el menu en alumnos - > anyadir
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
        stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2);
        stage.setY(primaryStage.getY() - 20);
        stage.setResizable(false);
        stage.showAndWait();
    }
    //metodo que se ejecuta al pulsar en el menu en alumnos - > eliminar
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
        stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2);
        stage.setY(primaryStage.getY() - 20);
        stage.setResizable(false);
        stage.showAndWait();
    }
    //metodo que se ejecuta al pulsar en el menu en alumnos - > listado
    @FXML
    private void pulsarMenuAlumnosListado(ActionEvent event) throws IOException {
        abrirListadoAlumnos();
    }
    //metodo que se ejecuta al pulsar en el menu en cursos - > anyadir
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
    //metodo que se ejecuta al pulsar en el menu en cursos - > eliminar
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
    //metodo que se ejecuta al pulsar en el menu en cursos - > listado
    @FXML
    private void pulsarMenuCursosListado(ActionEvent event) throws IOException {
        abrirListadoCursos();
    }
    //metodo que se ejecuta al pulsar en el menu en matriculaciones - > matricular
    @FXML
    private void pulsarMenuMatriculacionesMatricular(ActionEvent event) throws IOException {
        abrirMatricular(false);
    }
    //metodo que se ejecuta al pulsar en el menu en matriculaciones - > desmatricular
    @FXML
    private void pulsarMenuMatriculacionesDesmatricular(ActionEvent event) throws IOException {
        abrirMatricular(true);
    }
    //metodo que se ejecuta al pulsar con el raton en el boton atras (flecha izquierda)
    @FXML
    private void pulsarRatonBtnAtras(MouseEvent event) throws IOException {
        ventanaAnterior();
    }
    //metodo que se ejecuta al pulsar con el teclado en el boton atras (flecha izquierda)
    @FXML
    private void pulsarTecladoBtnAtras(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            ventanaAnterior();
        }
    }
    //metodo que se ejecuta al pulsar con el raton en el boton anyadir
    @FXML
    private void pulsarRatonBtnAnyadir(MouseEvent event) throws IOException {
        abrirAnyadirAlumno();
    }
    //metodo que se ejecuta al pulsar con el teclado en el boton anyadir
    @FXML
    private void pulsarTecladoBtnAnyadir(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            abrirAnyadirAlumno();
        }
    }
    //metodo que se ejecuta al pulsar con el raton en el boton eliminar
    @FXML
    private void pulsarRatonBtnEliminar(MouseEvent event) {
        confirmacionEliminarAlumno();
    }
    //metodo que se ejecuta al pulsar con el teclado en el boton eliminar
    @FXML
    private void pulsarTecladoBtnEliminar(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            confirmacionEliminarAlumno();
        }
    }
    //metodo que se ejecuta al pulsar con el raton en el boton visualizar
    @FXML
    private void pulsarRatonBtnVisualizacion(MouseEvent event) throws IOException {
        abrirVisualizar();
    }  
    //metodo que se ejecuta al pulsar con el teclado en el boton visualizar
    @FXML
    private void pulsarTecladoBtnVisualizacion(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            abrirVisualizar();
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
    //metodo que cambia la stage a matricular
    private void abrirMatricular(Boolean bool) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLMatriculaciones.fxml"));
        Parent root = (Parent) loader.load();

        FXMLMatriculacionesController controllerMatriculaciones = loader.<FXMLMatriculacionesController>getController();
        controllerMatriculaciones.initStage(primaryStage, bool);
        Scene scene = new Scene(root, primaryStage.getWidth() - 16, primaryStage.getHeight() - 39);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //metodo que devuelve la stage a la stage principal
    private void ventanaAnterior() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLAcademia.fxml"));
        Parent root = (Parent) loader.load();

        FXMLAcademiaController controllerAcademia = loader.<FXMLAcademiaController>getController();
        controllerAcademia.initStage(primaryStage);
        Scene scene = new Scene(root, primaryStage.getWidth() - 16, primaryStage.getHeight() - 39);
        primaryStage.setTitle("Academia");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    //metodo que cambia la stage a anyadir alumno
    private void abrirAnyadirAlumno() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLAnyadirAlumno.fxml"));
        Parent root = (Parent) loader.load();

        Scene scene = new Scene(root);

        Image icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
        stage.getIcons().add(icon);

        loader.<FXMLAnyadirAlumnoController>getController().initStage(stage, primaryStage);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2);
        stage.setY(primaryStage.getY() - 20);
        stage.setResizable(false);
        stage.showAndWait();
    }
    //metodo que cambia la stage a datos alumno
    private void abrirVisualizar() throws IOException {
        Alumno alumno = tablaAlumnos.getSelectionModel().getSelectedItem();
        if (alumno != null) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLDatosAlumno.fxml"));
            Parent root = (Parent) loader.load();
            loader.<FXMLDatosAlumnoController>getController().initStage(stage, primaryStage, alumno);
            Scene scene = new Scene(root);

            Image icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
            stage.getIcons().add(icon);

            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2);
            stage.setY(primaryStage.getY() - 20);
            stage.setResizable(false);
            stage.showAndWait();
        }
    }
    //metodo que comprueba el label de si se ha añadido correctamente el alumno
    private void comprobarAnyadido() {
        if (anyadido) {
            lblModificacionLista.setStyle("-fx-text-fill: green;");
            lblModificacionLista.setText("Alumno añadido correctamente");
        } else {
            lblModificacionLista.setText("");
        }
    }
    //alert para la confirmacion de la eliminacion de un alumno
    private void confirmacionEliminarAlumno() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Eliminar alumno");
        alert.setHeaderText(null);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/icon.png"));
        alert.setContentText("¿Está seguro de que desea eliminar el alumno de forma permanente?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            eliminarAlumno();
        }
    }
    //metodo que sirve para eliminar el alumno de la bd
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

        lblModificacionLista.setStyle("-fx-text-fill: red;");
        lblModificacionLista.setText("Alumno eliminado correctamente");
        inicializarTabla();
    }
    //metodo que sirve para inicializar la tabla
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
    //metodo para quitar los acentos en el filtro y buscar independientemente si hay tilde o no
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
