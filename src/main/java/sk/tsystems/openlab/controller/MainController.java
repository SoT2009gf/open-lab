package sk.tsystems.openlab.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.JsonObject;

import com.google.zxing.WriterException;

import sk.tsystems.openlab.entity.Job;
import sk.tsystems.openlab.json.JsonProcessor;
import sk.tsystems.openlab.service.JobService;
import sk.tsystems.openlab.util.QrCode;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/")
public class MainController {

	private int jobCount;
	private int positionInRow;
	List<Job> jobs;

	@Autowired
	JobService jobService;

	@RequestMapping
	public String index() {
		if (jobs == null) {
			jobs = new ArrayList<>();
		} else {
			jobs.clear();
		}
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
			try {
				QrCode.generateQRCodeImage(url, 350, 350, ".\\src\\main\\resources\\static\\images\\qr\\" + i + ".png");
			} catch (WriterException e) {
				System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
			}

			jobService.addJob(new Job(position, employmentType, startDate, endDate, requirements));
		}
	}

	public Job getJob(Integer positionInRow) {
		if (this.positionInRow + positionInRow > jobCount - 1) {
			this.positionInRow = 0;
		}
		return jobs.get(this.positionInRow + positionInRow);
	}

	public String getQrCode(Integer positionInRow) {
		return "<img src='/images/qr/" + (this.positionInRow + positionInRow)
				+ ".png' alt='Job url coded in qr image.'/>";
	}
}