/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pasteleria;

import java.util.Comparator;

/**
 *
 * @author MiguelÁngel
 */
public class Comparador implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Nodo n = (Nodo) o1;
        Nodo s = (Nodo) o2;
        if (n.getCostePrevision() > s.getCostePrevision())
            return -1;
        else if (n.getCostePrevision() < s.getCostePrevision())
            return 1;
        else
            return 0;
    }
    
}
