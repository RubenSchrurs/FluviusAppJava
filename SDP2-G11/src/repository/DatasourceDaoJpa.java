package repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import domein.Datasource;

public class DatasourceDaoJpa extends GenericDaoJpa<Datasource> implements DatasourceDao
{
	public DatasourceDaoJpa() {
		super(Datasource.class);
	}

	@Override
	public Datasource getDatasourceByName(String name) throws EntityNotFoundException 
	{
		try {
			return em.createNamedQuery("Datasource.findByName", Datasource.class)
						.setParameter("name", name)
						.getSingleResult();
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}
}

