package adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import etsii.cm.amigoinvisible.R;
import model.ClsPerson;

public class ListadoContactos_Adapter extends BaseAdapter{
    Context context;
    ArrayList<ClsPerson> lstContacts;

    public ListadoContactos_Adapter(Context context, ArrayList<ClsPerson> lstContacts) {
        this.context = context;
        this.lstContacts = lstContacts;
    }

    @Override
    public int getCount() {
        return lstContacts.size();
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
        View itemView = inflater.inflate(R.layout.plantilla_lista_contactos, parent, false);

        ImageView imgContactPhoto = (ImageView) itemView.findViewById(R.id.imgContactPhoto);
        TextView  txtContactName  = (TextView)  itemView.findViewById(R.id.txtContactName);

        if (lstContacts.get(position).getData_photo() != null) {
            imgContactPhoto.setImageBitmap(lstContacts.get(position).getData_photo());
        }

        txtContactName.setText(lstContacts.get(position).getData_name());

        return itemView;
    }
}