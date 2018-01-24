package wakugawa_CSCI201L_Lab2;

public abstract class Employee extends Person
{
	private int employeeID;
	private String jobTitle, company;
	public Employee(String firstName, String lastName, String birthdate, int employeeID, String jobTitle, String company)
	{
		super(firstName, lastName, birthdate);
		this.employeeID = employeeID;
		this.jobTitle = jobTitle;
		this.company = company;
		
	}
	
	public int getEmployeeID()
	{
		return employeeID;
	}
	
	public String getJobTitle()
	{
		return jobTitle;
	}
	
	public String getCompany()
	{
		return company;
	}
	
	public abstract double getAnnualSalary();
}