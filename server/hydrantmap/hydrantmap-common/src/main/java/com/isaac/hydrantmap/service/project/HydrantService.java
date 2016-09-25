package com.isaac.hydrantmap.service.project;

import java.util.Collection;

import com.isaac.hydrantmap.common.entity.HydrantEntity;

public interface HydrantService
{
	Collection<HydrantEntity> findAllHydrants();

}
