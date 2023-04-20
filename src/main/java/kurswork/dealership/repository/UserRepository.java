package kurswork.dealership.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import kurswork.dealership.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	User findByEmail(String email);
}
