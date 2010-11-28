package com.seismap.service;

import java.io.IOException;

import com.seismap.service.parser.DataProviderException;
import com.seismap.service.parser.InvalidDataException;


public interface DataLoadService {
	
	void dataLoadService(String file) throws InvalidDataException, DataProviderException, IOException;

}
