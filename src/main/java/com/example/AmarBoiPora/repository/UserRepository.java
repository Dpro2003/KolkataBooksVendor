package com.example.AmarBoiPora.repository;


import com.example.AmarBoiPora.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<Users,String> {

    Optional<Users> findByContactNumber(String contactNumber );
    Optional<Users> findByEmail(String email);
    boolean existsByContactNumber(String contactNumber);

    boolean existsByEmail(String email);

    boolean existsByAadhaarNumber(String aadhaarNumber);


}
