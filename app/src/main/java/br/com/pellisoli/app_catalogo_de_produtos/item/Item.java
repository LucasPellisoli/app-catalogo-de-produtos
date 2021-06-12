package br.com.pellisoli.app_catalogo_de_produtos.item;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Item {
  private String id;
  private String title;
  private String description;
  private double price;
  private ArrayList<String> images;
  private ArrayList<String> tags;
  private Boolean active;
  private Bitmap image;
  public Item() {
  }

  public Item(String title, String description, double price, ArrayList<String> images, Boolean active) {
    this.title = title;
    this.description = description;
    this.price = price;
    this.images = images;
    this.active = active;
  }

  public Item(String id, String title, String description, double price, ArrayList<String> images, Boolean active) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.price = price;
    this.images = images;
    this.active = active;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }
  public String getPriceFormated() {
    return "R$" + price;
  }
  public void setPrice(double price) {
    this.price = price;
  }

  public ArrayList<String> getImages() {
    return images;
  }

  public void setImages(ArrayList<String> images) {
    this.images = images;
  }

  public Bitmap getImage() {
    return image;
  }

  public void setImage(Bitmap image) {
    this.image = image;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }


  @NonNull
  @Override
  public String toString() {
    return title + " R$ "+price;
  }

  public static List<Item> jsonToItem(String json){
    List<Item> list = new ArrayList<>();

    try {
      JSONArray array = new JSONArray(json);
      if (array.length() > 0){
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonItem = array.getJSONObject(i);

            Item item = new Item();
            item.setId(jsonItem.getString("id"));
            item.setTitle(jsonItem.getString("title"));
            item.setDescription(jsonItem.getString("description"));
            item.setPrice(jsonItem.getDouble("price"));
            item.setActive(jsonItem.getBoolean("active"));

            ArrayList<String> imagens = new ArrayList<String>();
            JSONArray arrayImages = jsonItem.getJSONArray("images");
            for (int j = 0; j < arrayImages.length(); j++) {
              imagens.add(arrayImages.getString(j));
            }
            item.setImages(imagens);

            list.add(item);
          }
      }
    }catch (JSONException e){

    }
    return  list;
  }
}