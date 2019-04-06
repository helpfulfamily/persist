package army.helpful.persistha;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PersisthaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersisthaApplication.class, args);
	}
	@Bean
	@StreamMessageConverter
	public HibernateAwareObjectMapper customMessageConverter() {
		return new HibernateAwareObjectMapper();
	}


	public class HibernateAwareObjectMapper extends ObjectMapper {

		public HibernateAwareObjectMapper() {
			registerModule(new Hibernate5Module());
		}
	}
}
