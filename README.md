# Data Structures and Algorithms Project

## Synopsis
This program provides an online library search. A Client is able to search a book, author, or view the entire library. A Server holds all of the data and connects with the Client to display books. 

## Motivation
I built this program to combine several techniques I have learned. I also wanted to show to myself that I could build this program and have it run correctly. 

## How to Run
The Server must be runnning and connected to the Client file. To search something, a title, author, or both may be typed into the correct locations. To send the data to the Server the user can push the search button or press the enter key on their keyboard. 
[Server Sreenshot]
<img src="/images/server.png"> 

[Client Screenshot]
<img src="/images/client.png">

[Example of Program Running]
<img src="/images/running.png">



## Code Example
```
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
			if(libraryList[i][0].toLowerCase().equals("''" + locator + "''")){
				indexes.add(i);
			}
			else if (libraryList[i][1].toLowerCase().equals("by " + locator2)){
				indexes.add(i);
			}
		}
	}			
}
```
This part of the code is in the Server. It is the search method for finding the results of the search. I really appreciate how it is able to be a small portion of code that is vital to the whole program.
