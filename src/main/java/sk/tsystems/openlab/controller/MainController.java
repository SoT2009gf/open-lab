package sk.tsystems.openlab.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

	private static final String QR_FOLDER = System.getProperty("java.io.tmpdir");

	@Autowired
	JobService jobService;

	@Autowired
	ServletContext servletContext;

	@RequestMapping
	public String index() {
		try {
			refreshData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}

	private void refreshData() {
		JsonProcessor jsonProcessor = new JsonProcessor();

		JsonObject fetchedData = jsonProcessor.createtJSONObject();
		int jobCount = fetchedData.getAsJsonObject("SearchResult").getAsJsonPrimitive("SearchResultCount").getAsInt();

		List<Job> jobs = new ArrayList<Job>();
		for (int i = 0; i < jobCount; i++) {
			JsonObject jsonObject = fetchedData.getAsJsonObject("SearchResult").getAsJsonArray("SearchResultItems")
					.get(i).getAsJsonObject().getAsJsonObject("MatchedObjectDescriptor");

			String position = jsonObject.get("PositionTitle").getAsString();
			String employmentType = jsonObject.getAsJsonArray("PositionSchedule").get(0).getAsJsonObject().get("Name")
					.getAsString();
			String startDate = jsonObject.get("PublicationStartDate").getAsString();
			String endDate = jsonObject.get("PublicationEndDate").getAsString();
			String description = jsonObject.getAsJsonObject("UserArea").get("TextJobDescription").getAsString();
//			String salary = description.substring(description.indexOf("Minimum monthly salary is") + 25,
//					description.indexOf("€"));
//			System.out.println(salary);

			String url = "https://t-systems.jobs/careers-sk-en/" + jsonObject.get("PositionURI").getAsString();
			jobs.add(new Job(position, employmentType, startDate, endDate, description));

			QrCode qrcode = QrCode.encodeText(url, QrCode.Ecc.MEDIUM);
			BufferedImage img = qrcode.toImage(2, 8);
			try {
				ImageIO.write(img, "png", new File(QR_FOLDER + i + ".png"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		jobService.refreshJobs(jobs);
	}

	@RequestMapping(value = "/qrcode", method = RequestMethod.GET)
	public void getQrCode(HttpServletResponse response, int number) throws IOException {
			InputStream in = new FileInputStream(QR_FOLDER + (number) + ".png");
			response.setContentType(MediaType.IMAGE_PNG_VALUE);
			OutputStream os = response.getOutputStream();
			IOUtils.copy(in, os);
	}

	public List<Job> getJobs() {
		return jobService.getAllJobs();
	}
}