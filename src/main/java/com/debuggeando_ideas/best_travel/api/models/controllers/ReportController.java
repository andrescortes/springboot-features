package com.debuggeando_ideas.best_travel.api.models.controllers;

import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.IReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Report", description = "Report API")
@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {

    private static final MediaType FORCE_DOWNLOAD = new MediaType("application", "force-download");
    private static final String FORCE_DOWNLOAD_HEADER_VALUE = "attachment;filename=report.xlsx";
    private final IReportService reportService;

    @Operation(
        summary = "Generate a report",
        description = "Generate a report"
    )
    @ApiResponse(
        responseCode = "500",
        description = "When the occurred an error generating the report"
    )
    @GetMapping
    public ResponseEntity<Resource> readFile() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(FORCE_DOWNLOAD);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, FORCE_DOWNLOAD_HEADER_VALUE);
        byte[] bytes = reportService.readFile();
        ByteArrayResource resource = new ByteArrayResource(bytes);
        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(bytes.length)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource);
    }
}
