package repository;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import domein.ADoelstelling;
import domein.IDoelstelling;

public interface DoelstellingDao extends GenericDao<ADoelstelling>
{
	public ADoelstelling getDoelstellingByName(String name) throws EntityNotFoundException;
	
	public List<IDoelstelling> getSubDoelstellingenByNameMain(String name) throws EntityNotFoundException;
	
	public List<IDoelstelling> getUnusedDoelstellingen() throws EntityNotFoundException;
	
	public List<IDoelstelling> getDoelstellingenByCategorie(String c) throws EntityNotFoundException;
	
	public List<IDoelstelling> getMainDoelstellingen() throws EntityNotFoundException;
	
	public List<IDoelstelling> getDoelstellingenByDatasourceName(String name) throws EntityNotFoundException;
	
}
