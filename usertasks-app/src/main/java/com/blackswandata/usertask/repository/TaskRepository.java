package com.blackswandata.usertask.repository;

import com.blackswandata.usertask.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT * FROM task t WHERE t.status = 'PENDING' AND t.date_time < current_timestamp", nativeQuery = true)
    List<Task> findOutdatedTaskStatus();
}
