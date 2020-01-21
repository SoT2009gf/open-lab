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
			String applicationDeadline = jsonObject.get("PublicationEndDate").getAsString();
			String description = jsonObject.getAsJsonObject("UserArea").get("TextJobDescription").getAsString();
			String allRequirements = jsonObject.getAsJsonObject("UserArea").get("TextRequirementDescription")
					.getAsString();
			String salary = getSalary(description);
			if (salary.equals("Not mentioned.")) {
				salary = getSalary(allRequirements);
			}
			String accountabilities = getAccountabilities(description);
			if (accountabilities == null) {
				accountabilities = getAccountabilities(allRequirements);
				if (accountabilities == null) {
					accountabilities = description;
				}
			}
			String requirements = getRequirements(allRequirements);
			if (requirements == null) {
				requirements = getRequirements(description);
			}

			String url = "https://t-systems.jobs/careers-sk-en/" + jsonObject.get("PositionURI").getAsString();
			jobs.add(new Job(position, employmentType, applicationDeadline, accountabilities, salary, requirements));
			System.out.println(position);
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

	private String getSalary(String description) {
		description = description.toLowerCase();
		int salaryTextIndex = description.indexOf("salary");
		if (salaryTextIndex > 0) {
			StringBuilder salary = new StringBuilder();
			String salaryText = description.substring(salaryTextIndex);
			int positionOfFirstDigit = 0;
			for (int index = 0; index < salaryText.length(); index++) {
				if (Character.isDigit(salaryText.charAt(index))) {
					positionOfFirstDigit = index;
					break;
				}
			}

			for (int index = positionOfFirstDigit; index < salaryText.length(); index++) {
				if (Character.isDigit(salaryText.charAt(index))) {
					salary.append(salaryText.charAt(index));
				} else {
					break;
				}
			}

			salary.append(" ").append("&euro;");
			return salary.toString();
		}
		return "Not mentioned.";
	}

	private String getAccountabilities(String description) {
		String backup = description;
		description = description.toLowerCase();
		String accountabilitiesText = null;
		int accountabilitiesTextIndex = description.indexOf("accountabilities");
		if (accountabilitiesTextIndex == -1) {
			accountabilitiesTextIndex = description.indexOf("responsibilities");
		}
		int salaryTextIndex = description.indexOf("salary");
		int benefitsTextIndex = description.indexOf("other benefits");
		if (benefitsTextIndex == -1) {
			benefitsTextIndex = description.indexOf("benefits");
		}
		int requirementsTextIndex = description.indexOf("requirements");
		if (accountabilitiesTextIndex > 0) {
			if (requirementsTextIndex > 0 && requirementsTextIndex > accountabilitiesTextIndex) {
				accountabilitiesText = backup.substring(accountabilitiesTextIndex, requirementsTextIndex);
			}

		}
		if (accountabilitiesTextIndex > 0) {
			if (benefitsTextIndex > 0 && benefitsTextIndex > accountabilitiesTextIndex) {
				accountabilitiesText = backup.substring(accountabilitiesTextIndex, benefitsTextIndex);
			}
		}
		if (accountabilitiesTextIndex > 0) {
			if (salaryTextIndex > 0 && salaryTextIndex > accountabilitiesTextIndex) {
				accountabilitiesText = backup.substring(accountabilitiesTextIndex, salaryTextIndex);
			}
			return accountabilitiesText;
		}

		return null;
	}

	private String getRequirements(String description) {
		String backup = description;
		description = description.toLowerCase();
		int requirementsTextIndex = description.indexOf("requirements");
		if (requirementsTextIndex == -1) {
			requirementsTextIndex = description.indexOf("your skills");
		}
		if (requirementsTextIndex == -1) {
			int salaryTextIndex = description.indexOf("salary");
			if (salaryTextIndex > 0 && requirementsTextIndex > 0) {
				String requirementsText = backup.substring(requirementsTextIndex, salaryTextIndex);
				return requirementsText;
			} else {

				return backup;
			}
		}
		int salaryTextIndex = description.indexOf("salary");
		if (requirementsTextIndex > 0) {
			if (salaryTextIndex > 0) {
				String requirementsText = backup.substring(requirementsTextIndex, salaryTextIndex);
				return requirementsText;
			}
		}

		return null;
	}

}