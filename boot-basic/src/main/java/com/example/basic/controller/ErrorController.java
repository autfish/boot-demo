package com.example.basic.controller;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ErrorController extends AbstractErrorController {

    public ErrorController() {
        super(new DefaultErrorAttributes());
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
