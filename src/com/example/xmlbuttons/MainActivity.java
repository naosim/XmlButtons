package com.example.xmlbuttons;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.naosim.flatbutton.FlatButton;

public class MainActivity extends Activity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// set flat design background for Button class
		Drawable buttonBackground = new FlatButton.FlatBackgroundFactory().create(Color.LTGRAY);
		findViewById(R.id.defaultButton).setBackgroundDrawable(buttonBackground);

	}

}
