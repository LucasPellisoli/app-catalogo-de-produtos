package br.com.pellisoli.app_catalogo_de_produtos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import br.com.pellisoli.app_catalogo_de_produtos.helpers.AppBarStateChangeListener;
import br.com.pellisoli.app_catalogo_de_produtos.item.GetItem;
import br.com.pellisoli.app_catalogo_de_produtos.item.Item;
import br.com.pellisoli.app_catalogo_de_produtos.item.ItemAdapter;
import br.com.pellisoli.app_catalogo_de_produtos.item.ItemDAO;

public class ListItemActivity extends AppCompatActivity {

  public ListView listViewItem;
  public ProgressBar progressBarLoadProducts;
  public List<Item> listItems;
  public ItemAdapter itemAdapter;
  private AppBarLayout appBarLayout;
  private MaterialToolbar materialToolbar;
  private LinearLayout linearLayout_header;
  public String search_query;
  private EditText search_menu_open_input;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_item);
    this.listItems = new ArrayList<>();

    listViewItem = findViewById(R.id.listViewItem);
    progressBarLoadProducts = findViewById(R.id.progressBarLoadProducts);

    progressBarLoadProducts.setVisibility(View.INVISIBLE);
    progressBarLoadProducts.setActivated(false);

    appBarLayout = findViewById(R.id.AppBarLayout);
    materialToolbar = findViewById(R.id.MaterialToolbar);
    appBarLayout = findViewById(R.id.AppBarLayout);
    materialToolbar = findViewById(R.id.MaterialToolbar);
    linearLayout_header = findViewById(R.id.LinearLayout_header);

    search_menu_open_input = findViewById(R.id.search_menu_open_input);
    appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
      @Override
      public void onStateChanged(AppBarLayout appBarLayout, State state) {
        Log.d("STATE", state.name());
        if(state.name() == "COLLAPSED"){
          materialToolbar.setVisibility(View.VISIBLE);
          linearLayout_header.setVisibility(View.INVISIBLE);
        }else{
          materialToolbar.setVisibility(View.INVISIBLE);
          linearLayout_header.setVisibility(View.VISIBLE);
        }
      }
    });

    listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = listItems.get(position);
        ItemDAO.insert(item, ListItemActivity.this);
        Intent intent = new Intent(ListItemActivity.this, ItemDetailsActivity.class);
        intent.putExtra("itemID", item.getId());
        startActivity( intent );
      }
    });

    search_query = this.getIntent().getStringExtra("query");
    search_menu_open_input.setText(search_query);
  }

  @Override
  protected void onResume() {
    super.onResume();
    GetItem getItem = new GetItem( ListItemActivity.this, "/api/item/search/" + search_query);
    getItem.execute();

  }

  public void upadetList(){
    this.itemAdapter = new ItemAdapter(ListItemActivity.this, listItems);
    this.listViewItem.setAdapter(itemAdapter);
  }

  public void changeProgressiveBarStatus(boolean status){

    if(status){
      this.progressBarLoadProducts.setActivated(true);
      this.progressBarLoadProducts.setVisibility(View.VISIBLE);
    }else{
      this.progressBarLoadProducts.setActivated(false);
      this.progressBarLoadProducts.setVisibility(View.INVISIBLE);
    }
  }
}