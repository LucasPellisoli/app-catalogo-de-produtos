package br.com.pellisoli.app_catalogo_de_produtos.item;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.pellisoli.app_catalogo_de_produtos.services.Request;

public class EditeItem extends AsyncTask<String, String, String> {
  private String path;
  private String data;

  public EditeItem(String path, String data) {
    this.path = path;
    this.data = data;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected String doInBackground(String... strings) {
    Request request = new Request(Request.SEVIDOR);
    return request.put(this.path, data);
  }

  @Override
  protected void onPostExecute(String resposta) {
    super.onPostExecute(resposta);
    if (resposta != null) {
      List<Item> listItems = Item.jsonToItem(resposta);
      Log.d("RESPONSE", resposta);
    }
  }
}
