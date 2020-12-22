package com.app.component.dev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;

import com.app.dao.user.UserModel;
import com.app.service.user.UserService;

@Component
public class DevSetup {
        public static final String DEV_USERNAME = "admin";
        public static final String DEV_PASSWORD = "password";
        private static final String DEV_FULL_NAME = "Admin User";
        private static final String DEV_EMAIL = "admin@localdev.com";

        @Autowired
        private UserService userService;
        @Value("${local.development}")
        private boolean localDevelopment;

        @EventListener
        public void init(ApplicationReadyEvent event) {
                if (localDevelopment) {
                        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
                        userService.create(new UserModel(DEV_USERNAME, DEV_FULL_NAME, DEV_EMAIL,
                                        passwordEncoder.encode(DEV_PASSWORD), UserModel.Role.OWNER, null));
                }
        }
}
