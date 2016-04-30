package Pasteleria;

import java.io.*;
import java.util.*;

public class Pasteleria {
    private int [][] matrizBeneficio;
    //= {{2,5,3},{5,3,2},{6,4,9},{6,3,8},{7,5,8}};
    private int [] pedidos;
    //= {1,1,3,2,1};
    private int filas;
    private int columnas;
    private Cola conjuntoNodosVivos = new Cola();
    private Nodo solucion;
    private String ficheroSalida;

    @SuppressWarnings("empty-statement")
    public Pasteleria (String archivoEntrada, String archivoSalida) {
        leerArchivo(archivoEntrada);
        ficheroSalida = archivoSalida;
        solucion = new Nodo (filas, Integer.MIN_VALUE);
        conjuntoNodosVivos.add(solucion);
        
        //SOLO PARA PRUEBA, VALORES DE EJEMPLO
    }
    
    public int[][] getMatrizBeneficio() {
        return matrizBeneficio;
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
    
    public void leerArchivo(String archivo) {
        try {
            String cadena = new String();
            char next;
            FileReader fr = new FileReader(archivo);
            //Lee el primer caracter
            try (BufferedReader br = new BufferedReader(fr)) {
                //Lee el primer caracter
                next = (char) br.read();
                //System.out.println(next);
                while(next != ' ') {
                    cadena += next;
                    next = (char) br.read();
                    //System.out.println(next);
                }
                filas = Integer.parseInt(cadena);
                //Lee el segundo caracter
                next = (char) br.read();
                cadena = new String();
                //System.out.println(next);
                while(next != '\r') {
                    cadena += next;
                    next = (char) br.read();
                    //System.out.println(next);
                }
                columnas = Integer.parseInt(cadena);
                br.readLine();

                //Inicio Array de beneficios y rellenar
                matrizBeneficio = new int[filas][columnas];
                for(int i = 0; i < filas; i++) {
                    for(int j = 0; j < columnas; j++) {
                        next = (char) br.read();
                        //System.out.println(next);
                        cadena = new String();
                        while(next != ' ' && next != '\r') {
                            cadena += next;
                            next = (char) br.read();
                            //System.out.println(next);
                        }
                        matrizBeneficio[i][j] = Integer.parseInt(cadena);
                    }
                    br.readLine();
                }

                //Iniciar array de pedidos y rellenar
                pedidos = new int[filas];
                for(int i = 0; i < filas; i++) {
                    cadena = new String();
                    next = (char) br.read();
                    while(next != ' ' && next != '\uffff') {
                        cadena += next;
                        next = (char) br.read();
                    }
                    pedidos[i]= Integer.parseInt(cadena);
                }
                br.close();
            }
            
        }
        catch(Exception e){
            System.err.println(e);
        }        
    }
    
    public void escribirArchivo (){
        try {
            int beneficio = this.getSolucion().getCosteAsignado();
            int[] asignaciones = this.getSolucion().getAsignaciones();
            FileWriter fw = new FileWriter(ficheroSalida);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for(int i = 0; i < filas; i++) {
                if(i < filas - 1)
                    pw.print(asignaciones[i] + ", ");
                else
                    pw.print(asignaciones[i]);
            }
            pw.println();
            pw.println(beneficio);
            pw.close();
        }
        catch(Exception e) {
            System.err.println(e);
        }
        
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
        for (int i = 0; i < matrizBeneficio.length; i++)
            if (! cocineros [i] && matrizBeneficio [i][tipoPastel] > aux) {
                aux = matrizBeneficio [i][tipoPastel];
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
                int auxAsignacion = matrizBeneficio [j][pedidos[pedido] - 1];
                //auxAsignacion toma el valor que ya le ha sido asignado más lo que le es posible conseguir como máximo
                int auxPrevision = auxAsignacion;
                //Se guardan los posteleros que se ponen a true durante el calculo de la previsión para posteriormente volver a ponerlos a false
                LinkedList <Integer> devolverAFalse = new LinkedList();
                for (int i = 0; i < asignaciones.length; i++) {
                    if (asignaciones [i] == -1) {
                        int tipoPastel = pedidos [i] - 1;
                        int mejorPastelero = valorMaximo(cocineros, tipoPastel);
                        cocineros [mejorPastelero] = true;
                        auxPrevision += matrizBeneficio [mejorPastelero][tipoPastel];
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
                            auxAsignacion += matrizBeneficio [asignaciones [i] - 1][pedidos [i] - 1];
                    //auxAsignacion toma el valor que ya le ha sido asignado más lo que le es posible conseguir como máximo
                    int auxPrevision = auxAsignacion;
                    //Se guardan los posteleros que se ponen a true durante el calculo de la previsión para posteriormente volver a ponerlos a false
                    LinkedList <Integer> devolverAFalse = new LinkedList();
                    for (int i = 0; i < asignaciones.length; i++) {
                        if (asignaciones [i] == -1) {
                            int tipoPastel = pedidos [i] - 1;
                            int mejorPastelero = valorMaximo(cocineros, tipoPastel);
                            cocineros [mejorPastelero] = true;
                            auxPrevision += matrizBeneficio [mejorPastelero][tipoPastel];
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
