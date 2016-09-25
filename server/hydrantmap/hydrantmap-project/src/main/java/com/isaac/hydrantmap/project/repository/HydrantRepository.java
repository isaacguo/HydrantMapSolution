package com.isaac.hydrantmap.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.isaac.hydrantmap.common.entity.HydrantEntity;

@RepositoryRestResource(exported=false)
public interface HydrantRepository  extends JpaRepository<HydrantEntity, Long> {

}
