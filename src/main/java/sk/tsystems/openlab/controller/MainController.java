package sk.tsystems.openlab.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	private int positionInRow;
	List<Job> jobs = new ArrayList<>();
	private static final String QR_FOLDER = ".\\src\\main\\resources\\static\\images\\qr\\";

	@Autowired
	JobService jobService;

	@Scheduled(cron="0 0 */1 * * *")
	private void prepareData() {
		clearSavedData();
		storeDataFromJSON();
		jobs = jobService.getAllJobs();
		System.out.println("Data has been refreshed.");
	}

	@RequestMapping
	public String index() {
		clearSavedData();
		storeDataFromJSON();
		jobs = jobService.getAllJobs();
		return "index";
	}

	@RequestMapping("/slide")
	public String slide() {
		this.positionInRow++;
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
		String requirements;
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
			requirements = fetchedData.getAsJsonObject("SearchResult").getAsJsonArray("SearchResultItems").get(i)
					.getAsJsonObject().getAsJsonObject("MatchedObjectDescriptor").getAsJsonObject("UserArea")
					.get("TextRequirementDescription").getAsString();
			url = "https://t-systems.jobs/careers-sk-en/" + fetchedData.getAsJsonObject("SearchResult")
					.getAsJsonArray("SearchResultItems").get(i).getAsJsonObject()
					.getAsJsonObject("MatchedObjectDescriptor").get("PositionURI").getAsString();
			jobService.addJob(new Job(position, employmentType, startDate, endDate, requirements));

			QrCode qrcode = QrCode.encodeText(url, QrCode.Ecc.MEDIUM);
			BufferedImage img = qrcode.toImage(2, 8);
			try {
				ImageIO.write(img, "png", new File(QR_FOLDER + i + ".png"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
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
		jobs.clear();
		jobService.clearJobs();
	}

	public Job getJob(Integer positionInRow) {
		if (this.positionInRow + positionInRow > jobCount - 1) {
			this.positionInRow = 0;
		}
		return jobs.get(this.positionInRow + positionInRow);
	}

	public String getQrCode(Integer positionInRow) {
		return "<img class='qr-code-image' src='/images/qr/" + (this.positionInRow + positionInRow)
				+ ".png' alt='Job url coded in qr image.'/>";
	}
}