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
import java.time.LocalTime;
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
import modelo.Dias;

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
    private ArrayList<Alumno> listaAlumnos= (ArrayList<Alumno>) acceso.getAlumnos();
    boolean[] arrayBooleans = new boolean[4];
    private Stage primaryStage, emergenteStage;
    private Boolean vengoDeStageConMenu = false;
    
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1);
        gridAnyadirAlumnoSpnEdad.setValueFactory(valueFactory);
        
        gridAnyadirAlumnoSpnEdad.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if (!"-0123456789/n".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        
        gridAnyadirAlumnoTextNombre.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent keyEvent) -> {
            if ("-0123456789/n".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        
    }    
    
    @FXML
    private void pulsarRatonBtnCancelar(MouseEvent event) {
        if (vengoDeStageConMenu) {
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaAlumnos.fxml"));
                Parent root = (Parent) loader.load();

                FXMLListaAlumnosController controllerListaAlumnos = loader.<FXMLListaAlumnosController> getController();
                controllerListaAlumnos.initStage(primaryStage, false);
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();

                Stage stage = (Stage) btnCancelar.getScene().getWindow();
                stage.close();

            } catch (IOException e) {}
        }
    }

    @FXML
    private void pulsarTecladoBtnCancelar(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (vengoDeStageConMenu) {
                Stage stage = (Stage) btnCancelar.getScene().getWindow();
                stage.close();
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaAlumnos.fxml"));
                    Parent root = (Parent) loader.load();

                    FXMLListaAlumnosController controllerListaAlumnos = loader.<FXMLListaAlumnosController> getController();
                    controllerListaAlumnos.initStage(primaryStage, false);
                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                    primaryStage.show();

                    Stage stage = (Stage) btnCancelar.getScene().getWindow();
                    stage.close();

                } catch (IOException e) {}
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

    @FXML
    private void pulsarRatonBtnExaminar(MouseEvent event) {
        examinarImagen();
    }

    @FXML
    private void pulsarTecladoBtnExaminar(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            examinarImagen();
        }
    }
    
    private void comprobarCampos() {
        if (!gridAnyadirAlumnoTextNombre.getText().equals("")) {
            arrayBooleans[0] = true;
        }
        if (!gridAnyadirAlumnoTextDni.getText().equals("") 
                && gridAnyadirAlumnoTextDni.getText().substring(8).matches("[aA-zZ]+$")
                && gridAnyadirAlumnoTextDni.getLength() == 9) {
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
    
    private void ponerBooleansFalse() {
        arrayBooleans[0] = false;
        arrayBooleans[1] = false;
        arrayBooleans[2] = false;
        arrayBooleans[3] = false;
    }
    
    private void comprobarErrores() {
        
        gridAnyadirAlumnoTextNombre.setStyle(null);
        gridAnyadirAlumnoTextDni.setStyle(null);
        gridAnyadirAlumnoTextDireccion.setStyle(null);
        gridAnyadirAlumnoSpnEdad.setStyle(null);
        
        for (int i = 0; i < arrayBooleans.length; i++) {
            if (arrayBooleans[i] == false) {
                switch(i) {
                    case 0:
                        gridAnyadirAlumnoTextNombre.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                        break;
                    case 1:
                        gridAnyadirAlumnoTextDni.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
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
    
    private void examinarImagen() {
        FileChooser fileChooser = new FileChooser(); 
        FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterjpg, extFilterpng);
        File file = fileChooser.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            gridAnyadirAlumnoImgFotografia.setImage(image);
        } catch (Exception ex) {}
    }
    
    private void introducirEnBD() {
        System.out.println(arrayBooleans[0] + " " + arrayBooleans[1] + " " +
                arrayBooleans[2] + " " +  arrayBooleans[3]);
       if (arrayBooleans[0] == true && arrayBooleans[1] == true
            && arrayBooleans[2] == true && arrayBooleans[3] == true) {
           
            Calendar calendar = Calendar.getInstance();
            
            int mes = calendar.get(Calendar.MONTH) + 1;
            String mesString ;
            
            if (mes <= 9) {
                mesString = "0" + String.valueOf(mes);
            } else {
                mesString = String.valueOf(mes);
            }
            
            String fecha = String.valueOf(calendar.get(Calendar.DATE)) + "/" +
                    mesString + "/" +
                    String.valueOf(calendar.get(Calendar.YEAR));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(fecha,formatter);

            Alumno alumno = new Alumno(gridAnyadirAlumnoTextDni.getText(), 
                                    gridAnyadirAlumnoTextNombre.getText(),
                                    gridAnyadirAlumnoSpnEdad.getValueFactory().getValue(),
                                    gridAnyadirAlumnoTextDireccion.getText(),
                                    localDate,
                                    gridAnyadirAlumnoImgFotografia.getImage());

            listaAlumnos.add(alumno);
            acceso.salvar();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLListaAlumnos.fxml"));
                Parent root = (Parent) loader.load();

                FXMLListaAlumnosController controllerListaAlumnos = loader.<FXMLListaAlumnosController> getController();
                controllerListaAlumnos.initStage(primaryStage, true);
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();

                Stage stage = (Stage) btnCancelar.getScene().getWindow();
                stage.close();

            } catch (IOException e) {}
       }
    }
}
