package br.com.pellisoli.app_catalogo_de_produtos.item;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.pellisoli.app_catalogo_de_produtos.helpers.Banco;
import br.com.pellisoli.app_catalogo_de_produtos.helpers.ImageHelper;

public class ItemDAO {

  public static void insert(Item item, Context context){
    ContentValues valores = new ContentValues();
    valores.put("id", item.getId());
    valores.put("title", item.getTitle() );
    valores.put("description", item.getDescription());
    valores.put("price", item.getPrice());
    valores.put("image_64", ImageHelper.BitMapToString(item.getImage()));
    valores.put("active", item.getActive());
    valores.put("tags", "");

    Banco banco = new Banco(context);
    SQLiteDatabase db = banco.getWritableDatabase();

    db.insert("Item", null, valores);
  }

  public static List<Item> getItens(Context context){
    List<Item> lista = new ArrayList<>();
    Banco banco = new Banco(context);
    SQLiteDatabase db = banco.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT id, title, description,  price, image_64, tags, active FROM Item ORDER BY title", null );
    if( cursor.getCount() > 0 ){
      cursor.moveToFirst();
      do{
        Item item = new Item();
        item.setId(cursor.getString( 0));
        item.setTitle(cursor.getString( 1));
        item.setDescription(cursor.getString( 2));
        item.setPrice(cursor.getDouble( 3));
        item.setImage(ImageHelper.StringToBitMap(cursor.getString( 4)));
        item.setActive(true);
        lista.add( item );
      }while( cursor.moveToNext() );
    }
    return lista;
  }

  public static Item getItensById(Context context, String id){
    Banco banco = new Banco(context);
    SQLiteDatabase db = banco.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT id, title, description,  price, image_64, tags, active FROM Item WHERE id = '"+ id +"' ORDER BY title", null );
    if( cursor.getCount() > 0 ){
      cursor.moveToFirst();
      Item item = new Item();
      item.setId(cursor.getString( 0));
      item.setTitle(cursor.getString( 1));
      item.setDescription(cursor.getString( 2));
      item.setPrice(cursor.getDouble( 3));
      item.setImage(ImageHelper.StringToBitMap(cursor.getString( 4)));
      item.setActive(true);
      return item;
    }else{
      return null;
    }
  }
}

//            "     id TEXT NOT NULL PRIMARY KEY," +
//                    "     title TEXT NOT NULL ," +
//                    "     description TEXT NOT NULL ," +
//                    "     price REAL NOT NULL ," +
//                    "     image_64 TEXT NOT NULL," +
//                    "     tags TEXT NOT NULL," +
//                    "     active NUMERIC NOT NULL )"