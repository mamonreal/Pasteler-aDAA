package Pasteleria;

public class Principal {
    public static void main (String [] args) {
        //Pasteleria pasteleria = new Pasteleria(args[0], args[1]);
        Pasteleria pasteleria = new Pasteleria("entrada.txt", "salida.txt");
        //Pasteleria pasteleriaRec = new Pasteleria("entrada.txt", "salida.txt");
        //ALGORITMO RECURSIVO
        //imprimirSolucion(pasteleriaRec.ramificaYPodaRecursivo(pasteleria.getSolucion()));
        //ALGORITMO ITERATIVO
        long time_start, time_end;
        time_start = System.currentTimeMillis();
        //ReallyHeavyTask(); // llamamos a la tarea
        Nodo s = pasteleria.ramificaYPoda();
        time_end = System.currentTimeMillis();
        System.out.println("the task has taken "+ ( time_end - time_start ) +" milliseconds");
        imprimirSolucion(s);
        pasteleria.escribirArchivo();
        //Muestra la carpeta predeterminada en la que están los archivos
        System.out.println(System.getProperty("user.dir")) ;
    }
    
    public static void imprimirSolucion (Nodo n) {
        System.out.println("Se ha encontrado la siguiente solución:");
        System.out.println("");
        System.out.println("Beneficio: " + n.getCostePrevision());
        System.out.println("");
        int [] asignados = n.getAsignaciones();
        System.out.println("Se han asignados los siguintes pasteleros:");
        for (int i = 0; i < asignados.length; i++)
            System.out.println("Pedido "+ (i + 1) + ": Pastelero " + asignados[i]);
    }
}
