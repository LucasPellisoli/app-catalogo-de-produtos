package br.com.pellisoli.app_catalogo_de_produtos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.pellisoli.app_catalogo_de_produtos.item.Item;
import br.com.pellisoli.app_catalogo_de_produtos.item.ItemDAO;

public class ItemDetailsActivity extends AppCompatActivity {

  private ImageView imageView;
  private TextView title;
  private TextView price;
  private  TextView description;
  private  TextView tags;
  private String itemID;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_details);

    imageView = findViewById(R.id.item_details_view_image);
    title = findViewById(R.id.item_details_view_title);
    price = findViewById(R.id.item_details_view_price);
    description = findViewById(R.id.item_details_view_description);
    tags = findViewById(R.id.item_details_view_tags);
    this.itemID = this.getIntent().getStringExtra("itemID");
    Log.d("itemID", itemID);
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

  }
}