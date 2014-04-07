package application;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
 
public class Main extends Application {
 
    public static void main(String[] args) {
        launch(args);
    }
    
    String coor = "";
    int numOfCoor = 0;
    String desktopPath = "";
     
    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("File Chooser");
        Group root = new Group();
        
         
        Button buttonLoad = new Button("Cargar Archivo");
        buttonLoad.setOnAction(new EventHandler<ActionEvent>(){
 
            @Override
            public void handle(ActionEvent arg0) {
                FileChooser fileChooser = new FileChooser();
                 
                //Filechooser con filtro para archivos con solo archivos txt
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                 
                File file = fileChooser.showOpenDialog(primaryStage);
                if(file != null){
                    coor = readFile(file);
                    //se lee el archivo Template para editarlo y crear el html agregando las coordenadas
                    File html = new File("googlemapsTemplate.html");
            		String code = readFileHtml(html);
            		editFile(code,coor);
            		System.out.println("Cargado");
                    JavaFX_Map map = new JavaFX_Map();
                    map.start(primaryStage);
                }
            }
             
        });
         
        VBox vBox = VBoxBuilder.create()
                .children(buttonLoad)
                .build();
         
        root.getChildren().add(vBox);
        primaryStage.setScene(new Scene(root, 200, 100));
        primaryStage.show();
    }
     
    private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
         
        try {
 
            bufferedReader = new BufferedReader(new FileReader(file));
             
            String text;
            while ((text = bufferedReader.readLine()) != null) {
            	//text es la coordenada leída y se agrega a la siguiente cadena respetando la sintaxis
            	//del html de la API de googlemaps
                stringBuffer.append("new google.maps.LatLng(" + text + ")," + "\n");
                numOfCoor++;
            }
 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
         
        return stringBuffer.toString();
    }
    
    private void editFile(String codeToFile, String coorToPush){
		File openToEdit = new File("googlemaps.html");
		try {
			FileWriter editor = new FileWriter(openToEdit,false);
			//En el template se busca la cadena a modificar para insertar el codigo
			//con las coordenadas correspondientes
			String htmlCode = codeToFile.replace("//CoordenadasStart", coorToPush);
			editor.write(htmlCode);
			editor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    
    private String readFileHtml(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
         
        try {
 
            bufferedReader = new BufferedReader(new FileReader(file));
             
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text + "\n");
            }
 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
         
        return stringBuffer.toString();
    }
}
