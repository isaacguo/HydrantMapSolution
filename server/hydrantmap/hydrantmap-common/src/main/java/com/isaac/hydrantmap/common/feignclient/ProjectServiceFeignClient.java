package com.isaac.hydrantmap.common.feignclient;

import java.util.Collection;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.isaac.hydrantmap.common.HydrantMapConstants;
import com.isaac.hydrantmap.common.RequestMappingConstants;
import com.isaac.hydrantmap.common.entity.HydrantEntity;

@FeignClient(name = HydrantMapConstants.HydrantMap_Project)
public interface ProjectServiceFeignClient {

	@RequestMapping(method = RequestMethod.GET, value = RequestMappingConstants.HYDRANT_PATH)
	ResponseEntity<Collection<HydrantEntity>> findAllHydrants();
	
}
