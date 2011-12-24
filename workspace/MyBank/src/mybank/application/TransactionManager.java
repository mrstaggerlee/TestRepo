package mybank.application;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class TransactionManager {

	private Context _context;
	
	public TransactionManager(Context context)
	{
		this._context = context;
	}
	
	public ArrayList<TransactionInfo> GetTransactions()
	{
        Uri uri = Uri.parse("content://sms/inbox");
        final String[] projection =
        	new String[] { "_id", "thread_id", "address", "date", "body", "person" };
    	String selection = " person = 279 ";
    	String[] selectionArgs = null;
    	final String sortOrder = "date DESC";
    	
    	ArrayList<TransactionInfo> transactions = new ArrayList<TransactionInfo>();
    	
    	int COL_ADDRESS = 2;
    	int COL_BODY = 4;
    	int COL_PERSON = 5;
    	
        Cursor cursor = this._context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
        try
        {
	    	while (cursor.moveToNext()) {
	    		TransactionInfo info = new TransactionInfo(cursor.getString(COL_BODY));
	    		transactions.add(info);
	    	}
        }
        catch(Exception e)
        {
        	//arrayList.add(e.toString());
        }
        finally{
          cursor.close();
        }	
        
        return transactions;
	}
}
