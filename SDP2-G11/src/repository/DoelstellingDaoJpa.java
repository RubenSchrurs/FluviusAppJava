package repository;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import domein.ADoelstelling;
import domein.IDoelstelling;

public class DoelstellingDaoJpa extends GenericDaoJpa<ADoelstelling> implements DoelstellingDao
{
	public DoelstellingDaoJpa() {
		super(ADoelstelling.class);
	}

	@Override
	public ADoelstelling getDoelstellingByName(String name) throws EntityNotFoundException 
	{
		try {
			return em.createNamedQuery("ADoelstelling.findByName", ADoelstelling.class)
						.setParameter("name", name)
						.getSingleResult();
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}

	@Override
	public List<IDoelstelling> getSubDoelstellingenByNameMain(String name) throws EntityNotFoundException {
		try {
			return em.createNamedQuery("ADoelstelling.findSubDoelstellingenByName", IDoelstelling.class)
						.setParameter("name", name)
						.getResultList();
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}
	
	@Override
	public List<IDoelstelling> getUnusedDoelstellingen() throws EntityNotFoundException {
		try {
			return em.createNamedQuery("ADoelstelling.findUnusedDoelstellingen", IDoelstelling.class)
						.getResultList();
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}
	
	@Override
	public List<IDoelstelling> getDoelstellingenByCategorie(String c) throws EntityNotFoundException{
		try {
			return em.createNamedQuery("ADoelstelling.findDoelstellingenByCategorie", IDoelstelling.class)
						.setParameter("name", c)
						.getResultList();
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}
	
	@Override
	public List<IDoelstelling> getMainDoelstellingen() throws EntityNotFoundException {
		try {
			return em.createNamedQuery("ADoelstelling.findMainDoelstelling", IDoelstelling.class)
						.getResultList();
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}

	@Override
	public List<IDoelstelling> getDoelstellingenByDatasourceName(String name) throws EntityNotFoundException {
		try {
			return em.createNamedQuery("DoelstellingLeaf.findDoelstellingenByDatasource", IDoelstelling.class)
						.setParameter("name", name)
						.getResultList();
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}
}

