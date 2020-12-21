package com.app.service.user;

import java.util.Optional;

import com.app.dao.user.UserModel;
import com.app.dao.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel create(UserModel model) {
        return userRepository.save(model);
    }

    @Override
    public Optional<UserModel> findByUsername(String username) {
        UserModel model = new UserModel();
        model.setUsername(username);
        Example<UserModel> modelExample = Example.of(model);
        return userRepository.findOne(modelExample);
    }
}
