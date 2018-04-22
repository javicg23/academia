/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import accesoaBD.AccesoaBD;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import modelo.Alumno;

/**
 * FXML Controller class
 *
 * @author V
 */
public class FXMLAnyadirAlumnoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private GridPane gridAnyadirAlumno;
    @FXML
    private ImageView gridAnyadirAlumnoImgFotografia;
    @FXML
    private Label gridAnyadirAlumnoLblNombreCabecera;
    @FXML
    private TextField gridAnyadirAlumnoTextNombre;
    @FXML
    private Label gridAnyadirAlumnoLblDni;
    @FXML
    private TextField gridAnyadirAlumnoTextDni;
    @FXML
    private Label gridAnyadirAlumnoLblDireccion;
    @FXML
    private TextField gridAnyadirAlumnoTextDireccion;
    @FXML
    private Label gridAnyadirAlumnoLblEdad;
    @FXML
    private Spinner<Integer> gridAnyadirAlumnoSpnEdad;
    @FXML
    private Button gridAnyadirAlumnoBtnExaminar;

    private AccesoaBD acceso = new AccesoaBD();
    private ArrayList<Alumno> listaAlumnos = (ArrayList<Alumno>) acceso.getAlumnos();
    private boolean[] arrayBooleans = new boolean[4];
    private Stage primaryStage, emergenteStage;
    private Boolean vengoDeStageConMenu = false, vengoDesdeListaAlumnos = false;
    private Stage stage;

    public void initStage(Stage stageEmergente, Stage stage) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Añadir alumno");
        primaryStage = stage;
    }

    public void initStage(Stage stageEmergente, Stage stage, Boolean conMenu) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Añadir alumno");
        primaryStage = stage;
        vengoDeStageConMenu = conMenu;
    }

    public void initStage(Stage stageEmergente, Stage stage, Boolean conMenu, Boolean vengoDesdeLAlumnos) {
        emergenteStage = stageEmergente;
        emergenteStage.setTitle("Añadir alumno");
        primaryStage = stage;
        vengoDeStageConMenu = conMenu;
        vengoDesdeListaAlumnos = vengoDesdeLAlumnos;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1);
        gridAnyadirAlumnoSpnEdad.setValueFactory(valueFactory);

        gridAnyadirAlumnoSpnEdad.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"0123456789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });

        gridAnyadirAlumnoTextNombre.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if ("0123456789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });

        gridAnyadirAlumnoTextNombre.textProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue.length() > 50) {
                gridAnyadirAlumnoTextNombre.setText(oldValue);
            }
        }
        );

        gridAnyadirAlumnoTextDni.textProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue.length() > 9) {
                gridAnyadirAlumnoTextDni.setText(oldValue);
            }
        }
        );

        gridAnyadirAlumnoTextDireccion.textProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue.length() > 75) {
                gridAnyadirAlumnoTextDireccion.setText(oldValue);
            }
        }
        );

        gridAnyadirAlumnoSpnEdad.getEditor().textProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue.length() > 2) {
                gridAnyadirAlumnoSpnEdad.getEditor().setText(oldValue);
            }
        }
        );
    }
    //metodo que se ejecuta al pulsar con el raton en cancelar
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
    //metodo que se ejecuta al pulsar con el teclado en cancelar
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
    //metodo que se ejecutar al pulsar con el raton en guardar
    @FXML
    private void pulsarRatonBtnGuardar(MouseEvent event) {
        ponerBooleansFalse();
        comprobarCampos();
        introducirEnBD();
    }
    //metodo que se ejecuta al pulsar con el teclado en guardar
    @FXML
    private void pulsarTecladoBtnGuardar(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            ponerBooleansFalse();
            comprobarCampos();
            introducirEnBD();
        }
    }
    //metodo que se ejecuta al pulsar con el raton en examinar
    @FXML
    private void pulsarRatonBtnExaminar(MouseEvent event) {
        examinarImagen();
    }
    //metodo que se ejecutar al pulsar con el teclado en examinar
    @FXML
    private void pulsarTecladoBtnExaminar(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            examinarImagen();
        }
    }
    //metodo que sirve para comprobar si todos los campos introducidos estan correctos
    private void comprobarCampos() {
        if (!gridAnyadirAlumnoTextNombre.getText().equals("")) {
            arrayBooleans[0] = true;
        }
        if (!gridAnyadirAlumnoTextDni.getText().equals("")
                && gridAnyadirAlumnoTextDni.getLength() == 9
                && gridAnyadirAlumnoTextDni.getText().substring(0, 8).matches("\\d*")
                && gridAnyadirAlumnoTextDni.getText().substring(8).matches("[aA-zZ]+$")) {
            arrayBooleans[1] = true;
        }
        if (!gridAnyadirAlumnoTextDireccion.getText().equals("")) {
            arrayBooleans[2] = true;
        }
        if (gridAnyadirAlumnoSpnEdad.getValueFactory().getValue() != 0) {
            arrayBooleans[3] = true;
        }
        comprobarErrores();
    }
    //metodo para poner todos los booleans del array que sirve para comprobar a false
    private void ponerBooleansFalse() {
        arrayBooleans[0] = false;
        arrayBooleans[1] = false;
        arrayBooleans[2] = false;
        arrayBooleans[3] = false;
    }
    //metodo que sirve para que los campos erroneos se cambien de estilo
    private void comprobarErrores() {

        gridAnyadirAlumnoTextNombre.setStyle(null);
        gridAnyadirAlumnoTextDni.setStyle(null);
        gridAnyadirAlumnoTextDireccion.setStyle(null);
        gridAnyadirAlumnoSpnEdad.setStyle(null);

        for (int i = 0; i < arrayBooleans.length; i++) {
            if (arrayBooleans[i] == false) {
                switch (i) {
                    case 0:
                        gridAnyadirAlumnoTextNombre.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                        break;
                    case 1:
                        gridAnyadirAlumnoTextDni.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                        gridAnyadirAlumnoTextDni.setText("");
                        break;
                    case 2:
                        gridAnyadirAlumnoTextDireccion.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                        break;
                    case 3:
                        gridAnyadirAlumnoSpnEdad.setStyle("-fx-inner-border: red; -fx-focus-color: red;");
                        break;
                }
            }
        }
    }
    //metodo que sirve para poder seleccionar una imagen del pc
    private void examinarImagen() {
        FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().addAll(extFilterpng, extFilterjpg);
        File file = fileChooser.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            gridAnyadirAlumnoImgFotografia.setImage(image);
        } catch (Exception ex) {
        }
    }
    //metodo que sirve para introducir en la base de datos el alumno
    private void introducirEnBD() {
        if (arrayBooleans[0] == true && arrayBooleans[1] == true
                && arrayBooleans[2] == true && arrayBooleans[3] == true) {

            Calendar calendar = Calendar.getInstance();

            int mes = calendar.get(Calendar.MONTH) + 1;
            String mesString;

            if (mes <= 9) {
                mesString = "0" + String.valueOf(mes);
            } else {
                mesString = String.valueOf(mes);
            }

            String fecha = String.valueOf(calendar.get(Calendar.DATE)) + "/"
                    + mesString + "/"
                    + String.valueOf(calendar.get(Calendar.YEAR));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(fecha, formatter);

            String nombreArreglado = gridAnyadirAlumnoTextNombre.getText().trim().replaceAll(" +", " ");
            String direccionArreglado = gridAnyadirAlumnoTextDireccion.getText().trim().replaceAll(" +", " ");
            String dniArreglado = gridAnyadirAlumnoTextDni.getText().substring(0, 8) + gridAnyadirAlumnoTextDni.getText().substring(8).toUpperCase();

            Alumno alumno = new Alumno(dniArreglado,
                    nombreArreglado,
                    gridAnyadirAlumnoSpnEdad.getValueFactory().getValue(),
                    direccionArreglado,
                    localDate,
                    gridAnyadirAlumnoImgFotografia.getImage());

            boolean alumnoExistente = false;
            //bucle que salta si el alumno que hemos introducido ya existe con el mismo dni y no lo inserta
            for (Alumno alumnoLista : listaAlumnos) {
                if (alumnoLista.getDni().equals(alumno.getDni())) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Alumno existente");
                    alert.setHeaderText(null);
                    ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/icon.png"));
                    alert.setContentText("ERROR: El alumno que ha introducido ya existe.");
                    alumnoExistente = true;
                    alert.showAndWait();
                    break;
                }
            }
            //si no existe lo añade a la bd y lanza la ventana de listado alumnos
            if (!alumnoExistente) {
                listaAlumnos.add(alumno);
                acceso.salvar();

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaAlumnos.fxml"));
                    Parent root = (Parent) loader.load();

                    FXMLListaAlumnosController controllerListaAlumnos = loader.<FXMLListaAlumnosController>getController();
                    controllerListaAlumnos.initStage(primaryStage, true);
                    Scene scene = new Scene(root, primaryStage.getWidth() - 16, primaryStage.getHeight() - 39);
                    primaryStage.setScene(scene);
                    primaryStage.show();

                    stage = (Stage) btnCancelar.getScene().getWindow();
                    stage.close();

                } catch (IOException e) {
                }
            }
        }
    }
    //metodo que manda a lista alumnos con boolean a false indicando que no se ha insertado nada
    private void voyListaAlumnosFalseBoolean() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaAlumnos.fxml"));
        Parent root = (Parent) loader.load();

        FXMLListaAlumnosController controllerListaAlumnos = loader.<FXMLListaAlumnosController>getController();
        controllerListaAlumnos.initStage(primaryStage, false);
        Scene scene = new Scene(root, primaryStage.getWidth() - 16, primaryStage.getHeight() - 39);
        primaryStage.setScene(scene);
        primaryStage.show();

        stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}
