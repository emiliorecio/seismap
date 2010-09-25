package com.seismap.service.parser;

import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.Whitespace;

@Entry(id = "0", after={"4"}, whitespaces = { @Whitespace(position = 1, length = 79) })
public class Type0Entry extends AbstractEntry {

}
