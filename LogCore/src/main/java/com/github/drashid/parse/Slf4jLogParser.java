package com.github.drashid.parse;

import java.util.regex.Pattern;


/**
 * Lines matching the slf4j format:
 * 
 * {time} [{thread name}] {INFO/ERROR/WARN/DEBUG} {classname} - {message}
 * 
 * @author rashid8
 *
 */
public class Slf4jLogParser implements LogParser {

  private Pattern pattern = Pattern.compile("[0-9]+\\s+\\[\\w+\\]\\s+(INFO|WARN|ERROR|DEBUG).*");

  public boolean isStartLine(String line) {
    return pattern.matcher(line).matches();
  }

}
