package utils;

/**
 * Created by alberto on 19/04/2016.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.ClsParticipant;
import model.ClsPerson;

public class Empareja {
    public static void mezclaParticipantes (ArrayList<ClsParticipant> lsPart, Integer numIntentos){
        //ArrayList<ClsParticipant> res = new ArrayList<ClsParticipant>(lsPart);

        // A regala a B
        ArrayList<ClsPerson> a = new ArrayList<ClsPerson>();
        ArrayList<ClsPerson> b = new ArrayList<ClsPerson>();

        for(ClsParticipant participante: lsPart){
            a.add(participante.getData_person());
        }
        b.addAll(mezcla(a, numIntentos));

        // Añade el parámetro friend en el parcipante correspondiente
        for(int i = 0; i<a.size(); i++){
            lsPart.get(i).setData_friend(b.get(i));
        }
        //return res;
    }
    private static ArrayList<ClsPerson> mezcla (ArrayList<ClsPerson> ls, Integer numIntentos){
		/*
		 * copiamos en res la lista y barajamos tantas veces como numIntentos,
		 * si después sigue habiendo parejas optamos por hacer un simple shift
		 */

        ArrayList<ClsPerson> res = new ArrayList<ClsPerson>(ls);
        if(ls.size()>1){
            int i = 0;
            do{
                Collections.shuffle(res);
                i++;
            }
            while(numParejasCoincidentes(ls, res)!=0 && (numIntentos > i));
        }
        if(numParejasCoincidentes(ls, res)!=0){
            res = circula(ls, res);
        }
        return res;

    }
    private static ArrayList<ClsPerson> circula(ArrayList<ClsPerson> a, ArrayList<ClsPerson> b) {
		/*
		 * hace un shift a la derecha de un solo salto
		 */
        ArrayList<ClsPerson> res = new ArrayList <ClsPerson> ();
        res.addAll(a);
        ClsPerson aux = res.remove(0);
        res.add(a.size()-1, aux);

        return res;
    }

    private static Integer numParejasCoincidentes(ArrayList<ClsPerson> a, ArrayList<ClsPerson> b){
		/*
		 * Devuelve el número de parejas que coinciden, osea, el número de personas
		 * que tendrían que regalarse a si mismas
		 */
        Integer res = 0;
        for (int i=0; i<a.size(); i++){
            if(a.get(i).equals(b.get(i))) res++;
        }
        return res;
    }

} // end Empareja
