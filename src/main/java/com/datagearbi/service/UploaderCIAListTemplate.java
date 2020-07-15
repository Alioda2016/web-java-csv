package com.datagearbi.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.opencsv.exceptions.CsvValidationException;


public abstract class UploaderCIAListTemplate {

	public final void uploadFile() throws Exception {
		downloadFile();
	}

	public abstract void downloadFile() throws IOException;
}
