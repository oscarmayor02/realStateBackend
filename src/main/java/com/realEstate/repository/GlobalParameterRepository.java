package com.realEstate.repository;
import com.realEstate.model.GlobalParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalParameterRepository extends JpaRepository<GlobalParameter, Long> {
}