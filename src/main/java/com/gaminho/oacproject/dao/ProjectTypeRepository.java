package com.gaminho.oacproject.dao;

import com.gaminho.oacproject.model.MC;
import com.gaminho.oacproject.model.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTypeRepository extends JpaRepository<ProjectType, Long> {
}
