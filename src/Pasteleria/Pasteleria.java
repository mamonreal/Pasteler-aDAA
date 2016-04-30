package Pasteleria;

import java.util.*;

public class Pasteleria {
    private final int [][] matrizCoste = {{2,5,3},{5,3,2},{6,4,9},{6,3,8},{7,5,8}};
    private final int [] pedidos = {1,1,3,2,1};
    private Cola conjuntoNodosVivos = new Cola();
    private Nodo solucion;

    @SuppressWarnings("empty-statement")
    public Pasteleria (int n, int m) {
        solucion = new Nodo (n, Integer.MIN_VALUE);
        conjuntoNodosVivos.add(solucion);
        
        //SOLO PARA PRUEBA, VALORES DE EJEMPLO
    }
    
    public int[][] getMatrizCoste() {
        return matrizCoste;
    }

    public int[] getPedidos() {
        return pedidos;
    }

    public Cola getConjuntoNodosVivos() {
        return conjuntoNodosVivos;
    }

    public Nodo getSolucion() {
        return solucion;
    }

    public void setSolucion(Nodo solucion) {
        this.solucion = solucion;
    }
    
    
    //Este método devuelve true si hay que podar ese nodo
    //Un nodo se poda si su coste de prevision es mayor que el coste de una solucion
    public boolean poda (Nodo n) {
        return n.getCostePrevision() < solucion.getCosteAsignado();
    }
    
    public boolean esSolucion (Nodo n) {
        return n.esSolucion(n);
    }
    
    public boolean primerNodo(Nodo n) {
        return n.getCosteAsignado() == Integer.MIN_VALUE;
    }
    
    //Devuelve el número de cocinero que tiene el mayor beneficio para ese tipo de paste, siempre que no esté asignado todavía
    public int valorMaximo(boolean [] cocineros, int tipoPastel) {
        int k = 0;
        int aux = Integer.MIN_VALUE;
        for (int i = 0; i < matrizCoste.length; i++)
            if (! cocineros [i] && matrizCoste [i][tipoPastel] > aux) {
                aux = matrizCoste [i][tipoPastel];
                k = i;
            }
        return k;
    }
    
    //Dado un nodo, se generan sus hijos y se añaden al conjunto de nodos vivos
    public void expandirNodo (Nodo n) {
        int pedido = n.getUltimoAgente();
        if (primerNodo(n)){
            boolean [] cocineros = n.getCocinerosAsignados();
            int [] asignaciones = n.getAsignaciones();
            for (int j = 0; j < cocineros.length; j++) {
                cocineros [j] = true;
                asignaciones [pedido] = j + 1;
                int auxAsignacion = matrizCoste [j][pedidos[pedido] - 1];
                //auxAsignacion toma el valor que ya le ha sido asignado más lo que le es posible conseguir como máximo
                int auxPrevision = auxAsignacion;
                //Se guardan los posteleros que se ponen a true durante el calculo de la previsión para posteriormente volver a ponerlos a false
                LinkedList <Integer> devolverAFalse = new LinkedList();
                for (int i = 0; i < asignaciones.length; i++) {
                    if (asignaciones [i] == -1) {
                        int tipoPastel = pedidos [i] - 1;
                        int mejorPastelero = valorMaximo(cocineros, tipoPastel);
                        cocineros [mejorPastelero] = true;
                        auxPrevision += matrizCoste [mejorPastelero][tipoPastel];
                        devolverAFalse.add(mejorPastelero);
                    }
                }
                //Se vuelve a poner a false los cocineros que no han sido asignados todavia
                int dFalse = devolverAFalse.size();
                for (int i = 0; i < dFalse; i++) {
                    cocineros[devolverAFalse.getFirst()] = false;
                    devolverAFalse.removeFirst();
                }
                Nodo s = new Nodo(auxAsignacion, auxPrevision, asignaciones.clone(), cocineros.clone(), pedido + 1);
                conjuntoNodosVivos.add(s);
                cocineros [j] = false;
                asignaciones [pedido] = -1;
            }
            conjuntoNodosVivos.remove(n);
            conjuntoNodosVivos.sort();
        }
        else {
            boolean [] cocineros = n.getCocinerosAsignados();
            for (int j = 0; j < cocineros.length; j++) {
                if (! cocineros [j]) {
                    cocineros = n.getCocinerosAsignados();
                    int [] asignaciones = n.getAsignaciones();
                    cocineros [j] = true;
                    asignaciones [pedido] = j + 1;
                    int auxAsignacion = 0;
                    //auxAsignacion toma el valor que ya le ha sido asignado
                    for (int i = 0; i < asignaciones.length; i++)
                        if (asignaciones [i] != -1)
                            auxAsignacion += matrizCoste [asignaciones [i] - 1][pedidos [i] - 1];
                    //auxAsignacion toma el valor que ya le ha sido asignado más lo que le es posible conseguir como máximo
                    int auxPrevision = auxAsignacion;
                    //Se guardan los posteleros que se ponen a true durante el calculo de la previsión para posteriormente volver a ponerlos a false
                    LinkedList <Integer> devolverAFalse = new LinkedList();
                    for (int i = 0; i < asignaciones.length; i++) {
                        if (asignaciones [i] == -1) {
                            int tipoPastel = pedidos [i] - 1;
                            int mejorPastelero = valorMaximo(cocineros, tipoPastel);
                            cocineros [mejorPastelero] = true;
                            auxPrevision += matrizCoste [mejorPastelero][tipoPastel];
                            devolverAFalse.add(mejorPastelero);
                        }
                    }
                    //Se vuelve a poner a false los cocineros que no han sido asignados todavia
                    int dFalse = devolverAFalse.size();
                    for (int i = 0; i < dFalse; i++) {
                        cocineros[devolverAFalse.getFirst()] = false;
                        devolverAFalse.removeFirst();
                    }
                    Nodo s = new Nodo(auxAsignacion, auxPrevision, asignaciones.clone(), cocineros.clone(), pedido + 1);
                    conjuntoNodosVivos.add(s);
                    cocineros [j] = false;
                    asignaciones [pedido] = -1;
                }
            }
            conjuntoNodosVivos.remove(n);
            conjuntoNodosVivos.sort();
        }
    }
    
    
    
    public Nodo ramificaYPoda(Nodo n) {
        //while (! conjuntoNodosVivos.isEmpty()) 
            if (esSolucion(n) && n.getCosteAsignado() > solucion.getCosteAsignado()) {
                solucion = new Nodo(n);
                conjuntoNodosVivos.remove(n);
                if (! conjuntoNodosVivos.isEmpty())
                    ramificaYPoda(conjuntoNodosVivos.getNodo());
            }
            else if (poda(n)) {
                conjuntoNodosVivos.remove(n);
                if (! conjuntoNodosVivos.isEmpty())
                    ramificaYPoda(conjuntoNodosVivos.getNodo());
            }
            else {
                expandirNodo(n);
                if (! conjuntoNodosVivos.isEmpty())
                    ramificaYPoda(conjuntoNodosVivos.getNodo());
            }
        return solucion;
    }
}
