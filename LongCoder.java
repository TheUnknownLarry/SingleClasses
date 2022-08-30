package de.theunknownlarry.singleclasses;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encodes a string to long.
 * every string is saved in a list of longs.
 * each long represents up to 8 characters.
 * the list order is important for decoding.
 * if you decode the list you get one string composed out of all longs
 */
public final class LongCoder
{
	private LongCoder() {}

	public static String decode(final List<Long> textList)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < textList.size(); i++) {
			sb.append(decode8(textList.get(i).longValue()));
		}
		String text = sb.toString();
		text = text.replace("ae", "ä").replace("oe", "ö").replace("ue", "ü");
		text = text.replace("AE", "Ä").replace("OE", "Ö").replace("UE", "Ü");
		return text;
	}

	public static List<Long> encode(String text)
	{
		text = text.replace("ä", "ae").replace("ö", "oe").replace("ü", "ue");
		text = text.replace("Ä", "AE").replace("Ö", "OE").replace("Ü", "UE");
		final List<Long> list = new ArrayList<>();
		for (int i = 0; i < text.length(); i += 8) {
			list.add(encode8(text.substring(i, Math.min(i + 8, text.length()))));
		}
		return list;
	}

	private static String decode8(long text)
	{
		final List<Long> list = new ArrayList<>();
		for (int i = getLength(text); i >= 0; i--) {
			list.add((long) (text / Math.pow(100, i)));
			text = text - (long) (list.get(list.size() - 1).longValue() * Math.pow(100, i));
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(decodeCharacter(list.get(i).longValue()));
		}
		return sb.toString();
	}

	private static long encode8(final String text)
	{
		if (text.length() > 8) {
			throw new IllegalArgumentException("Bitte geben sie einen Text mit maximal 8 Zeichen an");
		}
		final List<Character> listOfChars = new ArrayList<>();
		for (int i = 0; i < text.length(); i++) {
			listOfChars.add(text.charAt(i));
		}
		long ergebnis = 0;
		for (int i = 0; i < listOfChars.size(); i++) {
			long zwischen = encodeCharacter(listOfChars.get(listOfChars.size() - i - 1).charValue());
			zwischen = (long) (zwischen * Math.pow(100, i));
			ergebnis += zwischen;
		}
		return ergebnis;
	}

	private static String decodeCharacter(final long c)
	{
		if (1 <= c && c <= 95) {
			return String.valueOf((char) (c + 31));
		}
		throw new IllegalArgumentException("Bitte geben sie einen Wert zwischen 1 und 95 an");
	}

	private static long encodeCharacter(final char c)
	{
		if (' ' <= c && c <= '~') {
			return c - 31L;
		}
		throw new IllegalArgumentException("Bitte geben sie einen korrekten Character an (Buchstaben, Zahlen, Leerzeichen und .,:;_!?&@+-*/=%$#'\"()[]{}<>|\\`^~), kein: " + c);
	}

	private static int getLength(final long text)
	{
		final int durch = String.valueOf(text).length() / 2;
		final int rest = String.valueOf(text).length() % 2;
		return durch + rest - 1;
	}
}
