package com.company.visitor;

import com.company.model.EmployeeComponent;

import java.util.HashSet;
import java.util.Set;

public class ReportingLineAnalysisVisitor implements EmployeeVisitor {
    private final  Set<Integer> reportedEmployees = new HashSet<>();

    @Override
    public void visit(EmployeeComponent employee) {
        if (employee == null) {
            return;
        }
        boolean debug = false;
        if (debug) {
            System.out.println("Visiting employee: " + employee.getFirstName() + " " + employee.getLastName());
        }
        visit(employee, 0);
    }

    private void visit(EmployeeComponent employee, int depth) {
        if (employee == null) {
            return;
        }

        if (depth > 4 && !reportedEmployees.contains(employee.getId())) {
            System.out.printf("Employee %s %s (ID: %d) has a reporting line that is too long by: %d%n",
                    employee.getFirstName(), employee.getLastName(), employee.getId(), depth - 4);
            reportedEmployees.add(employee.getId()); // Mark as reported
        }

        if (employee.getSubordinates() != null) {
            for (EmployeeComponent subordinate : employee.getSubordinates()) {
                visit(subordinate, depth + 1);
            }
        }
    }
}
