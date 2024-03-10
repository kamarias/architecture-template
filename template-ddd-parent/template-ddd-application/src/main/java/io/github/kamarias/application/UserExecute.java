package io.github.kamarias.application;

import io.github.kamarias.domain.User;
import io.github.kamarias.domain.repository.UserRepository;
import io.github.kamarias.model.UserDto;
import org.springframework.stereotype.Component;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/12/17 23:18
 */
@Component
public class UserExecute {

    private final UserRepository userRepository;

    public UserExecute(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String saveUser(UserDto userDto){
        User user = new User(userDto.getUserName(), userDto.getPassWord());
        user.handler();
        userRepository.save(user);
        return "abc";
    }





}
