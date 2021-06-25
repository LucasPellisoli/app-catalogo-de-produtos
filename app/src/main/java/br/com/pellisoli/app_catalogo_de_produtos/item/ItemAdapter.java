package br.com.pellisoli.app_catalogo_de_produtos.item;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import br.com.pellisoli.app_catalogo_de_produtos.R;
import br.com.pellisoli.app_catalogo_de_produtos.services.ImageSetter;

public class ItemAdapter  extends BaseAdapter {
  private List<Item> listItem;
  private final Activity activity;

  public ItemAdapter(@NonNull Activity activity, List<Item> listItem) {
    this.listItem = listItem;
    this.activity = activity;
  }

  public List<Item> getListItem() {
    return listItem;
  }

  public void setListItem(List<Item> listItem) {
    this.listItem = listItem;
  }

  @Override
  public int getCount() {
    return this.listItem.size();
  }

  @Override
  public Object getItem(int position) {
    return this.listItem.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = this.activity.getLayoutInflater().inflate(R.layout.list_view_item, parent, false);
    Item item = this.listItem.get(position);

    ImageView imageView = view.findViewById(R.id.item_view_image);
    TextView title = view.findViewById(R.id.item_view_title);
    TextView price = view.findViewById(R.id.item_view_price);


    title.setText(item.getTitle());
    price.setText(item.getPriceFormated());
    if (item.getImage() != null) {
      imageView.setImageBitmap(item.getImage());
    } else {
      ImageSetter imageSetter = new ImageSetter(imageView, item);
      imageSetter.execute();
    }
    Log.d("ITEM RENDER: ", item.getTitle());
    return view;
  }
}
