package com.isaac.hydrantmap.project.restcontroller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isaac.hydrantmap.common.RequestMappingConstants;
import com.isaac.hydrantmap.common.controller.AbstractController;
import com.isaac.hydrantmap.common.entity.HydrantEntity;
import com.isaac.hydrantmap.service.project.HydrantService;

@RestController
@RequestMapping(RequestMappingConstants.HYDRANT_PATH)
public class HydrantRestController extends AbstractController {

	
	@Autowired
	HydrantService hydrantService;

	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<HydrantEntity>> findAllHydrants()
	{
		logControllerInfo("findAllHydrants()");
		return new ResponseEntity<Collection<HydrantEntity>>(hydrantService.findAllHydrants(), HttpStatus.OK);
	}
	
}
