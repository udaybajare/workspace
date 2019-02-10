package com.invmgmt.model;

import java.io.File;

public class FilePojo {

    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FilePojo(File file) {
	super();
	this.file = file;
    }
    
    public FilePojo()
    {	
    }
}
