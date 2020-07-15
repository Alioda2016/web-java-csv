package com.datagearbi.serviceImpl;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.datagearbi.constants.InternalConstant;

import com.datagearbi.service.UploaderCIAListTemplate;
import com.opencsv.CSVWriter;

import io.github.bonigarcia.wdm.ChromeDriverManager;


@Service
public class UploaderCIAListTemplateImpl extends UploaderCIAListTemplate{
	
	private static final Logger logger = LoggerFactory.getLogger(UploaderCIAListTemplateImpl.class);

	
	@Autowired
	private InternalConstant internalConstant;
	
	
	
	@Override
	public void downloadFile() throws StaleElementReferenceException, IOException {
		logger.info("reading data from CIA Source");
		String csvFile = internalConstant.getDownloadDirectoryPath();
		String URL = internalConstant.getMlcuURL();
					String version = null;
					String versionURL = "https://chromedriver.storage.googleapis.com/LATEST_RELEASE";
					String html = null;
					try {
						html = Jsoup.connect(versionURL).get().html();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Document doc = Jsoup.parseBodyFragment(html);
					version = doc.body().ownText();
					ChromeDriverManager.getInstance().version(version).setup();
					WebDriver driver = new ChromeDriver();				
					 driver.get(URL);
						try
						{
							Thread.sleep(3000);
						}
						catch(Exception e)
						{
							logger.info("faild to get the URL" + e);
							e.printStackTrace();
						}
											
					 driver.manage().window().maximize();
					 List<WebElement> allLinks = driver.findElements(By.tagName("a"));
					  String href="";
					  CSVWriter writer = null;
					  File file = new File(internalConstant.getDownloadDirectoryPath());
					try {
						logger.info("deleting file if exist");
						FileUtils.forceDeleteOnExit(file);
						writer = new CSVWriter(new FileWriter(csvFile));
					} catch (IOException e1) {
						logger.info("error in writing");
						e1.printStackTrace();
					}
					  String [] record = "Occupation_desc,Entity_name,Country_name".split(",");
					  writer.writeNext(record);
					  int count = 1;
					 for(WebElement link:allLinks){
						 
					 if(link.getAttribute("href")!=null&&link
							 	.getAttribute("href").endsWith(".html")&&link
							 			.getAttribute("href").contains("world-leaders-1")&&!link
							 				.getAttribute("href").contains("index"))
					 {
						 href=link.getAttribute("href"); 						 
						 String html1 = null;
							try {
								html1 = Jsoup.connect(href).get().html();
							} catch (IOException e1) {
								logger.info("error getting href");
								e1.printStackTrace();
							}
							Document page = Jsoup.parseBodyFragment(html1);
							
						 List<Element> spans = page.getElementsByTag("span");
						 List<Element> descs = page.getElementsByClass("title");
						 List<Element> names = page.getElementsByClass("cos_name");
						 String countryName ="";
						 List<String> descriptions = new ArrayList<>();
						 List<String> listNames = new ArrayList<>();
						 
						 for(Element span: spans) {
							 if(span.id().contains("countryNameSpan_")) {
								 countryName = span.text();
								 logger.info("countryName: " + countryName);
							 }
						 }
						 for(Element desc: descs) {
							 descriptions.add(desc.text());
//							 logger.info("description: " + desc.text());
						 }
						 for(Element name: names) {
							 listNames.add(name.text());
//							 logger.info("name: " + name.text());
						 }
						 
						 count += listNames.size();
						 
//						 logger.info("listNames size: " + listNames.size());
//						 logger.info("descriptions list size: " + descriptions.size());
						 
						 
						 for (int i = 0; i < descriptions.size(); i++) {
							 String d = descriptions.get(i);
							 String n = listNames.get(i);
							 logger.info("description: " + d + ", " + " name: " + n);
							 String[] nextLine = new String[] {d, n, countryName};
							writer.writeNext(nextLine );
						}
						
						 logger.info("end writting ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");					
					 }
					 }
					    logger.info("All data records count: " + count);
					 	writer.close();
						driver.close();
		
					if (href.isEmpty()) {
						logger.error("web data not found");
		
						throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CIAList data not found in website");
					}
		
	}
}
