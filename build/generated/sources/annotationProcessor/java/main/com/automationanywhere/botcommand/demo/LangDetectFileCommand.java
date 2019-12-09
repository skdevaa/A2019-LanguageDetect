package com.automationanywhere.botcommand.demo;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import java.lang.ClassCastException;
import java.lang.Deprecated;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LangDetectFileCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(LangDetectFileCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    LangDetectFile command = new LangDetectFile();
    if(parameters.get("textfile") == null || parameters.get("textfile").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","textfile"));
    }

    if(parameters.get("encoding") == null || parameters.get("encoding").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","encoding"));
    }
    if(parameters.get("encoding") != null && parameters.get("encoding").get() != null && !(parameters.get("encoding").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","encoding", "String", parameters.get("encoding").get().getClass().getSimpleName()));
    }
    if(parameters.get("encoding") != null) {
      switch((String)parameters.get("encoding").get()) {
        case "UTF-8" : {

        } break;
        case "US-ASCII" : {

        } break;
        case "ISO-8859-1" : {

        } break;
        default : throw new BotCommandException(MESSAGES_GENERIC.getString("generic.InvalidOption","encoding"));
      }
    }

    if(parameters.get("modelfile") == null || parameters.get("modelfile").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","modelfile"));
    }

    if(parameters.get("textfile") != null && parameters.get("textfile").get() != null && !(parameters.get("textfile").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","textfile", "String", parameters.get("textfile").get().getClass().getSimpleName()));
    }
    if(parameters.get("encoding") != null && parameters.get("encoding").get() != null && !(parameters.get("encoding").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","encoding", "String", parameters.get("encoding").get().getClass().getSimpleName()));
    }
    if(parameters.get("modelfile") != null && parameters.get("modelfile").get() != null && !(parameters.get("modelfile").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","modelfile", "String", parameters.get("modelfile").get().getClass().getSimpleName()));
    }
    try {
      Optional<Value> result =  Optional.ofNullable(command.action(parameters.get("textfile") != null ? (String)parameters.get("textfile").get() : (String)null ,parameters.get("encoding") != null ? (String)parameters.get("encoding").get() : (String)null ,parameters.get("modelfile") != null ? (String)parameters.get("modelfile").get() : (String)null ));
      logger.traceExit(result);
      return result;
    }
    catch (ClassCastException e) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.IllegalParameters","action"));
    }
    catch (BotCommandException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }
}
