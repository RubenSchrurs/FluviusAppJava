package repository;

import javax.persistence.EntityNotFoundException;

import domein.User;

public interface UserDao extends GenericDao<User>
{
	public User getUserByName(String name) throws EntityNotFoundException;
}

