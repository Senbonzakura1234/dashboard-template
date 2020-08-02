package com.app.manager.controller;

import com.app.manager.model.midware_model.TestFormCheck;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
