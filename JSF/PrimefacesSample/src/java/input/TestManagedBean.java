/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author salim
 */
@ManagedBean
@ViewScoped
public final class TestManagedBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private UploadedFile uploadedFile;

    public TestManagedBean(){}

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void fileUploadListener(FileUploadEvent event){
        System.out.println("fileUploadListener");
        uploadedFile=event.getFile();
    }

    public void insert(){
        if(uploadedFile!=null){
            System.out.println(uploadedFile.getFileName());
        }
        else{
            System.out.println("The file object is null.");
        }
    }
}
