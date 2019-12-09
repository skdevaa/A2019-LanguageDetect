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
import com.automationanywhere.botcommand.data.impl.NumberValue;
import com.automationanywhere.botcommand.data.impl.RecordValue;

import com.automationanywhere.botcommand.data.model.Schema;
import com.automationanywhere.botcommand.data.model.record.Record;
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

import static com.automationanywhere.commandsdk.model.AttributeType.FILE;
import static com.automationanywhere.commandsdk.model.DataType.STRING;
import static com.automationanywhere.commandsdk.model.DataType.NUMBER;
import static com.automationanywhere.commandsdk.model.DataType.RECORD;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.List;


/**
 *
 *
 * @author Stefan Karsten
 */
@BotCommand
@CommandPkg(return_label = "Languages", node_label = "Detect Languages", 
label = "Detect Languages File", description = "Detect the languages in a file", 
name = "detectlangfile", icon = "pkg.svg", return_type = RECORD , return_sub_type = NUMBER , return_description = "Note: First Top 3 languages returned." , return_required = true)
public class LangDetectFile{
	

	@Execute
	public Value<Record> action(
			@Idx(index = "1", type = FILE) @Pkg(label = "Text File", default_value_type = STRING) @NotEmpty String textfile,
			@Idx(index = "2", type = AttributeType.SELECT ,  options = {
				@Idx.Option(index = "2.1",pkg = @Pkg(label = "UTF-8",value = "UTF-8", default_value_type = STRING)),	
				@Idx.Option(index = "2.2",pkg = @Pkg(label = "US-ASCII",value = "US-ASCII", default_value_type = STRING)),
				@Idx.Option(index = "2.3",pkg = @Pkg(label = "ISO-8859-1",value = "ISO-8859-1", default_value_type = STRING))})
			@Pkg(label = "Encoding", default_value_type = STRING , default_value = "UTF-8")  @NotEmpty String encoding,
			@Idx(index = "3", type = FILE ) @Pkg(label = "Model File", default_value_type = STRING) @NotEmpty String modelfile
			) throws IOException {


    	Value<Record> valuerecord = new RecordValue();
    	List<Schema> schemas = new ArrayList<Schema>();
		List<Value> values = new ArrayList<Value>();
        Record record  = new Record(); 
		File modelFile = new File(modelfile);
		
		Path textfilepath = Paths.get(textfile);


		String text =  Files.readString(textfilepath, Charset.forName(encoding));
			    	
		LanguageDetectorModel trainedModel = new LanguageDetectorModel(modelFile);
			    	
		// load the model
		LanguageDetector languageDetector = new LanguageDetectorME(trainedModel);
		
		Language[] languages = languageDetector.predictLanguages(text);
		
		int len = (languages.length > 3) ? 3 :  languages.length;
		for (int i = 0; i < len; i++) {
	  		  
		    String type = languages[i].getLang();
		    Double confidence =languages[i].getConfidence();

		    Schema schema =  new Schema();
		    schema.setName(type);	 
		    schemas.add(schema);
		    values.add(new NumberValue(confidence));
		}

		record = new Record();
		record.setSchema(schemas);
		record.setValues(values);
		valuerecord.set(record);
		return valuerecord;
	}
	

}
