package com.evgenltd.lt.repository;

import com.evgenltd.lt.entity.TicketAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketAttributeRepository extends JpaRepository<TicketAttribute, Long> {

    List<TicketAttribute> findByAttributeName(String name);

    Optional<TicketAttribute> findByAttributeNameAndTicketId(String name, Long id);

}
