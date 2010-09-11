package com.seismap.webapp;

import org.junit.Assert;
import org.junit.Test;

public class WebApplicationSmokeIT extends WebApplicationBaseIT {
	@Test
	public void smokeTest() throws Exception {
		Response response = executeGet("view/");
		Assert.assertEquals("Response code is wrong.", 200, response.getCode());
		Assert.assertTrue("Response was " + response, response.getBody()
				.contains("<title>SeisMap Server</title>"));
	}
}
