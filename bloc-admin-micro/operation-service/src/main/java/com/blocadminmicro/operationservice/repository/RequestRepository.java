package com.blocadminmicro.operationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocadminmicro.operationservice.entity.Request;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{

}
