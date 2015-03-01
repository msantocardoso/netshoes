package br.com.netshoes.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.NotFoundException;

import org.junit.Test;

public class StreamTest {

	@Test
	public void deveRetornarbComoCaracterQueNaoSeRepete() {

		char lResult = StreamImpl.firstChar(new StreamImpl("aAbBABac"));

		assertNotNull(lResult);
		assertEquals('b', lResult);
	}

	@Test
	public void deveRetornarzComoCaracterQueNaoSeRepete() {

		char lResult = StreamImpl.firstChar(new StreamImpl("1$zassjjj###fflowsdf16a51f65a1f1as6fgjlsjher51as6f51as6f1asfa"));

		assertNotNull(lResult);
		assertEquals('$', lResult);
	}

	@Test(expected=NotFoundException.class)
	public void deveLancarNotFoundException() {

		StreamImpl.firstChar(new StreamImpl("aaaabbbbcccddddeeeefff"));
	}
}
