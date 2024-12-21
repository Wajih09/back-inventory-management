package com.tech.gestiondestock.repository;

import com.tech.gestiondestock.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesDao extends JpaRepository<Roles, Integer> {
}
