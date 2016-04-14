package etsii.cm.amigoinvisible;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import model.ClsEvent;

public class Adaptador_lista_eventos extends BaseAdapter {
    Context context;
    ArrayList<ClsEvent> lstEvents;

    public Adaptador_lista_eventos(Context context, ArrayList<ClsEvent> lstEvents) {
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

        ImageView EventPhoto = (ImageView) itemView.findViewById(R.id.imgVwEventPhoto);
        TextView  EventName  = (TextView)  itemView.findViewById(R.id.txtVwEventName);
        TextView  EventDate  = (TextView)  itemView.findViewById(R.id.txtVwEventDate);

        EventName.setText(lstEvents.get(position).getData_name());
        EventDate.setText(lstEvents.get(position).getData_date_text());
        EventPhoto.setImageBitmap(lstEvents.get(position).getData_photo());

        return itemView;
    }
}
