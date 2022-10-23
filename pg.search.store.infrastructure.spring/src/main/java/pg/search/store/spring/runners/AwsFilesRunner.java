package pg.search.store.spring.runners;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import pg.lib.awsfiles.service.FileService;

import pg.search.store.domain.common.Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Component
public class AwsFilesRunner implements ApplicationRunner {
    private final FileService fileService;

    @Override
    public void run(final ApplicationArguments args) throws FileNotFoundException {
        File initialFiles = ResourceUtils.getFile("classpath:initial_photos");
        Objects.requireNonNull(initialFiles);

        Arrays.stream(Objects.requireNonNull(initialFiles.listFiles())).forEach(file -> {
            MultipartFile multipartFile;

            try {
                multipartFile = new MockMultipartFile(file.getName(), new FileInputStream(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (multipartFile.getName().contains("product_avatar")) {
                Files.setDefaultProductPhoto(fileService.initFile(multipartFile));
            } else if (multipartFile.getName().contains("user_avatar")) {
                Files.setDefaultUserPhoto(fileService.initFile(multipartFile));
            } else {
                fileService.uploadFile(multipartFile);
            }
            log.info("File {} has been added", file.getName());
        });
    }
}