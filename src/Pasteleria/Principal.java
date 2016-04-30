package Pasteleria;

public class Principal {
    public static void main (String [] args) {
        Pasteleria pasteleria = new Pasteleria("entrada.txt", "salida.txt");
        imprimirSolucion(pasteleria.ramificaYPoda(pasteleria.getSolucion()));
        pasteleria.escribirArchivo();
        //System.out.println(System.getProperty("user.dir")) ;
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
