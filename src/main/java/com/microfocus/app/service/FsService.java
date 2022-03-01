package com.microfocus.app.service;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class FsService {

	private static final Logger log = LogManager.getLogger(FsService.class);

	public static final String USER_INFO_FILE = "user_info.json";
	public static final String NEWSLETTER_USER_FILE = "newsletter_registration.json";
	public static final String DEFAULT_ROLE = "guest";

	public FsService() {
	}

	public static void writeUser(String user, String email) throws IOException {
		JsonFactory jsonFactory = new JsonFactory();

		File dataFile = new File(getFilePath(USER_INFO_FILE));
		if (dataFile.createNewFile()){
			if (log.isDebugEnabled()) {
				log.debug("Created: " + getFilePath(USER_INFO_FILE));
			}
		}

		JsonGenerator jGenerator = jsonFactory.createGenerator(dataFile, JsonEncoding.UTF8);

		jGenerator.writeStartObject();

		jGenerator.writeFieldName("name");
		jGenerator.writeRawValue("\"" + user + "\"");

		jGenerator.writeFieldName("email");
		jGenerator.writeRawValue("\"" + email + "\"");

		jGenerator.writeFieldName("role");
		jGenerator.writeRawValue("\"default\"");

		jGenerator.writeEndObject();

		jGenerator.close();
	}

	private static String getFilePath(String relativePath) {
		return System.getProperty("user.home") + File.separatorChar + relativePath;
	}
}