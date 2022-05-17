package repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import domein.User;

public class UserDaoJpa extends GenericDaoJpa<User> implements UserDao
{
	public UserDaoJpa() {
		super(User.class);
	}

	@Override
	public User getUserByName(String name) throws EntityNotFoundException 
	{
		try {
			return em.createNamedQuery("User.findByName", User.class)

						.setParameter("name", name)
						.getSingleResult();
						
		}
		catch(NoResultException e) {
			throw new EntityNotFoundException();
		}
	}
}

