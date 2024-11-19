package com.ExceptionLogger.ExceptionHandler.controller;

import com.ExceptionLogger.ExceptionHandler.model.ErrorDetail;
import com.ExceptionLogger.ExceptionHandler.repository.ErrorDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/errors")
public class ErrorDetailController {

    private final ErrorDetailRepository errorDetailRepository;

    @Autowired
    public ErrorDetailController(ErrorDetailRepository errorDetailRepository) {
        this.errorDetailRepository = errorDetailRepository;
    }

    @GetMapping
    public List<ErrorDetail> getAllErrors() {
        return errorDetailRepository.findAll();
    }
}
