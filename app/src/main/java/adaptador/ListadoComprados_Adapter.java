package adaptador;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import etsii.cm.amigoinvisible.R;
import model.ClsMyFriend;

public class ListadoComprados_Adapter extends BaseAdapter {

    Context context;
    ClsMyFriend alguien;

    public ListadoComprados_Adapter(Context context, ClsMyFriend alguien) {
        this.context = context;
        this.alguien = alguien;
    }

    @Override
    public int getCount() {
        return alguien.getData_wish().size();
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
        View itemView = inflater.inflate(R.layout.plantilla_lista_comprados, parent, false);

        ImageView imgVwWishPhoto  = (ImageView) itemView.findViewById(R.id.imgVwWishPhoto);
        TextView  txtVwWishtName  = (TextView)  itemView.findViewById(R.id.txtVwWishtName);
        ImageView imgVwWishBought = (ImageView) itemView.findViewById(R.id.imgVwWishBought);

        if (alguien.getData_wish().get(position).getData_photo() != null) {
            imgVwWishPhoto.setImageBitmap(alguien.getData_wish().get(position).getData_photo());
        }

        txtVwWishtName.setText(alguien.getData_wish().get(position).getData_text());

        if (alguien.getData_wish().get(position).getData_bought().equals("Y")) {
            imgVwWishBought.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.check));
        }

        return itemView;
    }
}