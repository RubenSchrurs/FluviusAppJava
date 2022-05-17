package domein;

import java.util.List;

public interface ISDG {

	String getName();

	String getDescription();

	ISDG getMainSDG();

	List<SDG> getSubSDG();

	ICategorie getCategorie();

	int getSDGid();

}