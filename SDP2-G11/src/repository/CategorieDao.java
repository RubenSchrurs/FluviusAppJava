package repository;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import domein.Categorie;
import domein.ICategorie;

public interface CategorieDao extends GenericDao<Categorie>
{
	public ICategorie getCategorieByName(String name) throws EntityNotFoundException;
	
	public List<ICategorie> getCategorieByDoelstellingName(String name) throws EntityNotFoundException;
}

