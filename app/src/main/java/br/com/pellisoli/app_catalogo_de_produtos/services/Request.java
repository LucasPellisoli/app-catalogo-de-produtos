package br.com.pellisoli.app_catalogo_de_produtos.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {
  public static final String SEVIDOR = "http://pellisoli.com.br";
  public String baseUrl;

  public Request(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  private void requestBody(DataOutputStream saida, String... strings){
    String parametros = "";

    for(String s: strings){
      parametros += s;
    }
    try {
      saida.writeBytes(parametros);
      saida.flush();
      saida.close();
    }catch (Exception e){
      Log.e("requestBody", e.getMessage());
    }finally {

    }
  }
  private String requestResponse(InputStream entrada){
    try {

    BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(entrada, "UTF-8"));
    String linha;
    StringBuffer resposta = new StringBuffer();

    while ( (linha=bufferedReader.readLine()) != null){
      resposta.append(linha);
      resposta.append("...");
    }

    bufferedReader.close();
    return resposta.toString();

    }catch (Exception e){
      Log.e("requestResponse", e.getMessage());
    }finally {

    }
    return  null;
  }

  public String get(String path){
    HttpURLConnection conn;

    try {

      URL url = new URL( this.baseUrl + path);

      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestProperty("charset", "utf-8");
      conn.setRequestProperty("Cache-Control", "no-cache");

      conn.setDefaultUseCaches(false);
      conn.setUseCaches(false);
      return this.requestResponse(conn.getInputStream());

    }catch (Exception e){
      Log.e("REQUEST GET", e.getMessage());
    }finally {

    }
    return  null;
  }

  public String post(String  path, String... strings){
    HttpURLConnection conn;

    try {

      URL url = new URL( this.baseUrl + path);

      conn = (HttpURLConnection) url.openConnection();

      conn.setRequestMethod("POST");

      conn.setRequestProperty("Content-Type", "application/json");
      conn.setDoInput(true);
      conn.setDoOutput(true);
      conn.setUseCaches(false);
      conn.setRequestProperty("charset", "utf-8");


      this.requestBody(new DataOutputStream(conn.getOutputStream()), strings);

      return this.requestResponse(conn.getInputStream());

    }catch (Exception e){
      Log.e("REQUEST POST", e.getMessage());
    }finally {

    }
    return  null;
  }

  public String put(String  path, String... strings){
    HttpURLConnection conn;

    try {

      URL url = new URL( this.baseUrl + path);

      conn = (HttpURLConnection) url.openConnection();

      conn.setRequestMethod("PUT");

      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      conn.setDoInput(true);
      conn.setDoOutput(true);
      conn.setUseCaches(false);
      conn.setRequestProperty("charset", "utf-8");


      this.requestBody(new DataOutputStream(conn.getOutputStream()), strings);

      return this.requestResponse(conn.getInputStream());

    }catch (Exception e){
      Log.e("REQUEST POST", e.getMessage());
    }finally {

    }
    return  null;
  }

  public static boolean isNetwork(Context context){
    ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    return conn.getActiveNetworkInfo().isConnected();
  };

}
