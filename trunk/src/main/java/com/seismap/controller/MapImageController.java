package com.seismap.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seismap.service.impl.MercatorCoordinatesConverter;
import com.seismap.service.impl.MercatorCoordinatesConverter.GoogleTilePosition;
import com.seismap.service.impl.MercatorCoordinatesConverter.LatitudeLongitudeBounds;

import edu.umn.gis.mapscript.imageObj;
import edu.umn.gis.mapscript.mapObj;
import edu.umn.gis.mapscript.mapscript;

@Controller
@RequestMapping("mapimage")
public class MapImageController {

	@RequestMapping("view/{zoom}/{x}/{y}")
	public ResponseEntity<byte[]> view(@PathVariable int zoom,
			@PathVariable int x, @PathVariable int y) throws IOException {
		String baseDir = System.getProperty("seismap.base.dir");
		if (baseDir == null) {
			throw new IOException("set -Dseismap.base.dir=/path/to/seismap");
		}
		String mapServerDir = baseDir + "/src/main/mapserver";
		String mapServerOutDir = baseDir + "/target/mapserver";
		MercatorCoordinatesConverter converter = new MercatorCoordinatesConverter(
				256);

		GoogleTilePosition googleTile = converter.createGoogleTilePosition(x,
				y, zoom);
		LatitudeLongitudeBounds bounds = googleTile
				.getLatitudeLongitudeBounds();
		String mapDef = IOUtils.toString(new FileInputStream(mapServerDir + "/map/tile.map"));
		mapDef = mapDef.replace("${minLatitude}", Double.toString(bounds
				.getMinLatitude()));
		mapDef = mapDef.replace("${minLongitude}", Double.toString(bounds
				.getMinLongitude()));
		mapDef = mapDef.replace("${maxLatitude}", Double.toString(bounds
				.getMaxLatitude()));
		mapDef = mapDef.replace("${maxLongitude}", Double.toString(bounds
				.getMaxLongitude()));
		mapDef = mapDef.replace("${mapServerDir}", mapServerDir);
		mapDef = mapDef.replace("${mapServerOutDir}", mapServerOutDir);
		String tilesDir = mapServerOutDir + "/map";
		new File(tilesDir).mkdirs();
		new File(mapServerOutDir).mkdirs();
		new File(mapServerOutDir  + "/log").mkdirs();
		new File(mapServerOutDir  + "/img").mkdirs();
		String defFile = tilesDir + "/" + zoom + '-' + x + '-' + y + ".map";
		IOUtils.write(mapDef, new FileOutputStream(defFile));

		mapObj map = new mapObj(defFile);
		Object xc=map.getExtent();
		map.setTransparent(1);	
		imageObj image = map.draw();
		File file = new File(mapServerOutDir  + "/img/" + zoom + '-' + x + '-' + y + ".png");
		mapscript.msSaveImage(map, image, file.getAbsolutePath());
		byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.IMAGE_PNG);
		image.delete();
		map.delete();
		return new ResponseEntity<byte[]>(bytes, responseHeaders, HttpStatus.OK);

	}
}
