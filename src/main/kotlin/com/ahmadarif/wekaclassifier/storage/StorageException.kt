package com.ahmadarif.wekaclassifier.storage

/**
 * Created by ARIF on 20-Jul-17.
 */
open class StorageException : RuntimeException {

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

}