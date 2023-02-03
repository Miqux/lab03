package com.example.lab03.Services;

import com.example.lab03.Interface.UserService;
import com.example.lab03.Models.ProfileNames;
import com.example.lab03.Models.Role;
import com.example.lab03.Repository.RoleRepository;
import com.example.lab03.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
@Profile(ProfileNames.USERS_IN_DATABASE)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return convertToUserDetails(user);
    }

    private UserDetails convertToUserDetails(com.example.lab03.Models.User user) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getType().toString()));
        }

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);//UWAGA: klasa ma też drugi konstruktor – więcej parametrów!!!
    }
    public UserDetails GetCurrentUser(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public boolean CreateUser(com.example.lab03.Models.User user) {
        Role roleUser = roleRepository.save(new Role(Role.Types.ROLE_USER));
        Role roleAdmin = roleRepository.save(new Role(Role.Types.ROLE_ADMIN));
        user.setEnabled(true);
        user.setAddedDate(LocalDate.now());
        user.setRoles(new HashSet<>(Arrays.asList(roleUser)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public byte[] GetUserAvatarByUsername(String id) {
        if(userRepository.findByUsername(id) == null)
            return new byte[] { (byte)0xe0, 0x4f, (byte)0xd0,
                    0x20, (byte)0xea, 0x3a, 0x69, 0x10, (byte)0xa2, (byte)0xd8, 0x08, 0x00, 0x2b,
                    0x30, 0x30, (byte)0x9d };
        return userRepository.findByUsername(id).getFileContent();
    }

    @Override
    public ArrayList<com.example.lab03.Models.User> findAll() {
        return userRepository.findAll();
    }
    @Transactional
    @Override
    public void DeleteUserById(int id) {
        userRepository.deleteById(new Long(id));
    }
}
