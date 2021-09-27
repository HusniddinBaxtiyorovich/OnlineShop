package uz.personal.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import uz.personal.utils.UserSession;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;

@Configuration
@PropertySource(value = {"classpath:application.properties", "classpath:hibernate.properties"})
@PropertySource(name = "hibernate", value = "classpath:hibernate.properties")
@EnableTransactionManagement
@EnableJpaAuditing
public class HibernateConfig implements AuditorAware<Long> {

  private final UserSession userSession;

  public HibernateConfig(UserSession userSession) {
    this.userSession = userSession;
  }

  @Bean
  public Module hibernate5Module() {
    return new Hibernate5Module();
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);

    return transactionManager;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  @Override
  public Optional<Long> getCurrentAuditor() {
    return Optional.ofNullable(
        userSession.getUser() == null ? null : userSession.getUser().getId());
  }
}
