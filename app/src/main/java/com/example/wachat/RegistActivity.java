package com.example.wachat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utils.httpUtils;

public class RegistActivity extends AppCompatActivity {
    private Button regist_confirm_button;
    private EditText phone_number;
    private EditText nick;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        regist_confirm_button = (Button) findViewById(R.id.btn_regist_confirm);
        phone_number = (EditText) findViewById(R.id.phone_input);
        nick = (EditText) findViewById(R.id.nick_input);
        password = (EditText) findViewById(R.id.password_input);
        // 注册页面的确认按钮
        regist_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入框的数据
                String inputTextPhone = phone_number.getText().toString();
                String inputTextNick = nick.getText().toString();
                String inputTextPassword = password.getText().toString();

                //输入检查
                if(inputTextPhone.length()==0){
                    Toast.makeText(RegistActivity.this , "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inputTextNick.length()==0){
                    Toast.makeText(RegistActivity.this , "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inputTextPassword.length()==0){
                    Toast.makeText(RegistActivity.this , "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                //把数据存入服务器数据库
                Map<String, String> map = new HashMap<>();
                map.put("phone_number", inputTextPhone);
                map.put("nick", inputTextNick);
                map.put("password", inputTextPassword);
                JSONObject params = new JSONObject(map);
                //调用http post
                Thread trd = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            String result = httpUtils.httpPost("/regist", params, "utf-8");
                            if(result.equals("regist success")){
                                Looper.prepare();
                                Toast.makeText(RegistActivity.this , "注册成功! ", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                trd.start();
            }
        });
    }
}