package com.datagearbi.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InternalConstant {
	@Value("${cia.file.directory}")
	private String DownloadDirectoryPath;
	
	@Value("${cia.fileName}")
	private String fileName;

	@Value("${cia.website.url}")
	private String mlcuURL;
	
	@Value("${cia.indicatorFile.name}")
	private String IndicatorFileName;

	public InternalConstant() {
	}

	public String getDownloadDirectoryPath() {
		return DownloadDirectoryPath;
	}

	public void setDownloadDirectoryPath(String downloadDirectoryPath) {
		DownloadDirectoryPath = downloadDirectoryPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMlcuURL() {
		return mlcuURL;
	}

	public void setMlcuURL(String mlcuURL) {
		this.mlcuURL = mlcuURL;
	}

	public String getIndicatorFileName() {
		return IndicatorFileName;
	}

	public void setIndicatorFileName(String indicatorFileName) {
		IndicatorFileName = indicatorFileName;
	}

	
}
