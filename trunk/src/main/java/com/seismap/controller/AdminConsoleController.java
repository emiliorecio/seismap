package com.seismap.controller;

import java.io.File;
import java.io.FilenameFilter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("admin")
public class AdminConsoleController {

	// TODO: desharcodear
	private File dataFilesDirectory = new File(
			"C:\\workspaces\\seismap\\trunk\\src\\test\\resources\\datafiles");

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
	public boolean loadDataFile(final @RequestParam String file, ModelMap model) {
		File[] files = dataFilesDirectory.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.equals(file);
			}
		});
		if (files.length == 0) {
			throw new IllegalArgumentException("No such file: " + file);
		}
		// TODO: llamar al servicio aca
		return true;
	}
}
