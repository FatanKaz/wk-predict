package com.ahmadarif.wekaclassifier

import com.ahmadarif.wekaclassifier.storage.StorageService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class WekaClassifierApplication(val storageService: StorageService): CommandLineRunner {

    override fun run(vararg p0: String?) {
        storageService.init()
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(WekaClassifierApplication::class.java, *args)
        }
    }
}