package com.codepath.androidgridimagesearch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		String url = getIntent().getStringExtra("url");
		SmartImageView ivImage = (SmartImageView) findViewById(R.id.ivResult);
		ivImage.setImageUrl(url);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	public void onFinish(View v){
		this.finish();
	}
}
