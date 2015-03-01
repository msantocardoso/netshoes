package br.com.netshoes.stream;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.NotFoundException;

public class StreamImpl implements Stream {

	private String string;

	private int indice;

	public StreamImpl(String string) {
		this.string = string;
		this.indice = 0;
	}

	static char firstChar(Stream pInput) {

		String lInput = pInput.toString();

		Set<Character> lSet = getCaracteres(pInput);

		char lChar = ' ';
		LinkedList<Character> lList = new LinkedList<Character>();
		for (Character character : lSet) {
			int lTotal = totalDeOcorrencias(character, lInput);
			if(lTotal == 1) {
				lList.add(character);
			}
		}

		if(lList.isEmpty())
			throw new NotFoundException("Não há caracteres que não se repetem!");

		lChar = (lList).getFirst();

		return lChar;
	}

	private static Set<Character> getCaracteres(Stream pInput) {

		Set<Character> lList = new LinkedHashSet<>();
		while(pInput.hasNext()) {
			lList.add(pInput.getNext());
		}
		return lList;
	}

	private static int totalDeOcorrencias(char charAt,String pString) {
		String lREGEX=""+charAt;

		Pattern p = Pattern.compile(lREGEX);
		Matcher m = p.matcher(pString);
		int lCount = 0;
		while(m.find()) {
			lCount++;
		}
		return lCount;
	}

	@Override
	public char getNext() {
		char lChar = ' ';

		if(dentroIntervalo()) {
			lChar = string.charAt(indice);
		}

		this.proximoCaracter();

		return lChar;
	}

	private boolean dentroIntervalo() {
		return (this.indice >= 0 && this.indice < string.length());
	}

	private void proximoCaracter() {
		indice++;
	}

	@Override
	public boolean hasNext() {
		return indice < string.length();
	}

	@Override
	public String toString() {
		return string;
	}
}