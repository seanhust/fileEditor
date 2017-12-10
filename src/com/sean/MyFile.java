package com.sean;

import java.io.File;

public class MyFile implements IFile {

    File file = null;

    public File getFile(){
        return file;
    }
    @Override
    public File open(File file) {
        this.file = file;
        return file;
    }

    @Override
    public boolean save(File file) {
        return false;
    }

    @Override
    public File create() {
        return null;
    }

    @Override
    public void editor() {

    }
}
