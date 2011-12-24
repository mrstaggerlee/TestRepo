package mybank.application;

import java.text.DecimalFormat;
import java.util.ArrayList;

import mybank.application.R;
import mybank.datamart.CategoryDataTable;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class AssignDialog extends Dialog {

	public interface ReadyListener {
		public void ready(TransactionInfo transaction);
	}

	private TransactionInfo _transaction;
	
	private ReadyListener readyListener;

	public AssignDialog(Context context, TransactionInfo transaction, ReadyListener readyListener) {
		super(context);
		setContentView(R.layout.assign_dialog);
		this._transaction = transaction;
		this.readyListener = readyListener;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.assign_dialog);
		setTitle("Assign Transaction");
		
		CheckBox cbBeer = (CheckBox) findViewById(R.id.checkboxBeer);
		//cbBeer.setChecked(beer);

		Spinner assignCategory = (Spinner)findViewById(R.id.assignCategory);
		
        ArrayList<String> categoryList = new ArrayList<String>();
        CategoryDataTable categoryDataTable = new CategoryDataTable(this.getContext());
        categoryDataTable.open();
        
        Cursor cursor = categoryDataTable.fetchAllCategorys();
        
        cursor.moveToFirst();
        
        while(cursor.isAfterLast()==false)
        {
        	categoryList.add(cursor.getString(1));
        	cursor.moveToNext();
        }
        
        categoryDataTable.close();
        
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, categoryList);
        
        assignCategory.setAdapter(categoryAdapter);
        
		DecimalFormat currencyFormat = new DecimalFormat("#,##0.00;(#,##0.00)");
		
		TextView tvDate = (TextView) findViewById(R.id.transDate);
		TextView tvType = (TextView) findViewById(R.id.transType);
		TextView tvAccount = (TextView) findViewById(R.id.transAccount);
		TextView tvHow = (TextView) findViewById(R.id.transHow);
		TextView tvWho = (TextView) findViewById(R.id.transWho);
		TextView tvAmount = (TextView) findViewById(R.id.transAmount);
		try{
			tvDate.setText(this._transaction.getDate());
			tvType.setText(this._transaction.getType());
			tvAccount.setText(this._transaction.getAccount());
			tvHow.setText(this._transaction.getHow());
			tvWho.setText(this._transaction.getWho());
			tvAmount.setText(currencyFormat.format(this._transaction.getAmount()));
		}
		catch(Exception e){
			tvAmount.setText(this._transaction.getMessage());
		}
		Button buttonOK = (Button) findViewById(R.id.buttonOK);
		buttonOK.setOnClickListener(new OKListener());
	}

	private class OKListener implements android.view.View.OnClickListener {

		public void onClick(View v) {
			CheckBox cbBeer = (CheckBox) findViewById(R.id.checkboxBeer);
			TextView tvType = (TextView) findViewById(R.id.transType);

			readyListener.ready(new TransactionInfo(""));

			AssignDialog.this.dismiss();
		}
	}

}