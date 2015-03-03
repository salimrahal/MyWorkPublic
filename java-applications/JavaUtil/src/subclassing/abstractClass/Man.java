/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subclassing.abstractClass;

/**
 *
 * @author salim
 */
public class Man extends Person {

    @Override
    public void run(int speed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int computeFat(){
        int res = super.computefat();
        int manratio = 20;
        return res+manratio;
    }
    
    public static void main(String[] args) {
        Man m = new Man();
        System.out.println(m.computeFat());
    }
}
