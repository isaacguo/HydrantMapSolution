package com.isaac.hydrantmap.project.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isaac.hydrantmap.common.entity.HydrantEntity;
import com.isaac.hydrantmap.common.service.AbstractService;
import com.isaac.hydrantmap.project.repository.HydrantRepository;
import com.isaac.hydrantmap.service.project.HydrantService;

@Service
@Transactional
public class HydrantServiceImpl extends AbstractService implements HydrantService {

	@Autowired
	HydrantRepository hydrantRepository;
	@Override
	public Collection<HydrantEntity> findAllHydrants() {
		return hydrantRepository.findAll();

	}

}
