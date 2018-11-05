package com.taiji.Login;


        import android.app.Activity;
        import android.content.ComponentName;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.pm.PackageInfo;
        import android.content.pm.PackageManager;
        import android.os.Bundle;
        import android.os.Handler;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.Window;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.CompoundButton.OnCheckedChangeListener;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.taiji.Login.viewmanager.LoginPostTask;
        import com.taiji.UpdateApp.Update_AppActivity;

        import com.taiji.UpdateApp.viewmanager.CheckAppVersionTask;
        import com.taiji.pubsec.orcode.addressorcode.R;

        import org.json.JSONObject;

        import java.text.SimpleDateFormat;
        import java.util.Date;

        import orcode.pubsec.taiji.com.business.util.validation.LoginBusiness;


public class LoginActivity extends Activity {

    private EditText userName, password,ip_et,port_et;
    private CheckBox rem_pw, auto_login,set_ip;
    private Button btn_login;
    private ImageButton btnQuit;
    private LinearLayout setIpPannel;
    private String userNameValue,passwordValue;
    private SharedPreferences sp;
    private boolean threadReLogin=false;
    Handler myHandler=new Handler();

    public static String  loginUserName="";
   // public static String personName ="";
    public static String  loginPwd="";
  //  public static String  Url_loginStep1="";
  //  public static String  Url_loginStep2="";
    public  static String loginIp ="52.4.1.188";
    public  static String loginPort ="9003";
    //public static String  loginPersonCode="";
    //public static String  loginUnitCode="";
    //public static String pdaComIp="";
    //public static String pdaComPass="";
    //public static String pdaComNum="";
    //public static String pdaComPort="";
    public static boolean isLogin=false;
    private  String jwzhIp_temp="";
    private  String jwzhPort_temp="";
    String curVersion="";
    int curVersionCode;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ȥ������
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        if(isLogin)
        {
            Intent intent = new Intent(LoginActivity.this, com.taiji.article.caseitem.CaseScanAllActivity.class);
            LoginActivity.this.startActivity(intent);
            finish();
        }


        //���ʵ������
     //   sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        userName = (EditText) findViewById(R.id.et_zh);
        password = (EditText) findViewById(R.id.et_mima);
        ip_et = (EditText) findViewById(R.id.ip_et);
        port_et = (EditText) findViewById(R.id.port_et);
        rem_pw = (CheckBox) findViewById(R.id.cb_set_ip);
        auto_login = (CheckBox) findViewById(R.id.cb_auto);
        btn_login = (Button) findViewById(R.id.btn_login);
      //  btnQuit = (ImageButton)findViewById(R.id.img_btn);
        set_ip= (CheckBox) findViewById(R.id.cb_set_ip);
        setIpPannel= (LinearLayout) findViewById(R.id.set_ip_pannel);

        getCurVersion();
        TextView tv=(TextView) findViewById(R.id.tv_ver);
        tv.setText("V"+curVersion);

        SavLogin.readUser(LoginActivity.this);
        if(!loginUserName.equals("") && !loginPwd.equals("") )
        {
             userName.setText(loginUserName);
             password.setText(loginPwd);
        }
        if(!loginIp.equals("") && !loginPort.equals(""))
        {
            ip_et.setText(loginIp);
            port_et.setText(loginPort);
        }

        Date d=new Date();
        String fmtStrDate="yyyy-MM-dd";
        SimpleDateFormat dfDate = new SimpleDateFormat(fmtStrDate);
        String dateStr = dfDate.format(d);
        if(dateStr.indexOf("2016-03")>=0 ||dateStr.indexOf("2016-04")>=0||dateStr.indexOf("2016-05")>=0)
        {
            //Intent intent = new Intent(LoginActivity.this, com.taiji.jwzhapp.ActivityV3.MainActivity.class);
          // LoginActivity.this.startActivity(intent);
            //Intent intent = new Intent(LoginActivity.this, Update_AppActivity.class);
            //LoginActivity.this.startActivity(intent);
        }
        else
        {
            //showTimeOut();
            //return;
        }

        /*
        if(getResources().getString(R.string.isOnlyTestMQT).equals("1"))
        {
            JWZHLoginHelper jmh = new JWZHLoginHelper(LoginActivity.this);
            String userName = getResources().getString(R.string.mqt_userName);
            String pwd = getResources().getString(R.string.mqt_pwd);
            String ip = getResources().getString(R.string.mqt_ip);
            String psot = getResources().getString(R.string.mqt_post);
            jmh.JWZHLogin(LoginActivity.this, userName, pwd, ip, Integer.parseInt(psot), true);
            return;
        }

        if(getResources().getString(R.string.isTestCollectionCiew).equals("1"))
        {
            Intent intent = new Intent(LoginActivity.this, com.taiji.jwzhapp.androiduiview.collection.CollectionDetailActivity.class);
            loginIp=getResources().getString(R.string.testCollection_ip);
           loginPort=getResources().getString(R.string.testCollection_post);
            LoginActivity.this.startActivity(intent);
            return;
        }*/

        btn_login.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
/*
                Intent intent=new Intent();
                //  intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                if (android.os.Build.VERSION.SDK_INT >= 12) {
                  // intent.setFlags(32);//3.1以后的版本需要设置Intent.FLAG_INCLUDE_STOPPED_PACKAGES
                }
             //   loginUserName = userName.getText().toString();
            //    loginPwd = password.getText().toString();
                //设置Action
                intent.setAction("com.taiji.pubsec.zgcapp.action");
                intent.putExtra("loginName", userName.getText().toString());
                intent.putExtra("pwd", password.getText().toString());
                try {
                    startActivity(intent);
                }catch (Exception ex)
                {

                }
*/
                /*
                 Intent mIntent = new Intent();
                  ComponentName comp = new ComponentName("com.taiji.pubsec.zgc.webviewappFile","com.taiji.pubsec.zgc.webviewapp.WebActivity");
                  mIntent.setComponent(comp);
                mIntent.setAction("android.intent.action.VIEW");
                try {
                    startActivity(mIntent);
                }catch (Exception ex){}
                */
                // Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                // startActivity(intent);

                //sendBroadcast(intent);
                doLoginClick();
            }
        });

        set_ip.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean select) {
                if(setIpPannel==null)
                {
                    return;
                }
                if (select) {
                    setIpPannel.setVisibility(View.VISIBLE);
                    setIpPannel.requestFocus();
                    jwzhIp_temp = ip_et.getText().toString();
                    jwzhPort_temp = port_et.getText().toString();
                } else {
                    setIpPannel.setVisibility(View.GONE);
                    ip_et.setText(jwzhIp_temp);
                    port_et.setText(jwzhPort_temp);
                }
            }
        });

        if(!loginUserName.equals("") && !loginPwd.equals("") )
        {
            if(getResources().getString(R.string.isAutoLogin).equals("1")) {
                threadReLogin=true;
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        while (threadReLogin) {
                            if(isDoLogin)
                            {
                                continue;
                            }
                            try {
                                doLoginClick();
                                Thread.sleep(30000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }).start();
            }
        }
        /*
        btnQuit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        */

    }
    private void doLoginClick()
    {
        loginUserName = userName.getText().toString();
        loginPwd = password.getText().toString();
        if(getResources().getString(R.string.isLoginTestSystem).equals("0")){//&& loginUserName.equals("ZL") && loginPwd.equals("123456789")){
            loginIp = ip_et.getText().toString();
            loginPort = port_et.getText().toString();
            LoginJWZH();
        }
        else
        {
            loginIp = ip_et.getText().toString();
            loginPort = port_et.getText().toString();
            // showTimeOut();
           // Url_loginStep1 = getResources().getString(R.string.http) + LoginActivity.loginIp + ":" + LoginActivity.loginPort + getResources().getString(R.string.url_GetLoginStep1);
          //  Url_loginStep2 = getResources().getString(R.string.http) + LoginActivity.loginIp + ":" + LoginActivity.loginPort + getResources().getString(R.string.url_GetLoginStep2);
            LoginJWZH();
        }
    }

    boolean isDoLogin=false;
    private void LoginJWZH() {
        if(isDoLogin)
        {
            showAlertText("正在登录..");
            return;
        }
        isDoLogin=true;
        LoginBusiness.loginIp=LoginActivity.this.loginIp;
        LoginBusiness.loginPort=LoginActivity.this.loginPort;
        if(getResources().getString(R.string.isLoginTestSystem).equals("0")) {
            SavLogin.savUser(LoginActivity.this, LoginActivity.loginUserName, LoginActivity.loginPwd, LoginActivity.this.loginIp, LoginActivity.this.loginPort);
            Intent intent = new Intent(LoginActivity.this, com.taiji.article.caseitem.CaseScanAllActivity.class);
            LoginActivity.this.startActivity(intent);
            finish();
            return;
        }

        LoginPostTask post = new LoginPostTask(LoginActivity.this,"");
        post.setHttpLoginListener(new LoginPostTask.httpLoginListener() {
            @Override
            public void httpLoginPostBack(String progressId, String result) {
                try {
                    if (result.equals("0")) {
                        isLogin=true;
                        threadReLogin = false;
                        isDoLogin = false;
                        SavLogin.savUser(LoginActivity.this, LoginActivity.loginUserName, LoginActivity.loginPwd,LoginActivity.this.loginIp,LoginActivity.this.loginPort);

                        try {
                            if(getResources().getString(R.string.isStartcheckAppUpdate).equals("1")) {
                                checkAppUpdate();
                            }
                            else
                            {
                                Intent intent = new Intent(LoginActivity.this, com.taiji.article.caseitem.CaseScanAllActivity.class);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            }
                        }
                        finally {

                        }

                    } else {
                        isDoLogin=false;
                        if(result.indexOf("errCode")>=0)
                        {
                            showAlertText("连接错误 "+result);
                            return;
                        }
                        if(result.equals("-10"))
                        {
                            showAlertText("无法访问服务");
                        }
                        else if(result.equals("-20"))
                        {
                            showAlertText("登录名或密码错误");
                        }
                        else if(result.equals("-30"))
                        {
                            showAlertText("用户没有设备使用权");
                        }
                        else if(result.equals("-40"))
                        {
                            showAlertText("获取用户信息失败");
                        }
                        else if(result.equals("-50"))
                        {
                            showAlertText("设备没有注册");
                        }
                        else
                        {
                            showAlertText("登录失败");
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        post.execute();

    }

    private void checkAppUpdate() {
        getCurVersion();
        CheckAppVersionTask post = new CheckAppVersionTask(LoginActivity.this,"",curVersion);
        post.setHttpCheckAppListener(new CheckAppVersionTask.httpCheckAppListener() {
            @Override
            public void httpCheckAppBack(String progressId, String result) {
                try {
                    if(result.indexOf("errCode")>=0)
                    {
                        showAlertText("连接错误 "+result);
                        return;
                    }
                    ;if(result.equals("-104"))
                    {
                        showAlertText("检测更新失败:登录超时");
                        return;
                    }
                    if(result.equals("-406"))
                    {
                        showAlertText("检测更新失败:权限不足");
                        return;
                    }
                    JSONObject p = new JSONObject(result);
                    JSONObject jp = (JSONObject) p.get("appVersionBean");
                    String versionName = jp.getString("versionNum");
                    String forceUpdate = jp.getString("forceUpdate");
                    if (curVersion.compareTo(versionName) < 0 && !versionName.equals("null")) {
                        Intent intent = new Intent(LoginActivity.this, Update_AppActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putString("forceUpdate", forceUpdate);
                        intent.putExtras(mBundle);
                        LoginActivity.this.startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent = new Intent(LoginActivity.this, com.taiji.article.caseitem.CaseScanAllActivity.class);
                        LoginActivity.this.startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showAlertText("检测更新失败");
                        }
                    });
                    e.printStackTrace();
                }
            }
        });
        post.execute();
    }


    private void getCurVersion() {
        try {
            PackageInfo pInfo = LoginActivity.this.getPackageManager().getPackageInfo(
                    LoginActivity.this.getPackageName(), 0);
            curVersion = pInfo.versionName;
            curVersionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("update", e.getMessage());
            curVersion = "1.1.1000";
            curVersionCode = 111000;
        }

    }

    private  void showTimeOut()
    {
        Toast.makeText(this, "系统过期", Toast.LENGTH_LONG).show();
    }

    private  void showAlertText(String text)
    {
        Toast.makeText(this,text, Toast.LENGTH_LONG).show();
    }
}