package com.taiji.pubsec.orcode.phonegaptest;

import org.apache.cordova.CordovaActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.taiji.pubsec.orcode.addressorcode.R;


public class MainTestActivity extends CordovaActivity  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //解决黑屏问题
        super.init();
        //  this.appView.addJavascriptInterface(this, "js1");
        //  this.appView.setBackgroundResource(R.drawable.login);
        //  super.setIntegerProperty("splashscreen",R.drawable.login);
        //固定页脚
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // super.loadUrl("file:///android_asset/www/index.html", 3000);
        super.loadUrl("file:///android_asset/www/index.html");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
