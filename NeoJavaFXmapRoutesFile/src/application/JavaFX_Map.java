package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class JavaFX_Map extends Application {
	
	private Scene scene;
	MyBrowser myBrowser;
	
	public JavaFX_Map(){
		myBrowser = new MyBrowser();
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("NeoMapUbication");
		
		try {
			scene = new Scene(myBrowser,640,480);
			//css autogenerado por el proyecto JavaFX editable
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			myBrowser.reload();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	class MyBrowser extends Region{
		HBox toolbar;
		
		WebView webView = new WebView();
		WebEngine webEngine = webView.getEngine();
		
		public MyBrowser(){
			//Obtiene ruta del archivo que traza las coordenadas dentro del mapa
			String desktopPath = desktopPath();
			File f = new File(desktopPath);
			try {
				webEngine.load(f.toURI().toURL().toString());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			getChildren().add(webView);
	}
		public void reload(){
			webEngine.reload();
		}
		
		public String desktopPath(){
			String desktopPath = "";
			try{
				//user.home obtiene la ruta del usuario en turno
				desktopPath = System.getProperty("user.home") + "/Desktop/googlemaps.html";
				desktopPath = desktopPath.replace("\\", "/");
				System.out.println(desktopPath);
			}catch(Exception e){
				e.printStackTrace();
			}
			return desktopPath;
		}
		
	}
	
	
}
