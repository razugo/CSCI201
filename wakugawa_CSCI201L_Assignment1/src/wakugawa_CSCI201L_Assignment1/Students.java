package wakugawa_CSCI201L_Assignment1;

import java.util.ArrayList;
import java.util.Comparator;

public class Students {
	private ArrayList<student> students = new ArrayList<student>();
	
	public Students(ArrayList<student> studs) {
		for(int i = 0; i < studs.size(); i++) {
			students.add(studs.get(i));
		}
	}
	public ArrayList<student> getList() {
		return students;
	}
	
	
	public static class student {
		private Name name;
		private double average, numGrades;
		private static Comparator<student> cmpName = new Comparator<student>() {
			public int compare(student s1, student s2) {
				int result = s1.printName().compareTo(s2.printName());
				if(result == 0) {
					return ((Double)s1.getAverage()).compareTo((Double)s2.getAverage());
				}
				return result;
			}
		};
		private static Comparator<student> cmpAverage = new Comparator<student>() {
			public int compare(student s1, student s2) {
				int result = ((Double)s1.getAverage()).compareTo((Double)s2.getAverage());
				if(result == 0) {
					return s1.printName().compareTo(s2.printName());
				}
				return result;
			}
		};
		
		
		public student(String fname, String lname, double average, double numGrades) {
			name = new Name(fname, lname);
			this.average = average;
			this.numGrades = numGrades;
		}
		
		public void addGrade(double newGrade) {
			double total = average * numGrades;
			total += newGrade;
			numGrades++;
			average = total / numGrades;
		}
		
		public static Comparator<student> getCompareName(){
			return cmpName;
		}
		public static Comparator<student> getCompareAverage(){
			return cmpAverage;
		}
		
		public double getAverage() {
			return average;
		}
		
		public String printName() {
			return name.toString();
		}
		
		public String toString() {
			return name.toString() + " " + average;
		}
	}
	
	public static class Name {
		private String fname, lname;
		public Name(String fname, String lname) {
			this.fname = fname;
			this.lname = lname;
		}

		public String toString() {
			return lname + ", " + fname;
		}
	}
}
