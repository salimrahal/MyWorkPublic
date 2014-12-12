/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.ResDao;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author salim
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author salim
 */
@ManagedBean(name = "resService")
@ApplicationScoped
public class ResService implements Serializable {

    ResDao resdao;

    public ResService() {
        resdao = new ResDao();
    }

    public List<ResAlgVo> retrieveAlgResults() throws Exception {
        return resdao.retrieveAlgResList();
    }

    public List<ResTrfVo> retrieveTrfResults() throws Exception {
        return resdao.retrieveTrfResList();
    }

}
