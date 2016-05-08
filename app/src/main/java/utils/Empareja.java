package utils;

import java.util.ArrayList;
import java.util.Collections;

import model.ClsParticipant;
import model.ClsPerson;

public class Empareja {

    private static final Integer numIntentos = 10;

    public static void Participantes (ArrayList<ClsParticipant> lsPart){
        /*
        modifica una lista de participantes en un evento para que ningún ClsPerson se
        tenga que regalar a si mismo.
        Mediante un suffle n-veces y luego un shift a la derecha
         */
        //ArrayList<ClsParticipant> res = new ArrayList<ClsParticipant>(lsPart);

        // A regala a B
        ArrayList<ClsPerson> a = new ArrayList<ClsPerson>();
        ArrayList<ClsPerson> b = new ArrayList<ClsPerson>();

        for(ClsParticipant participante: lsPart){
            a.add(participante.getData_person());
        }
        b.addAll(mezcla(a));

        // Añade el parámetro friend en el parcipante correspondiente
        for(int i = 0; i<a.size(); i++){
            lsPart.get(i).setData_friend(b.get(i).getData_id_person());
        }

        //return res;
    }
    private static ArrayList<ClsPerson> mezcla (ArrayList<ClsPerson> ls){
		/*
		 * Copiamos la lista en res y barajamos tantas veces como numIntentos.
		 * Si después sigue habiendo parejas entonces optamos por hacer un simple shift
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
		 * Realiza un shift a la derecha de un solo salto
		 */
        ArrayList<ClsPerson> res = new ArrayList <ClsPerson> ();
        res.addAll(a);
        ClsPerson aux = res.remove(0);
        res.add(a.size()-1, aux);
        return res;
    }

    private static Integer numParejasCoincidentes (ArrayList<ClsPerson> a, ArrayList<ClsPerson> b){
		/*
		 * Devuelve el número de parejas que coinciden, es decir,
		 * el número de personas que tendrían que regalarse a si mismas
		 */
        Integer res = 0;
        for (int i=0; i<a.size(); i++){
            if(a.get(i).equals(b.get(i))) res++;
        }
        return res;
    }

}