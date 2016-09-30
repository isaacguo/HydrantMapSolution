package com.isaac.hydrantmap;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.isaac.hydrantmap.common.HydrantForm;
import com.isaac.hydrantmap.common.HydrantStatus;
import com.isaac.hydrantmap.common.WayToAcquireWater;
import com.isaac.hydrantmap.common.entity.FirehouseEntity;
import com.isaac.hydrantmap.common.entity.HydrantEntity;
import com.isaac.hydrantmap.project.repository.FirehouseRepository;
import com.isaac.hydrantmap.project.repository.HydrantRepository;
import com.isaac.hydrantmap.service.project.HydrantService;

@SpringBootApplication
// @EnableDiscoveryClient
@EnableFeignClients
@EnableAutoConfiguration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.isaac.hydrantmap.common.feignclient.consumer.project.*"))
//@Transactional
public class HydrantmapProjectApplication {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean
	CommandLineRunner commandLineRunner(HydrantRepository hydrantRepository, FirehouseRepository firehouseRepository,
			HydrantService hydrantService)

	{
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {

				logger.info("In CommandLineRunner.run()");


				hydrantRepository.findAll().forEach(r->{logger.info(r.toString());});
				
				/*
				 * 
				 * HydrantEntity hydrant = new HydrantEntity();
				 * 
				 * hydrant.setAddress("轩辕西路水厂东侧110米处");
				 * hydrant.setAltitude(40.094411);
				 * hydrant.setLongitude(115.211122); RegionEntity district = new
				 * RegionEntity(); district.setName("涿鹿县");
				 * hydrant.setDistrict(district);
				 * 
				 * hydrant.setWaterSourceNumber(1311101001L);
				 * hydrant.setPipeDiameter("300"); PipeTypeEntity pipeType = new
				 * PipeTypeEntity(); pipeType.setName("环状");
				 * hydrant.setPipeType(pipeType);
				 * 
				 * FirehouseEntity responsibleUnit = new FirehouseEntity();
				 * responsibleUnit.setName("涿鹿县中队");
				 * hydrant.setResponsibleUnit(responsibleUnit);
				 * 
				 * hydrant.setStatus("良好"); WaterSourceFormEntity
				 * waterSourceForm = new WaterSourceFormEntity();
				 * waterSourceForm.setName("地下消火栓");
				 * hydrant.setWaterSourceForm(waterSourceForm);
				 * 
				 * HydrantEntity hydrant1 = new
				 * HydrantEntity(1311101002L,district,"轩辕西路加油站前10米处",
				 * responsibleUnit,waterSourceForm,"300",pipeType,115.193019,40.
				 * 377616,"良好");
				 * 
				 * Set<HydrantEntity> hydrants=new HashSet<>();
				 * hydrants.add(hydrant); hydrants.add(hydrant1);
				 * hydrantRepository.save(hydrants);
				 */
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(HydrantmapProjectApplication.class, args);
	}
}
