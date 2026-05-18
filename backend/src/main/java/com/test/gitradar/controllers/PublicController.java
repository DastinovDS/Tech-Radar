package com.test.gitradar.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.gitradar.dto.UrlDTO;
import com.test.gitradar.services.ApiRequestService;
import com.test.gitradar.services.UrlApiBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/public")
public class PublicController {

    @Autowired
    private ApiRequestService apiRequestService;

    @PostMapping("/inspect")
    public ResponseEntity<JsonNode> inspectRepository(@RequestBody UrlDTO url) {
        String apiUrl = UrlApiBuilderService.buildPublicUrl(url);

        JsonNode response = apiRequestService.performApiRequest(apiUrl, null).block();

        return ResponseEntity.ok(response);
    }
}