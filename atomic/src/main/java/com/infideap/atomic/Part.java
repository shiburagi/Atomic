package com.infideap.atomic;

import java.io.File;

/**
 * Created by Shiburagi on 20/10/2016.
 */

public class Part {
    private String key;
    private String name;
    private File file;

    Part(String s, File file) {
        key = s;
        this.file = file;
        name = file.getAbsolutePath();
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }
}
