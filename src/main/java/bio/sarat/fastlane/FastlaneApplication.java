package bio.sarat.fastlane;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class FastlaneApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FastlaneApplication.class, args);
	}
	
	@Bean(name = "auditingDateTimeProvider")
	public DateTimeProvider dateTimeProvider() {
		return () -> Optional.of(OffsetDateTime.now());
	}

}
