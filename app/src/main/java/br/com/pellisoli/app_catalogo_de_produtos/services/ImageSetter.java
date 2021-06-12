package br.com.pellisoli.app_catalogo_de_produtos.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Set;

import br.com.pellisoli.app_catalogo_de_produtos.R;
import br.com.pellisoli.app_catalogo_de_produtos.item.Item;

public class ImageSetter extends AsyncTask<String, Void, Bitmap> {
  private ImageView bmImage;
  private Item item;

  public ImageSetter(ImageView bmImage, Item item ) {
    this.bmImage = bmImage;
    this.item = item;
  }

  private String BitMapToString(Bitmap bitmap){
    ByteArrayOutputStream baos=new  ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
    byte [] b=baos.toByteArray();
    String temp=Base64.encodeToString(b, Base64.DEFAULT);
    return temp;
  }

  private Bitmap StringToBitMap(String encodedString){
    try {
      byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
      Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
      return bitmap;
    } catch(Exception e) {
      e.getMessage();
      return null;
    }
  }
  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    bmImage.setImageResource(R.drawable.ic_launcher_foreground);
  }

  @Override
  protected Bitmap doInBackground(String... urls) {
    String urldisplay = this.item.getImages().get(0);
    Bitmap mIcon11 = null;
    try {
      InputStream in = new java.net.URL(urldisplay).openStream();
      mIcon11 = BitmapFactory.decodeStream(in);
    } catch (Exception e) {
      Log.e("Error", e.getMessage());
      e.printStackTrace();
    }
    this.item.setImage(mIcon11);
    return mIcon11;
  }

  @Override
  protected void onPostExecute(Bitmap result) {
    bmImage.setImageBitmap(result);
  }
}