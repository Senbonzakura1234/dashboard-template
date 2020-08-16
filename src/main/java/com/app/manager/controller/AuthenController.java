package com.app.manager.controller;

import com.app.manager.entity.User;
import com.app.manager.entity.UserRole;
import com.app.manager.model.HelperMethod;
import com.app.manager.model.midware_model.LoginModel;
import com.app.manager.model.midware_model.RegisterModel;
import com.app.manager.model.midware_model.pojo.GithubPojo;
import com.app.manager.model.midware_model.pojo.GooglePojo;
import com.app.manager.model.midware_model.pojo.OauthUtils;
import com.app.manager.service.interfaceClass.RoleService;
import com.app.manager.service.interfaceClass.UserRoleService;
import com.app.manager.service.interfaceClass.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
public class AuthenController {
    private OauthUtils oauthUtils;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    AuthenticationManager authenticationManager;
    private static final String authorizationRequestBaseUri
            = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls
            = new HashMap<>();

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    // Login

    @GetMapping("/login")
    public String login(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        model.addAttribute("loginModel", new LoginModel());


        //
        Iterable<ClientRegistration> clientRegistrations = null;
        var type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);

        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            //noinspection unchecked
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        if (clientRegistrations != null) {
            clientRegistrations.forEach(registration ->
                    oauth2AuthenticationUrls.put(registration.getClientName(),
                            authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        }
        model.addAttribute("urls", oauth2AuthenticationUrls);
        //
        return "views/authen/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginModel loginModel,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
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
        if (authentication.isAuthenticated()) return "redirect:/";
        return "redirect:/login?error";
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        model.addAttribute("registerModel", new RegisterModel());

        //
        Iterable<ClientRegistration> clientRegistrations = null;
        var type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);

        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            //noinspection unchecked
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        if (clientRegistrations != null) {
            clientRegistrations.forEach(registration ->
                    oauth2AuthenticationUrls.put(registration.getClientName(),
                            authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        }
        model.addAttribute("urls", oauth2AuthenticationUrls);
        //

        return "views/authen/register";
    }

    @PostMapping(value = "/register")
    public String register(@Validated @ModelAttribute RegisterModel registerModel,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            for (var item :
                    bindingResult.getAllErrors()) {
                System.out.println(item.getDefaultMessage());
            }
            return "views/authen/register";
        } else {
            var basicRole = roleService.findBasicRole();
            if (basicRole.isEmpty()) return "views/authen/register";
            List<UserRole> roleList = new ArrayList<>();
            var user = new User();
            user.setUsername(registerModel.getUsername());
            user.setEmail(registerModel.getEmail());
            user.setPassword(encoder.encode(registerModel.getPassword()));
            userService.add(user);
            var basicUserRole = new UserRole();
            basicUserRole.setUserId(user.getId());
            basicUserRole.setRoleId(basicRole.get().getId());
            roleList.add(basicUserRole);

            if (roleList.isEmpty()) {
                return "views/authen/register";
            }
            for (var item : roleList
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
            if (authentication.isAuthenticated()) return "redirect:/";
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/oauth2/callback")
    public String googleCallBack(OAuth2AuthenticationToken authentication, Model model) {
        var registrationId = authentication.getAuthorizedClientRegistrationId();
        var client = authorizedClientService
                .loadAuthorizedClient(
                        registrationId,
                        authentication.getName());
        var userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();

//        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
//            var restTemplate = new RestTemplate();
//            var headers = new HttpHeaders();
//            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
//                    .getTokenValue());
//            var entity = new HttpEntity<>("", headers);
//            if (registrationId.toLowerCase().equals("google")) {
//                var response = restTemplate
//                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, GooglePojo.class);
//                var userAttributes = response.getBody();
//                if (userAttributes == null) return "redirect:/login?error";
//                System.out.println(userAttributes.getName());
//                System.out.println("Null");
//            } else if (registrationId.toLowerCase().equals("github")) {
//                var response = restTemplate
//                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, GithubPojo.class);
//                var userAttributes = response.getBody();
//                if (userAttributes == null) return "redirect:/login?error";
//                System.out.println(userAttributes.getName());
//            } else {
//                return "redirect:/login?error";
//            }
//
////            model.addAttribute("name", userAttributes.get("name"));
//            return "redirect:/dashboard";
//        }
        if(StringUtils.isEmpty(userInfoEndpointUri)) return "redirect:/login?error";
        oauthUtils = new OauthUtils();
        Optional<User> user = oauthUtils.getPojoInfo(userInfoEndpointUri,
                client.getAccessToken().getTokenValue(), registrationId);
        if(user.isEmpty()) return "redirect:/login?error";
        System.out.println(user.get().getUsername());
        System.out.println(user.get().getEmail());



        return "redirect:/dashboard";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest req,
                         HttpServletResponse res, Model model) {
        var auth = SecurityContextHolder.
                getContext().getAuthentication();
        if (auth != null) new SecurityContextLogoutHandler().logout(req, res, auth);
        model.addAttribute("current_user", "");
        return "redirect:/login";
    }
}
