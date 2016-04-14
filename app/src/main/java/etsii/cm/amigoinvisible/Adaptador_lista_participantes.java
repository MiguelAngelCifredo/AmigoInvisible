package etsii.cm.amigoinvisible;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import model.ClsParticipant;

public class Adaptador_lista_participantes extends BaseAdapter {

    Context context;
    ArrayList<ClsParticipant> lstParticipants;

    public Adaptador_lista_participantes(Context context, ArrayList<ClsParticipant> lstParticipants) {
        this.context = context;
        this.lstParticipants = lstParticipants;
    }

    @Override
    public int getCount() {
        return lstParticipants.size();
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
        View itemView = inflater.inflate(R.layout.plantilla_lista_participantes, parent, false);

        ImageView ParticipantPhoto = (ImageView) itemView.findViewById(R.id.imgVwParticipantPhoto);
        TextView  ParticipantName  = (TextView)  itemView.findViewById(R.id.txtVwParticipantName);

        ParticipantPhoto.setImageBitmap(lstParticipants.get(position).getData_person().getData_photo());
        ParticipantName.setText(lstParticipants.get(position).getData_person().getData_name());

        return itemView;
    }
}
