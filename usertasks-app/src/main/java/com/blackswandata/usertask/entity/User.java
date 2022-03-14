package com.blackswandata.usertask.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(name = "user_name", unique = true)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.PERSIST
            })
    @JoinTable(name = "users_task",
            joinColumns = { @JoinColumn( name = "user_id") },
            inverseJoinColumns = { @JoinColumn( name = "task_id")})
    private Set<Task> tasks = new HashSet<>();

    public void addTask(Task task) {
        this.tasks.add(task);
        task.getUsers().add(this);
    }

    public void removeTask(Long taskId) {
        Task task = this.tasks.stream().filter(t -> t.getId() == taskId).findFirst().orElse(null);
        if(task != null) this.tasks.remove(task);
        task.getUsers().remove(this);
    }

    public Optional<Task> getTask(Set<Task> tasks, Long taskId) {
        return tasks.stream().filter(t -> t.getId() == taskId).findFirst();
    }
}
