package com.codepath.androidgridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {
	
	Spinner spnSize;
	Spinner spnColor;
	Spinner spnType;
	EditText etSite;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		spnSize = (Spinner) findViewById(R.id.spnSize);
		ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this, R.array.size_options, android.R.layout.simple_spinner_dropdown_item);
		sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnSize.setAdapter(sizeAdapter);
		
		
		spnColor = (Spinner) findViewById(R.id.spnColor);
		ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(this, R.array.color_options, android.R.layout.simple_spinner_dropdown_item);
		colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnColor.setAdapter(colorAdapter);
		
		
		spnType = (Spinner) findViewById(R.id.spnType);
		ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.type_options, android.R.layout.simple_spinner_dropdown_item);
		sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnType.setAdapter(typeAdapter);
		
		etSite = (EditText) findViewById(R.id.etSite);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	public void onSaveSettings(View v){
		Intent i = new Intent();
		
		i.putExtra("size", spnSize.getSelectedItem().toString());
		i.putExtra("color", spnColor.getSelectedItem().toString());
		i.putExtra("type", spnType.getSelectedItem().toString());
		i.putExtra("site", etSite.getText().toString());
		setResult(RESULT_OK, i);
		finish();
	}
	
}
