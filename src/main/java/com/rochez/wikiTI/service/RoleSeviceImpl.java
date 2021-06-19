package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Role;
import com.rochez.wikiTI.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleSeviceImpl implements RoleService{
    @Autowired
    RoleRepository roleRepository;
    @Override
    public Role getRole(Long id) {
        return roleRepository.findById(id).get();
    }
}
