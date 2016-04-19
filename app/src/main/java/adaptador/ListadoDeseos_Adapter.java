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

public class ListadoDeseos_Adapter extends BaseAdapter {

    Context context;
    ClsMyFriend alguien;

    public ListadoDeseos_Adapter(Context context, ClsMyFriend alguien) {
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
        View itemView = inflater.inflate(R.layout.plantilla_lista_deseos, parent, false);

        ImageView WishPhoto  = (ImageView) itemView.findViewById(R.id.imgVwWishPhoto);
        TextView  WishName   = (TextView)  itemView.findViewById(R.id.txtVwWishtName);

        if (alguien.getData_wish().get(position).getData_photo()==null)
            WishPhoto.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.caja_editable));
        else
            WishPhoto.setImageBitmap(alguien.getData_wish().get(position).getData_photo());

        WishName.setText(alguien.getData_wish().get(position).getData_text());

        return itemView;
    }
}