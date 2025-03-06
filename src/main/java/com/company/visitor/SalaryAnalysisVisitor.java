package com.company.visitor;

import com.company.model.EmployeeComponent;

public class SalaryAnalysisVisitor implements EmployeeVisitor {

    @Override
    public void visit(EmployeeComponent employee) {
        if (!employee.getSubordinates().isEmpty()) {
            double averageSubordinateSalary = employee.getSubordinates().stream()
                    .mapToInt(EmployeeComponent::getSalary)
                    .average()
                    .orElse(0);

            double minExpectedSalary = averageSubordinateSalary * 1.2; // 20% more than average
            double maxExpectedSalary = averageSubordinateSalary * 1.5; // 50% more than average

            if (employee.getSalary() < minExpectedSalary) {
                double difference = minExpectedSalary - employee.getSalary();
                System.out.printf("Manager %s %s (ID: %d) earns less than they should by: %.2f%n",
                        employee.getFirstName(), employee.getLastName(), employee.getId(), difference);
            } else if (employee.getSalary() > maxExpectedSalary) {
                double difference = employee.getSalary() - maxExpectedSalary;
                System.out.printf("Manager %s %s (ID: %d) earns more than they should by: %.2f%n",
                        employee.getFirstName(), employee.getLastName(), employee.getId(), difference);
            }
        }
    }
}
