package adaptador;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import etsii.cm.amigoinvisible.R;
import model.ClsEvent;

public class ListadoEventos_Adapter extends BaseAdapter {
    Context context;
    ArrayList<ClsEvent> lstEvents;

    public ListadoEventos_Adapter(Context context, ArrayList<ClsEvent> lstEvents) {
        this.context = context;
        this.lstEvents = lstEvents;
    }

    @Override
    public int getCount() {
        return lstEvents.size();
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
        View itemView = inflater.inflate(R.layout.plantilla_lista_eventos, parent, false);

        ImageView imgVwEventPhoto = (ImageView) itemView.findViewById(R.id.imgVwEventPhoto);
        TextView  txtVwEventName  = (TextView)  itemView.findViewById(R.id.txtVwEventName);
        TextView  txtVwEventDate  = (TextView)  itemView.findViewById(R.id.txtVwEventDate);

        txtVwEventName.setText(lstEvents.get(position).getData_name());
        txtVwEventDate.setText(lstEvents.get(position).getData_date_text());

        if (lstEvents.get(position).getData_photo() != null) {
            imgVwEventPhoto.setImageBitmap(lstEvents.get(position).getData_photo());
        }

        return itemView;
    }
}
