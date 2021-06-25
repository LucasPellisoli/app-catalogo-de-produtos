package br.com.pellisoli.app_catalogo_de_produtos.item;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.com.pellisoli.app_catalogo_de_produtos.LoginActivity;
import br.com.pellisoli.app_catalogo_de_produtos.services.Request;

public class Login extends AsyncTask<String, String, String> {
  private String path;
  private String data;
  private Context ct;

  public Login(String path, String data, Context ct) {
    this.path = path;
    this.data = data;
    this.ct = ct;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected String doInBackground(String... strings) {
    Request request = new Request(Request.SEVIDOR);
    return request.post(this.path, data);
  }

  @Override
  protected void onPostExecute(String resposta) {
    super.onPostExecute(resposta);
    if (resposta != null) {
      Log.d("RESPONSE", resposta);
      SharedPreferences preferences = ct.getSharedPreferences("br.com.pellisoli", Context.MODE_PRIVATE);

      JSONObject jsonObject = null;
      String token = "";

      try {
        jsonObject = new JSONObject(resposta);
        token = jsonObject.getString("token");
      } catch (JSONException e) {
        e.printStackTrace();
      }
      SharedPreferences.Editor editor = preferences.edit();
      editor.putString("userToken", token);
      editor.apply();
    }
  }

}
