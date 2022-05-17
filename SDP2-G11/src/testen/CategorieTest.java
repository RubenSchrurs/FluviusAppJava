package testen;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domein.ADoelstelling;
import domein.Categorie;
import domein.Datasource;
import domein.DoelstellingLeaf;

//@ExtendWith(MockitoExtension.class)
public class CategorieTest 
{
	private Categorie categorie;
	
	@BeforeEach
	public void before() {
		categorie = new Categorie("Energie", new ArrayList<>());
	}
	
	@Test
	public void voegDoelstellingBijCategorie() {
		final String DOELSTELLINGNAAM = "Reductie aardgas";
		final int THRESHOLD = 100;
		final List<Datasource> DATASOURCES = new ArrayList<>();
		
		ADoelstelling doelstelling = new DoelstellingLeaf(DOELSTELLINGNAAM, THRESHOLD);
		doelstelling.setDatasources(DATASOURCES);
		
		Assertions.assertFalse(categorie.getDoelstellingen().contains(doelstelling));
		categorie.addDoelstelling(doelstelling);
		Assertions.assertTrue(categorie.getDoelstellingen().contains(doelstelling));
	}
	
	/*
	@Mock
	private CategorieDao categorieRepositoryDummy;
	
	@InjectMocks
	private Categorie categorie;
	
	@Test
	public void voegDoelstellingBijCategorie() {
		final String DOELSTELLINGNAAM = "Reductie aardgas";
		final int THRESHOLD = 1000;
		final List<Datasource> DATASOURCES = new ArrayList<>();
		
		Doelstelling doelstelling = new Doelstelling(DOELSTELLINGNAAM, THRESHOLD, DATASOURCES);
		
		//mock trainen
		Mockito.when(categorieRepositoryDummy.findAll()).thenReturn(Arrays.asList(categorie));

		//assertions
		Assertions.assertFalse(categorie.getDoelstellingen().contains(doelstelling));
		categorie.addDoelstelling(doelstelling);
		Assertions.assertTrue(categorie.getDoelstellingen().contains(doelstelling));
		
		//controle
		Mockito.verify(categorieRepositoryDummy).findAll();
	}
	*/
}

