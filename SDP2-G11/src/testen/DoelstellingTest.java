package testen;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domein.DomeinController;
import domein.Fluvius;

@ExtendWith(MockitoExtension.class)
public class DoelstellingTest 
{
	@Mock
	private Fluvius fluviusDummy;
	
	@InjectMocks
	private DomeinController dc;
	
	@Test
	public void geefDoelstellingNamen() 
	{
		//mock trainen
		Mockito.when(fluviusDummy.getAllDoelstellingen()).thenReturn(new ArrayList<>());
		
		//assertions
		List<String> list = dc.geefDoelstellingNamen();
		
		//controle
		Mockito.verify(fluviusDummy).getAllDoelstellingen();
	}
	
}
