package beito.PMServer.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	author: beito123
*/

public class TextFormat {

	final public static String ESCAPE = "\u00a7";

	final public static String BLACK = ESCAPE + "0";
	final public static String DARK_BLUE = ESCAPE + "1";
	final public static String DARK_GREEN = ESCAPE + "2";
	final public static String DARK_AQUA = ESCAPE + "3";
	final public static String DARK_RED = ESCAPE + "4";
	final public static String PURPLE = ESCAPE + "5";
	final public static String GOLD = ESCAPE + "6";
	final public static String GRAY = ESCAPE + "7";
	final public static String DARK_GRAY = ESCAPE + "8";
	final public static String BLUE = ESCAPE + "9";
	final public static String GREEN = ESCAPE + "a";
	final public static String AQUA = ESCAPE + "b";
	final public static String RED = ESCAPE + "c";
	final public static String LIGHT_PURPLE = ESCAPE + "d";
	final public static String YELLOW = ESCAPE + "e";
	final public static String WHITE = ESCAPE + "f";

	final public static String BOLD = ESCAPE + "k";
	final public static String OBFUSCATED = ESCAPE + "l";
	final public static String ITALIC = ESCAPE + "m";
	final public static String UNDERLINE = ESCAPE + "n";
	final public static String STRIKETHROUGH = ESCAPE + "o";
	final public static String RESET = ESCAPE + "r";

	final public static Pattern ansiCodePattern = Pattern.compile(":");

	public static List<String> tokenize(String str){
		Matcher matcher = ansiCodePattern.matcher(str);
		List<String> mathces = new ArrayList<String>();
		while(matcher.find()){
			mathces.add(matcher.group());
		}
		return mathces;
		//return ansiCodePattern.split(str);
	}

	public static String toANSI(String str){
		return toAnsi(tokenize(str));
	}

	public static String toAnsi(List<String> string){
		String newString = "";
		for(String str: string){
			switch(str){
				case BOLD:
					newString += Terminal.BOLD;
					break;
				case OBFUSCATED:
					newString += Terminal.OBFUSCATED;
					break;
				case ITALIC:
					newString += Terminal.ITALIC;
					break;
				case UNDERLINE:
					newString += Terminal.UNDERLINE;
					break;
				case STRIKETHROUGH:
					newString += Terminal.STRIKETHROUGH;
					break;
				case RESET:
					newString += Terminal.RESET;
					break;
				//
				case BLACK:
					newString += Terminal.BLACK;
					break;
				case DARK_BLUE:
					newString += Terminal.DARK_BLUE;
					break;
				case DARK_GREEN:
					newString += Terminal.DARK_GREEN;
					break;
				case DARK_AQUA:
					newString += Terminal.DARK_AQUA;
					break;
				case DARK_RED:
					newString += Terminal.DARK_RED;
					break;
				case PURPLE:
					newString += Terminal.PURPLE;
					break;
				case GOLD:
					newString += Terminal.GOLD;
					break;
				case GRAY:
					newString += Terminal.GRAY;
					break;
				case DARK_GRAY:
					newString += Terminal.DARK_GRAY;
					break;
				case BLUE:
					newString += Terminal.BLUE;
					break;
				case GREEN:
					newString += Terminal.GREEN;
					break;
				case AQUA:
					newString += Terminal.AQUA;
					break;
				case RED:
					newString += Terminal.RED;
					break;
				case LIGHT_PURPLE:
					newString += Terminal.LIGHT_PURPLE;
					break;
				case YELLOW:
					newString += Terminal.YELLOW;
					break;
				case WHITE:
					newString += Terminal.WHITE;
					break;
				default:
					newString += str;
			}
		}
		return newString;
	}
}
