package com.app.manager.controller;

import com.app.manager.model.HelperMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalVariables {
    @ModelAttribute
    public void addAttributes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()){
            model.addAttribute("current_user",
                    HelperMethod.upperCaseFirstChar(auth.getName()));
            model.addAttribute("avatar",
                    "https://res.cloudinary.com/senbonzakura/image/upload/v1573316200/avatar_tpygpm.jpg");
        }
    }
}
