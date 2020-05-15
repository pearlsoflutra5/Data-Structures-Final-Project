/*
Author: Kacie Rae
Date: 5-13-20
Description: Server which holds book information to give to Client. 
*/

import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;

public class finalServer extends Application {
	private TextArea ta = new TextArea();

	@Override 
	public void start(Stage primaryStage) {
		ta.setWrapText(true);
		//ta.setDisable(true);
		
		Scene scene = new Scene(new ScrollPane(ta), 545, 185);
		primaryStage.setTitle("My Library"); 
		primaryStage.setScene(scene);
		primaryStage.show();
		
		new Thread(() -> {
			try {
				ServerSocket serverSocket = new ServerSocket(8000);
				Platform.runLater(() -> ta.appendText("Welcome to my library! \n"));
				Socket socket = serverSocket.accept();
				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
				
				while (true) {
					String title = inputFromClient.readUTF();
					String author = inputFromClient.readUTF();
					Platform.runLater(() -> {
		
					});
				}
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}).start();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
