package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domein.AanmeldController;
import domein.User;
import repository.UserDao;

@ExtendWith(MockitoExtension.class)
public class UserTest 
{
	@Mock
	private UserDao userRepositoryDummy;
	
	@InjectMocks
	private AanmeldController ac;

	@ParameterizedTest
	@CsvSource({"Robbert, 12345678", "RubenGUIGod, guifixer"})
	public void testMeldAan(String username, String password) 
	{
		//mock trainen
		Mockito.when(userRepositoryDummy.getUserByName(username)).thenReturn(new User(username, password, "Director", false));
		
		//assertions
		User user = ac.meldAan(username, password);
		Assertions.assertEquals(username, user.getUsername());
		Assertions.assertEquals(password, user.getPassword());
		
		//controle
		Mockito.verify(userRepositoryDummy, Mockito.times(1)).getUserByName(username);
	}
	
	@ParameterizedTest
	@CsvSource({"Fout, 1234", "Geen, abc"})
	public void invalidData(String username, String password) 
	{
		//mock trainen
		Mockito.when(userRepositoryDummy.getUserByName(username)).thenReturn(null);
		
		//assertions
		Assertions.assertNull(ac.meldAan(username, password));
		
		//controle
		Mockito.verify(userRepositoryDummy, Mockito.times(1)).getUserByName(username);
	}
	
	@ParameterizedTest
	@CsvSource({"Robbert, 12345678"})
	public void accountBlocked(String username, String password) 
	{
		final boolean ISBLOCKED = true;
		
		//mock trainen
		Mockito.when(userRepositoryDummy.getUserByName(username)).thenReturn(new User(username, password, "Director", false));
		
		//assertions
		User user = ac.meldAan(username, password);
		
		user.setIsBlocked(ISBLOCKED);
		
		Assertions.assertThrows(IllegalStateException.class, () -> {
			ac.meldAan(user.getUsername(), user.getPassword());
		});
		
		//controle
		Mockito.verify(userRepositoryDummy, Mockito.times(2)).getUserByName(username);
	}
	
}

