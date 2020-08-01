package com.app.manager.controller;

import com.app.manager.entity.User;
import com.app.manager.entity.UserRole;
import com.app.manager.model.midware_model.LoginModel;
import com.app.manager.model.midware_model.RegisterModel;
import com.app.manager.service.interfaceClass.RoleService;
import com.app.manager.service.interfaceClass.UserRoleService;
import com.app.manager.service.interfaceClass.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
public class AuthenController {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired private UserService userService;
    @Autowired private PasswordEncoder encoder;
    @Autowired private RoleService roleService;
    @Autowired
    AuthenticationManager authenticationManager;
    // Login

    @GetMapping("/login")
    public String login(Model model) {
        if(SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken) ){
            return "redirect:/";
        }
        model.addAttribute("loginModel", new LoginModel());
        return "views/authen/login";
    }
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginModel loginModel,
                        BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            for (var item :
                    bindingResult.getAllErrors()) {
                System.out.println(item.getDefaultMessage());
            }
            return "views/authen/login";
        }
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginModel.getUsername(),
                        loginModel.getPassword(),
                        new ArrayList<>()
                )
        );
        if(authentication.isAuthenticated()) return "redirect:/";
        return "redirect:/login?error";
    }

    @GetMapping(value = "/register")
    public String register(Model model){
        if(SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken) ){
            return "redirect:/";
        }
        model.addAttribute("registerModel", new RegisterModel());
        return "views/authen/register";
    }

    @PostMapping(value = "/register")
    public String register(@Validated @ModelAttribute RegisterModel registerModel,
                           BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            for (var item :
                    bindingResult.getAllErrors()) {
                System.out.println(item.getDefaultMessage());
            }
            return "views/authen/register";
        }else {
            List<UserRole> roleList = new ArrayList<>();

            var user = new User();
            user.setUsername(registerModel.getUsername());
            user.setEmail(registerModel.getEmail());
            user.setPassword(encoder.encode(registerModel.getPassword()));
            userService.add(user);
            var basicRole = roleService.findBasicRole("");
            if(basicRole.isEmpty()) return "views/authen/register";
            var basicUserRole = new UserRole();
            basicUserRole.setUserId(user.getId());
            basicUserRole.setRoleId(basicRole.get().getId());
            roleList.add(basicUserRole);

            if(roleList.isEmpty()){
                return "views/authen/register";
            }
            for (var item: roleList
            ) {
                System.out.println(userRoleService.add(item).getDescription());
            }

            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            registerModel.getUsername(),
                            registerModel.getPassword(),
                            new ArrayList<>()
                    )
            );
            if(authentication.isAuthenticated()) return "redirect:/";
            return "redirect:/login";
        }
    }



    @PostMapping("/logout")
    public String logout(HttpServletRequest req,
                         HttpServletResponse res) {
        var auth = SecurityContextHolder.
                getContext().getAuthentication();
        if(auth != null) new SecurityContextLogoutHandler().logout(req, res, auth);
        return "redirect:/login";
    }
}
