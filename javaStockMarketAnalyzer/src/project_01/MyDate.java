package project_1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// MyDate class insures that a valid date is entered
public final class MyDate {
	
	private Date fromDate;
	private Date toDate;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	public MyDate(String fromDate, String toDate) throws ParseException {
		
		Date d1 = (isValidDate(fromDate) ? dateFormat.parse(fromDate): null);
		Date d2 = (isValidDate(toDate) ? dateFormat.parse(toDate): null);
		
		if (d1 == null || d2 == null) {
			System.out.println("Your date is invalid");
			System.exit(0);
		}
		else {
			this.fromDate = d1;
			this.toDate = d2;
		}
		
	}

	public Date getFromDate() {
		return fromDate;
	}

	public Date getToDate() {
		return toDate;
	}
	
	
	private boolean isValidDate(String inDate) {
		
		//Date format validation (Regex:Regular expression)
		
		String regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
		Pattern pattern = Pattern.compile(regex);
		
		    Matcher matcher = pattern.matcher(inDate);
		   return matcher.matches();
	}
}
