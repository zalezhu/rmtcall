/**
 * 
 */
package cn.com.cardinfo.rmtctrl.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author Zale
 *
 */
public class ValidationUtil {
	public static final Pattern REX_IPV4_PATTERN = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
	public static boolean isIPv4(String c) {
		if (StringUtils.isEmpty(c))
			return false;
		Matcher match = REX_IPV4_PATTERN.matcher(c);
		if (match.matches()) {
			int ip1 = Integer.parseInt(match.group(1));
			int ip2 = Integer.parseInt(match.group(2));
			int ip3 = Integer.parseInt(match.group(3));
			int ip4 = Integer.parseInt(match.group(4));
			if (ip1 < 0 || ip1 > 255 || ip2 < 0 || ip2 > 255 || ip3 < 0
					|| ip3 > 255 || ip4 < 0 || ip4 > 255)
				return false;
			else
				return true;
		} else {
			return false;
		}
	}
}
