package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GreetingController {
    @RequestMapping("/")
    public ResponseEntity<String> Test() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
}
