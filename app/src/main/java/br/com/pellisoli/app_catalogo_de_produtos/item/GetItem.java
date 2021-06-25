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

import br.com.pellisoli.app_catalogo_de_produtos.ListItemActivity;
import br.com.pellisoli.app_catalogo_de_produtos.MainActivity;
import br.com.pellisoli.app_catalogo_de_produtos.services.Request;

public class GetItem extends AsyncTask<String, String, String> {
  private MainActivity main_activity;
  private ListItemActivity list_activity;
  private String path;

  public GetItem(MainActivity activity, String path) {
    this.path = path;
    this.main_activity = activity;
  }

  public GetItem(ListItemActivity activity, String path) {
    this.path = path;
    this.list_activity = activity;
  }

  private void setInListView(List<Item> listItems){
//    ArrayAdapter adapter = new ArrayAdapter(this.activity, android.R.layout.simple_list_item_1, listItems);
    if(this.main_activity != null){
      ItemAdapter itemAdapter = new ItemAdapter(this.main_activity, listItems);
      this.main_activity.listViewItem.setAdapter(itemAdapter);
    }else {
      ItemAdapter itemAdapter = new ItemAdapter(this.list_activity, listItems);
      this.list_activity.listViewItem.setAdapter(itemAdapter);
    }

  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    if(this.main_activity != null){
      this.main_activity.changeProgressiveBarStatus(true);
    }else {
      this.list_activity.changeProgressiveBarStatus(true);
    }
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


      if(this.main_activity != null){
        this.main_activity.listItems = listItems;
        this.main_activity.upadetList();
      }else {
        this.list_activity.listItems = listItems;
        this.list_activity.upadetList();
      }
    }

    if(this.main_activity != null){
      this.main_activity.changeProgressiveBarStatus(false);
    }else {
      this.list_activity.changeProgressiveBarStatus(false);
    }

  }
}
