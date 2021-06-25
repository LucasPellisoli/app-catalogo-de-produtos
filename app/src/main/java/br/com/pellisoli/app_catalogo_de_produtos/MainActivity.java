package br.com.pellisoli.app_catalogo_de_produtos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
  private EditText search_menu_open_input;
  private Button registerItem;
  private Button login;
  private Button sair;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SharedPreferences preferences = this.getSharedPreferences("br.com.pellisoli", Context.MODE_PRIVATE);

    this.listItems = new ArrayList<>();

    listViewItem = findViewById(R.id.listViewItem);
    progressBarLoadProducts = findViewById(R.id.progressBarLoadProducts);

    progressBarLoadProducts.setVisibility(View.INVISIBLE);
    progressBarLoadProducts.setActivated(false);

    appBarLayout = findViewById(R.id.AppBarLayout);
    materialToolbar = findViewById(R.id.MaterialToolbar);
    linearLayout_header = findViewById(R.id.LinearLayout_header);

    search_menu_open_input = findViewById(R.id.search_menu_open_input);
    registerItem = findViewById(R.id.btnregisterItem);

    login = findViewById(R.id.login_btn2);
    sair = findViewById(R.id.sair_btn);

    if (Request.isNetwork(getApplicationContext())){
      Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(getApplicationContext(), "Internet Is Not Connected", Toast.LENGTH_SHORT).show();
    }

    String userToken = preferences.getString("userToken", "");
    if(!userToken.equalsIgnoreCase(""))
    {
      registerItem.setVisibility(View.VISIBLE);
      login.setVisibility(View.INVISIBLE);
      sair.setVisibility(View.VISIBLE);
    }else {
      registerItem.setVisibility(View.INVISIBLE);
      login.setVisibility(View.VISIBLE);
      sair.setVisibility(View.INVISIBLE);
    }

    registerItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, FormItemActivity.class);
        startActivity( intent );
      }
    });

    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity( intent );
      }
    });

    sair.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userToken", "");
        editor.apply();
        onResume();
      }
    });

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

    search_menu_open_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_DONE) {
          // TODO do something
          Log.d("EDITTEXT", "search_menu_open_input: "+ search_menu_open_input.getText());
          Intent intent = new Intent(MainActivity.this, ListItemActivity.class);
          intent.putExtra("query", search_menu_open_input.getText().toString());
          startActivity( intent );
        }
        return false;
      }
    });

    search_menu_open_input.setOnTouchListener(new TextView.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if(event.getAction() == MotionEvent.ACTION_UP) {
          if(event.getRawX() >= (search_menu_open_input.getRight() - search_menu_open_input.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
            // your action here
            Log.d("EDITTEXT", "search_menu_open_input: "+ search_menu_open_input.getText());
            Intent intent = new Intent(MainActivity.this, ListItemActivity.class);
            intent.putExtra("query", search_menu_open_input.getText().toString());
            startActivity( intent );
          }
        }
        return false;
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    GetItem getItem = new GetItem(MainActivity.this, "/api/item");
    getItem.execute();
    search_menu_open_input.setText("");

    SharedPreferences preferences = this.getSharedPreferences("br.com.pellisoli", Context.MODE_PRIVATE);
    String userToken = preferences.getString("userToken", "");
    if(!userToken.equalsIgnoreCase(""))
    {
      registerItem.setVisibility(View.VISIBLE);
      login.setVisibility(View.INVISIBLE);
      sair.setVisibility(View.VISIBLE);
    }else {
      registerItem.setVisibility(View.INVISIBLE);
      login.setVisibility(View.VISIBLE);
      sair.setVisibility(View.INVISIBLE);
    }
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