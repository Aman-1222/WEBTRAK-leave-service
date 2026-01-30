package com.company.leave_service.service;

import com.company.leave_service.entity.LeaveRequest;

import com.company.leave_service.entity.enums.LeaveStatus;
import com.company.leave_service.repository.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRepository repository;

    public LeaveRequest applyLeave(LeaveRequest request, String email) {
        request.setEmployeeEmail(email);
        request.setStatus(LeaveStatus.PENDING);
        return repository.save(request);
    }

    public List<LeaveRequest> getMyLeaves(String email) {
        return repository.findByEmployeeEmail(email);
    }

    public List<LeaveRequest> getAllLeaves() {
        return repository.findAll();
    }

    public LeaveRequest approve(Long id) {
        LeaveRequest leave = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus(LeaveStatus.APPROVED);
        return repository.save(leave);
    }

    public LeaveRequest reject(Long id) {
        LeaveRequest leave = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus(LeaveStatus.REJECTED);
        return repository.save(leave);
    }
}
