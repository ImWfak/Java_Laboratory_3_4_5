package info.services;

import info.entities.Role;
import info.entities.User;
import info.entities.enums.RoleEnum;
import info.repositories.RoleRepository;
import info.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format("No such user with this username: %s", username)
                )
        );
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoleCollection().stream().map(
                        role -> new SimpleGrantedAuthority(role.getName().toString())
                ).collect(Collectors.toList())
        );
    }
    public void save(User user) {
        List<RoleEnum> roleEnumList = List.of(RoleEnum.values());
        for (Role role : user.getRoleCollection()) {
            if(!roleEnumList.contains(role.getName()))
                throw new RuntimeException("No such role in roleEnum: " + role.getName());
            roleRepository.findByName(role.getName())
                    .orElseThrow(() -> new RuntimeException("No such role in table roles: " + role.getName()));
        }
        user.setRoleCollection(user.getRoleCollection());
        userRepository.save(user);
    }
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public User findNyUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
    public void updateById(Long id, User user) {
        User updatableUser = findById(id);
        if (updatableUser == null || user == null)
            return;
        updatableUser.setUsername(          user.getUsername()          );
        updatableUser.setPassword(          user.getPassword()          );
        updatableUser.setRoleCollection(    user.getRoleCollection()    );
        save(updatableUser);
    }
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
