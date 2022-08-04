package com.poly.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.poly.bookstore.DAO.ThuThuDAO;

public class LoginMainActivity extends AppCompatActivity {

    TextInputEditText edUsername,edPass;
    CheckBox checkBox;
    String strUser ,strPass;
    ThuThuDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        Button btnlogin = findViewById(R.id.btnLogin);
        Button btnHuylogin = findViewById(R.id.btnCancel);
        edUsername=findViewById(R.id.edUsername);
        edPass=findViewById(R.id.edPassWord);
        checkBox=findViewById(R.id.chkRememberPass);
        dao=new ThuThuDAO(this);


        //doc trong sharef
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        String user =preferences.getString("USERNAME","");
        String pass =preferences.getString("PASSWORD","");
        Boolean rm =preferences.getBoolean("REMEMBER",false);

        //check
        edUsername.setText(user);
        edPass.setText(pass);
        checkBox.setChecked(rm);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
        btnHuylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUsername.setText("");
                edPass.setText("");
            }
        });
    }
    public void rmbUser(String u,String p,boolean status){
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!status){
            editor.clear();
        }else {
            //luu du lieu
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMEMBER",status);
        }
        //luu lai all
        editor.commit();
    }
    public void checkLogin(){
        strUser = edUsername.getText().toString();
        strPass =edPass.getText().toString();
        if (strUser.equals("")||strPass.equals("")){
            Toast.makeText(this, "Tài khoản và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
        }else {
            if (dao.checkLogin(strUser,strPass)>0){
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rmbUser(strUser,strPass,checkBox.isChecked());
                Intent intent = new Intent(LoginMainActivity.this,MainActivity.class);
                intent.putExtra("user",strUser);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "Tài khoản và mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }
}