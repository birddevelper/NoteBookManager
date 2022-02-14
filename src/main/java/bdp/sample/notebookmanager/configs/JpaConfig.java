package bdp.sample.notebookmanager.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "bdp.sample.notebookmanager.repositories")//, entityManagerFactoryRef="emf"
@PropertySource("application.properties")
@ComponentScan({"bdp.sample.notebookmanager"})
@EntityScan(basePackages = "bdp.sample.notebookmanager")
@EnableTransactionManagement
public class JpaConfig {

    @Autowired
    private Environment env;




    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));


        return dataSource;
    }






}