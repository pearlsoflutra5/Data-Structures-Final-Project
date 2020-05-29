/*
Author: Kacie Rae
Date: 5-13-20
Description: Client which connects to Server. 
*/

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.input.*;
import javafx.event.*;


public class finalClient extends Application {
	private TextField ltitle = new TextField();
	private TextField lauthor = new TextField();
	private Button btSearch= new Button("Search");
	DataOutputStream toServer;
	DataInputStream fromServer;
	private TextArea ta = new TextArea();
	
	@Override
	public void start(Stage primaryStage) {
		ta.setWrapText(true);
		ta.setEditable(false);
		
		GridPane gridPane = new GridPane();
		gridPane.add(new Label("Type 'all' to see the whole library."), 1, 0);
		gridPane.add(new Label("Title"), 0, 1);
		gridPane.add(new Label("Author"), 0, 2);
		gridPane.add(ltitle, 1, 1);
		gridPane.add(lauthor, 1, 2);
		gridPane.add(btSearch, 2, 2);
		
		ltitle.setAlignment(Pos.BASELINE_LEFT);
		lauthor.setAlignment(Pos.BASELINE_LEFT);
		ltitle.setPrefColumnCount(10);
		lauthor.setPrefColumnCount(10);
						
		BorderPane pane = new BorderPane();
		pane.setCenter(new BorderPane(ta));
		pane.setTop(gridPane);
		
		Scene scene = new Scene(pane, 545, 240);
		primaryStage.setTitle("Client Search Bar"); 
		primaryStage.setScene(scene); 
		primaryStage.show(); 
		
		try {
			Socket socket = new Socket("localhost", 8000);
			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());
			new Thread(() -> {
				try {
					while (true) { 
						String serverText = fromServer.readUTF();
						Platform.runLater(() -> ta.appendText(serverText));	
					}        
				} 
				catch (IOException ex) {
						ex.printStackTrace();
				}
			}).start();

		}
		catch (IOException ex) {
			ta.appendText(ex.toString() + '\n');
		}
			
		btSearch.setOnAction(e -> {
			try {
				String title = ltitle.getText().trim();
				String author = lauthor.getText().trim();
				if (title != null && author != null){
					toServer.writeUTF(title);
					toServer.writeUTF(author);
					toServer.flush();
					ltitle.setText("");
					lauthor.setText("");
				}			
			} 
			catch (IOException ex) {
				System.err.println(ex);
			}
		});
		gridPane.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				try {
					String title = ltitle.getText().trim();
					String author = lauthor.getText().trim();
					if (title != null && author != null){
						toServer.writeUTF(title);
						toServer.writeUTF(author);
						toServer.flush();
						ltitle.setText("");
						lauthor.setText("");
					}
					
				} 
				catch (IOException ex) {
					System.err.println(ex);
				}			
			}
		});


	}
	public static void main(String[] args) {
		launch(args);
	}
}
