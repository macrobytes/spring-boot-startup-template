package com.app.service.user;

import java.util.Optional;
import com.app.dao.user.UserModel;

public interface UserService {
    public Optional<UserModel> findByUsername(String username);

    public UserModel create(UserModel userModel);
}