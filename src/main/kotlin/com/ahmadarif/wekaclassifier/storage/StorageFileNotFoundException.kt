package com.ahmadarif.wekaclassifier.storage

/**
 * Created by ARIF on 20-Jul-17.
 */
class StorageFileNotFoundException : StorageException {

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

}