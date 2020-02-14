package uca.grsni.dniparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserIdExtractor {
	private static Pattern p = Pattern.compile("u[a-zA-Z0-9]{8}");

	public static String extract(String line) {

		Matcher m = p.matcher(line);
		String match = "";

		// if we find a match, get the group
		if (m.find()) {
			// we're only looking for one group, so get it
			String theGroup = m.group(0);

			// print the group out for verification
			match = theGroup;
		}
		return match;
	}
}
