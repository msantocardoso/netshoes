package br.com.netshoes.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class CepTransformTest {

	@Test
	public void deveSubstituirOsCaracteresDaEsquedaParaDireita() {

		CepTransform lTransform = new CepTransform("0123456789");

		do {
			lTransform.getCep();
		} while (lTransform.hasNext());

		assertNotNull("Lista de transformacoes vazia!", lTransform.getTransformacoes());
		assertEquals(9, lTransform.getTransformacoes().size());

		assertEquals("0123456780", lTransform.getTransformacoes().get(0));
		assertEquals("0123456700", lTransform.getTransformacoes().get(1));
		assertEquals("0123456000", lTransform.getTransformacoes().get(2));
		assertEquals("0123450000", lTransform.getTransformacoes().get(3));
		assertEquals("0123400000", lTransform.getTransformacoes().get(4));
		assertEquals("0123000000", lTransform.getTransformacoes().get(5));
		assertEquals("0120000000", lTransform.getTransformacoes().get(6));
		assertEquals("0100000000", lTransform.getTransformacoes().get(7));
		assertEquals("0000000000", lTransform.getTransformacoes().get(8));
	}
}