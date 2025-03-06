package com.company.model;

import com.company.visitor.EmployeeVisitor;

import java.util.ArrayList;
import java.util.List;

public class Employee implements EmployeeComponent {

    private final  int id;
    private final String  firstName;
    private final String lastName;
    private final int salary;
    private final Integer managerId;
    private final List<EmployeeComponent> subordinates = new ArrayList<>();

    public Employee(int id, String firstName, String lastName, int salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    @Override
    public void accept(EmployeeVisitor visitor) {
        //System.out.println("Visiting employee: " + this.getFirstName() + " " + this.getLastName());
        visitor.visit(this);
        for (EmployeeComponent subordinate : subordinates) {
            subordinate.accept(visitor);
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public int getSalary() {
        return salary;
    }

    @Override
    public Integer getManagerId() {
        return managerId;
    }

    @Override
    public List<EmployeeComponent> getSubordinates() {
        return subordinates;
    }
    public void addSubordinate(EmployeeComponent subordinate) {
        subordinates.add(subordinate);
    }
}
