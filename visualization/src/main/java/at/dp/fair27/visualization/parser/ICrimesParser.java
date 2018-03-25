package at.dp.fair27.visualization.parser;

import at.dp.fair27.visualization.ParserException;
import java.util.Map;

public interface ICrimesParser {

  Map<Integer, Long> getParsedCrimesRecords() throws ParserException;
}
