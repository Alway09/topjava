package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/users")
public class UsersMvcController extends AbstractUserController {
    private static final Logger log = LoggerFactory.getLogger(UsersMvcController.class);

    public UsersMvcController(UserService service) {
        super(service);
    }

    @GetMapping
    public String get(Model model) {
        log.info("users");
        model.addAttribute("users", super.getAll());
        return "users";
    }

    @PostMapping
    public String set(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        log.info("setUser {}", userId);
        SecurityUtil.setAuthUserId(userId);
        return "redirect:meals";
    }
}
