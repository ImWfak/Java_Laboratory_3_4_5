package info.controllers;

import info.entities.User;
import info.entities.enums.RoleEnum;
import info.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@RequestMapping("/register")
@Controller
public class RegisterController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private Boolean checkUserExist(User user) {
        for (User userInList : userService.findAll()) {
            if (userInList.getUsername().equals(user.getUsername()))
                return true;
        }
        return false;
    }
    @PostMapping("/user")
    public String registerUser(RedirectAttributes redirectAttributes, User user) {
        if (checkUserExist(user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "User with this username already exist");
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/load/register/user";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user, RoleEnum.ROLE_USER);
        return "redirect:/load/login";
    }
    @PostMapping("/admin")
    public String registerAdmin(RedirectAttributes redirectAttributes, User user) {
        if (checkUserExist(user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "User with this username already exist");
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/load/register/admin";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user, RoleEnum.ROLE_ADMIN);
        return "redirect:/load/login";
    }
}
