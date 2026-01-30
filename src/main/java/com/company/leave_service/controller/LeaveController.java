package com.company.leave_service.controller;

import com.company.leave_service.entity.LeaveRequest;
import com.company.leave_service.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService service;

    // EMPLOYEE
    @PostMapping
    public LeaveRequest applyLeave(
            @RequestBody LeaveRequest request,
            @RequestHeader("X-USER-EMAIL") String email) {

        return service.applyLeave(request, email);
    }

    @GetMapping("/my")
    public List<LeaveRequest> myLeaves(
            @RequestHeader("X-USER-EMAIL") String email) {

        return service.getMyLeaves(email);
    }

    // ADMIN / HR
    @GetMapping
    public List<LeaveRequest> allLeaves() {
        return service.getAllLeaves();
    }

    @PutMapping("/{id}/approve")
    public LeaveRequest approve(@PathVariable Long id) {
        return service.approve(id);
    }

    @PutMapping("/{id}/reject")
    public LeaveRequest reject(@PathVariable Long id) {
        return service.reject(id);
    }
}
