package edu.ayd.joyfukitchen.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import edu.ayd.joyfukitchen.bean.User;
import edu.ayd.joyfukitchen.util.ToastUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseActivity{
    private EditText email_edt;
    private EditText password_edt;
    private Button login_btn;
    private TextView regist_txt;
    private String email;
    private String password;
    private OkHttpClient okHttpClient;
    private Gson gson;
    private String login_url;
    private User user;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    ToastUtil.show(LoginActivity.this,"登录失败！！！");
                    break;
                case 1:
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,CheckCodeActivity.class);
                    LoginActivity.this.startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		init();
	}


	private void init() {
        email_edt =(EditText) findViewById(R.id.email_edt);
        password_edt =(EditText) findViewById(R.id.password_edt);
        login_btn =(Button) findViewById(R.id.login_btn);
        regist_txt =(TextView) findViewById(R.id.regist_txt);

        login_btn.setOnClickListener(new LoginOnClickListener());
        regist_txt.setOnClickListener(new TextViewOnClickListener());
        okHttpClient=new OkHttpClient();
        gson = new Gson();
	}


    /**
     * 单击登录按钮，进行email与密码的校验
     */
    private class LoginOnClickListener implements OnClickListener{
        @Override
        public void onClick(View v) {

            email = email_edt.getText().toString();
            password = password_edt.getText().toString();
            login_url = "http://www.chedles.xyz/joyfulkitchen/recipe/findOneUsers.do";

            FormBody body=new FormBody.Builder()
                    .add("userName", email)
                    .add("password", password)
                    .build();

            //创建一个请求对象
            final Request request=new Request.Builder()
                    .url(login_url)
                    .post(body)
                    .build();
            //发送请求获取响应
            new Thread(){
                @Override
                public void run() {
                    try {
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("IOException", "IO异常");
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                try {
                                    user=gson.fromJson(response.body().string(), User.class);
                                    Log.i("user",user.toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if(user!=null){
                                    handler.sendEmptyMessage(1);
                                }else
                                    handler.sendEmptyMessage(0);
                            }
                        });
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();

        }
    }



    /**
     * 单击新用户注册，跳转到注册页面
     */
	private class TextViewOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this,RegistActivity.class);
            LoginActivity.this.startActivity(intent);
		}
	}


/*
	http://blog.csdn.net/u012303938/article/details/50108725
	http://blog.csdn.net/icyfox_bupt/article/details/38869979
	*/


}
