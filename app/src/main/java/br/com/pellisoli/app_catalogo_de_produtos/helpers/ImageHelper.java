package br.com.pellisoli.app_catalogo_de_produtos.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import br.com.pellisoli.app_catalogo_de_produtos.item.Item;

public class ImageHelper {

  public static final String BitMapToString(Bitmap bitmap){
    ByteArrayOutputStream baos=new  ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
    byte [] b=baos.toByteArray();
    String temp=Base64.encodeToString(b, Base64.DEFAULT);
    return temp;
  }

  public static final Bitmap StringToBitMap(String encodedString){
    try {
      byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
      Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
      return bitmap;
    } catch(Exception e) {
      e.getMessage();
      return null;
    }
  }
}
