package com.seismap.service.parser;

import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.Whitespace;

@Entry(values = '0', after={'4', ' '}, type = EntryType.THREE, whitespaces = { @Whitespace(position = 1, length = 79) })
public class Type0Entry extends AbstractEntry {

}
