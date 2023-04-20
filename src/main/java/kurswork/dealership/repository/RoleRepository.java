package kurswork.dealership.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kurswork.dealership.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
	Role findByName(String name);
}
