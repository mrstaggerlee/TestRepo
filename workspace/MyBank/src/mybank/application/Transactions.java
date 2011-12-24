package mybank.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mybank.application.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Transactions extends Activity implements OnItemClickListener {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        setContentView(R.layout.transactions);
        
    	ListView listView = (ListView)findViewById(R.id.myListView);
    	listView.setOnItemClickListener(this);
    	
    	TransactionManager manager = new TransactionManager(getApplicationContext());
    	ArrayList<TransactionInfo> transactions = manager.GetTransactions();
    	
        try
        {
          //String[] items = new String[arrayList.size()];
          //arrayList.toArray(items);
          listView.setAdapter(new ArrayAdapter<TransactionInfo>(this, android.R.layout.simple_list_item_1, transactions));
        }
        catch(Exception e)
        {
        	Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        
    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
    {
		String itemText = "testing";
		
		ListView listView = (ListView)findViewById(R.id.myListView);
		TransactionInfo listItem = (TransactionInfo)listView.getItemAtPosition(position);
		try{
			AssignDialog dialog = new AssignDialog(this, listItem, new OnReadyListener());
			dialog.show();
		}
		catch(Exception e)
		{
		  Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
    }

	private class OnReadyListener implements AssignDialog.ReadyListener {

		public void ready(TransactionInfo transaction) {
//			Log.d("Third", "checkbox beer is " + beer);
//			Log.d("Third", "text type is " + type);
		}

	}
	
	private String GetContact(String rawContactId)
    {
    	String lookupKey = ContactsContract.Contacts.LOOKUP_KEY;
    	String displayName = "<not found>";
    	
//    	Uri lookupUri = Uri.withAppendedPath(Contacts.CONTENT_URI, lookupKey);
    	Cursor rw = getContentResolver().query( ContactsContract.RawContacts.CONTENT_URI, new String[]{ContactsContract.RawContacts.CONTACT_ID}, ContactsContract.RawContacts._ID + " = " + rawContactId, null, null);
    	try {
    		while (rw.moveToNext()) {
    			String contactId = rw.getString(0);
    			Cursor c = getContentResolver().query( ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Contacts.DISPLAY_NAME}, ContactsContract.Contacts._ID + " = " + contactId, null, null);
    			while( c.moveToNext()){
    				displayName = c.getString(0);
    			}
    		}
    	} finally {
    		rw.close();
    	}
    	return rawContactId + "-" + displayName;
    }

}