package com.example.android.navigationdrawerexample;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class ChildActivity extends Activity {
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		Bundle bundle=new Bundle();
		bundle=getIntent().getExtras();
		position=bundle.getInt("Planet numble");
		PlanetFragment planetfragment=new PlanetFragment(position);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_layout, planetfragment).commit();
		
	}
	
}
