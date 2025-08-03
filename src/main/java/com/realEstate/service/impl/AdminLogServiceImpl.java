package com.realEstate.service.impl;

import com.realEstate.model.AdminLog;
import com.realEstate.repository.AdminLogRepository;
import com.realEstate.service.AdminLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class AdminLogServiceImpl implements AdminLogService {

    @Autowired
    private AdminLogRepository logRepository;

    @Override
    public void logAction(String action, String performedBy, String details) {
        AdminLog log = new AdminLog();
        log.setAction(action);
        log.setPerformedBy(performedBy);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        logRepository.save(log);
    }

    @Override
    public List<AdminLog> getAllLogs() {
        return logRepository.findAllByOrderByTimestampDesc();
    }
}
