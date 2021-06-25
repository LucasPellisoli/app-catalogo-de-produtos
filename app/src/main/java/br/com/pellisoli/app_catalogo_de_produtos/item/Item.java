package br.com.pellisoli.app_catalogo_de_produtos.item;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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

  public Item(String id, String title, String description, double price, ArrayList<String> images, ArrayList<String> tags, Boolean active) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.price = price;
    this.images = images;
    this.tags = tags;
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

  public ArrayList<String> getTags() {
    return tags;
  }

  public void setTags(ArrayList<String> tags) {
    this.tags = tags;
  }

  public String getTagsToString(){
    return  String.join(", ", this.tags);
  }

  public void setTagsByString(String tags){
    String[] parts = tags.split(",");
    ArrayList<String> tagsArray = new ArrayList<String>(Arrays.asList(parts));
    setTags(tagsArray);
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

            ArrayList<String> tagItem = new ArrayList<>();
            JSONArray tagsArray = jsonItem.getJSONArray("tags");
            for (int j = 0; j < tagsArray.length(); j++) {
              JSONObject jsonTag = tagsArray.getJSONObject(j);
              tagItem.add(jsonTag.getString("value"));
            }
            item.setTags(tagItem);
            list.add(item);
          }
      }
    }catch (JSONException e){
        Log.e("JsonToItem", e.getMessage());
    }
    return  list;
  }

  public static String itemToJson(Item item){
    String json = "{";

    if(item.id != null && !item.id.isEmpty())
      json += "\"id\": \"" + item.id + "\",";

    if(item.description != null && !item.description.isEmpty())
      json += "\"description\": \"" +  item.description + "\",";

    if(item.title != null && !item.title.isEmpty())
      json += "\"title\": \"" + item.title  + "\",";

    json += "\"price\": \"" + item.price + "\",";

    if(item.images != null && !item.images.isEmpty())
      json += "\"images\": [\"" + item.images.get(0) + "\"],";

    if(item.tags != null && !item.tags.isEmpty()){
      json += "\"tags\": [";

      for (String tag : item.tags)
        json += "{\"value\": \""+ tag +"\", \"type\": { \"id\": 1, \"description\": \"padrao\", \"visible\": true, \"list\": false, \"listvalues\": \"\", \"active\": true\n }, \"active\": true\n}";

      json += "],";
    }
    json += "\"active\": true";

    return  json += "}";
  }


}