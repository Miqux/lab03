package com.example.lab03.Repository;

import com.example.lab03.Models.Category;
import com.example.lab03.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
