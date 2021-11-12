package employee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task{
	private int EmpID;
	private int ProjectID;
	private LocalDate DateFrom;
	private LocalDate DateTo;

	public int getEmpID(){
		return EmpID;
	}
	public int getProjectID(){
		return ProjectID;
	}
	public LocalDate getDateFrom(){
		return DateFrom;
	}
	public LocalDate getDateTo(){
		return DateTo;
	}

	public Task(int EmpID, int ProjectID, LocalDate DateFrom,LocalDate DateTo){
		this.EmpID = EmpID;
		this.ProjectID = ProjectID;
		this.DateFrom = DateFrom;
		this.DateTo = DateTo;
	}
	public Task(String EmpID,String ProjectID, String DateFrom, String DateTo){
		this.EmpID = Integer.valueOf(EmpID);
		this.ProjectID = Integer.valueOf(ProjectID);
		this.DateFrom = LocalDate.parse(DateFrom, formatter);
		if(DateTo.equals("NULL")){
			this.DateTo = LocalDate.now();
		}else{
			this.DateTo = LocalDate.parse(DateTo, formatter);;
		}
	}
	public Task(String[] strings){
		this(strings[0],strings[1],strings[2],strings[3]);
	}
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	static void setFormatter(String formatter){
		Task.formatter = DateTimeFormatter.ofPattern(formatter);
	}
}