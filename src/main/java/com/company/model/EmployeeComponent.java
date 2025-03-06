package com.company.model;

import com.company.visitor.EmployeeVisitor;

import java.util.List;

public interface EmployeeComponent {
     void accept(EmployeeVisitor visitor);
     int getId();
     String getFirstName();
     String getLastName();
     int getSalary();
     Integer getManagerId();
     List<EmployeeComponent> getSubordinates();
}
