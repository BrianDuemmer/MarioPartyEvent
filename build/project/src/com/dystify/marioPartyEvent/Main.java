package com.dystify.marioPartyEvent;
	
import java.io.IOException;

import com.dystify.marioPartyEvent.util.Util;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.fxml.FXMLLoader;


public class Main extends Application 
{
	public static Font mp_font;
	public static int chatVoteMillis = 20000;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// load dependencies
			mp_font = Font.loadFont(Util.loadFile("/common/mp_font.ttf").toExternalForm(), 10);
			
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setOnCloseRequest((WindowEvent e) -> { System.exit(0); });
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		launch(args);
	}
}
