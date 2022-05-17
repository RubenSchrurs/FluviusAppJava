package repository;

import javax.persistence.EntityNotFoundException;

import domein.Datasource;

public interface DatasourceDao extends GenericDao<Datasource>
{
	public Datasource getDatasourceByName(String name) throws EntityNotFoundException;
	
}

