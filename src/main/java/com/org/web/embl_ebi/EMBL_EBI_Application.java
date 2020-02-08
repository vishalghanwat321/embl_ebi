package com.org.web.embl_ebi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote This application is to manage person details, base framework spring boot is used to build this application.
 * @since 2020
 */

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class EMBL_EBI_Application {

    public static void main(String[] args) {
        SpringApplication.run(EMBL_EBI_Application.class, args);
    }
}