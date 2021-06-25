package br.com.pellisoli.app_catalogo_de_produtos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.pellisoli.app_catalogo_de_produtos.item.Item;
import br.com.pellisoli.app_catalogo_de_produtos.item.ItemDAO;

public class ItemDetailsActivity extends AppCompatActivity {

  private ImageView imageView;
  private TextView title;
  private TextView price;
  private  TextView description;
  private  TextView tags;
  private String itemID;
  private Button btnEdit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_details);

    imageView = findViewById(R.id.item_details_view_image);
    title = findViewById(R.id.item_details_view_title);
    price = findViewById(R.id.item_details_view_price);
    description = findViewById(R.id.item_details_view_description);
    tags = findViewById(R.id.item_details_view_tags);
    btnEdit = findViewById(R.id.bt_editar);

    SharedPreferences preferences = this.getSharedPreferences("br.com.pellisoli", Context.MODE_PRIVATE);

    this.itemID = this.getIntent().getStringExtra("itemID");
    Log.d("itemID", itemID);


    btnEdit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(ItemDetailsActivity.this, FormItemActivity.class);
        intent.putExtra("id", itemID);
        startActivity( intent );
      }
    });
    String userToken = preferences.getString("userToken", "");
    if(!userToken.equalsIgnoreCase(""))
    {
      btnEdit.setVisibility(View.VISIBLE);
    }else {
      btnEdit.setVisibility(View.INVISIBLE);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    Item item = ItemDAO.getItensById(ItemDetailsActivity.this, this.itemID);

    imageView.setImageBitmap(item.getImage());
    title.setText(item.getTitle());
    price.setText(item.getPriceFormated());
    description.setText(item.getDescription());
    if(item.getTags() != null){
        tags.setText(String.join(" | ", item.getTags()));
    }else{
      tags.setVisibility(View.INVISIBLE);
    }
    Log.d("item", item.toString());

    SharedPreferences preferences = this.getSharedPreferences("br.com.pellisoli", Context.MODE_PRIVATE);
    String userToken = preferences.getString("userToken", "");
    if(!userToken.equalsIgnoreCase(""))
    {
      btnEdit.setVisibility(View.VISIBLE);
    }else {
      btnEdit.setVisibility(View.INVISIBLE);
    }

  }
}