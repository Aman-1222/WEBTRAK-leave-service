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
    private final LeaveNotificationProducer notificationProducer;

    public LeaveRequest applyLeave(LeaveRequest request, String email) {
        request.setEmployeeEmail(email);
        request.setStatus(LeaveStatus.PENDING);
        LeaveRequest saved = repository.save(request);

        notificationProducer.send(
                "Leave Applied | Employee: " + email +
                        " | From: " + request.getStartDate() +
                        " | To: " + request.getEndDate()
        );

        return saved;
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
        LeaveRequest saved = repository.save(leave);

        notificationProducer.send(
                "Leave APPROVED | Employee: " + leave.getEmployeeEmail()
        );

        return saved;
    }

    public LeaveRequest reject(Long id) {
        LeaveRequest leave = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus(LeaveStatus.REJECTED);
        LeaveRequest saved = repository.save(leave);

        notificationProducer.send(
                "Leave REJECTED | Employee: " + leave.getEmployeeEmail()
        );

        return saved;
    }
}
