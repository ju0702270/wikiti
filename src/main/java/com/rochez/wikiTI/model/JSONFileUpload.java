package com.rochez.wikiTI.model;

import lombok.Data;

import java.io.Serializable;



/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description : Classe Model JSONFileUpload : ce model est un peu spécifique car il n'intervient pas dans la base de donnée
 * Cette classe est là pour faciliter la response sous format JSON des images uploadée avec ckfinder.
 */
public class JSONFileUpload implements Serializable {

    private String fileName;
    private String url;
    private int uploaded;

    public JSONFileUpload(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
        this.uploaded= 1;
    }
    public JSONFileUpload() {
        super();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUploaded() {
        return uploaded;
    }

    public void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }

}
