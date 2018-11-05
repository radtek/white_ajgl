package com.taiji.pubsec.orcode.phonegaptest;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import  com.taiji.pubsec.orcode.addressorcode.CaptureActivity;

public class Plugin_intent extends CordovaPlugin {

    /**
     * 注意 构造方法不能为
     *
     * Plugin_intent(){}
     *
     * 可以不写或者 定义为如下
     *
     */
    public Plugin_intent() {
    }

    CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args,
                           CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        Log.i("123", action);

        if (action.equals("intent")) {
            // 获取args的第一个参数
            String message = args.getString(0);
            this.function();
            return true;
        }
        return false;

    }

    private void function() {
        // cordova.getActivity() 获取当前activity的this
        Log.i("123", cordova.getActivity().toString());
        Intent intent = new Intent(cordova.getActivity(), CaptureActivity.class);
        cordova.startActivityForResult((CordovaPlugin) this,intent, 200);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        //传递返回值 给js方法
        callbackContext.success("activity 跳转成功了");
        Toast.makeText(cordova.getActivity(), "123", Toast.LENGTH_LONG).show();

    }
}