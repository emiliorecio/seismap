package com.seismap.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class DataLoadService {

    @Value("${seismap.data-files-directory:./data}")
    private String dataFilesDirectory;

    public List<File> listDataFiles() {
        File dir = new File(dataFilesDirectory);
        if (!dir.exists() || !dir.isDirectory()) {
            return Collections.emptyList();
        }
        File[] files = dir.listFiles((d, name) -> name.endsWith(".data"));
        return files != null ? Arrays.asList(files) : Collections.emptyList();
    }

    public void loadDataFile(String filename) throws IOException {
        File dir = new File(dataFilesDirectory);
        File[] matches = dir.listFiles((d, name) -> name.equals(filename));
        if (matches == null || matches.length == 0) {
            throw new IllegalArgumentException("No such data file: " + filename);
        }
        // TODO: Implement actual data parsing and loading logic
        // This will be ported from the legacy DataLoadService/parser
        throw new UnsupportedOperationException("Data loading not yet implemented");
    }
}
