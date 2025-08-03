package com.realEstate.service;

import com.realEstate.model.AdminLog;

import java.util.List;

public interface AdminLogService {

    void logAction(String action, String performedBy, String details);

    List<AdminLog> getAllLogs();
}
