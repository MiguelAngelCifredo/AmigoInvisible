package etsii.cm.amigoinvisible;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador_ListaIconoTexto extends BaseAdapter {

    Context context;
    ArrayList<String> titulos;
    ArrayList<Bitmap> imagenes;
    LayoutInflater inflater;

    public Adaptador_ListaIconoTexto(Context context, ArrayList<String> titulos, ArrayList<Bitmap> imagenes) {
        this.context = context;
        this.titulos = titulos;
        this.imagenes = imagenes;
    }

    @Override
    public int getCount() {
        return titulos.size();
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
        TextView txtTitle;
        ImageView imgImg;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.lista_icono_texto, parent, false);

        txtTitle = (TextView) itemView.findViewById(R.id.tituloLista);
        imgImg = (ImageView) itemView.findViewById(R.id.iconLista);

        txtTitle.setText(titulos.get(position));
        imgImg.setImageBitmap(imagenes.get(position));

        return itemView;
    }
}