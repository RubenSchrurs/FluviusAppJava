package repository;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import domein.ISDG;
import domein.SDG;

public interface SDGDao extends GenericDao<SDG>{
	
	public SDG getSDGByName(String name) throws EntityNotFoundException;
	
	public List<ISDG> getSubSDGsByNameMainSDG(String name) throws EntityNotFoundException;
	
	public List<ISDG> getFreeSDGs() throws EntityNotFoundException;
	
	public List<ISDG> getSDGsByCategorieName(String name) throws EntityNotFoundException;
	
	public List<ISDG> getSDGByDoelstellingName(String name) throws EntityNotFoundException;

}