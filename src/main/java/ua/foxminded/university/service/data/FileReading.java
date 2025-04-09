package ua.foxminded.university.service.data;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
public class FileReading {

	public static List<String> readFile(String pathToFile) {
		List<String> list = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(pathToFile))) {
			log.info("read file by path: " + pathToFile);
			list.addAll(stream.toList());
		} catch (IOException e) {
			log.error(pathToFile + ": File not found" + "\n" + e);
		}
		return list;
	}

	public static String getAsString(String path) {
		StringBuilder filesContent = new StringBuilder();
		for (String str : readFile(path)) {
			filesContent.append(str + "\n");
		}
		log.info("File content converted to string:" + path);
		return filesContent.toString();
	}

	public static List<String> getAsList(String path) {
		log.info("File content converted to list:" + path);
		return readFile(path);
	}
}
