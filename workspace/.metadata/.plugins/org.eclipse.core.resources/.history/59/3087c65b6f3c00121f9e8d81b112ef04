package util;

import java.util.Comparator;


// Vergleicht zwei Strings.
public class StringComparator implements Comparator<String>{

	public static int compare2Strings (String arg0, String arg1) {
		System.out.println("Comparing "+arg0+" "+arg1);
		int currentchar =0;
		while (true) {
			if (currentchar == arg0.length()) return -1;
			if (currentchar == arg1.length()) return 1;

			if (arg0.charAt(currentchar) < arg1.charAt(currentchar)) return -1;
			if (arg0.charAt(currentchar) > arg1.charAt(currentchar)) return 1;
			

			currentchar++;
		}
	}
	
	public int compare(String arg0, String arg1) {
		
		return compare2Strings(arg0, arg1);
	}
		
}

