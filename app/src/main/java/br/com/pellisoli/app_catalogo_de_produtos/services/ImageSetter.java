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