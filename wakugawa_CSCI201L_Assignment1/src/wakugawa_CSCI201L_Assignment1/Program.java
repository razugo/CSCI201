package wakugawa_CSCI201L_Assignment1;

import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import wakugawa_CSCI201L_Assignment1.Students.student;

public class Program {
	public static void main(String[] args) throws IOException{
		Scanner scan = new Scanner(System.in);
		ArrayList<student> students = new ArrayList<student>();
		String filename = openList(students, scan);
		int option = 0;
		System.out.println();
		
		while(option != 7) {
			displayMenu();
			try {
				System.out.print("Input number: ");
				option = Integer.parseInt(scan.nextLine());
				System.out.println();
				switch(option) {
					case 1:
						displayRoster(students);
						break;
					case 2:
						addStudent(students, scan);
						break;
					case 3:
						removeStudent(students, scan);
						break;
					case 4:
						addGrade(students, scan);
						break;
					case 5:
						sortRoster(students, scan);
						break;
					case 6:
						writeFile(filename, students);
						break;
					case 7:
						exit(filename, students, scan);
						break;
					default:
				}
				
			}
			catch(NumberFormatException e) {
				System.out.println("Invalid input. Returning to menu.");
			}
			catch(Exception e) {
				System.out.println("Unexpected error. Returning to menu.");
			}
			System.out.println();
		}
		
		scan.close();
	}
	
	public static void displayMenu() {
		System.out.println("Operation Menu");
		System.out.println("\t1) Display Roster");
		System.out.println("\t2) Add Student");
		System.out.println("\t3) Remove Student");
		System.out.println("\t4) Add Grade");
		System.out.println("\t5) Sort Roster");
		System.out.println("\t6) Write File");
		System.out.println("\t7) Exit");
	}
	
	//---------------------MENU FUNCTIONS--------------------
	
	//how should the scores be displayed?
	//I store them as doubles and they could be long like 12.12345678
	//should they be cut off and rounded the nearest hundred?
	public static void displayRoster(ArrayList<student> studs) {
		for(student temp : studs)
		{
			System.out.println(temp);
		}
	}
	
	//should we do error checking?
	//if input is more than 2 words
	public static void addStudent(ArrayList<student> studs, Scanner scan) {
		String newName = null;
		String[] name = {};
		System.out.print("What is the studnt's name? ");
		newName = scan.nextLine();
		name = newName.split(" ");
		if(name.length != 2) {
			System.out.println("Invalid, must have first and last name. Returning to menu.");
			return;
		}
		System.out.println(newName);
		studs.add(new student(name[0], name[1], 0, 0));
	}
	
	public static void removeStudent(ArrayList<student> studs, Scanner scan) {
		int response;
		student removed;
		System.out.println("Please choose a student to remove");
		for(int i = 0; i < studs.size(); i++) {
			System.out.println("\t" + (i + 1) + ") " + studs.get(i).printName());
		}
		try {
			response = Integer.parseInt(scan.nextLine()) - 1;
			removed = studs.get(response);
			System.out.println("Removed: " + removed.printName());
		}
		catch(NumberFormatException e) {
			System.out.println("Invalid input. Returning to menu.");
		}
		catch(IndexOutOfBoundsException e) {
			System.out.println("Number out of bounds. Returning to menu.");
		}
	}
	
	//what happens if an input is out of bounds?
	//invalid inputs? letters rather than numbers
	public static void addGrade(ArrayList<student> studs, Scanner scan) {
		student mod;
		int response;
		double score;
		for(int i = 0; i < studs.size(); i++) {
			System.out.println("\t" + (i + 1) + ") " + studs.get(i).printName());
		}
		
		try {
			System.out.print("Please choose a student to grade: ");
			response = Integer.parseInt(scan.nextLine()) - 1;
			mod = studs.get(response);
			
			System.out.print("Please ender a new score: ");
			score = Double.parseDouble(scan.nextLine());
			
			mod.addGrade(score);
		}
		catch(NumberFormatException e) {
			System.out.println("Invalid input. Returning to menu");
		}
		catch(IndexOutOfBoundsException e) {
			System.out.println("Number out of bounds. Returning to menu.");
		}
		
	}
	
	//For sorting GPA is is ascending or descending order?
	public static void sortRoster(ArrayList<student> studs, Scanner scan) {
		int response;
		System.out.println("\t1) Alphabetically");
		System.out.println("\t2) GPA");
		
		System.out.print("How would you like to sort the roster? ");
		try {
			response = Integer.parseInt(scan.nextLine());
			if(response == 1) {
				Collections.sort(studs, student.getCompareName());
			}
			else if(response == 2) {
				Collections.sort(studs, student.getCompareAverage());
			}
			else {
				System.out.println("Number out of bounds. Returning to menu.");
			}
			displayRoster(studs);
		}
		catch(NumberFormatException e) {
			System.out.println("Invalid response. Returning to menu.");
		}
		catch(Exception e) {
			System.out.println("Unexpected Error. Returning to menu.");
		}
		
	}
	
	public static void writeFile(String filename, ArrayList<student> studs) {
		try {
			System.out.println("Writing to: " + filename);
			Students temp = new Students(studs);
			Writer writer = new FileWriter(filename);
			Gson gson =  new GsonBuilder().create();
			String jsonString = gson.toJson(temp);
			writer.write(jsonString);
            writer.close();
            System.out.println("Successfuly saved to: " + filename);
		}
		catch(Exception e)
		{
			System.out.println("Unable to write to file");
		}
	}
	
	public static void exit(String filename, ArrayList<student> studs, Scanner scan) {
		String response;
		System.out.println("About to exit.");
		System.out.print("Would you like to save to \"" + filename + "\"? (y/n) ");
		try {
			response = scan.nextLine();
			if(response.equals("Y") || response.equals("y")) {
				writeFile(filename, studs);
			}
		}
		catch(Exception e) {
			System.out.println("Unexpected error. Exiting program.");
		}
	}
	
	public static String openList(ArrayList<student> inputStuds, Scanner scan)
	{
		Gson gson = new Gson();
		FileReader fRead;
		JsonReader jReader;
		Students allStuds;
		String response;
		ArrayList<student> studs;
		while(true) {
			try {
				System.out.print("What is the name of the input file? ");
				response = scan.nextLine();
				fRead = new FileReader(response);
				jReader = new JsonReader(fRead);
				allStuds = gson.fromJson(jReader, Students.class);
				studs = allStuds.getList();
				fRead.close();
				break;
			}
			catch(FileNotFoundException e) {
				System.out.println("That file could not be found.");
			}
			catch(JsonSyntaxException e) {
				System.out.println("That file is not a well-formed JSON file.");
			}
			catch(Exception e) {
				System.out.println("Unexpected Error. Please input another file.");
			}
			
		}
		
		for(int i = 0; i < studs.size(); i++) {
			inputStuds.add(studs.get(i));
		}
		
		return response;
	}
}
