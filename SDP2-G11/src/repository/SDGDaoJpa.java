package repository;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import domein.ISDG;
import domein.SDG;

public class SDGDaoJpa extends GenericDaoJpa<SDG> implements SDGDao{

	public SDGDaoJpa() {
		super(SDG.class);
	}

	@Override
	public SDG getSDGByName(String name) throws EntityNotFoundException {
		try {
			return em.createNamedQuery("SDG.findByName", SDG.class)
						.setParameter("name", name)
						.getSingleResult();
						
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}

	@Override
	public List<ISDG> getSubSDGsByNameMainSDG(String name) throws EntityNotFoundException {
		try {
			return em.createNamedQuery("SDG.findSubSDGsByNameMainSDG", ISDG.class)
						.setParameter("name", name)
						.getResultList();
						
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}

	@Override
	public List<ISDG> getFreeSDGs() throws EntityNotFoundException {
		try {
			return em.createNamedQuery("SDG.findFreeSDGs", ISDG.class)
						.getResultList();
						
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}

	@Override
	public List<ISDG> getSDGsByCategorieName(String name) throws EntityNotFoundException {
		try {
			return em.createNamedQuery("SDG.findMainSDGsByCategorieName", ISDG.class)
						.setParameter("name", name)
						.getResultList();
						
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}

	@Override
	public List<ISDG> getSDGByDoelstellingName(String name) throws EntityNotFoundException {
		try {
			return em.createNamedQuery("SDG.findSDGByDoelstellingName", ISDG.class)
						.setParameter("name", name)
						.getResultList();
						
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}
	
	

}
