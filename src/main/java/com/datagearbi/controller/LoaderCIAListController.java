package com.datagearbi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.datagearbi.service.UploaderCIAListTemplate;

@RestController
@CrossOrigin(origins = "*")
public class LoaderCIAListController {

	@Autowired
	private UploaderCIAListTemplate UploaderTemplate;

	@Value("${cia.file.directory}")
	private String fileLocation;
	
	private static final Logger logger = LoggerFactory.getLogger(LoaderCIAListController.class);

	@GetMapping(value = "${loader.route.manual.path}")
	public ResponseEntity<String> testLoader() {

		try {

			logger.info(" start test pathlist  -----------------");
			this.UploaderTemplate.uploadFile();

			return ResponseEntity.ok().body("File uploaded successfully!, at : " + fileLocation);
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "/")
	public String index() {

		return "CIA UPLOADER WORKING SUCCESSFULLY!!";

	}

}
