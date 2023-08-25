package com.scaler.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "usergroup")
@Getter
@Setter
public class Group extends BaseModel {
    private String name;

    @ManyToMany
    private List<User> members;

    @OneToMany
    private List<Expense> expenses;

    @ManyToOne
    private User createdBy;
}
