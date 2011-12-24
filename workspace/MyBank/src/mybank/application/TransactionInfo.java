package mybank.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionInfo {
	private String _account;
	private String _type;
	private String _date;
	private String _how;
	private String _who;
	private double _amount;
	private double _balance;
    private String _message;
    
	public TransactionInfo(String message)
	{
		this._message = message;
		
		final String findInfo = "^([^,]+),([^,]+), (\\d\\d/\\d\\d/\\d\\d) ([^,]+),(([^,]+),)? R([-\\d,\\.]+), .+R([-\\d,\\.]+)\\.";
    	
    	Pattern regexInfo = Pattern.compile(findInfo, Pattern.CASE_INSENSITIVE);
    	
    	Matcher matcher = regexInfo.matcher(message);
    	if(matcher.find())
    	{
    		_account = matcher.group(1);
    		_type = matcher.group(2);
    		_date = matcher.group(3);
    		_how = matcher.group(4);
    		_who = matcher.group(6);
    		
    		try{
        		_amount = Double.valueOf(matcher.group(7));    			
    		}
    		catch (Exception e){}

    		try{
    			_balance = Double.valueOf(matcher.group(8));		
    		}
    		catch (Exception e){}

    		try {
	    		SimpleDateFormat dateFormatSource = new SimpleDateFormat("dd/MM/yy");
	    		SimpleDateFormat dateFormatTarget = new SimpleDateFormat("yyyy/MM/dd");				
    			_date = dateFormatTarget.format( dateFormatSource.parse(_date)  );
			} catch (ParseException e) {
			}
    	}
	}
	
	public String getMessage()
	{
		return _message;
	}
	public String getAccount()
	{
	  return _account;
	}
	public String getType()
	{
	  return _type.trim();
	}
	public String getDate()
	{
	  return _date;
	}
	public String getHow()
	{
	  return _how.trim();
	}
	public String getWho()
	{
	  return _who.trim();
	}
	public double getAmount()
	{
	  return _amount;
	}
	public double getBalance()
	{
	  return _balance;
	}
	
	@Override
	public String toString()
	{
		return _date;
	}
}

