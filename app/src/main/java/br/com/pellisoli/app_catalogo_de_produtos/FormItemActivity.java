package br.com.pellisoli.app_catalogo_de_produtos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.pellisoli.app_catalogo_de_produtos.item.Item;
import br.com.pellisoli.app_catalogo_de_produtos.item.ItemDAO;

public class FormItemActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText description;
    private EditText price;
    private EditText tags;
    private Button btnSalver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_item);


        etTitle = findViewById(R.id.form_title);
        description = findViewById(R.id.form_description);
        price = findViewById(R.id.form_price);
        tags = findViewById(R.id.form_tags);
        btnSalver = findViewById(R.id.form_salvar);


        btnSalver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = new Item();
                item.setDescription(description.getText().toString());
                item.setTitle(etTitle.getText().toString());
                item.setPrice(Double.parseDouble(price.getText().toString()));

                String[] strArray = tags.getText().toString().split(",");
                item.setTags(new ArrayList(Arrays.asList(strArray)));

                createItem(item);
            }
        });
    }

    private void createItem(Item item){
        String json = Item.itemToJson(item);

        int a = 0;
    }
}

