/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vo;

import com.safirasoft.ResVo;

/**
 *
 * @author salim
 */
public class ResSingleton {
    private static final ResSingleton INSTANCE = new ResSingleton();
    
    private ResVo resvo;

    private ResSingleton() {
    }

    public ResVo getResvo() {
        return resvo;
    }

    public void setResvo(ResVo resvo) {
        this.resvo = resvo;
    }
    
    ResSingleton getInstance(){
        return INSTANCE;
    }
}
