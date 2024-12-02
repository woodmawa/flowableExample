package com.flowable.restControllers

import com.flowable.services.DeployProcessService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

import java.io.IOException

@RestController @RequestMapping("/api/process")
class DeployProcessController {

    @Autowired
    private DeployProcessService deployProcessService

    @PostMapping("/deploy")
    public ResponseEntity<String> deployProcess(@RequestParam("file") MultipartFile file) {
        try {
                boolean deployed = deployProcessService.deployProcessDefinition(file)
                if (deployed) {
                    return ResponseEntity.ok("Process deployed successfully.")
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to deploy process.")
                }
        } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file.")
        }
    }


}
