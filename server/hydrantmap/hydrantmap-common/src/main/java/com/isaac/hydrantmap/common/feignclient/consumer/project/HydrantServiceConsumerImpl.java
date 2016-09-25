package com.isaac.hydrantmap.common.feignclient.consumer.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.isaac.hydrantmap.common.entity.HydrantEntity;
import com.isaac.hydrantmap.common.feignclient.ProjectServiceFeignClient;
import com.isaac.hydrantmap.common.service.AbstractService;
import com.isaac.hydrantmap.service.project.HydrantService;

@Service
public class HydrantServiceConsumerImpl extends AbstractService implements HydrantService
{
	@Autowired
	ProjectServiceFeignClient projectServiceFeignClient;

	public ProjectServiceFeignClient getProjectServiceFeignClient()
	{
		return projectServiceFeignClient;
	}

	@Override
	public Collection<HydrantEntity> findAllHydrants() {
		logServiceInfo("findAllHydrants");
		ResponseEntity<Collection<HydrantEntity>> result = projectServiceFeignClient.findAllHydrants();
		if (result.getStatusCode() == HttpStatus.OK)
			return result.getBody();
		else
			return null;
	}

}
