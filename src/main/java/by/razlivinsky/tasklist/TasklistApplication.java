package by.razlivinsky.tasklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The TasklistApplication class is the entry point for the Tasklist application.
 * It uses Spring Boot's SpringApplication to run the application.
 *
 * @author razlivinsky
 * @since 10.03.2024
 */
@SpringBootApplication
public class TasklistApplication {
	/**
	 * The main method to start the Tasklist application.
	 *
	 * @param args Command-line arguments passed to the application
	 */
 	public static void main(String[] args) {
		SpringApplication.run(TasklistApplication.class, args);
	}
}
