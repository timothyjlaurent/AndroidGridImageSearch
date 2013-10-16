package com.codepath.androidgridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	String size;
	String color;
	String type;
	String site;
	String lastSearch;
	int gridIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this , imageResults);
		gvResults.setAdapter(imageAdapter);
		gridIndex = 0;
		gvResults.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult= imageResults.get(position);
				i.putExtra("url", imageResult.getFullUrl());
				startActivity(i);
			}
		});
		gvResults.setOnScrollListener( new EndlessScrollListener(){
			@Override
			public void onLoadMore(int page, int totalItemsCount){
				onImageSearch(gvResults);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	
	public void setupViews(){
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}
	
	
	public void onImageSearch(View v){
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for "+query, Toast.LENGTH_SHORT)
			.show();
		AsyncHttpClient client = new AsyncHttpClient();
		//https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=Android
		if (lastSearch != null && lastSearch.equals(query)){
			gridIndex += 8;
		} else {
			gridIndex = 0;
			imageResults.clear();
		}
		lastSearch = query;
		
		
		String quer = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" +
				"start=" + gridIndex + "&v=1.0&q=" + Uri.encode(query);
		if (size != null && !size.equals("")){
			quer += "&imgsz=" + size ;
		}
		if (color != null && !color.equals("")){
			quer += "&imgcolor=" + color;
		}
		if (type != null && !type.equals("")){
			quer += "&imgtype=" + type;
		}
		if (site != null && !site.equals("")){
			quer += "&as_sitesearch=" + site;
		}
		
		Log.d("DEBUG", quer);
		client.get(quer, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject response){
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					//imageResults.clear();
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					Log.d("DEBUG", imageResults.toString());
				} catch (JSONException e) {
					e.printStackTrace();
			
				}
			}
		});
	}
	
	public void onSetOptions(MenuItem mi){
		Log.d("DEBUG", "clicked settings");
		Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
		startActivityForResult(i, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode == RESULT_OK && requestCode == 1){
			size = data.getExtras().getString("size");
			if (size.equals("Small")){
				size = "small";
			} else if (size.equals("Medium")){
				size = "medium";
			} else if (size.equals("Large")){
				size = "large";
			} else if (size.equals("Extra Large")){
				size = "xlarge";
			}
			color = data.getExtras().getString("color").toLowerCase();
			type = data.getExtras().getString("type").toLowerCase();
			site = data.getExtras().getString("site");
		}
	}
	
}
