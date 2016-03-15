package ro.pub.cs.systems.pdsd.lab04.contactsmanager;

import java.util.ArrayList;

import ro.pub.cs.systems.pdsd.lab04.general.Constants;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class ContactsManagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);
        

        
        
        final Button fieldsButton = (Button) findViewById(R.id.fields);
        final Button saveButton = (Button) findViewById(R.id.save);
        final Button cancelButton = (Button) findViewById(R.id.cancel);
        final LinearLayout details = (LinearLayout)  findViewById(R.id.details);
        
        final EditText nameText = (EditText) findViewById(R.id.name);
        final EditText phoneText = (EditText) findViewById(R.id.phoneNr);
        final EditText emailText = (EditText) findViewById(R.id.email);
        final EditText addressText = (EditText) findViewById(R.id.address);
        final EditText jobTitleText = (EditText) findViewById(R.id.jobTitle);
        final EditText companyText = (EditText) findViewById(R.id.company);
        final EditText imText = (EditText) findViewById(R.id.messenger);
        final EditText webSiteText = (EditText) findViewById(R.id.webSite);
        
        
        Intent intent = getIntent();
        if (intent != null) {
          String phone = intent.getStringExtra("ro.pub.cs.systems.pdsd.lab04.contactsmanager.PHONE_NUMBER_KEY");
          if (phone != null) {
            phoneText.setText(phone);
          } else {
            Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
          }
        } 
        
        
        fieldsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(details.getVisibility() == View.GONE) {
					fieldsButton.setText("Hide Additional Details");
					details.setVisibility(View.VISIBLE);
				} else {
					fieldsButton.setText("Show Additional Details");
					details.setVisibility(View.GONE);
				}
			}
        });
        
        saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = nameText.getText().toString();
				String phone = phoneText.getText().toString();
				String email = emailText.getText().toString();
				String address = addressText.getText().toString();
				String jobTitle = jobTitleText.getText().toString();
				String company = companyText.getText().toString();
				String website = webSiteText.getText().toString();
				String im = imText.getText().toString();
				
				
				Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
				intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
				if (name != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
				}
				if (phone != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
				}
				if (email != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
				}
				if (address != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
				}
				if (jobTitle != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
				}
				if (company != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
				}
				ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
				if (website != null) {
				  ContentValues websiteRow = new ContentValues();
				  websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
				  websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
				  contactData.add(websiteRow);
				}
				if (im != null) {
				  ContentValues imRow = new ContentValues();
				  imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
				  imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
				  contactData.add(imRow);
				}
				intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
//				startActivity(intent);
				startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
			}
			
        });
        
        cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(Activity.RESULT_CANCELED, new Intent());
//				finish();
			}
        });
    
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contacts_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	switch(requestCode) {
    	  case Constants.CONTACTS_MANAGER_REQUEST_CODE:
    	    setResult(resultCode, new Intent());
    	    finish();
    	    break;
    	  }
    	}
    
    
}
