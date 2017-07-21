package com.ahmadarif.wekaclassifier.extension

import weka.core.converters.ArffLoader
import java.io.File

/**
 * Created by ARIF on 20-Jul-17.
 */
fun loader(file: File): ArffLoader {
    val loader = ArffLoader()
    loader.setFile(file)
    return loader
}