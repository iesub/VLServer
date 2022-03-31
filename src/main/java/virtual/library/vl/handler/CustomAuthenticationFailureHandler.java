package virtual.library.vl.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.ui.Model;
import virtual.library.vl.dto.AuthorizationDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationFailureHandler
        implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {


        Map<String, Object> data = new HashMap<>();
        AuthorizationDTO userInfo = new AuthorizationDTO();
        userInfo.setAuthenticated(false);
        data.put(
                "userInfo",
                userInfo
                );

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(data));
    }
}