/**
 * 
 */
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ezak4
 *
 */
@Controller
@RequestMapping("/")
public class TemplateController {
    
    @GetMapping("login") // Service name
    public String getLoginView() {
        return "login"; // HTML page name
    }
    
    @GetMapping("courses")
    public String getCourses() {
        return "courses";
    }
}
