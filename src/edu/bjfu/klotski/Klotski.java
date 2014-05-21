package edu.bjfu.klotski;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class Klotski extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_klotski, menu);
        return true;
    }

    
}
