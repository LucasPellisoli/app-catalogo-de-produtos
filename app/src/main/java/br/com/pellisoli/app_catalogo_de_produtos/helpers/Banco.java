package br.com.pellisoli.app_catalogo_de_produtos.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.ArrayList;

import br.com.pellisoli.app_catalogo_de_produtos.MainActivity;
import br.com.pellisoli.app_catalogo_de_produtos.item.ItemDAO;

public class Banco extends SQLiteOpenHelper {
  private static final int VERSAO = 1;
  private static final String NOME = "Catalogo_E_Shop";


  public Banco(Context context){
    super(context, NOME, null, VERSAO);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE IF NOT EXISTS Item ( " +
            "     id TEXT NOT NULL PRIMARY KEY," +
            "     title TEXT NOT NULL ," +
            "     description TEXT NOT NULL ," +
            "     price REAL NOT NULL ," +
            "     image_64 TEXT NOT NULL," +
            "     tags TEXT NOT NULL," +
            "     active NUMERIC NOT NULL )"
    );
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL("DROP TABLE Item");
    onCreate(db);
  }

}