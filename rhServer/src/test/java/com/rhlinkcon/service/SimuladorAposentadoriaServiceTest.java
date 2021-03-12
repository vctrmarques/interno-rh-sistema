package com.rhlinkcon.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SimuladorAposentadoriaServiceTest {

	@InjectMocks
	private SimuladorAposentadoriaService service;

	@Test
	public void dataDeAposentadoriaTest() {
		LocalDate dataNascimento = LocalDate.of(1962, 10, 3);
		LocalDate dataDeAposentadoriaEsperada = LocalDate.of(2015, 10, 3);
		LocalDate dataDeAposentadoria = service.dataDeAposentadoria(53, dataNascimento);
		assertEquals(dataDeAposentadoriaEsperada, dataDeAposentadoria);
	}

}
