package com.estafet.invoicesystem.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by estafet on 01/12/15.
 */
@XmlRootElement(name = "uploadFileRequest", namespace = "http://webservice.invoicesystem.estafet.com/")
public class UploadFileRequest {
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
