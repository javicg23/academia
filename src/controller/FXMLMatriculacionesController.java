/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.Curso;
import modelo.LocalDateAdapter;
import modelo.Matricula;

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
    private TableView<Curso> tablaMatricularCursos;
    @FXML
    private TableColumn<Curso, String> tablaMatricularCursosColumnaCurso;
    @FXML
    private TableColumn<Curso, String> tablaMatricularCursosColumnaProfesor;
    @FXML
    private TableColumn<Curso, String> tablaMatricularColumnaHora;
    @FXML
    private TableView<Alumno> tablaMatricularAlumnos;
    @FXML
    private TableColumn<Alumno, Image> tablaMatricularAlumnosColumnaFotografia;
    @FXML
    private TableColumn<Alumno, String> tablaMatricularAlumnosColumnaAlumno;
    @FXML
    private TableColumn<Alumno, String> tablaMatricularAlumnosColumnaDni;
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
    private TableView<Curso> tablaDesmatricularCursos;
    @FXML
    private TableColumn<Curso, String> tablaDesmatricularCursosColumnaCurso;
    @FXML
    private TableColumn<Curso, String> tablaDesmatricularCursosColumnaProfesor;
    @FXML
    private TableColumn<Curso, String> tablaDesmatricularColumnaHora;
    @FXML
    private TableView<Alumno> tablaDesmatricularAlumnos;
    @FXML
    private TableColumn<Alumno, Image> tablaDesmatricularAlumnosColumnaFotografía;
    @FXML
    private TableColumn<Alumno, String> tablaDesmatricularAlumnosColumnaAlumno;
    @FXML
    private TableColumn<Alumno, String> tablaDesmatricularAlumnosColumnaDni;
    @FXML
    private Button btnMatricular;
    @FXML
    private TabPane tabPane;
    @FXML
    private Label lblMatricularModificado;
    @FXML
    private Label lblDesmatricularModificado;

    private Boolean seleccionarTab = false;
    private Stage primaryStage;
    private ObservableList<Curso> listaCursos = null; // Coleccion vinculada a la vista.
    private ObservableList<Alumno> listaAlumnos = null; // Coleccion vinculada a la vista.
    private ObservableList<Curso> listaCursosDesmatricular = null; // Coleccion vinculada a la vista.
    private ObservableList<Alumno> listaAlumnosDesmatricular = null; // Coleccion vinculada a la vista.
    private AccesoaBD baseDatos;
    @FXML
    private Label lblCursosAMatricular;
    @FXML
    private Label lblCursosADesmatricular;

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
        //************************
        //**** MATRICULAR ********
        //************************

        //listener para que cuando se pulse una celdase activen los botones de eliminar y ver los datos del alumno
        btnMatricular.disableProperty().bind(Bindings.equal(-1, tablaMatricularCursos.getSelectionModel().selectedIndexProperty()));
        //inicializar tabla alumnos
        baseDatos = new AccesoaBD();
        ArrayList<Alumno> alumnos = (ArrayList<Alumno>) baseDatos.getAlumnos();
        if (alumnos != null) {
            listaAlumnos = FXCollections.observableArrayList(alumnos);
            tablaMatricularAlumnos.setItems(listaAlumnos); //vincular la vista y el modelo
            //asignar el estilo a las celdas
            tablaMatricularAlumnosColumnaDni.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
            tablaMatricularAlumnosColumnaDni.setStyle("-fx-alignment: CENTER;");
            tablaMatricularAlumnosColumnaAlumno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
            tablaMatricularAlumnosColumnaAlumno.setStyle("-fx-alignment: CENTER;");
            tablaMatricularAlumnosColumnaFotografia.setCellValueFactory(cel -> new SimpleObjectProperty<Image>(cel.getValue().getFoto()));
            tablaMatricularAlumnosColumnaFotografia.setCellFactory(c -> new ImageTableCell<>());
            tablaMatricularAlumnosColumnaFotografia.setStyle("-fx-alignment: CENTER;");
        }

        //el mensaje de la tabla vacia de alumnos en matricular
        tablaMatricularAlumnos.setPlaceholder(new Label("No hay alumnos que mostrar"));
        //el mensaje de la tabla vacia de cursos en matricular
        tablaMatricularCursos.setPlaceholder(new Label("No hay cursos que mostrar"));
        //el mensaje de la tabla vacia de alumnos en desmatricular
        tablaDesmatricularAlumnos.setPlaceholder(new Label("No hay alumnos que mostrar"));
        //el mensaje de la tabla vacia de cursos en desmatricular
        tablaDesmatricularCursos.setPlaceholder(new Label("No hay cursos que mostrar"));

        //sentencia para aplicar el filtro a la lista de alumnos
        textMatricularFiltrarAlumnos.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarAlumnos(tablaMatricularAlumnos, listaAlumnos, newValue);
        });

        //poder seleccionar varios cursos
        tablaMatricularCursos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //metodo para inicializar la tabla de cursos, mostrando aquellos donde puede matricularse
        tablaMatricularAlumnos.setRowFactory(tableRow -> {
            TableRow<Alumno> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    Alumno alumnosSeleccionado = (Alumno) tableRow.getSelectionModel().getSelectedItem();
                    baseDatos = new AccesoaBD();
                    List<Matricula> matriculas = baseDatos.getMatriculas();
                    List<Curso> cursos = baseDatos.getCursos();
                    Set<Curso> cursosAMostrarSet = new HashSet<>();

                    //bucle para almacenar los cursos en los que el alumno esta matriculado
                    List<Curso> cursosMatriculados = new ArrayList<>();
                    for (int k = 0; k < matriculas.size(); k++) {
                        Matricula matricula = matriculas.get(k);
                        if (alumnosSeleccionado.getDni().equals(matricula.getAlumno().getDni())) {
                            cursosMatriculados.add(matricula.getCurso());
                        }
                    }
                    //comparar los cursos matriculados con los que hay para añadirlos a la lista que se mostrar
                    //la lista con los cursos donde no estan matriculados un alumno

                    for (int k = 0; k < cursos.size(); k++) {
                        if (!cursoDentroArray(cursosMatriculados, cursos.get(k))) {
                            cursosAMostrarSet.add(cursos.get(k));
                        }
                    }

                    listaCursos = FXCollections.observableArrayList(cursosAMostrarSet);
                    tablaMatricularCursos.setItems(listaCursos); //vincular la vista y el modelo
                    //asignar el estilo a las celdas
                    tablaMatricularCursosColumnaCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulodelcurso()));
                    tablaMatricularCursosColumnaProfesor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfesorAsignado()));
                    tablaMatricularColumnaHora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora().toString()));
                }
                if (!textMatricularFiltrarCursos.getText().isEmpty()) {
                    filtrarCursos(tablaMatricularCursos, listaCursos, textMatricularFiltrarCursos.getText());
                }
            });
            return row;
        });

        //sentencia para aplicar el filtro a la lista de cursos
        textMatricularFiltrarCursos.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarCursos(tablaMatricularCursos, listaCursos, newValue);
        });
        //************************
        //**** FIN MATRICULAR ****
        //************************

        //************************
        //**** DESMATRICULAR *****
        //************************
        //listener para que cuando se pulse una celdase activen los botones de eliminar y ver los datos del alumno
        btnDesmatricular.disableProperty().bind(Bindings.equal(-1, tablaDesmatricularCursos.getSelectionModel().selectedIndexProperty()));

        //inicializar tabla alumnos
        baseDatos = new AccesoaBD();
        ArrayList<Alumno> alumnosDesmatricular = (ArrayList<Alumno>) baseDatos.getAlumnos();
        if (alumnosDesmatricular != null) {
            listaAlumnosDesmatricular = FXCollections.observableArrayList(alumnosDesmatricular);
            tablaDesmatricularAlumnos.setItems(listaAlumnosDesmatricular); //vincular la vista y el modelo
            //asignar el estilo a las celdas
            tablaDesmatricularAlumnosColumnaDni.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
            tablaDesmatricularAlumnosColumnaDni.setStyle("-fx-alignment: CENTER;");
            tablaDesmatricularAlumnosColumnaAlumno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
            tablaDesmatricularAlumnosColumnaAlumno.setStyle("-fx-alignment: CENTER;");
            tablaDesmatricularAlumnosColumnaFotografía.setCellValueFactory(cel -> new SimpleObjectProperty<Image>(cel.getValue().getFoto()));
            tablaDesmatricularAlumnosColumnaFotografía.setCellFactory(c -> new ImageTableCell<>());
            tablaDesmatricularAlumnosColumnaFotografía.setStyle("-fx-alignment: CENTER;");
        }

        //sentencia para aplicar el filtro a la lista de alumnos
        textDesmatricularFiltrarAlumnos.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarAlumnos(tablaDesmatricularAlumnos, listaAlumnosDesmatricular, newValue);
        });

        //aplicar el poder seleccionar diferentes filas
        tablaDesmatricularCursos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //metodo para inicializar la tabla de cursos, mostrando aquellos donde puede desmatricularse
        tablaDesmatricularAlumnos.setRowFactory(tableRow -> {
            TableRow<Alumno> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    Alumno alumnosSeleccionado = (Alumno) tableRow.getSelectionModel().getSelectedItem();
                    baseDatos = new AccesoaBD();
                    List<Matricula> matriculas = baseDatos.getMatriculas();
                    List<Curso> cursos = baseDatos.getCursos();

                    //bucle para almacenar los cursos en los que el alumno esta matriculado
                    List<Curso> cursosMatriculados = new ArrayList<>();
                    for (int k = 0; k < matriculas.size(); k++) {
                        Matricula matricula = matriculas.get(k);
                        if (alumnosSeleccionado.getDni().equals(matricula.getAlumno().getDni())) {
                            cursosMatriculados.add(matricula.getCurso());
                        }
                    }

                    listaCursosDesmatricular = FXCollections.observableArrayList(cursosMatriculados);
                    tablaDesmatricularCursos.setItems(listaCursosDesmatricular); //vincular la vista y el modelo
                    //asignar el estilo a las celdas
                    tablaDesmatricularCursosColumnaCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulodelcurso()));
                    tablaDesmatricularCursosColumnaCurso.setStyle("-fx-alignment: CENTER;");
                    tablaDesmatricularCursosColumnaProfesor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfesorAsignado()));
                    tablaDesmatricularCursosColumnaProfesor.setStyle("-fx-alignment: CENTER;");
                    tablaDesmatricularColumnaHora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora().toString()));
                    tablaDesmatricularColumnaHora.setStyle("-fx-alignment: CENTER;");
                }
                if (!textDesmatricularFiltrarCursos.getText().isEmpty()) {
                    filtrarCursos(tablaDesmatricularCursos, listaCursosDesmatricular, textDesmatricularFiltrarCursos.getText());
                }
            });
            return row;
        });

        //sentencia para aplicar el filtro a la lista de cursos
        textDesmatricularFiltrarCursos.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarCursos(tablaDesmatricularCursos, listaCursosDesmatricular, newValue);
        });

    }

    public List<Curso> interseccion(List<Curso> list1, List<Curso> list2) {
        List<Curso> list = new ArrayList<Curso>();

        for (Curso t : list1) {
            if (list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
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
        stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2);
        stage.setY(primaryStage.getY() - 20);
        stage.setResizable(false);
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
        stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2);
        stage.setY(primaryStage.getY() - 20);
        stage.setResizable(false);
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
        stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2);
        stage.setY(primaryStage.getY() - 20);
        stage.setResizable(false);
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
        stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2);
        stage.setY(primaryStage.getY() - 20);
        stage.setResizable(false);
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

        FXMLAcademiaController controllerAcademia = loader.<FXMLAcademiaController>getController();
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

            FXMLAcademiaController controllerAcademia = loader.<FXMLAcademiaController>getController();
            controllerAcademia.initStage(primaryStage);
            Scene scene = new Scene(root);
            primaryStage.setTitle("Academia");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    @FXML
    private void pulsarRatonBtnDesmatricular(MouseEvent event) throws IOException, Exception {
        boolean seleccion = seleccionadasTablasDesmatricular();
        if (seleccion) {
            confirmarDesmatricular();
        }
    }

    @FXML
    private void pulsarTecladoBtnDesmatricular(KeyEvent event) throws IOException, Exception {
        if (event.getCode().equals(KeyCode.ENTER)) {
            boolean seleccion = seleccionadasTablasDesmatricular();
            if (seleccion) {
                confirmarDesmatricular();
            }
        }
    }

    @FXML
    private void pulsarTecladoBtnMatricular(KeyEvent event) throws IOException, Exception {
        if (event.getCode().equals(KeyCode.ENTER)) {
            boolean seleccion = seleccionadasTablasMatricular();
            if (seleccion) {
                matricular();
            }
        }
    }

    @FXML
    private void pulsarRatonBtnMatricular(MouseEvent event) throws IOException, Exception {
        boolean seleccion = seleccionadasTablasMatricular();
        if (seleccion) {
            matricular();
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

    private boolean seleccionadasTablasMatricular() {
        boolean seleccionado = true;
        ObservableList<Alumno> alumnosSeleccionados = tablaMatricularAlumnos.getSelectionModel().getSelectedItems();
        ObservableList<Curso> cursosSeleccionados = tablaMatricularCursos.getSelectionModel().getSelectedItems();
        if (alumnosSeleccionados.size() == 0 || cursosSeleccionados.size() == 0) {
            seleccionado = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selección incorrecta");
            alert.setHeaderText(null);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/icon.png"));
            alert.setContentText("ERROR: Falta seleccionar un alumno.");
            alert.showAndWait();
        }
        return seleccionado;
    }

    private void matricular() throws Exception {
        baseDatos = new AccesoaBD();
        ObservableList<Alumno> alumnosSeleccionados = tablaMatricularAlumnos.getSelectionModel().getSelectedItems();
        ObservableList<Curso> cursosSeleccionados = tablaMatricularCursos.getSelectionModel().getSelectedItems();
        //coger la fecha de matriculacion que sera la del dia actual
        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DATE, 1);
        SimpleDateFormat formatoCalendario = new SimpleDateFormat("yyyy-MM-dd");
        String fechaMatriculacion = formatoCalendario.format(calendario.getTime());
        LocalDateAdapter localDateAdapter = new LocalDateAdapter();
        LocalDate fechaMatriculacionLocal = localDateAdapter.unmarshal(fechaMatriculacion);
        //recorrer ambas listas y crear las matriculas
        for (int i = 0; i < alumnosSeleccionados.size(); i++) {
            Alumno alumno = alumnosSeleccionados.get(i);
            for (int j = 0; j < cursosSeleccionados.size(); j++) {
                Curso curso = cursosSeleccionados.get(j);
                Matricula matricula = new Matricula(fechaMatriculacionLocal, curso, alumno);
                baseDatos.getMatriculas().add(matricula);
            }
        }
        baseDatos.salvar();

        Alumno alumnosSeleccionado = (Alumno) tablaMatricularAlumnos.getSelectionModel().getSelectedItem();
        baseDatos = new AccesoaBD();
        List<Matricula> matriculas = baseDatos.getMatriculas();
        List<Curso> cursos = baseDatos.getCursos();
        Set<Curso> cursosAMostrarSet = new HashSet<>();

        //bucle para almacenar los cursos en los que el alumno esta matriculado
        List<Curso> cursosMatriculados = new ArrayList<>();
        for (int k = 0; k < matriculas.size(); k++) {
            Matricula matricula = matriculas.get(k);
            if (alumnosSeleccionado.getDni().equals(matricula.getAlumno().getDni())) {
                cursosMatriculados.add(matricula.getCurso());
            }
        }
        //comparar los cursos matriculados con los que hay para añadirlos a la lista que se mostrar
        //la lista con los cursos donde no estan matriculados un alumno

        for (int k = 0; k < cursos.size(); k++) {
            if (!cursoDentroArray(cursosMatriculados, cursos.get(k))) {
                cursosAMostrarSet.add(cursos.get(k));
            }
        }

        listaCursos = FXCollections.observableArrayList(cursosAMostrarSet);
        tablaMatricularCursos.setItems(listaCursos); //vincular la vista y el modelo
        //asignar el estilo a las celdas
        tablaMatricularCursosColumnaCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulodelcurso()));
        tablaMatricularCursosColumnaCurso.setStyle("-fx-alignment: CENTER;");
        tablaMatricularCursosColumnaProfesor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfesorAsignado()));
        tablaMatricularCursosColumnaProfesor.setStyle("-fx-alignment: CENTER;");
        tablaMatricularColumnaHora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora().toString()));
        tablaMatricularColumnaHora.setStyle("-fx-alignment: CENTER;");

        lblMatricularModificado.setStyle("-fx-text-fill: green;");
        lblMatricularModificado.setText("Alumno matriculados correctamente");

    }

    private boolean seleccionadasTablasDesmatricular() {
        boolean seleccionado = true;
        ObservableList<Alumno> alumnosSeleccionados = tablaDesmatricularAlumnos.getSelectionModel().getSelectedItems();
        ObservableList<Curso> cursosSeleccionados = tablaDesmatricularCursos.getSelectionModel().getSelectedItems();
        if (alumnosSeleccionados.size() == 0 || cursosSeleccionados.size() == 0) {
            seleccionado = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selección incorrecta");
            alert.setHeaderText(null);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/icon.png"));
            alert.setContentText("ERROR: Falta un elemento por seleccionar.");
            alert.showAndWait();
        }
        return seleccionado;
    }

    private void confirmarDesmatricular() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Desmatricular alumno/s");
        alert.setHeaderText(null);
        alert.setContentText("¿Esta seguro que desea desmatricular al alumno del curso/s de forma permanente?");
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/icon.png"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            desmatricular();
        }

    }

    private void desmatricular() throws Exception {
        baseDatos = new AccesoaBD();
        Alumno alumnoSeleccionado = tablaDesmatricularAlumnos.getSelectionModel().getSelectedItem();
        ObservableList<Curso> cursosSeleccionados = tablaDesmatricularCursos.getSelectionModel().getSelectedItems();

        //eliminar matriculas
        List<Matricula> matriculas = baseDatos.getMatriculas();
        for (int i = 0; i < cursosSeleccionados.size(); i++) {
            boolean encontrada = false;
            Curso curso = cursosSeleccionados.get(i);
            for (int j = 0; !encontrada && j < matriculas.size(); j++) {
                Matricula matricula = matriculas.get(j);
                if (alumnoSeleccionado.getDni().equals(matricula.getAlumno().getDni()) && curso.equals(matricula.getCurso())) {
                    matriculas.remove(j);
                    encontrada = true;
                }
            }
        }

        baseDatos.salvar();

        Alumno alumnosSeleccionado = (Alumno) tablaDesmatricularAlumnos.getSelectionModel().getSelectedItem();
        baseDatos = new AccesoaBD();
        List<Matricula> matriculasActualizadas = baseDatos.getMatriculas();
        List<Curso> cursos = baseDatos.getCursos();

        //bucle para almacenar los cursos en los que el alumno esta matriculado
        List<Curso> cursosMatriculados = new ArrayList<>();
        for (int k = 0; k < matriculasActualizadas.size(); k++) {
            Matricula matricula = matriculasActualizadas.get(k);
            if (alumnosSeleccionado.getDni().equals(matricula.getAlumno().getDni())) {
                cursosMatriculados.add(matricula.getCurso());
            }
        }

        listaCursosDesmatricular = FXCollections.observableArrayList(cursosMatriculados);
        tablaDesmatricularCursos.setItems(listaCursosDesmatricular); //vincular la vista y el modelo
        //asignar el estilo a las celdas
        tablaDesmatricularCursosColumnaCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulodelcurso()));
        tablaDesmatricularCursosColumnaCurso.setStyle("-fx-alignment: CENTER;");
        tablaDesmatricularCursosColumnaProfesor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfesorAsignado()));
        tablaDesmatricularCursosColumnaProfesor.setStyle("-fx-alignment: CENTER;");
        tablaDesmatricularColumnaHora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora().toString()));
        tablaDesmatricularColumnaHora.setStyle("-fx-alignment: CENTER;");

        lblDesmatricularModificado.setStyle("-fx-text-fill: green;");
        lblDesmatricularModificado.setText("Alumno desmatriculados correctamente");

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

    private boolean cursoDentroArray(List<Curso> array, Curso curso) {
        boolean encontrado = false;
        for (int i = 0; !encontrado && i < array.size(); i++) {
            if (array.get(i).equals(curso)) {
                encontrado = true;
            }
        }
        return encontrado;
    }

    private void filtrarAlumnos(TableView<Alumno> tabla, ObservableList<Alumno> lista, String newValue) {
        baseDatos = new AccesoaBD();
        ObservableList<Alumno> alumnosTotal = lista;
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
        lista = FXCollections.observableArrayList(alumnosFiltro);
        tabla.setItems(lista); //vincular la vista y el modelo

    }

    private void filtrarCursos(TableView<Curso> tabla, ObservableList<Curso> lista, String newValue) {
        baseDatos = new AccesoaBD();
        ObservableList<Curso> cursosTotal = lista;
        Set<Curso> cursosFiltro = new HashSet();
        for (int i = 0;cursosTotal != null && i < cursosTotal.size(); i++) {
            Curso curso = cursosTotal.get(i);
            String[] cursoPartesNombre = curso.getTitulodelcurso().split(" ");
            for (int j = 0; j < cursoPartesNombre.length; j++) {
                if (quitarAcentos(cursoPartesNombre[j].toLowerCase()).startsWith(quitarAcentos(newValue.toLowerCase()))) {
                    cursosFiltro.add(curso);
                }
            }
            if (quitarAcentos(curso.getTitulodelcurso().toLowerCase()).startsWith(quitarAcentos(newValue.toLowerCase()))) {
                cursosFiltro.add(curso);
            }
        }
        lista = FXCollections.observableArrayList(cursosFiltro);
        tabla.setItems(lista); //vincular la vista y el modelo

    }

}
