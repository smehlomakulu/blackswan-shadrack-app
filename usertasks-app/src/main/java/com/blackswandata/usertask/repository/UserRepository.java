package com.blackswandata.usertask.repository;

import com.blackswandata.usertask.entity.Task;
import com.blackswandata.usertask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
