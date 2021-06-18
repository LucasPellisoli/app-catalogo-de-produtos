package br.com.pellisoli.app_catalogo_de_produtos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import br.com.pellisoli.app_catalogo_de_produtos.helpers.AppBarStateChangeListener;
import br.com.pellisoli.app_catalogo_de_produtos.item.GetItem;
import br.com.pellisoli.app_catalogo_de_produtos.item.Item;
import br.com.pellisoli.app_catalogo_de_produtos.item.ItemAdapter;
import br.com.pellisoli.app_catalogo_de_produtos.item.ItemDAO;
import br.com.pellisoli.app_catalogo_de_produtos.services.Request;

public class MainActivity extends AppCompatActivity {

  public ListView listViewItem;
  public ProgressBar progressBarLoadProducts;
  public List<Item> listItems;
  public ItemAdapter itemAdapter;
  private AppBarLayout appBarLayout;
  private MaterialToolbar materialToolbar;
  private LinearLayout linearLayout_header;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    this.listItems = new ArrayList<>();

    listViewItem = findViewById(R.id.listViewItem);
    progressBarLoadProducts = findViewById(R.id.progressBarLoadProducts);

    progressBarLoadProducts.setVisibility(View.INVISIBLE);
    progressBarLoadProducts.setActivated(false);

    appBarLayout = findViewById(R.id.AppBarLayout);
    materialToolbar = findViewById(R.id.MaterialToolbar);
    linearLayout_header = findViewById(R.id.LinearLayout_header);
    if (Request.isNetwork(getApplicationContext())){

      Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();

    } else {

      Toast.makeText(getApplicationContext(), "Internet Is Not Connected", Toast.LENGTH_SHORT).show();
    }

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
        ItemDAO.insert(item, MainActivity.this);
        Intent intent = new Intent(MainActivity.this, ItemDetailsActivity.class);
        intent.putExtra("itemID", item.getId());
        startActivity( intent );
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    GetItem getItem = new GetItem(MainActivity.this, "/api/item");
    getItem.execute();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    getMenuInflater().inflate(R.menu.search_menu,menu);
    MenuItem menuItem = menu.findItem(R.id.search_menu);
    SearchView searchView = (SearchView) menuItem.getActionView();

    searchView.setQueryHint("pesquisar");

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
        searchView.clearFocus();
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });

    return super.onCreateOptionsMenu(menu);
  }

  public void upadetList(){
    this.itemAdapter = new ItemAdapter(MainActivity.this, listItems);
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

  // TODO: remover android:usesCleartextTraffic="true" do manifest quando tiver HTTPS;
}