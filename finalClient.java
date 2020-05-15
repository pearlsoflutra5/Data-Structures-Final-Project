/*
Author: Kacie Rae
Date: 5-13-20
Description: Client which connects to Server. 
*/

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;

public class finalClient extends Application {
	private TextField ltitle = new TextField();
	private TextField lauthor = new TextField();
	private TextField lgenre = new TextField();
	private Button btSearch= new Button("Search");
	DataOutputStream toServer = null;
	DataInputStream fromServer = null;

	
	private TextArea ta = new TextArea();
	
	@Override
	public void start(Stage primaryStage) {
		ta.setWrapText(true);
		
		GridPane gridPane = new GridPane();
		gridPane.add(new Label("Title"), 0, 0);
		gridPane.add(new Label("Author  "), 0, 1);
		//gridPane.add(new Label("Genre"), 0, 2);
		gridPane.add(ltitle, 1, 0);
		gridPane.add(lauthor, 1, 1);
		//gridPane.add(lgenre, 1, 2);
		gridPane.add(btSearch, 2, 1);
		
		ltitle.setAlignment(Pos.BASELINE_LEFT);
		lauthor.setAlignment(Pos.BASELINE_LEFT);
		//lgenre.setAlignment(Pos.BASELINE_LEFT);
		
		ltitle.setPrefColumnCount(10);
		lauthor.setPrefColumnCount(10);
		//lgenre.setPrefColumnCount(10);
						
		BorderPane pane = new BorderPane();
		pane.setCenter(new ScrollPane(ta));
		pane.setTop(gridPane);
		
		
		Scene scene = new Scene(pane, 545, 240);
		primaryStage.setTitle("Client Search Bar"); 
		primaryStage.setScene(scene); 
		primaryStage.show(); 
				
		btSearch.setOnAction(e -> {
			try {
				String title = ltitle.getText().trim();
				String author = lauthor.getText().trim();
				//String genre = lgenre.getText().trim();
				toServer.writeUTF(title);
				toServer.writeUTF(author);
				toServer.flush();
			} 
			catch (IOException ex) {
				System.err.println(ex);
			}

		});

		try {
			Socket socket = new Socket("localhost", 8000);
			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());
		}
		catch (IOException ex) {
			ta.appendText(ex.toString() + '\n');
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
