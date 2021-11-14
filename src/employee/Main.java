package employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.time.LocalDate; 
import java.time.Period;
import java.util.HashMap;

public class Main{
	TreeMap<Integer,ArrayList<Task>> emp;
	public Main(){
		emp = new TreeMap<Integer,ArrayList<Task>>();
		read();
		find_pair_employee();
	}
	void read(){
		System.out.println("Enter file with data");
		String file = "";
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
        	file = reader.readLine();
        	System.out.println("Enter formatter(default is \"yyyy-MM-dd\" to used pres Enter)");
        	String formatter = reader.readLine();
        	if(!formatter.equals("")){
        		Task.setFormatter(formatter);
        	} 
        }catch(IOException e){
			System.err.println("IOException: " + e.getMessage());
		}
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
    		for(String line; (line = br.readLine()) != null; ) {
        		String[] str = line.replaceAll(" ","").split(",",5);
				int emplID = Integer.valueOf(str[0]);
				if(emp.containsKey(emplID)){
					emp.get(emplID).add(new Task(str));
				}else{
					emp.put(emplID,new ArrayList<Task>());
					emp.get(emplID).add(new Task(str));
				}
    		}
		}catch(IOException e){
			System.err.println("IOException: " + e.getMessage());
		}
    }
	void find_pair_employee(){
		for (Integer emplID1 : emp.keySet()){
			emp.get(emplID1).sort(new TaskComparator());
		}
		Iterator<Integer> e1 = emp.keySet().iterator();
		Iterator<Integer> e2;
		Integer keyE1,keyE2;
		ArrayList<Task> emp1,emp2;
		LocalDate start,end;
		HashMap<Integer,HashMap<Integer,Period>> results = new HashMap<Integer,HashMap<Integer,Period>>();
		Period max = Period.of(0,0,0);
		Period maxCopy;
		int maxEmployee1=-1;
		int maxEmployee2=-1;
		while(e1.hasNext()){
			keyE1 = e1.next();
			e2 = emp.tailMap(keyE1).keySet().iterator();
			if(e2.hasNext())
				e2.next();
			while (e2.hasNext()){
				keyE2 = e2.next();
				emp1 = emp.get(keyE1);
				emp2 = emp.get(keyE2);
				for(int i = 0; i < emp1.size(); i++){
					for(int j = 0; j  < emp2.size(); j++){
						int i2 = -1;
						int j2 = -1;
						if(emp1.get(i).getProjectID() == emp2.get(j).getProjectID()){
							for(i2 = i; i2 < emp1.size() && emp1.get(i2).getProjectID() == emp2.get(j).getProjectID();i2++ ){
								for(j2=j; j2<emp2.size() && emp1.get(i2).getProjectID() == emp2.get(j2).getProjectID();j2++ ){
									if( emp1.get(i2).getDateFrom().isBefore(emp2.get(j2).getDateTo()) && emp1.get(i2).getDateTo().isAfter(emp2.get(j2).getDateFrom()) ){
										start = emp1.get(i2).getDateFrom().isAfter(emp2.get(j2).getDateFrom()) ? emp1.get(i2).getDateFrom() : emp2.get(j2).getDateFrom();
										end = emp1.get(i2).getDateTo().isBefore(emp2.get(j2).getDateTo()) ? emp1.get(i2).getDateTo() : emp2.get(j2).getDateTo();
										if(!results.containsKey(keyE1)){
											results.put(keyE1,new HashMap<Integer,Period>());
											results.get(keyE1).put(keyE2,Period.between(start, end));
										}else if(!results.get(keyE1).containsKey(keyE2))
											results.get(keyE1).put(keyE2,Period.between(start, end));
										else
											results.get(keyE1).put(keyE2,results.get(keyE1).get(keyE2).plus(Period.between(start, end)));
										maxCopy = max.minus(results.get(keyE1).get(keyE2));
										if(maxCopy.isNegative()){
											maxCopy = results.get(keyE1).get(keyE2);
											max = Period.of(maxCopy.getYears(),maxCopy.getMonths(),maxCopy.getDays());
											maxEmployee1 = keyE1;
											maxEmployee2 = keyE2;
										}
									}
								}
							}
							i=i2;
							j=j2;
						}
					}
				}
			}		
		}
		System.out.printf(
			"Employee %d and Employee %d have work longest together-\n %d years %d months % days\n\n"
			,maxEmployee1,maxEmployee2,max.getYears(),max.getMonths(),max.getDays());

	}
	public static void main(String[] args) throws IOException {
		Main e = new Main();
    }
}