package com.example.sendemail;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	

	public void sendEmail(View view){
		//launching the email app
	    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  
	    emailIntent.setType("plain/text");  
	    //adding a chooser
	    startActivity(Intent.createChooser(emailIntent, "Send your email in:"));
	    
		}
	
	public void sendEmailExtra(View view){
	    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  
	    String aEmailList[] = { "user@fakehost.com","user2@fakehost.com" };  
	    String aEmailCCList[] = { "user3@fakehost.com","user4@fakehost.com"};  
	    String aEmailBCCList[] = { "user5@fakehost.com" };  
	    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);  
	    emailIntent.putExtra(android.content.Intent.EXTRA_CC, aEmailCCList);  
	    emailIntent.putExtra(android.content.Intent.EXTRA_BCC, aEmailBCCList);  
	    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My subject");  
	    emailIntent.setType("plain/text");  
	    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "My message body.");  
	    startActivity(emailIntent);  
	}
	
	

}
