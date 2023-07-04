package com.debuggeando_ideas.best_travel.util;

import com.debuggeando_ideas.best_travel.util.enums.SortType;
import com.debuggeando_ideas.best_travel.util.exceptions.WriterNotCompleteException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Slf4j
public class BestTravelUtil {

    private static final Random random = new Random();

    private BestTravelUtil() {

    }

    public static LocalDateTime getRandomSoon() {
        int randomNumber = random.nextInt(5 - 2) + 2;
        return LocalDateTime.now().plusHours(randomNumber);
    }

    public static LocalDateTime getRandomLater() {
        int randomNumber = random.nextInt(12 - 6) + 6;
        return LocalDateTime.now().plusHours(randomNumber);
    }

    public static PageRequest chooseSortType(Integer page, Integer size, SortType sortType,
        Sort by) {
        PageRequest pageRequest = null;
        switch (sortType) {
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, by.ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, by.descending());
        }
        if (Objects.isNull(pageRequest)) {
            throw new IllegalArgumentException("PageRequest is null");
        }
        return pageRequest;
    }

    public static void writeNotification(String text, String path) {
        validateFile(path);
        try (
            FileWriter fileWriter = new FileWriter(path, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            bufferedWriter.write(text);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new WriterNotCompleteException(e.getMessage());
        }
    }

    private static void validateFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            log.info("File already exists.");
            return;
        }
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                log.error("Failed to create directory.");
                return;
            }
        }
        try {
            boolean created = file.createNewFile();
            if (created) {
                log.info("Directory and file created successfully.");
            } else {
                log.info("Failed to create file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
