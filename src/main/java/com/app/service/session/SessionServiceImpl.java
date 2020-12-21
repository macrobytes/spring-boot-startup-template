package com.app.service.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;
import javax.servlet.http.HttpServletRequest;
import com.app.service.user.UserService;

@Service
public class SessionServiceImpl implements SessionService {
    private static final String USER_ID = "userId";

    @Autowired
    private UserService userService;

    @Override
    public long getUserId(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute(USER_ID);
        if (null == userId) {
            return init(request);
        }
        return userId;
    }

    @Override
    public long init(HttpServletRequest request) {
        String username = getUserName(request);
        long userId = userService.findByUsername(username).get().getId();
        request.getSession().setAttribute(USER_ID, userId);
        return userId;
    }

    @Override
    public void terminate(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    private String getUserName(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        byte[] encodedUsernamePassword = authHeader.substring(6).getBytes();
        String decodedUsernamePassword = new String(Base64.decodeBase64(encodedUsernamePassword));
        return decodedUsernamePassword.substring(0, decodedUsernamePassword.indexOf(":"));
    }
}
