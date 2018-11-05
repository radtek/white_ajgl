package com.taiji.Login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.taiji.pubsec.orcode.addressorcode.R;

import orcode.pubsec.taiji.com.business.util.validation.LoginBusiness;


/**
 * Created by z0 on 2016/3/16.
 */
public class SavLogin {
    public static int savUser(Context context,String userName,String userPwd,String ip,String port)
    {
        try {
            if (context == null) {
                return -1;
            }
            //实例化SharedPreferences对象（第一步）
            SharedPreferences mySharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.savLogin_fileName), Activity.MODE_PRIVATE);
            //实例化SharedPreferences.Editor对象（第二步）
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            //用putString的方法保存数据
            editor.putString("userName", userName==null?"":userName);
            editor.putString("userPwd", userPwd==null?"":userPwd);
            editor.putString("ip", ip==null?"":ip);
            editor.putString("port", port==null?"":port);
            editor.commit();  //使用toast信息提示框提示成功写入数据
            // Toast.makeText(this, "数据成功写入SharedPreferences！" , Toast.LENGTH_LONG).show();

            return 0;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return -1;
        }
    }

    public static int delUser(Context context)
    {
        return savUser(context,"","",context.getResources().getString(R.string.case_Ip), context.getResources().getString(R.string.case_post));
    }

    //返回 userName#usePwd
    public static int readUser(Context context)
    {
        try {
            if (context == null) {
                return -1;
            }
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences= context.getSharedPreferences(context.getResources().getString(R.string.savLogin_fileName), Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        LoginActivity.loginUserName =sharedPreferences.getString("userName", "");
        LoginActivity.loginPwd =sharedPreferences.getString("userPwd", "");
            LoginBusiness.loginIp=sharedPreferences.getString("ip", context.getResources().getString(R.string.case_Ip));
            LoginBusiness.loginPort =sharedPreferences.getString("port", context.getResources().getString(R.string.case_post));
            LoginActivity.loginIp =LoginBusiness.loginIp;
            LoginActivity.loginPort =LoginBusiness.loginPort;
            return 0;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return -1;
        }
    }

    public static int savUpdateCompletedIP(Context context,String ip,String port)
    {
        try {
            if (context == null) {
                return -1;
            }
            //实例化SharedPreferences对象（第一步）
            SharedPreferences mySharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.savLogin_fileName), Activity.MODE_PRIVATE);
            //实例化SharedPreferences.Editor对象（第二步）
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            //用putString的方法保存数据
            editor.putString("ip", ip==null?"":ip);
            editor.putString("port", port==null?"":port);
            editor.commit();  //使用toast信息提示框提示成功写入数据
            // Toast.makeText(this, "数据成功写入SharedPreferences！" , Toast.LENGTH_LONG).show();

            return 0;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return -1;
        }
    }
}
