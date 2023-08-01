package umstj.nt_tcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class NtTcpApplication {

	public static void main(String[] args) {
		SpringApplication.run(NtTcpApplication.class, args);
	}


	}
