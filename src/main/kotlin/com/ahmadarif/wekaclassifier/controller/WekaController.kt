package com.ahmadarif.wekaclassifier.controller

import com.ahmadarif.wekaclassifier.service.WekaService
import com.ahmadarif.wekaclassifier.storage.StorageService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.io.IOException
import java.util.*

/**
 * Created by ARIF on 20-Jul-17.
 */
@Controller
class WekaController(val wekaService: WekaService, val storageService: StorageService) {

    @GetMapping("/")
    fun welcome(model: MutableMap<String, Any>): String {
        return "index"
    }

    @RequestMapping(value = "/upload", method = arrayOf(RequestMethod.POST))
    @Throws(IOException::class)
    fun uploadingPost(
            @RequestParam("uploadingFile") uploadedFile: MultipartFile,
            @RequestParam("className") className: String,
            redirAttrs: RedirectAttributes
    ): String {
        val file = storageService.store(uploadedFile)
        val message = wekaService.sample5(file, className)

        redirAttrs.addFlashAttribute("message", message)
        return "redirect:/"
    }
}