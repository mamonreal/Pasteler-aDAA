package Pasteleria;

import java.util.*;

public class Cola {
    private ArrayList <Nodo> cola = new ArrayList();
    
    public void add (Nodo n) {
        cola.add(n);
    }
    
    public void remove (Nodo n) {
        cola.remove(n);
    }
    
    public boolean isEmpty () {
        return cola.isEmpty();
    }
    
    public Nodo getNodo() {
        return cola.get(0);
    }
    
    public void sort() {
        Comparador comparador = new Comparador();
        Collections.sort((List <Nodo>) cola, comparador);
    }
}
