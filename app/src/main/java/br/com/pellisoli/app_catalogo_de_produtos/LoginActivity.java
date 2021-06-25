package br.com.pellisoli.app_catalogo_de_produtos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.pellisoli.app_catalogo_de_produtos.item.Item;
import br.com.pellisoli.app_catalogo_de_produtos.item.Login;

public class LoginActivity extends AppCompatActivity {

    private EditText user;
    private EditText pass;
    private Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.form_user);
        pass = findViewById(R.id.form_pass);
        btnEdit = findViewById(R.id.login_btn);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login login = new Login("/api/login",
                        "{\"mail\" : \"" + user.getText().toString() + "\",\"password\" :  \""+ pass.getText().toString() +"\" }",
                        LoginActivity.this);
                login.execute();
                finish();
            }
        });
    }

    public void saveToken(String token){

    }


}