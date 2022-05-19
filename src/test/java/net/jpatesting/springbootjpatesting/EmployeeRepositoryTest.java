package net.jpatesting.springbootjpatesting;

import java.util.ArrayList;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import java.util.List;
import net.jpatesting.springbootjpatesting.Employee;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTest {

	
	@Autowired 
	private EmployeeRepository employeeRepository;
    
	//Junit for save employee
	@Test
	@Order(1)
	@Rollback(value=false)
	public void saveEmployeeTest() {
	Employee employee=Employee.builder()
	                 .firstname("Mahammad")
	                 .lastname("Rafi")
	                 .email("mahammad.mst@gmail.com")
	                 .build();
	
	employeeRepository.save(employee);
	
	Assertions.assertThat(employee.getId()).isGreaterThan(0);
		}
	
	
	@Test
	@Order(2)
	public void getEmployeeTest() {
		
		Employee emp=employeeRepository.findById(1L).get();
		Assertions.assertThat(emp.getId()).isEqualTo(1L);
		}
@Test
@Order(3)
 public void getListOfEmployeesTest() {
	 
	List<Employee> employees=employeeRepository.findAll();
    Assertions.assertThat(employees.size()).isGreaterThan(0);

}
@Test
@Order(4)
@Rollback(value=false)
public void UpdateEmployeeTest() {
	Employee employee=employeeRepository.findById(1L).get();
	employee.setEmail("moula@gmail.com");
	
	Employee employeeUpdated=employeeRepository.save(employee);
	
	Assertions.assertThat(employeeUpdated.getEmail()).isEqualTo("moula@gmail.com");
}

@Test
@Order(5)
@Rollback(value=false)
public void deleteEmployeeTest() {
	Employee employee=employeeRepository.findById(1L).get();
	employeeRepository.delete(employee);
	Employee employee1=null;
	
	Optional<Employee> optionalEmployee=employeeRepository.findByEmail("moula@gmail.com");
	if(optionalEmployee.isPresent()) {
		employee1=optionalEmployee.get();
		}

		Assertions.assertThat(employee1).isNull();
}

}
