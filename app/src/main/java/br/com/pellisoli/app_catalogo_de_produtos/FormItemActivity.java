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

import br.com.pellisoli.app_catalogo_de_produtos.item.CreateItem;
import br.com.pellisoli.app_catalogo_de_produtos.item.EditeItem;
import br.com.pellisoli.app_catalogo_de_produtos.item.Item;
import br.com.pellisoli.app_catalogo_de_produtos.item.ItemDAO;
import br.com.pellisoli.app_catalogo_de_produtos.services.Request;

public class FormItemActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText description;
    private EditText price;
    private EditText tags;
    private Button btnSalver;

    private String id;

    private  Item item;


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

                if(id!= null && id != "")
                    item.setId(id);

                item.setDescription(description.getText().toString());
                item.setTitle(etTitle.getText().toString());
                item.setPrice(Double.parseDouble(price.getText().toString()));

                String[] strArray = tags.getText().toString().split(",");
                item.setTags(new ArrayList(Arrays.asList(strArray)));

                item.setImages(new ArrayList(Arrays.asList(new String[]{"https://cdn.gaudiumpress.org/wp-content/uploads/2021/04/13172034/Imagem-de-Cristo-construida-no-Rio-Grande-do-Sul-sera-a-maior-do-Brasil-1.jpg"})));

                if(id!= null && id != ""){
                    editItem(item);
                }else {
                    createItem(item);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        id = getIntent().getStringExtra("id");

        if(id!= null && id != ""){
            Item item = ItemDAO.getItensById(FormItemActivity.this, this.id);
            etTitle.setText(item.getTitle());
            description.setText(item.getDescription());
            price.setText(String.valueOf(item.getPrice()));
            tags.setText(String.join(",", item.getTags()));
        }

    }

    private void editItem(Item item){
        String json = Item.itemToJson(item);
        Request request = new Request(Request.SEVIDOR);
        EditeItem editeItem = new EditeItem("/api/item/" + item.getId(), json);
        editeItem.execute();
        finish();
    }

    private void createItem(Item item){
        String json = Item.itemToJson(item);

        Request request = new Request(Request.SEVIDOR);
        CreateItem createItem = new CreateItem("/api/item", json);
        createItem.execute();
        finish();
    }
}

