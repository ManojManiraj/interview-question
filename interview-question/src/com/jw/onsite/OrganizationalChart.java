package com.jw.onsite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class Employee{
	String name;
	int id;
	Employee manager;
	
	public Employee(String name, int id) {
		this.name = name;
		this.id = id;
	}
}

public class OrganizationalChart {
	
	/*
	 * This employeeMap will be a mapping of Manager and their reporting employees
	 * Employees who do not have any manager(Top level manager) will have key value as null
	 * Top Level Manager will have the reporting employee as himself (This helps to keep track of Employees who do not have any managers)
	 * Ideally there will be just one top level manager, but sometimes there might be multiple top level managers, this code can handle even such scenario
	*/
	
	public static Map<String,ArrayList<Employee>> employeeMap = new HashMap<String,ArrayList<Employee>>();
	public static Queue<Employee> currentLevel = new LinkedList<Employee>();
	
	public static void main(String args[]) {
		Employee adrian = new Employee("adrian",1);
		Employee tom = new Employee("tom",2);
		Employee david = new Employee("david",3);
		Employee john = new Employee("john",4);
		Employee joe = new Employee("joe",5);
		Employee  jason = new Employee("jason",6);
		
		tom.manager = adrian;
		david.manager = adrian;
		john.manager = tom;
		joe.manager = david;
		jason.manager = david;
		
		Employee[] employees = {adrian, tom, david, john, joe, jason};
		
		printEmployees(employees);
	}
	
	public static void printEmployees(Employee[] employees) {
		if (employees != null && employees.length == 0) {
			System.out.println("No Employees to print in the organizational chart");
			return;
		}
		buildEmployeeMap(employees); 
		print();
	}

	private static void print() {
		if(employeeMap.containsKey(null)) {
			currentLevel.addAll(employeeMap.get(null)); 
			StringBuilder sb = new StringBuilder();
			List<Employee> nextLevel = new LinkedList<Employee>();
			while(!currentLevel.isEmpty()) {
				Employee employee = currentLevel.peek();
				sb.append(employee.name);
				sb.append(",");
			
				if(employeeMap.containsKey(employee.name) && employeeMap.get(employee.name) != null && employeeMap.get(employee.name).size() > 0)
					nextLevel.addAll(employeeMap.get(employee.name));
				
				currentLevel.remove(currentLevel.peek());

				if(currentLevel.isEmpty()) {
					sb.deleteCharAt(sb.length()-1);
					System.out.println(sb.toString());
					sb.setLength(0);
					currentLevel.addAll(nextLevel);
					nextLevel.clear();
				}	
			}
		}
	}

	private static void buildEmployeeMap(Employee[] employees) {
		for(Employee employee : employees) {
			if(employee.manager != null) {
			if(employeeMap.containsKey(employee.manager.name)) {
				employeeMap.get(employee.manager.name).add(employee);
			}else {
				ArrayList<Employee> reportingEmployees = new ArrayList<Employee>();
				reportingEmployees.add(employee);
				employeeMap.put(employee.manager.name, reportingEmployees);
			}
			}else {
				if(employeeMap.get(null) != null)
					employeeMap.get(null).add(employee);
				else {
					ArrayList<Employee> reportingEmployees = new ArrayList<Employee>();
					reportingEmployees.add(employee);
					employeeMap.put(null, reportingEmployees);
				}
			}
		}
	}
}
