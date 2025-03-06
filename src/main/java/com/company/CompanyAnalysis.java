package com.company;

import com.company.model.Employee;
import com.company.model.EmployeeComponent;
import com.company.visitor.ReportingLineAnalysisVisitor;
import com.company.visitor.SalaryAnalysisVisitor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CompanyAnalysis {
    public static void main(String[] args) {
        String csvFile = "employees.csv"; // Path to the CSV file
        Map<Integer, EmployeeComponent> employees = new HashMap<>();
        EmployeeComponent ceo = null;

        // Read the CSV file and build the employee hierarchy
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Skip invalid lines
                if (data.length < 4 || data.length > 5) {
                    System.err.println("Invalid line in CSV file: " + line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(data[0].trim());
                    String firstName = data[1].trim();
                    String lastName = data[2].trim();
                    int salary = Integer.parseInt(data[3].trim());
                    Integer managerId = (data.length == 5 && !data[4].trim().isEmpty())
                            ? Integer.parseInt(data[4].trim())
                            : null;

                    EmployeeComponent employee = new Employee(id, firstName, lastName, salary, managerId);
                    employees.put(id, employee);

                    if (managerId == null) {
                        ceo = employee; // CEO has no manager
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in line: " + line);
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Build the hierarchy by linking subordinates to their managers
        for (EmployeeComponent employee : employees.values()) {
            if (employee.getManagerId() != null) {
                EmployeeComponent manager = employees.get(employee.getManagerId());
                if (manager != null) {
                    ((Employee) manager).addSubordinate(employee);
                } else {
                    System.err.println("Manager not found for employee: " + employee.getId());
                }
            }
        }

        // Perform salary analysis using the SalaryAnalysisVisitor
        if (ceo != null) {
            SalaryAnalysisVisitor salaryVisitor = new SalaryAnalysisVisitor();
            ceo.accept(salaryVisitor);

            // Perform reporting line analysis using the ReportingLineAnalysisVisitor
            ReportingLineAnalysisVisitor reportingLineVisitor = new ReportingLineAnalysisVisitor();
            ceo.accept(reportingLineVisitor);
        } else {
            System.err.println("CEO not found in the CSV file.");
        }
    }
}

