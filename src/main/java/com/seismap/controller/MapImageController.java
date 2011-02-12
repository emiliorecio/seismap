package com.seismap.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.IOUtils;
import edu.umn.gis.mapscript.imageObj;
import edu.umn.gis.mapscript.mapObj;
import edu.umn.gis.mapscript.mapscript;

@Controller
@RequestMapping("mapimage")
public class MapImageController {

	@RequestMapping("view")
	public ResponseEntity<byte[]> view() throws IOException {
		mapObj map = new mapObj("c:\\temp\\mapserver\\csharptutorial.map");
		map.selectOutputFormat("gif");
		imageObj image = map.draw();
		// Image image = new Image
		// byte[] bytes = image.getBytes();
		File file = File.createTempFile("seismap_", ".gif");
		mapscript.msSaveImage(map, image, file.getAbsolutePath());
		Resource resoure = new FileSystemResource(file);

		// image.save(, map);
		InputStream stream = new FileInputStream(file);
		byte[] bytes = IOUtils.readFully(stream, -1, false);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.IMAGE_GIF);
		return new ResponseEntity<byte[]>(bytes, responseHeaders,
				HttpStatus.OK);

	}
}
