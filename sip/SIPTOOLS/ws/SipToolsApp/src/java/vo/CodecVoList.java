/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vo;

import java.util.List;

/**
 *
 * @author salim
 */
public class CodecVoList {
      List<CodecVo> codecList;

    public CodecVoList(List<CodecVo> codecList) {
        this.codecList = codecList;
    }

      
    public List<CodecVo> getCodecList() {
        return codecList;
    }

    public void setCodecList(List<CodecVo> codecList) {
        this.codecList = codecList;
    }
}
