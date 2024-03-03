package com.example.servicesbcrypt.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryUser extends JpaRepository<BeanUser, Long>{
    BeanUser findBeanUserByEmail (String email);

    boolean existsBeanUserByEmail (String email);
}
