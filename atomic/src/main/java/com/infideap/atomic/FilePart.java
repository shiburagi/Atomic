package com.infideap.atomic;

import java.io.File;

/**
 * Created by Shiburagi on 20/10/2016.
 */

public class FilePart extends Part {
    private File file;

    public FilePart(String s, File file) {
        super(s, file.getName());

        this.file = file;
    }

    public File getFile() {
        return file;
    }

}
