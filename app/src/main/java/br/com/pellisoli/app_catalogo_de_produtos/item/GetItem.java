package br.com.pellisoli.app_catalogo_de_produtos.item;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.pellisoli.app_catalogo_de_produtos.MainActivity;
import br.com.pellisoli.app_catalogo_de_produtos.services.Request;

public class GetItem extends AsyncTask<String, String, String> {
  private MainActivity activity;
  private String path;

  public GetItem(MainActivity activity, String path) {
    this.path = path;
    this.activity = activity;
  }

  private void setInListView(List<Item> listItems){
//    ArrayAdapter adapter = new ArrayAdapter(this.activity, android.R.layout.simple_list_item_1, listItems);
    ItemAdapter itemAdapter = new ItemAdapter(this.activity, listItems);
    this.activity.listViewItem.setAdapter(itemAdapter);
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    this.activity.changeProgressiveBarStatus(true);

  }

  @Override
  protected String doInBackground(String... strings) {
    Request request = new Request(Request.SEVIDOR);
    return request.get(this.path);
  }

  @Override
  protected void onPostExecute(String resposta) {
    super.onPostExecute(resposta);

    if (resposta != null){
      List<Item> listItems = Item.jsonToItem(resposta);
      Log.d("RESPONSE", resposta);
      this.activity.listItems = listItems;
      this.activity.upadetList();
    }
    this.activity.changeProgressiveBarStatus(false);

  }
}
