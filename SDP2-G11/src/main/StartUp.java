package main;


import java.io.File;
import java.io.IOException;

import domein.DomeinController;
import gui.StartGUI;

public class StartUp {
	
	private DomeinController dc;

	public static void main(String[] args) {
		new StartUp().run();	
	}
	private void run() {
		dc = new DomeinController(true);
		try {
		      File logFile = new File("Logging.txt");
		      if (logFile.createNewFile()) {
		        System.out.println("\nFile created: " + logFile.getName());
		      } else {
		        System.out.println("\nFile already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("\nAn error occurred.");
		      e.printStackTrace();
		    }
		new StartGUI().run();
	}

}
