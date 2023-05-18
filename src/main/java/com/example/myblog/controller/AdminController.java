package com.example.myblog.controller;

import com.example.myblog.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AdminController{
    private final AdminService adminService;
}
