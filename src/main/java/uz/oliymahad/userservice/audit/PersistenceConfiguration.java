package uz.oliymahad.userservice.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAwareProvider")
public class PersistenceConfiguration {

    @Bean
    AuditorAware<String> auditorAwareProvider(){
        return new AuditorAwareImpl();
    }
}
