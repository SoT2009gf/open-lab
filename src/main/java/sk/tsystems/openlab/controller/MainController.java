package sk.tsystems.openlab.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.JsonObject;

import io.nayuki.qrcodegen.QrCode;

import sk.tsystems.openlab.entity.Job;
import sk.tsystems.openlab.json.JsonProcessor;
import sk.tsystems.openlab.service.JobService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/")
public class MainController {

	private int jobCount;
	private List<String> qrCodes = new ArrayList<>();
	private static final String QR_FOLDER = System.getProperty("java.io.tmpdir");

	@Autowired
	JobService jobService;
	
	@Autowired
	ServletContext servletContext;

	@RequestMapping
	public String index() {
		clearSavedData();
		storeDataFromJSON();
		return "index";
	}

	private void storeDataFromJSON() {
		JsonProcessor jsonProcessor = new JsonProcessor();

		JsonObject fetchedData = jsonProcessor.createtJSONObject();
		jobCount = fetchedData.getAsJsonObject("SearchResult").getAsJsonPrimitive("SearchResultCount").getAsInt();
		String position;
		String employmentType;
		String startDate;
		String endDate;
		String description;
		String url;
		
		for (int i = 0; i < jobCount; i++) {
			position = fetchedData.getAsJsonObject("SearchResult").getAsJsonArray("SearchResultItems").get(i)
					.getAsJsonObject().getAsJsonObject("MatchedObjectDescriptor").get("PositionTitle").getAsString();
			employmentType = fetchedData.getAsJsonObject("SearchResult").getAsJsonArray("SearchResultItems").get(i)
					.getAsJsonObject().getAsJsonObject("MatchedObjectDescriptor").getAsJsonArray("PositionSchedule")
					.get(0).getAsJsonObject().get("Name").getAsString();
			startDate = fetchedData.getAsJsonObject("SearchResult").getAsJsonArray("SearchResultItems").get(i)
					.getAsJsonObject().getAsJsonObject("MatchedObjectDescriptor").get("PublicationStartDate")
					.getAsString();
			endDate = fetchedData.getAsJsonObject("SearchResult").getAsJsonArray("SearchResultItems").get(i)
					.getAsJsonObject().getAsJsonObject("MatchedObjectDescriptor").get("PublicationEndDate")
					.getAsString();
			description = fetchedData.getAsJsonObject("SearchResult").getAsJsonArray("SearchResultItems").get(i)
					.getAsJsonObject().getAsJsonObject("MatchedObjectDescriptor").getAsJsonObject("UserArea").get("TextJobDescription")
					.getAsString();
			url = "https://t-systems.jobs/careers-sk-en/" + fetchedData.getAsJsonObject("SearchResult")
					.getAsJsonArray("SearchResultItems").get(i).getAsJsonObject()
					.getAsJsonObject("MatchedObjectDescriptor").get("PositionURI").getAsString();
			jobService.addJob(new Job(position, employmentType, startDate, endDate, description));

			QrCode qrcode = QrCode.encodeText(url, QrCode.Ecc.MEDIUM);
			BufferedImage img = qrcode.toImage(2, 8);
			try {
				ImageIO.write(img, "png", new File(QR_FOLDER + i + ".png"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			qrCodes.add("<img class='qr-code-image' src='" + servletContext.getContextPath() + "/qrcode?number=" + i + "' alt='Qr code image.'>");
		}
	}

	private void clearSavedData() {
		Path path = FileSystems.getDefault().getPath(QR_FOLDER + "0.png");

		if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
			try {
				for (int i = 0; i < jobCount; i++) {
					Path file = FileSystems.getDefault().getPath(QR_FOLDER + i + ".png");
					Files.delete(file);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		qrCodes.clear();
		jobService.clearJobs();
	}

	@RequestMapping(value = "/qrcode", method = RequestMethod.GET)
	public void getQrCode(HttpServletResponse response, int number) throws IOException {
		if (number <= jobCount - 1) {
			InputStream in = new FileInputStream(QR_FOLDER + (number) + ".png");
			response.setContentType(MediaType.IMAGE_PNG_VALUE);
			OutputStream os = response.getOutputStream();
			IOUtils.copy(in, os);
		}
	}

	public List<Job> getJobs() {
		return jobService.getAllJobs();
	}
	
	public List<String> getQrCodes() {
		return qrCodes;
	}
}