package com.wallace.uploadcnab.web.controller;

import com.wallace.uploadcnab.domain.User;
import com.wallace.uploadcnab.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping
public class UserController {

    private static String authorizationRequestBaseUri = "oauth2/authorization";
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
    @Autowired
    private UserService userService;

    @GetMapping({"/sign-up"})
    public String newUser(User user) {

        return "user/register";
    }

    @PostMapping("/user/save")
    public String saveUser(User user, RedirectAttributes attr) {
        try {
            user.setRole("USER");
            userService.save(user);
            attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
        } catch (DataIntegrityViolationException ex) {
            attr.addFlashAttribute("falha", "Cadastro não realizado, email já existente.");
        }
        return "redirect:/login";
    }

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping({"/login"})
    public String login(Model model) {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        assert clientRegistrations != null;
        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put("Entrar com " + registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);

        return "login";
    }

    @GetMapping({"/login-error"})
    public String loginError(ModelMap model, HttpServletRequest resp) {
        HttpSession session = resp.getSession();
        String lastException = String.valueOf(session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION"));
        if (lastException.contains(SessionAuthenticationException.class.getName())) {
            model.addAttribute("alerta", "erro");
            model.addAttribute("titulo", "Acesso recusado!");
            model.addAttribute("texto", "Você já está logado em outro dispositivo.");
            model.addAttribute("subtexto", "Faça o logout ou espere sua sessão expirar.");
            return "login";
        }

        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put("Entrar com " + registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);

        model.addAttribute("alerta", "erro");
        model.addAttribute("titulo", "Crendenciais inválidas!");
        model.addAttribute("texto", "Login ou senha incorretos, tente novamente.");
        model.addAttribute("subtexto", "Acesso permitido apenas para cadastros já ativados.");
        return "login";
    }

    @GetMapping("/expired")
    public String sessaoExpirada(ModelMap model) {
        model.addAttribute("alerta", "erro");
        model.addAttribute("titulo", "Acesso recusado!");
        model.addAttribute("texto", "Sua sessão expirou.");
        model.addAttribute("subtexto", "Você logou em outro dispositivo");
        return "login";
    }

    // acesso negado
    @GetMapping({"/access-denied"})
    public String acessoNegado(ModelMap model, HttpServletResponse resp) {
        model.addAttribute("status", resp.getStatus());
        model.addAttribute("error", "Acesso Negado");
        model.addAttribute("message", "Você não tem permissão para acesso a esta área ou ação.");
        return "error";
    }
}
