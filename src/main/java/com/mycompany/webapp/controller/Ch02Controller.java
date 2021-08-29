package com.mycompany.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/ch02")
public class Ch02Controller {
   private static final Logger Logger = LoggerFactory.getLogger(Ch02Controller.class);
   
   
   @RequestMapping("/content")
   public String content() {
      Logger.info("실행");
      return "ch02/content";
   }
   
   //@GetMapping("/method")
   @RequestMapping(value="/method", method=RequestMethod.GET)
   public String method1(){
      Logger.info("실행1");
      return "redirect:/ch02/content";
   }
   //@PostMapping("/method")
   @RequestMapping(value="/method", method=RequestMethod.POST)
   public String method2() {
      Logger.info("실행2");
      
      return "redirect:/ch02/content";
   }
   //@PutMapping("/method3")
   @RequestMapping(value="/method", method=RequestMethod.PUT)
   public String method3() {
      Logger.info("실행3");
      
      return "redirect:/ch02/content";
   }
   //@DeleteMapping("/method3")
   @RequestMapping(value="/method", method=RequestMethod.DELETE)
   public String method4() {
      Logger.info("실행4");
      
      return "redirect:/ch02/content";
   }
   
   
   
   
   @GetMapping("/modelandview")
   public ModelAndView method5() {
	   Logger.info("실행");
	   ModelAndView modelAndView = new ModelAndView();
	   modelAndView.setViewName("ch02/modelandview");
	   
	   return modelAndView;
   }
   
   @PostMapping("/login")
   public String login() {
	   Logger.info("login 실행");
	   
	   return "ch02/loginResult";
   }
   
   @PostMapping("/login1")
   public String login1() {
	   Logger.info("실행");
	   return "ch02/loginResult";
   }
   
   @PostMapping("/login2")
   public String login2() {
	   Logger.info("실행");
	   return "redirect:/ch01/content";
   }
   
   @GetMapping("/boardlist")
   public String boardlist() {
	   Logger.info("실행");
	   return "ch02/boardList";
   }
   
   @GetMapping("/boardwriteform")
   public String boardWriteForm() {
	   return "ch02/boardWriteForm";
   }
   
   @PostMapping("/boardwrite")
   public String boardWrite() {
	   return "redirect:ch02/boardList";
   }
}