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
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Execute;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.model.AttributeType;

import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;

import static com.automationanywhere.commandsdk.model.AttributeType.TEXT;
import static com.automationanywhere.commandsdk.model.AttributeType.FILE;
import static com.automationanywhere.commandsdk.model.DataType.STRING;
import static com.automationanywhere.commandsdk.model.DataType.DICTIONARY;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author Stefan Karsten
 */
@BotCommand
@CommandPkg(return_label = "Languages", node_label = "Detect Languages", 
label = "Detect Languages", description = "Detect the languages in a text", 
name = "detectlang", icon = "pkg.svg", return_type = DICTIONARY, return_required = true)
public class LangDetect {
	

	@Execute
	public DictionaryValue action(
			@Idx(index = "1", type = FILE) @Pkg(label = "Text File", default_value_type = STRING) @NotEmpty String textfile,
			@Idx(index = "2", type = FILE ) @Pkg(label = "Model File", default_value_type = STRING) @NotEmpty String modelfile
			) throws IOException {

		File modelFile = new File(modelfile);
		
		Path textfilepath = Paths.get(textfile);

		String text =  Files.readString(textfilepath, StandardCharsets.UTF_8);
		Map<String, Value> returnvalue = new HashMap<>();
			    	
		LanguageDetectorModel trainedModel = new LanguageDetectorModel(modelFile);
			    	
		// load the model
		LanguageDetector languageDetector = new LanguageDetectorME(trainedModel);
		
		Language[] languages = languageDetector.predictLanguages(text);
		for (int i = 0; i < languages.length; i++) {
			if (languages[i].getConfidence()> 0.1) {
	  		  returnvalue.put(languages[i].getLang(),new StringValue(Double.toString(languages[i].getConfidence())));
			}
		}
		

		return new DictionaryValue(returnvalue);
	}
}
