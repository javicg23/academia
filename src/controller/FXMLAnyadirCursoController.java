/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modelo.Curso;
import modelo.Dias;

/**
 * FXML Controller class
 *
 * @author V
 */
public class FXMLAnyadirCursoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private GridPane gridAnyadirCurso;
    @FXML
    private Label gridAnyadirCursoLblTitulo;
    @FXML
    private TextField gridAnyadirCursoTextTitulo;
    @FXML
    private Label gridAnyadirCursoLblProfesor;
    @FXML
    private TextField gridAnyadirCursoTextProfesor;
    @FXML
    private Label gridAnyadirCursoLblNMax;
    @FXML
    private Spinner<Integer> gridAnyadirCursoSpnNMax;
    @FXML
    private Label gridAnyadirCursoLblHora;
    @FXML
    private TextField gridAnyadirCursoTextHora;
    @FXML
    private Label gridAnyadirCursoLblInicio;
    @FXML
    private DatePicker gridAnyadirCursoDateInicio;
    @FXML
    private Label gridAnyadirCursoLblFin;
    @FXML
    private DatePicker gridAnyadirCursoDateFin;
    @FXML
    private Label gridAnyadirCursoLblAula;
    @FXML
    private ComboBox<String> gridAnyadirCursoCmbAula;
    @FXML
    private Label gridAnyadirCursoLblDias;
    @FXML
    private CheckBox gridAnyadirCursoCheckLunes;
    @FXML
    private CheckBox gridAnyadirCursoCheckMartes;
    @FXML
    private CheckBox gridAnyadirCursoCheckMiercoles;
    @FXML
    private CheckBox gridAnyadirCursoCheckJueves;
    @FXML
    private CheckBox gridAnyadirCursoCheckViernes;

    private AccesoaBD acceso = new AccesoaBD();
    private ArrayList<Curso> listaCursos = (ArrayList<Curso>) acceso.getCursos();
    private boolean[] arrayBooleans = new boolean[8];
    private Stage primaryStage, emergenteStage;
    private Boolean vengoDeStageConMenu = false, vengoDesdeListaCursos = false;
    private Stage stage;

    public void initStage(Stage stageEmergente, Stage stage) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Añadir curso");
        primaryStage = stage;
    }

    public void initStage(Stage stageEmergente, Stage stage, Boolean conMenu) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Añadir curso");
        primaryStage = stage;
        vengoDeStageConMenu = conMenu;
    }

    public void initStage(Stage stageEmergente, Stage stage, Boolean conMenu, Boolean vengoDesdeLCursos) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Añadir curso");
        primaryStage = stage;
        vengoDeStageConMenu = conMenu;
        vengoDesdeListaCursos = vengoDesdeLCursos;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        gridAnyadirCursoDateInicio.setEditable(false);
        gridAnyadirCursoDateFin.setEditable(false);

        gridAnyadirCursoTextProfesor.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if ("0123456789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });

        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 1);
        gridAnyadirCursoSpnNMax.setValueFactory(valueFactory);

        gridAnyadirCursoSpnNMax.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"0123456789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });

        gridAnyadirCursoTextHora.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"0123456789:".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });

        ObservableList<String> options = FXCollections.observableArrayList("1G 0.1",
                "1G 0.2", "1G 0.3", "1G 0.4", "1G 1.0", "1G 1.1", "1G 1.2", "1G 1.3",
                "1G 1.4", "1G 2.0", "1G 2.1", "1G 2.2", "1G 2.3", "1G 2.4");
        gridAnyadirCursoCmbAula.setItems(options);
        gridAnyadirCursoCmbAula.setVisibleRowCount(4);

        gridAnyadirCursoTextTitulo.textProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue.length() > 50) {
                gridAnyadirCursoTextTitulo.setText(oldValue);
            }
        }
        );

        gridAnyadirCursoTextProfesor.textProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue.length() > 50) {
                gridAnyadirCursoTextProfesor.setText(oldValue);
            }
        }
        );

        gridAnyadirCursoSpnNMax.getEditor().textProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue.length() > 3) {
                gridAnyadirCursoSpnNMax.getEditor().setText(oldValue);
            }
        }
        );

        gridAnyadirCursoTextHora.textProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue.length() > 5) {
                gridAnyadirCursoTextHora.setText(oldValue);
            }
        }
        );

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
    private void pulsarRatonBtnGuardar(MouseEvent event) {
        ponerBooleansFalse();
        comprobarCampos();
        introducirEnBD();
    }

    @FXML
    private void pulsarTecladoBtnGuardar(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            ponerBooleansFalse();
            comprobarCampos();
            introducirEnBD();
        }
    }

    private void comprobarCampos() {

        if (!gridAnyadirCursoTextTitulo.getText().equals("")) {
            arrayBooleans[0] = true;
        }
        if (!gridAnyadirCursoTextProfesor.getText().equals("")) {
            arrayBooleans[1] = true;
        }
        if (gridAnyadirCursoSpnNMax.getValueFactory().getValue() != 0) {
            arrayBooleans[2] = true;
        }
        if (gridAnyadirCursoTextHora.getText().length() == 5
                && gridAnyadirCursoTextHora.getText().charAt(2) == ':'
                && Integer.parseInt(gridAnyadirCursoTextHora.getText().substring(0, 2)) <= 24
                && Integer.parseInt(gridAnyadirCursoTextHora.getText().substring(3, 5)) <= 59) {
            arrayBooleans[3] = true;
        }
        if (gridAnyadirCursoDateInicio.getValue() != null
                && gridAnyadirCursoDateInicio.getValue().compareTo(gridAnyadirCursoDateFin.getValue()) <= 0) {
            arrayBooleans[4] = true;
        }
        if (gridAnyadirCursoDateFin.getValue() != null
                && gridAnyadirCursoDateFin.getValue().compareTo(gridAnyadirCursoDateInicio.getValue()) >= 0) {
            arrayBooleans[5] = true;
        }
        if (gridAnyadirCursoCmbAula.getValue() != null) {
            arrayBooleans[6] = true;
        }
        if (gridAnyadirCursoCheckLunes.isSelected()
                || gridAnyadirCursoCheckMartes.isSelected()
                || gridAnyadirCursoCheckMartes.isSelected()
                || gridAnyadirCursoCheckMiercoles.isSelected()
                || gridAnyadirCursoCheckJueves.isSelected()
                || gridAnyadirCursoCheckViernes.isSelected()) {
            arrayBooleans[7] = true;
        }

        comprobarErrores();
    }

    private void ponerBooleansFalse() {
        arrayBooleans[0] = false;
        arrayBooleans[1] = false;
        arrayBooleans[2] = false;
        arrayBooleans[3] = false;
        arrayBooleans[4] = false;
        arrayBooleans[5] = false;
        arrayBooleans[6] = false;
        arrayBooleans[7] = false;
    }

    private void comprobarErrores() {

        gridAnyadirCursoTextTitulo.setStyle(null);
        gridAnyadirCursoTextProfesor.setStyle(null);
        gridAnyadirCursoSpnNMax.setStyle(null);
        gridAnyadirCursoTextHora.setStyle(null);
        gridAnyadirCursoDateInicio.setStyle(null);
        gridAnyadirCursoDateFin.setStyle(null);
        gridAnyadirCursoCmbAula.setStyle(null);
        gridAnyadirCursoCheckLunes.setStyle(null);
        gridAnyadirCursoCheckMartes.setStyle(null);
        gridAnyadirCursoCheckMiercoles.setStyle(null);
        gridAnyadirCursoCheckJueves.setStyle(null);
        gridAnyadirCursoCheckViernes.setStyle(null);

        for (int i = 0; i < arrayBooleans.length; i++) {
            if (arrayBooleans[i] == false) {
                switch (i) {
                    case 0:
                        gridAnyadirCursoTextTitulo.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                        break;
                    case 1:
                        gridAnyadirCursoTextProfesor.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                        break;
                    case 2:
                        gridAnyadirCursoSpnNMax.setStyle("-fx-inner-border: red; -fx-focus-color: red;");
                        break;
                    case 3:
                        gridAnyadirCursoTextHora.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                        break;
                    case 4:
                        gridAnyadirCursoDateInicio.setStyle("-fx-background-color: red; -fx-focus-color: red; ");
                        break;
                    case 5:
                        gridAnyadirCursoDateFin.setStyle("-fx-background-color: red; -fx-focus-color: red;");
                        break;
                    case 6:
                        gridAnyadirCursoCmbAula.setStyle("-fx-inner-border: red; -fx-focus-color: red;");
                        break;
                    case 7:
                        gridAnyadirCursoCheckLunes.setStyle("-fx-text-fill: red;");
                        gridAnyadirCursoCheckMartes.setStyle("-fx-text-fill: red;");
                        gridAnyadirCursoCheckMiercoles.setStyle("-fx-text-fill: red;");
                        gridAnyadirCursoCheckJueves.setStyle("-fx-text-fill: red;");
                        gridAnyadirCursoCheckViernes.setStyle("-fx-text-fill: red;");
                }
            }
        }
    }

    private void introducirEnBD() {
        if (arrayBooleans[0] == true && arrayBooleans[1] == true
                && arrayBooleans[2] == true && arrayBooleans[3] == true
                && arrayBooleans[4] == true && arrayBooleans[5] == true
                && arrayBooleans[6] == true && arrayBooleans[7] == true) {

            ArrayList<Dias> dias = new ArrayList<>();
            if (gridAnyadirCursoCheckLunes.isSelected()) {
                dias.add(Dias.Lunes);
            }
            if (gridAnyadirCursoCheckMartes.isSelected()) {
                dias.add(Dias.Martes);
            }
            if (gridAnyadirCursoCheckMiercoles.isSelected()) {
                dias.add(Dias.Miercoles);
            }
            if (gridAnyadirCursoCheckJueves.isSelected()) {
                dias.add(Dias.Jueves);
            }
            if (gridAnyadirCursoCheckViernes.isSelected()) {
                dias.add(Dias.Viernes);
            }

            String tituloArreglado = gridAnyadirCursoTextTitulo.getText().trim().replaceAll(" +", " ");
            String profesorArreglado = gridAnyadirCursoTextProfesor.getText().trim().replaceAll(" +", " ");

            Curso curso = new Curso(tituloArreglado,
                    profesorArreglado,
                    gridAnyadirCursoSpnNMax.getValueFactory().getValue(),
                    gridAnyadirCursoDateInicio.getValue(),
                    gridAnyadirCursoDateFin.getValue(),
                    LocalTime.parse(gridAnyadirCursoTextHora.getText()),
                    dias, gridAnyadirCursoCmbAula.getValue());

            boolean cursoExistente = false;

            for (Curso cursoLista : listaCursos) {
                if (cursoLista.equals(curso)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Curso existente");
                    alert.setHeaderText(null);
                    ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/icon.png"));
                    alert.setContentText("ERROR: El curso que ha introducido ya existe.");
                    cursoExistente = true;
                    alert.showAndWait();
                    break;
                }
            }

            if (!cursoExistente) {
                listaCursos.add(curso);
                acceso.salvar();

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaCursos.fxml"));
                    Parent root = (Parent) loader.load();

                    FXMLListaCursosController controllerListaCursos = loader.<FXMLListaCursosController>getController();
                    controllerListaCursos.initStage(primaryStage, true);
                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                    primaryStage.show();

                    stage = (Stage) btnCancelar.getScene().getWindow();
                    stage.close();

                } catch (IOException e) {
                }
            }
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
}
