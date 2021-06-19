package com.rochez.wikiTI.security;

import com.rochez.wikiTI.model.Utilisateur;
import com.rochez.wikiTI.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description :Class pour définir la connexion à l'application
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UtilisateurRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Utilisateur user = userRepository.findByMail(mail);
        if(user == null) throw new UsernameNotFoundException(mail+" not found");
        return new MyUserDetails(user);
    }

}
