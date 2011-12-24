package mybank.application;

import mybank.application.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

public class MyBank extends TabActivity  {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        try
        {
            // Create an Intent to launch an Activity for the tab (to be reused)
            intent = new Intent().setClass(this, Summary.class);
	        spec = tabHost.newTabSpec("summary").setIndicator("Summary").setContent(intent);
	        tabHost.addTab(spec);
	
	        // Do the same for the other tabs
	        intent = new Intent().setClass(this, Transactions.class);
	        spec = tabHost.newTabSpec("transactions").setIndicator("Transactions").setContent(intent);
	        tabHost.addTab(spec);
	        
	        tabHost.setCurrentTab(0);
        }
        catch(Exception e)
        {
        	Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

        

//
//        intent = new Intent().setClass(this, SongsActivity.class);
//        spec = tabHost.newTabSpec("songs").setIndicator("Songs",
//                          res.getDrawable(R.drawable.ic_tab_songs))
//                      .setContent(intent);
//        tabHost.addTab(spec);

        //tabHost.setCurrentTab(2);       
        
    }

}