package controlador;

/**
 *
 * @author Daniel Migales Puertas
 *
 */
//
//Ejercicio2. Hospital
//Vamos a crear un programa de control de análisis clínicos, en concreto de insulina. 
//Se trata de guardar los datos de análisis de una persona ( Nombre, apellidos, numero_de_analisis, fecha, nivel de insulina)
//Las personas se irán haciendo análisis semanales de insulina, si todo esta correcto no sucederá nada, simplemente se guardaran los datos del ultimo análisis hecho.
//En el momento que una persona tenga una variación de insulina +-10, con respecto a su último análisis de insulina , significa que algo sucede, por lo que se enviará un mensaje al móvil del paciente avisándolo. ( En nuestro caso mostrará un mensaje por el terminal)
//En dicho mensaje, se pondrá en nombre del paciente, la fecha, y la variación detectada.
//Decide como acceder y guardar a los datos de este programa.
//Crea el programa utilizando programación a eventos encapsulados en JavaBeans
//
//
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/vista/UI.fxml"));
        primaryStage.setTitle("Hospital General IFP");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
