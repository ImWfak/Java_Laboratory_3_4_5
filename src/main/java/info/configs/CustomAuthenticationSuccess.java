package info.configs;

import info.entities.enums.RoleEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

public class CustomAuthenticationSuccess implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        Set<String> roleSet = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roleSet.contains(RoleEnum.ROLE_ADMIN.toString()))
            request.getSession(false).setMaxInactiveInterval(60);
        else
            request.getSession(false).setMaxInactiveInterval(120);
        response.sendRedirect("/load/applicantsTable");
    }
}
