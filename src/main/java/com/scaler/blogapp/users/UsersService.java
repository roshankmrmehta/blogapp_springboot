package com.scaler.blogapp.users;

import com.scaler.blogapp.users.dtos.CreateUserRequest;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserEntity createUser(CreateUserRequest u){
        var newUser = UserEntity.builder()
                .username(u.getUsername())
        //        .password(password) // TODO: encrypt password
                .email(u.getEmail())
                .build();

        return usersRepository.save(newUser);
    }

    public UserEntity getUser(String username){
        return usersRepository.findByUserName(username).orElseThrow(()-> new UserNotFoundException(username));
    }

    public UserEntity getUser(Long userId){
        return usersRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
    }

    public UserEntity loginUser(String username, String password){
        var user = usersRepository.findByUserName(username).orElseThrow(()-> new UserNotFoundException(username));
        // TODO: match password
        return user;
    }

    public static class UserNotFoundException extends IllegalArgumentException{
        public UserNotFoundException(String username){
            super("user with username"+username+" not found");
        }

        public UserNotFoundException(Long userId){
            super("user with id:  "+userId+" not found");
        }
    }
}
