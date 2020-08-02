package com.app.manager.controller;

import com.app.manager.model.midware_model.TestFormCheck;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestController {
    @GetMapping("/test/formCheck")
    public String index(Model model) {
        model.addAttribute("testFormCheck", new TestFormCheck());
        return "views/test/formCheck";
    }

    @PostMapping("/test/formCheck")
    public String index(@Validated @ModelAttribute TestFormCheck testFormCheck,
                        BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            for (var item :
                    bindingResult.getAllErrors()) {
                System.out.println(item.getDefaultMessage());
            }
            return "redirect:/test/formCheck";
        }
        System.out.println("Input result:");
        System.out.println(testFormCheck.isCheck1());
        System.out.println(testFormCheck.isCheck2());
        System.out.println(testFormCheck.isCheck3());
        System.out.println(testFormCheck.isCheck4());
        System.out.println(testFormCheck.getOptionsRadios());
        System.out.println(testFormCheck.getOptionsRadio2());
        return "redirect:/test/formCheck";
    }

    @GetMapping("/test/error/500")
    public void test500() throws Exception{
        throw new Exception("error");
    }
    @PostMapping("/test/error/405")
    public void test405(){
    }

    @RequestMapping("/test/error/400")
    public void test400(@RequestParam(value = "id") String id){
    }

    @RequestMapping("/test/error/403")
    public void test403(){
    }
}
