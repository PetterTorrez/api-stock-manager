package com.bluedot.stock_manager.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
public class HelloWorldController {

  @GetMapping
  public String sayHello() {
    return "This is the base endpoint for testing purposes.";
  }
}
