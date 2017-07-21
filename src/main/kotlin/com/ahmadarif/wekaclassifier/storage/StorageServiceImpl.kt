package com.ahmadarif.wekaclassifier.storage

/**
 * Created by ARIF on 20-Jul-17.
 */
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File

import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream

@Service
class StorageServiceImpl : StorageService {

    private val rootLocation: Path = Paths.get("uploadingdir")

    override fun store(file: MultipartFile): File {
        try {
            if (file.isEmpty) {
                throw StorageException("Failed to store empty file " + file.originalFilename)
            }
            val savedFile = Files.write(this.rootLocation.resolve(file.originalFilename), file.bytes)
            return File(savedFile.toString())
        } catch (e: IOException) {
            throw StorageException("Failed to store file " + file.originalFilename, e)
        }

    }

    override fun loadAll(): Stream<Path> {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter { path -> path != this.rootLocation }
                    .map { path -> this.rootLocation.relativize(path) }
        } catch (e: IOException) {
            throw StorageException("Failed to read stored files", e)
        }
    }

    override fun load(filename: String): Path {
        return rootLocation.resolve(filename)
    }

    override fun loadAsResource(filename: String): Resource {
        try {
            val file = load(filename)
            val resource = UrlResource(file.toUri())
            if (resource.exists() || resource.isReadable) {
                return resource
            } else {
                throw StorageFileNotFoundException("Could not read file: " + filename)

            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("Could not read file: " + filename, e)
        }

    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }

    override fun init() {
        try {
            File(rootLocation.toString()).mkdirs()
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }

    }
}