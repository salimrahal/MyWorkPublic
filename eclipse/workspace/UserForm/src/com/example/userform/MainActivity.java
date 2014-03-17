package com.example.userform;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

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

	public void sendFeedback(View view) {
		// Do click handling here
		// reading values from text fields
		final EditText nameObj = (EditText) findViewById(R.id.EditTextName);
		String name = nameObj.getText().toString();
		final EditText emailObj = (EditText) findViewById(R.id.EditTextEmail);
		String email = emailObj.getText().toString();
		final EditText feedbackField = (EditText) findViewById(R.id.EditTextFeedbackBody);
		String feedback = feedbackField.getText().toString();

		// reading from spinner (combo box)
		final Spinner spinnerFeedbackObj = (Spinner) findViewById(R.id.SpinnerFeedbackType);
		String feedbackType = spinnerFeedbackObj.getSelectedItem().toString();

		// reading from checkbox
		final CheckBox checkboxObj = (CheckBox) findViewById(R.id.CheckBoxResponse);
		boolean requiresResponse = checkboxObj.isChecked();
		//now call the email module and send it
		sendEmailExtra(name, email, feedback, feedbackType, requiresResponse);
	}

	public void sendEmailExtra(String name, String email, String feedback,
			String feedbackType, boolean requiresResponse) {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		String aEmailList[] = { "admin@safiratech.com" };
		// String aEmailCCList[] = { "user3@fakehost.com","user4@fakehost.com"};
		// String aEmailBCCList[] = { "user5@fakehost.com" };
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
		// emailIntent.putExtra(android.content.Intent.EXTRA_CC, aEmailCCList);
		// emailIntent.putExtra(android.content.Intent.EXTRA_BCC,
		// aEmailBCCList);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback("
				+ feedbackType + ")");
		emailIntent.setType("plain/text");

		String msgbody = feedback + "/n" + name + "(" + email + ")";
		if (requiresResponse)
			msgbody += "(Requires response)";
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, msgbody);
		startActivity(emailIntent);
	}

}
