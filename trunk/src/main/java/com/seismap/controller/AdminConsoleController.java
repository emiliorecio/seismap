package com.seismap.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seismap.service.dataload.DataLoadService;
import com.seismap.service.parser.DataProviderException;
import com.seismap.service.parser.InvalidDataException;

@Controller
@RequestMapping("admin")
public class AdminConsoleController extends SeismapController implements
		InitializingBean {

	private DataLoadService dataLoadService;

	public AdminConsoleController(DataLoadService dataLoadService) {
		this.dataLoadService = dataLoadService;
	}

	private File dataFilesDirectory = new File(
			"C:/workspaces/seismap/trunk/src/test/resources/datafiles");

	public File getDataFilesDirectory() {
		return dataFilesDirectory;
	}

	public void setDataFilesDirectory(File dataFilesDirectory) {
		this.dataFilesDirectory = dataFilesDirectory;
	}

	@RequestMapping("")
	public String home() {
		return "admin";
	}

	@RequestMapping("data-files")
	public String listDataFiles(ModelMap model) {
		File[] files = dataFilesDirectory.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.endsWith(".data");
			}
		});
		model.addAttribute("files", files);
		return "data-files-list";
	}

	@RequestMapping("load-data-file")
	@ResponseBody
	public boolean loadDataFile(final @RequestParam String file, ModelMap model)
			throws InvalidDataException, DataProviderException, IOException {
		File[] files = dataFilesDirectory.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.equals(file);
			}
		});
		if (files.length == 0) {
			throw new IllegalArgumentException("No such file: " + file);
		}
		dataLoadService.load(files[0].getAbsolutePath());
		return true;
	}

	public void afterPropertiesSet() throws Exception {
		if (dataFilesDirectory == null) {
			throw new IllegalStateException("dataFilesDirectoy is null");
		}
		if (!dataFilesDirectory.exists()) {
			throw new IllegalStateException(
					"dataFilesDirectoy does not exist: "
							+ dataFilesDirectory.getAbsolutePath());
		}

	}
}
