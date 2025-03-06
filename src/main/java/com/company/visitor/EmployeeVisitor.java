package com.company.visitor;

import com.company.model.EmployeeComponent;

public interface EmployeeVisitor {
    void visit(EmployeeComponent employee);
}
