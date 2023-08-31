package com.fields.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fields.Models.Props;

import java.util.List;

public interface PropsRepository extends JpaRepository<Props, Long>{

    Props findOne(Long Id);

    void delete(Long id);

    List<Props> getByRefName(String refName);
    List<Props> getById(Long id);

    List<Props> findAll();
}

