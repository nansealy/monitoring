package com.sahno.controller.rest;

import com.sahno.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExportController {
    @Autowired
    private ExportService exportService;


    @GetMapping(value = "/export", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody byte[] export(@RequestParam("id") Long id) {
        return exportService.export(id);
    }
}
