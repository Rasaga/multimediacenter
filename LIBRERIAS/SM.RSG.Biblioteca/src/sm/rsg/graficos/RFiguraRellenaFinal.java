/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.rsg.graficos;

/**
 * Clase abstracta para figuras Rellenas. Si es una figura de este tipo,
 * sobrescribira el método "esRellenable", que se rellenará o pintará
 * su relleno si es de este tipo.
 * @author Ramon
 */
public abstract class RFiguraRellenaFinal extends RFigura2D {
    
    /**
     * Metodo esRellenable. En este caso, al ser una figura rellena
     * devolverá true.
     * @return Valor true.
     */
    @Override
    public boolean esRellenable(){
        return true;
    }
}
