package Pasteleria;

public class Nodo {
    private int costeAsignado;
    private int costePrevision;
    private int [] asignaciones;
    private boolean [] cocinerosAsignados;
    private int ultimoAgente; 

    

    public Nodo (int n) {
        //N es el númer de pasteles pedidos y el número de cocineros
        costeAsignado = 0;
        costePrevision = 0;
        ultimoAgente = 0;
        asignaciones = new int [n];
        cocinerosAsignados = new boolean [n];
        for (int i = n; i < asignaciones.length; i++) {
            asignaciones [i] = -1;
            cocinerosAsignados [i] = false;
        }
    }
    
    public Nodo (int n, int m) {
        //N es el númer de pasteles pedidos y el número de cocineros
        costeAsignado = m;
        costePrevision = m;
        ultimoAgente = 0;
        asignaciones = new int [n];
        cocinerosAsignados = new boolean [n];
        for (int i = 0; i < asignaciones.length; i++) {
            asignaciones [i] = -1;
            cocinerosAsignados [i]= false;
        }
    }
    
    
    public Nodo (Nodo n) {
        costeAsignado = n.getCosteAsignado();
        costePrevision = n.getCostePrevision();
        asignaciones = n.getAsignaciones();
        ultimoAgente = n.getUltimoAgente();
        cocinerosAsignados = n.getCocinerosAsignados();
    }
    
    public Nodo (int costeAsignado, int costePrevision, int [] asignaciones, boolean [] cocinerosAsignados, int ultimoAgente) {
        this.costeAsignado = costeAsignado;
        this.costePrevision = costePrevision;
        this.asignaciones = asignaciones;
        this.cocinerosAsignados = cocinerosAsignados;
        this.ultimoAgente = ultimoAgente;
    }
    
    public int getCostePrevision() {
        return costePrevision;
    }

    public void setCostePrevision(int costePrevision) {
        this.costePrevision = costePrevision;
    }    

    public int getCosteAsignado() {
        return costeAsignado;
    }

    public void setCosteAsignado(int costeAsignado) {
        this.costeAsignado = costeAsignado;
    }

    public int[] getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(int[] asignaciones) {
        this.asignaciones = asignaciones;
        
    }
    public boolean[] getCocinerosAsignados() {
        return cocinerosAsignados;
    }

    public void setCocinerosAsignados(boolean[] cocinerosAsignados) {
        this.cocinerosAsignados = cocinerosAsignados;
    }
    
    public int getUltimoAgente() {
        return ultimoAgente;
    }

    public void setUltimoAgente(int ultimoAgente) {
        this.ultimoAgente = ultimoAgente;
    }
    
    public boolean esSolucion (Nodo n) {
        return ultimoAgente == cocinerosAsignados.length;
    }
}
