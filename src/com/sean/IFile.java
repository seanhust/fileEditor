package com.sean;

import java.io.File;


public interface IFile {

    File open(File file);

    boolean save(File file);

    File create();

    void editor();


}
