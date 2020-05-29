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
	DataOutputStream toServer;
	DataInputStream fromServer;
	public int index;
	public ArrayList<String> titles = new ArrayList<String>();
	public ArrayList<String> authors = new ArrayList<String>();
	public ArrayList<Integer> indexes = new ArrayList<Integer>();

	@Override 
	public void start(Stage primaryStage) {
		ta.setWrapText(true);
		ta.setEditable(false);
		
		Scene scene = new Scene(new ScrollPane(ta), 545, 185);
		primaryStage.setTitle("My Library"); 
		primaryStage.setScene(scene);
		primaryStage.show();		
		
		new Thread(() -> {
			try {
				ServerSocket serverSocket = new ServerSocket(8000);
				Platform.runLater(() -> ta.appendText("Welcome to my library of classics! \n"));
				
				Socket socket = serverSocket.accept();
				DataInputStream toServer = new DataInputStream(socket.getInputStream());
				DataOutputStream fromServer = new DataOutputStream(socket.getOutputStream());

				while (true) {
					String titleKeyword = toServer.readUTF();
					String authorKeyword = toServer.readUTF();
					System.out.println(titleKeyword + " " + authorKeyword);
					search(titleKeyword, authorKeyword);
					
					if (!indexes.isEmpty()){
						for(int i = 0; i<indexes.size(); i++) {
							index = indexes.get(i);
							for(int j = 0; j< libraryList[index].length; j++){
								String string = libraryList[index][j];
								Platform.runLater(() -> ta.appendText(string + " "));
								fromServer.writeUTF(string + " ");
							}
							Platform.runLater(() -> ta.appendText("\n"));
							fromServer.writeUTF("\n");
							fromServer.flush();	
						}
						Platform.runLater(() -> ta.appendText("-----------------------------------------\n"));
						fromServer.writeUTF("-----------------------------------------\n");
					}
					else {
						Platform.runLater(() -> {
							ta.appendText("There is no match for your search.\n");
							ta.appendText("-----------------------------------------\n");
						});
						
						fromServer.writeUTF("There is no match for your search.\n");
						fromServer.writeUTF("-----------------------------------------\n");
					}
				}
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}).start();
	}

	public  void search(String t, String a){
		indexes.clear();
		String locator = t.toLowerCase();
		String locator2 = a.toLowerCase();
		if (locator != null && locator.equals("all") || locator2 != null && locator2.equals("all")){
			for(int i = 0; i<libraryList.length; i++){
				indexes.add(i);
			}
		}
		else{
			for(int i = 0; i<libraryList.length; i++){
				if (libraryList[i][1].toLowerCase().contains(locator2)){
					indexes.add(i);
				}
				else if(libraryList[i][0].toLowerCase().contains(locator)){
					indexes.add(i);
				}
			}
		}			
	}
	String[][] libraryList = {
		{"''The Odyssey''", "by Homer",}, 
		{"''The Iliad''", "by Homer"},
		{"''To Win Her Heart''", "by Karen Whitemeyer"}, 
		{"''A Bride for Keeps''", "by Melissa Jagears"},
		{"''To Kill a Mockingbird''", "by Harper Lee"},
		{"''Pride and Prejudice''", "by Jane Austin"},
		{"''Emma''", "by Jane Austin"},
		{"''1984''", "by George Orwell"},
		{"''Animal Farm''", "by George Orwell"},
		{"''Jane Eyre''", "by Charlotte Bronte"},
		{"''The Catcher in the Rye''", "by J D Salinger"},
		{"''The Great Gatsby''", "by F Scott Fitzgerald"},
		{"''Little Women''", "by Louisa May Alcott"},
		{"''The Adventures of Huckleberry Finn''", "by Mark Twain"},
		{"''Great Expectations''", "by Charles Dickens"},
		{"''Fahrenheit 451''", "by Ray Bradbury"},
		{"''The Grapes of Wrath''", "by John Steinbeck"},
		{"''Of Mice and Men''", "by John Steinbeck"},
		{"''Catch 22''", "by Joseph Heller"},
		{"''The Scarlet Letter''", "by Nathaniel Hawthorne"},
		{"''Tale of Two Cities''", "by Charles Dickens"},
		{"''War and Peace''", "by Leo Tolstoy"},
		{"''The Secret Garden''", "by Frances Hodgess Burnett"},
		{"''Old Yeller''", "by Fred Gipson"},
		{"''Savage Sam''", "by Fred Gipson"},
	};	
	public static void main(String[] args) {
		launch(args);
	}
}
