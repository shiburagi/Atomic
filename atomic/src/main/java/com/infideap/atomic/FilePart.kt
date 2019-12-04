package com.infideap.atomic

import java.io.File

/**
 * Created by Shiburagi on 20/10/2016.
 */
class FilePart(s: String?, val file: File?) : Part(s, file?.name) {
    fun getFile(): File? {
        return file
    }

}