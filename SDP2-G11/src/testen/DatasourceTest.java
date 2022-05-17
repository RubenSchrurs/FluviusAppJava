package testen;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import domein.DomeinController;
import domein.Fluvius;

@ExtendWith(MockitoExtension.class)
public class DatasourceTest 
{
	@Mock
	private Fluvius fluviusDummy;
	
	@InjectMocks
	private DomeinController dc;
	
	@CsvSource({"Datasource1, 50, Ton","Datasource2, 70, kWh"})
	public void testToevoegen() {
		
	}
}

