package etsii.cm.amigoinvisible;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import model.ClsMyFriend;

public class Adaptador_Lista_Deseos extends BaseAdapter {

    Context context;
    ClsMyFriend miAmigo;

    public Adaptador_Lista_Deseos(Context context, ClsMyFriend miAmigo) {
        this.context = context;
        this.miAmigo = miAmigo;
    }

    @Override
    public int getCount() {
        return miAmigo.getData_wish().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.plantilla_lista_deseos, parent, false);

        TextView  WishName  = (TextView)  itemView.findViewById(R.id.txtVwWishtName);
        ImageView WishPhoto = (ImageView) itemView.findViewById(R.id.imgVwWishPhoto);

        WishName.setText(miAmigo.getData_wish().get(position).getData_text());
        WishPhoto.setImageBitmap(miAmigo.getData_wish().get(position).getData_photo());

        return itemView;
    }
}