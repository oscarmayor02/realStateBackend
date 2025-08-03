package com.realEstate.controller;

import com.realEstate.model.AdminLog;
import com.realEstate.service.AdminLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/logs")
public class AdminLogController {

    @Autowired
    private AdminLogService logService;

    @GetMapping
    public ResponseEntity<List<AdminLog>> getLogs() {
        return ResponseEntity.ok(logService.getAllLogs());
    }
}
