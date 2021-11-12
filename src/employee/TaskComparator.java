package employee;

//import java.util.ArrayList;
import java.util.Comparator;

class TaskComparator implements Comparator<Task> {
    public int compare(Task t1, Task t2)
    {
    	if(t1.getProjectID() < t2.getProjectID())
    		return 1;
    	else if(t1.getProjectID() > t2.getProjectID())
    		return -1;
        else{// t1.ProjectID == t2.ProjectID
            if(t1.getDateFrom().isAfter(t2.getDateFrom()))
            	return 1;
            else if(t1.getDateFrom().isBefore(t2.getDateFrom()))
            	return -1;
            else{
            	if(t1.getDateTo().isAfter(t2.getDateTo()))
            		return 1;
            	else if(t1.getDateTo().isBefore(t2.getDateTo()))
            		return -1;
            	else
            		return 0;
            }
        }
    }
}