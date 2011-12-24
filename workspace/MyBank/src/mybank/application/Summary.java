package mybank.application;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import mybank.application.R;

import mybank.datamart.CategoryDataTable;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Summary extends Activity  {
    /** Called when the activity is first created. */
	
	private TableLayout _tableLayout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);  
        
    	TransactionManager manager = new TransactionManager(getApplicationContext());
    	ArrayList<TransactionInfo> transactions = manager.GetTransactions();
    	
    	double debitTotal = 0;
    	double creditTotal = 0;
    	
    	for(Iterator<TransactionInfo> iterator = transactions.iterator(); iterator.hasNext();)
    	{
    		TransactionInfo transactionInfo = iterator.next();
    		if(transactionInfo.getAmount() < 0)
    			debitTotal += transactionInfo.getAmount();
    		else
    			creditTotal += transactionInfo.getAmount();
    	}
    	
        this._tableLayout = (TableLayout)findViewById(R.id.summaryLayout);
        
        DecimalFormat currencyFormat = new DecimalFormat("#,##0.00;(#,##0.00)");
        this.addRow("Total Transactions", Integer.toString(transactions.size()));
        this.addRow("Total Debit", currencyFormat.format(debitTotal));
        this.addRow("Total Credit", currencyFormat.format(creditTotal));

        ArrayList<String> categoryList = new ArrayList<String>();
        CategoryDataTable categoryDataTable = new CategoryDataTable(this);
        categoryDataTable.open();
        
        Cursor cursor = categoryDataTable.fetchAllCategorys();
        
        cursor.moveToFirst();
        
        while(cursor.isAfterLast()==false)
        {
        	categoryList.add(cursor.getString(0));
        	cursor.moveToNext();
        }
        
        categoryDataTable.close();
        
        this.addRow("Total Categories", Integer.toString(categoryList.size()));
//        android:layout_column="1"
//            android:text="Open..."
//            android:padding="3dip" />        
    }

    private void addRow(String heading, String value)
    {
        TableRow row = new TableRow(this);
        //row.setLayoutParams(new LayoutParams(LayoutParams.))
        
        TextView headingColumn = new TextView(this);
        headingColumn.setText(heading);
        headingColumn.setLayoutParams(new TableRow.LayoutParams(0));
        row.addView(headingColumn);

        TextView valueColumn = new TextView(this);
        valueColumn.setText(value);
        valueColumn.setLayoutParams(new TableRow.LayoutParams(1));
        valueColumn.setGravity(Gravity.RIGHT);
        
        row.addView(valueColumn);
        row.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        
        this._tableLayout.addView(row);
    }
}