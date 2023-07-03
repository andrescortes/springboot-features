package com.debuggeando_ideas.best_travel.infraestructure.helper;

import com.debuggeando_ideas.best_travel.util.exceptions.ConverterStringToHtmlException;
import com.debuggeando_ideas.best_travel.util.exceptions.EmailSenderException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class EmailHelper {

    private final JavaMailSender mailSender;

    public void sendMail(String to, String name, String product) {
        try {
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper mailMessageHelper = new MimeMessageHelper(
                mailMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
            );
            mailMessageHelper.setFrom(new InternetAddress("andresabyac@gmail.com"));
            mailMessageHelper.setTo(new InternetAddress(to));
            mailMessageHelper.setSubject("Travel Agency");
            mailMessageHelper.setText(Objects.requireNonNull(readHtmlContent(name, product)), true);
            addImages(mailMessageHelper);
            mailSender.send(mailMessage);
        } catch (MessagingException e) {
            log.error("Error sending email", e);
            throw new EmailSenderException(e.getMessage());
        }
    }

    public void addImages(MimeMessageHelper mailMessageHelper) {
        String pathImages = "src/main/resources/email/images";
        File dir = new File(pathImages);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : Objects.requireNonNull(files)) {
                if (file.isFile() && isImageFile(file)) {
                    try {
                        String pathRelativeImage = pathImages.replace("src/main/resources/", "");
                        pathRelativeImage = pathRelativeImage.concat("/").concat(file.getName());
                        ClassPathResource pathResource = new ClassPathResource(pathRelativeImage);
                        String fileName = file.getName()
                            .substring(0, file.getName().lastIndexOf("."));
                        mailMessageHelper.addInline(fileName, pathResource);
                    } catch (MessagingException e) {
                        log.error("Error adding images", e);
                        throw new EmailSenderException(e.getMessage());
                    }
                }
            }
        }
    }

    private boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
    }

    private String readHtmlContent(String name, String product) {
        try (Stream<String> lines = Files.lines(
            Paths.get("src/main/resources/email/email-template.html"))) {
            String stringHtml = lines.collect(Collectors.joining());
            return stringHtml.replace("{name}", name).replace("{product}", product);
        } catch (IOException e) {
            log.error("Error reading html content", e);
            throw new ConverterStringToHtmlException(e.getMessage());
        }
    }
}
