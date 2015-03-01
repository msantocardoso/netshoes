package br.com.netshoes.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.com.netshoes.infrastructure.ModificadorCaracteres;

public class ModificadorCaracteresTest {

	@Test
	public void deveSubstituirOsCaracteresDaEsquedaParaDireita() {

		ModificadorCaracteres lModificador = new ModificadorCaracteres("0123456789", '0');

		do {
			lModificador.getCep();
		} while (lModificador.temProximo());

		assertNotNull("Lista de transformacoes vazia!", lModificador.getModificacoes());
		assertEquals(9, lModificador.getModificacoes().size());

		assertEquals("0123456780", lModificador.getModificacoes().get(0));
		assertEquals("0123456700", lModificador.getModificacoes().get(1));
		assertEquals("0123456000", lModificador.getModificacoes().get(2));
		assertEquals("0123450000", lModificador.getModificacoes().get(3));
		assertEquals("0123400000", lModificador.getModificacoes().get(4));
		assertEquals("0123000000", lModificador.getModificacoes().get(5));
		assertEquals("0120000000", lModificador.getModificacoes().get(6));
		assertEquals("0100000000", lModificador.getModificacoes().get(7));
		assertEquals("0000000000", lModificador.getModificacoes().get(8));
	}
}