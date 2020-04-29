package project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import project.db.CreditRequestDAO;
import project.model.ClientAccountant;
import project.model.CreditWorker;

import java.util.ArrayList;

@Component
public class UserRolesService implements UserDetailsService {
    @Autowired
    private CreditRequestDAO requests;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClientAccountant accountant = requests.findClientByLogin(username);
        CreditWorker worker = requests.findCreditWorkerByLogin(username);
        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        if (username.equals("boss")) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        }
        if (accountant == null && worker == null) {
            throw new UsernameNotFoundException("No user " + username + " found");
        } else if (accountant != null) {
            roles.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
            return new User(username, accountant.getEncodedPassword(), roles);
        } else {
            roles.add(new SimpleGrantedAuthority("ROLE_WORKER"));
            return new User(username, worker.getEncodedPassword(), roles);
        }

    }
}
