/*
 * Copyright (c) 2019 Automation Anywhere.
 * All rights reserved.
 *
 * This software is the proprietary information of Automation Anywhere.
 * You shall use it only in accordance with the terms of the license agreement
 * you entered into with Automation Anywhere.
 */
/**
 *
 */
package com.automationanywhere.botcommand.demo;


import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.DictionaryValue;
import com.automationanywhere.botcommand.data.impl.NumberValue;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.data.model.record.Record;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Execute;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.DataType;

import static com.automationanywhere.commandsdk.model.AttributeType.VARIABLE;

import static com.automationanywhere.commandsdk.model.DataType.RECORD;
import static com.automationanywhere.commandsdk.model.DataType.DICTIONARY;

import java.util.HashMap;

import java.util.Map;


/**
 *
 *
 * @author Stefan Karsten
 */
@BotCommand
@CommandPkg(return_label = "Language", node_label = "Get Detected Language", 
label = "Get Detected Language", description = "Get Top Detected Languages", 
name = "getlang", icon = "pkg.svg", return_type = DICTIONARY , return_description = "Note: Use the 'language' key to access language and 'confidence' key to access the confidence." , return_required = true)
public class RetrieveLanguage{
	

	@Execute
	public DictionaryValue action(
			@Idx(index = "1", type = VARIABLE) @Pkg(label = "Sorted Record with detected Languages", default_value_type = RECORD) @NotEmpty Record record,
			@Idx(index = "2", type = AttributeType.NUMBER ) @Pkg(label = "Index", default_value_type = DataType.NUMBER)  Double index
			) {

		Map<String, Value> result= new HashMap<>();
		

		index = (index == null) ? 0 : index ;
			
		String lang = 	 record.getSchema().get(index.intValue()).getName();
		Double conf = 	 (Double)record.getValues().get(index.intValue()).get();
		
		result.put("language", new StringValue(lang));
		result.put("confidence", new NumberValue(conf));
			    	
		
		return new DictionaryValue(result);

	}
	

}
