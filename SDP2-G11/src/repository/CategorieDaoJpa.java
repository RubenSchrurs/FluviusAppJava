package repository;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import domein.Categorie;
import domein.ICategorie;

public class CategorieDaoJpa extends GenericDaoJpa<Categorie> implements CategorieDao
{
	public CategorieDaoJpa() {
		super(Categorie.class);
	}

	@Override
	public ICategorie getCategorieByName(String name) throws EntityNotFoundException 
	{
		try {
			return em.createNamedQuery("Categorie.findByName", ICategorie.class)
						.setParameter("name", name)
						.getSingleResult();
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}

	@Override
	public List<ICategorie> getCategorieByDoelstellingName(String name) throws EntityNotFoundException {
		try {
			return em.createNamedQuery("Categorie.findCategorieByDoelstellingName", ICategorie.class)
						.setParameter("name", name)
						.getResultList();
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}
}

