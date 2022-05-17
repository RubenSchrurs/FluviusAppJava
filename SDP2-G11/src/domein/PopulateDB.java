package domein;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import repository.CategorieDaoJpa;
import repository.DatasourceDaoJpa;
import repository.DoelstellingDaoJpa;
import repository.SDGDaoJpa;
import repository.UserDaoJpa;

public class PopulateDB 
{
	public static void run() 
	{
		
		DatasourceDaoJpa datadao = new DatasourceDaoJpa();
		
		//Datasources data
		Datasource data1 = null;
		Datasource data2 = null;
		Datasource data3 = null;
		Datasource data4 = null;
		Datasource data5 = null;
		try {
			data1 = new Datasource("Houtafval", 5);
			data1.setEenheidData("Ton");
			data2 = new Datasource(new File("Energieverbruik.csv"));
			data3 = new Datasource(new File("Afvalreductie.csv"));
			data4 = new Datasource(new File("Uitstoot.csv"));
			data5 = new Datasource(new File("Waterverbruik.csv"));
			datadao.insert(data2);
			datadao.insert(data3);
			datadao.insert(data4);
			datadao.insert(data5);
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		List<Datasource> sourceLijst = new ArrayList<>();
		sourceLijst.add(data1);
		datadao.insert(data1);
		
		
		DoelstellingDaoJpa doelstellingdao = new DoelstellingDaoJpa();
		
		//Doelstellingen data
		ADoelstelling doelstelling1 = new DoelstellingComposite("Testdoelstelling",10);
		ADoelstelling doelstelling2 = new DoelstellingComposite("Energie verbruik",5);
		ADoelstelling doelstelling5 = new DoelstellingComposite("Test Subdoelstelling",5);
		doelstelling2.setDatasources(sourceLijst);
		ADoelstelling doelstelling3 = new DoelstellingComposite("Afval Reductie", 10);
		ADoelstelling doelstelling4 = new DoelstellingComposite("Vermindering uitstoot", 7);
		ADoelstelling doelstelling6 = new DoelstellingComposite("Subdoelstelling", 7);
		ADoelstelling doelstelling7 = new DoelstellingLeaf("Reductie Houtafval", 50);
		
		doelstelling1.setMainDoelstelling(null);
		doelstelling2.setMainDoelstelling(doelstelling1);
		doelstelling5.setMainDoelstelling(doelstelling2);
		doelstelling1.addSubDoelstelling(doelstelling2);
		doelstelling2.addSubDoelstelling(doelstelling5);
		doelstelling6.setMainDoelstelling(doelstelling3);
		doelstelling3.addSubDoelstelling(doelstelling6);
		doelstelling7.setMainDoelstelling(doelstelling3);
		doelstelling7.addDatasource(data3);
		doelstelling3.addSubDoelstelling(doelstelling7);
		

		List<ADoelstelling> doelstellingLijst1 = new ArrayList<>();
		List<ADoelstelling> doelstellingLijst2 = new ArrayList<>();
		List<ADoelstelling> doelstellingLijst3 = new ArrayList<>();
		List<ADoelstelling> doelstellingLijst4 = new ArrayList<>();
		
		doelstellingLijst1.add(doelstelling1);
		doelstellingLijst1.add(doelstelling2);

		doelstellingLijst2.add(doelstelling1);
		doelstellingLijst2.add(doelstelling2);
		
		CategorieDaoJpa categoriedao = new CategorieDaoJpa();
		
		//Categorie�n data
		Categorie categorie1 = new Categorie("Testcategorie", doelstellingLijst1);
		categoriedao.insert(categorie1);
		
		
		UserDaoJpa userdao = new UserDaoJpa();
		UserDaoJpa.startTransaction();
		
		//Users data
		userdao.insert(new User("Lenneth", "12345678", "MVO Co\u00f6rdinator", false));
		userdao.insert(new User("Robbert", "12345678", "Director", false));
		userdao.insert(new User("Alexander", "12345678", "Manager", false));
		userdao.insert(new User("Ruben", "12345678", "Director", false));
		userdao.insert(new User("Brian", "12345678", "Manager", false));
		userdao.insert(new User("a", "a", "MVO Co\u00f6rdinator", false));
		
		SDGDaoJpa SDGdao = new SDGDaoJpa();
		
		//SDG data
		//Begin SDG1: No Poverty
		SDG target1 = new SDG(1,"No poverty");
		target1.setMainSDG(null);
		target1.setCategorie(categorie1);
		
		SDG subtarget11 = new SDG(1,"Target1.1");
		subtarget11.setMainSDG(target1);
		subtarget11.setDescription("By 2030, eradicate extreme poverty for all people everywhere, currently measured as people living on less than $1.25 a day");
		target1.addSubSDG(subtarget11);
		
		SDG subtarget12 = new SDG(1,"Target1.2");
		subtarget12.setMainSDG(target1);
		subtarget12.setDescription("By 2030, reduce at least by half the proportion of men, women and children of all ages living in poverty in all its dimensions according to national definitions");
		target1.addSubSDG(subtarget12);
		
		SDG subtarget13 = new SDG(1,"Target1.3");
		subtarget13.setMainSDG(target1);
		subtarget13.setDescription("Implement nationally appropriate social protection systems and measures for all, including floors, and by 2030 achieve substantial coverage of the poor and the vulnerable");
		target1.addSubSDG(subtarget13);
		
		SDG subtarget14 = new SDG(1,"Target1.4");
		subtarget14.setMainSDG(target1);
		subtarget14.setDescription("By 2030, ensure that all men and women, in particular the poor and the vulnerable, have equal rights to economic resources, as well as access to basic services, ownership and control over land and other forms of property, inheritance, natural resources, appropriate new technology and financial services, including microfinance");
		target1.addSubSDG(subtarget14);
		
		SDG subtarget15 = new SDG(1,"Target1.5");
		subtarget15.setMainSDG(target1);
		subtarget15.setDescription("By 2030, build the resilience of the poor and those in vulnerable situations and reduce their exposure and vulnerability to climate-related extreme events and other economic, social and environmental shocks and disasters");
		target1.addSubSDG(subtarget15);
		
		SDG subtarget1a = new SDG(1,"Target1.a");
		subtarget1a.setMainSDG(target1);
		subtarget1a.setDescription("Ensure significant mobilization of resources from a variety of sources, including through enhanced development cooperation, in order to provide adequate and predictable means for developing countries, in particular least developed countries, to implement programmes and policies to end poverty in all its dimensions");
		target1.addSubSDG(subtarget1a);
		
		SDG subtarget1b = new SDG(1,"Target1.b");
		subtarget1b.setMainSDG(target1);
		subtarget1b.setDescription("Create sound policy frameworks at the national, regional and international levels, based on pro-poor and gender-sensitive development strategies, to support accelerated investment in poverty eradication actions");
		target1.addSubSDG(subtarget1b);
		//Einde SDG1 
		
// ***********************************************************************************************************		
		
		//Begin SDG2: Zero hunger
		SDG target2 = new SDG(2,"Zero hunger");
		target2.setMainSDG(null);
		
		SDG subtarget21 = new SDG(2,"Target2.1");
		subtarget21.setMainSDG(target2);
		subtarget21.setDescription("By 2030, end hunger and ensure access by all people, in particular the poor and people in vulnerable situations, including infants, to safe, nutritious and sufficient food all year round");
		target2.addSubSDG(subtarget21);
		
		SDG subtarget22 = new SDG(2,"Target2.2");
		subtarget22.setMainSDG(target2);
		subtarget22.setDescription("By 2030, end all forms of malnutrition, including achieving, by 2025, the internationally agreed targets on stunting and wasting in children under 5 years of age, and address the nutritional needs of adolescent girls, pregnant and lactating women and older persons");
		target2.addSubSDG(subtarget22);
	
		SDG subtarget23 = new SDG(2,"Target2.3");
		subtarget23.setMainSDG(target2);
		subtarget23.setDescription("By 2030, double the agricultural productivity and incomes of small-scale food producers, in particular women, indigenous peoples, family farmers, pastoralists and fishers, including through secure and equal access to land, other productive resources and inputs, knowledge, financial services, markets and opportunities for value addition and non-farm employment");
		target2.addSubSDG(subtarget23);
		
		SDG subtarget24 = new SDG(2,"Target2.4");
		subtarget24.setMainSDG(target2);
		subtarget24.setDescription("By 2030, ensure sustainable food production systems and implement resilient agricultural practices that increase productivity and production, that help maintain ecosystems, that strengthen capacity for adaptation to climate change, extreme weather, drought, flooding and other disasters and that progressively improve land and soil quality");
		target2.addSubSDG(subtarget24);
		
		SDG subtarget25 = new SDG(2,"Target2.5");
		subtarget25.setMainSDG(target2);
		subtarget25.setDescription("By 2020, maintain the genetic diversity of seeds, cultivated plants and farmed and domesticated animals and their related wild species, including through soundly managed and diversified seed and plant banks at the national, regional and international levels, and promote access to and fair and equitable sharing of benefits arising from the utilization of genetic resources and associated traditional knowledge, as internationally agreed");
		target2.addSubSDG(subtarget25);
		
		SDG subtarget2a = new SDG(2,"Target2.a");
		subtarget2a.setMainSDG(target2);
		subtarget2a.setDescription("Increase investment, including through enhanced international cooperation, in rural infrastructure, agricultural research and extension services, technology development and plant and livestock gene banks in order to enhance agricultural productive capacity in developing countries, in particular least developed countries");
		target2.addSubSDG(subtarget2a);
		
		SDG subtarget2b = new SDG(2,"Target2.b");
		subtarget2b.setMainSDG(target2);
		subtarget2b.setDescription("Correct and prevent trade restrictions and distortions in world agricultural markets, including through the parallel elimination of all forms of agricultural export subsidies and all export measures with equivalent effect, in accordance with the mandate of the Doha Development Round");
		target2.addSubSDG(subtarget2b);
		
		SDG subtarget2c = new SDG(2,"Target2.c");
		subtarget2c.setMainSDG(target2);
		subtarget2c.setDescription("Adopt measures to ensure the proper functioning of food commodity markets and their derivatives and facilitate timely access to market information, including on food reserves, in order to help limit extreme food price volatility");
		target2.addSubSDG(subtarget2c);
		//Einde SDG2
		
// *********************************************************************************************************
		
		//Begin SDG3: Good health and well-being
		SDG target3 = new SDG(3,"Good health and well-being");
		target3.setMainSDG(null);
		
		SDG subtarget31 = new SDG(3,"Target3.1");
		subtarget31.setMainSDG(target3);
		subtarget31.setDescription("By 2030, reduce the global maternal mortality ratio to less than 70 per 100,000 live births");
		target3.addSubSDG(subtarget31);
		
		SDG subtarget32 = new SDG(3,"Target3.2");
		subtarget32.setMainSDG(target3);
		subtarget32.setDescription("By 2030, end preventable deaths of newborns and children under 5 years of age, with all countries aiming to reduce neonatal mortality to at least as low as 12 per 1,000 live births and under-5 mortality to at least as low as 25 per 1,000 live births");
		target3.addSubSDG(subtarget32);
		
		SDG subtarget33 = new SDG(3,"Target3.3");
		subtarget33.setMainSDG(target3);
		subtarget33.setDescription("By 2030, end the epidemics of AIDS, tuberculosis, malaria and neglected tropical diseases and combat hepatitis, water-borne diseases and other communicable diseases");
		target3.addSubSDG(subtarget33);
		
		SDG subtarget34 = new SDG(3,"Target3.4");
		subtarget34.setMainSDG(target3);
		subtarget34.setDescription("By 2030, reduce by one third premature mortality from non-communicable diseases through prevention and treatment and promote mental health and well-being");
		target3.addSubSDG(subtarget34);
		
		SDG subtarget35 = new SDG(3,"Target3.5");
		subtarget35.setMainSDG(target3);
		subtarget35.setDescription("Strengthen the prevention and treatment of substance abuse, including narcotic drug abuse and harmful use of alcoho");
		target3.addSubSDG(subtarget35);
		
		SDG subtarget36 = new SDG(3,"Target3.6");
		subtarget36.setMainSDG(target3);
		subtarget36.setDescription("By 2020, halve the number of global deaths and injuries from road traffic accidents");
		target3.addSubSDG(subtarget36);
		
		SDG subtarget37 = new SDG(3,"Target3.7");
		subtarget37.setMainSDG(target3);
		subtarget37.setDescription("By 2030, ensure universal access to sexual and reproductive health-care services, including for family planning, information and education, and the integration of reproductive health into national strategies and programmes");
		target3.addSubSDG(subtarget37);
		
		SDG subtarget38 = new SDG(3,"Target3.8");
		subtarget38.setMainSDG(target3);
		subtarget38.setDescription("Achieve universal health coverage, including financial risk protection, access to quality essential health-care services and access to safe, effective, quality and affordable essential medicines and vaccines for all");
		target3.addSubSDG(subtarget38);
		
		SDG subtarget39 = new SDG(3,"Target3.9");
		subtarget39.setMainSDG(target3);
		subtarget39.setDescription("By 2030, substantially reduce the number of deaths and illnesses from hazardous chemicals and air, water and soil pollution and contamination");
		target3.addSubSDG(subtarget39);
		
		SDG subtarget3a = new SDG(3,"Target3.a");
		subtarget3a.setMainSDG(target3);
		subtarget3a.setDescription("Strengthen the implementation of the World Health Organization Framework Convention on Tobacco Control in all countries, as appropriate");
		target3.addSubSDG(subtarget3a);
		
		SDG subtarget3b = new SDG(3,"Target3.b");
		subtarget3b.setMainSDG(target3);
		subtarget3b.setDescription("Support the research and development of vaccines and medicines for the communicable and non-communicable diseases that primarily affect developing countries, provide access to affordable essential medicines and vaccines, in accordance with the Doha Declaration on the TRIPS Agreement and Public Health, which affirms the right of developing countries to use to the full the provisions in the Agreement on Trade-Related Aspects of Intellectual Property Rights regarding flexibilities to protect public health, and, in particular, provide access to medicines for all");
		target3.addSubSDG(subtarget3b);
		
		SDG subtarget3c = new SDG(3,"Target3.c");
		subtarget3c.setMainSDG(target3);
		subtarget3c.setDescription("Substantially increase health financing and the recruitment, development, training and retention of the health workforce in developing countries, especially in least developed countries and small island developing States");
		target3.addSubSDG(subtarget3c);
		
		SDG subtarget3d = new SDG(3,"Target3.d");
		subtarget3d.setMainSDG(target3);
		subtarget3d.setDescription("Strengthen the capacity of all countries, in particular developing countries, for early warning, risk reduction and management of national and global health risks");
		target3.addSubSDG(subtarget3d);
		//Einde SDG3
		
// *************************************************************************************************
		
		//Begin SDG4: Quality education
		SDG target4 = new SDG(4,"Quality education");
		target4.setMainSDG(null);
		
		SDG subtarget41 = new SDG(4,"Target4.1");
		subtarget41.setMainSDG(target4);
		subtarget41.setDescription("By 2030, ensure that all girls and boys complete free, equitable and quality primary and secondary education leading to relevant and effective learning outcomes");
		target4.addSubSDG(subtarget41);
		
		SDG subtarget42 = new SDG(4,"Target4.2");
		subtarget42.setMainSDG(target4);
		subtarget42.setDescription("By 2030, ensure that all girls and boys have access to quality early childhood development, care and pre-primary education so that they are ready for primary education");
		target4.addSubSDG(subtarget42);
		
		SDG subtarget43 = new SDG(4,"Target4.3");
		subtarget43.setMainSDG(target4);
		subtarget43.setDescription("By 2030, ensure equal access for all women and men to affordable and quality technical, vocational and tertiary education, including university");
		target4.addSubSDG(subtarget43);
		
		SDG subtarget44 = new SDG(4,"Target4.4");
		subtarget44.setMainSDG(target4);
		subtarget44.setDescription("By 2030, substantially increase the number of youth and adults who have relevant skills, including technical and vocational skills, for employment, decent jobs and entrepreneurship");
		target4.addSubSDG(subtarget44);
		
		SDG subtarget45 = new SDG(4,"Target4.5");
		subtarget45.setMainSDG(target4);
		subtarget45.setDescription("By 2030, eliminate gender disparities in education and ensure equal access to all levels of education and vocational training for the vulnerable, including persons with disabilities, indigenous peoples and children in vulnerable situations");
		target4.addSubSDG(subtarget45);
		
		SDG subtarget46 = new SDG(4,"Target4.6");
		subtarget46.setMainSDG(target4);
		subtarget46.setDescription("By 2030, ensure that all youth and a substantial proportion of adults, both men and women, achieve literacy and numeracy");
		target4.addSubSDG(subtarget46);
		
		SDG subtarget47 = new SDG(4,"Target4.7");
		subtarget47.setMainSDG(target4);
		subtarget47.setDescription("By 2030, ensure that all learners acquire the knowledge and skills needed to promote sustainable development, including, among others, through education for sustainable development and sustainable lifestyles, human rights, gender equality, promotion of a culture of peace and non-violence, global citizenship and appreciation of cultural diversity and of culture�s contribution to sustainable development");
		target4.addSubSDG(subtarget47);
		
		SDG subtarget4a = new SDG(4,"Target4.a");
		subtarget4a.setMainSDG(target4);
		subtarget4a.setDescription("Build and upgrade education facilities that are child, disability and gender sensitive and provide safe, non-violent, inclusive and effective learning environments for all");
		target4.addSubSDG(subtarget4a);
		
		SDG subtarget4b = new SDG(4,"Target4.b");
		subtarget4b.setMainSDG(target4);
		subtarget4b.setDescription("By 2020, substantially expand globally the number of scholarships available to developing countries, in particular least developed countries, small island developing States and African countries, for enrolment in higher education, including vocational training and information and communications technology, technical, engineering and scientific programmes, in developed countries and other developing countries");
		target4.addSubSDG(subtarget4b);
		
		SDG subtarget4c = new SDG(4,"Target4.c");
		subtarget4c.setMainSDG(target4);
		subtarget4c.setDescription("By 2030, substantially increase the supply of qualified teachers, including through international cooperation for teacher training in developing countries, especially least developed countries and small island developing States");
		target4.addSubSDG(subtarget4c);
		//Einde SDG4
		
// *****************************************************************************************************
		
		//Start SDG5: Gender equality
		SDG target5 = new SDG(5,"Gender equality");
		target5.setMainSDG(null);
		
		SDG subtarget51 = new SDG(5,"Target5.1");
		subtarget51.setMainSDG(target5);
		subtarget51.setDescription("End all forms of discrimination against all women and girls everywhere");
		target5.addSubSDG(subtarget51);
		
		SDG subtarget52 = new SDG(5,"Target5.2");
		subtarget52.setMainSDG(target5);
		subtarget52.setDescription("Eliminate all forms of violence against all women and girls in the public and private spheres, including trafficking and sexual and other types of exploitation");
		target5.addSubSDG(subtarget52);
		
		SDG subtarget53 = new SDG(5,"Target5.3");
		subtarget53.setMainSDG(target5);
		subtarget53.setDescription("Eliminate all harmful practices, such as child, early and forced marriage and female genital mutilation");
		target5.addSubSDG(subtarget53);
		
		SDG subtarget54 = new SDG(5,"Target5.4");
		subtarget54.setMainSDG(target5);
		subtarget54.setDescription("Recognize and value unpaid care and domestic work through the provision of public services, infrastructure and social protection policies and the promotion of shared responsibility within the household and the family as nationally appropriate");
		target5.addSubSDG(subtarget54);
		
		SDG subtarget55 = new SDG(5,"Target5.5");
		subtarget55.setMainSDG(target5);
		subtarget55.setDescription("Ensure women�s full and effective participation and equal opportunities for leadership at all levels of decision-making in political, economic and public life");
		target5.addSubSDG(subtarget55);
		
		SDG subtarget56 = new SDG(5,"Target5.6");
		subtarget56.setMainSDG(target5);
		subtarget56.setDescription("Ensure universal access to sexual and reproductive health and reproductive rights as agreed in accordance with the Programme of Action of the International Conference on Population and Development and the Beijing Platform for Action and the outcome documents of their review conferences");
		target5.addSubSDG(subtarget56);
		
		SDG subtarget5a = new SDG(5,"Target5.a");
		subtarget5a.setMainSDG(target5);
		subtarget5a.setDescription("Undertake reforms to give women equal rights to economic resources, as well as access to ownership and control over land and other forms of property, financial services, inheritance and natural resources, in accordance with national laws");
		target5.addSubSDG(subtarget5a);
		
		SDG subtarget5b = new SDG(5,"Target5.b");
		subtarget5b.setMainSDG(target5);
		subtarget5b.setDescription("Enhance the use of enabling technology, in particular information and communications technology, to promote the empowerment of women");
		target5.addSubSDG(subtarget5b);
		
		SDG subtarget5c = new SDG(5,"Target5.c");
		subtarget5c.setMainSDG(target5);
		subtarget5c.setDescription("Adopt and strengthen sound policies and enforceable legislation for the promotion of gender equality and the empowerment of all women and girls at all levels");
		target5.addSubSDG(subtarget5c);
		//Einde SDG5
		
// **************************************************************************************
		
		//Begin SDG6: Clean water and sanitation
		SDG target6 = new SDG(6,"Clean water and sanitation");
		target6.setMainSDG(null);
		
		SDG subtarget61 = new SDG(6,"Target6.1");
		subtarget61.setMainSDG(target6);
		subtarget61.setDescription("By 2030, achieve universal and equitable access to safe and affordable drinking water for all");
		target6.addSubSDG(subtarget61);
		
		SDG subtarget62 = new SDG(6,"Target6.2");
		subtarget62.setMainSDG(target6);
		subtarget62.setDescription("By 2030, achieve access to adequate and equitable sanitation and hygiene for all and end open defecation, paying special attention to the needs of women and girls and those in vulnerable situations");
		target6.addSubSDG(subtarget62);
		
		SDG subtarget63 = new SDG(6,"Target6.3");
		subtarget63.setMainSDG(target6);
		subtarget63.setDescription("By 2030, improve water quality by reducing pollution, eliminating dumping and minimizing release of hazardous chemicals and materials, halving the proportion of untreated wastewater and substantially increasing recycling and safe reuse globally");
		target6.addSubSDG(subtarget63);
		
		SDG subtarget64 = new SDG(6,"Target6.4");
		subtarget64.setMainSDG(target6);
		subtarget64.setDescription("By 2030, substantially increase water-use efficiency across all sectors and ensure sustainable withdrawals and supply of freshwater to address water scarcity and substantially reduce the number of people suffering from water scarcity");
		target6.addSubSDG(subtarget64);
		
		SDG subtarget65 = new SDG(6,"Target6.5");
		subtarget65.setMainSDG(target6);
		subtarget65.setDescription("By 2030, implement integrated water resources management at all levels, including through transboundary cooperation as appropriate");
		target6.addSubSDG(subtarget65);
		
		SDG subtarget66 = new SDG(6,"Target6.6");
		subtarget66.setMainSDG(target6);
		subtarget66.setDescription("By 2020, protect and restore water-related ecosystems, including mountains, forests, wetlands, rivers, aquifers and lakes");
		target6.addSubSDG(subtarget66);
		
		SDG subtarget6a = new SDG(6,"Target6.a");
		subtarget6a.setMainSDG(target6);
		subtarget6a.setDescription("By 2030, expand international cooperation and capacity-building support to developing countries in water- and sanitation-related activities and programmes, including water harvesting, desalination, water efficiency, wastewater treatment, recycling and reuse technologies");
		target6.addSubSDG(subtarget6a);
		
		SDG subtarget6b = new SDG(6,"Target6.b");
		subtarget6b.setMainSDG(target6);
		subtarget6b.setDescription("Support and strengthen the participation of local communities in improving water and sanitation management");
		target6.addSubSDG(subtarget6b);
		//Einde SDG6
		
// **************************************************************************************		
		
		//Begin SDG7	
		SDG target7 = new SDG(7,"Affordable and clean energy");
		target7.setMainSDG(null);
		
		SDG subtarget71 = new SDG(7,"Target7.1");
		subtarget71.setMainSDG(target7);
		subtarget71.setDescription("By 2030, ensure universal access to affordable, reliable and modern energy services");
		target7.addSubSDG(subtarget71);
		
		SDG subtarget72 = new SDG(7,"Target7.2");
		subtarget72.setMainSDG(target7);
		subtarget72.setDescription("By 2030, increase substantially the share of renewable energy in the global energy mix");
		target7.addSubSDG(subtarget72);
		
		SDG subtarget73 = new SDG(7,"Target7.3");
		subtarget73.setMainSDG(target7);
		subtarget73.setDescription("By 2030, double the global rate of improvement in energy efficiency");
		target7.addSubSDG(subtarget73);
		
		SDG subtarget7a = new SDG(7,"Target7.a");
		subtarget7a.setMainSDG(target7);
		subtarget7a.setDescription("By 2030, enhance international cooperation to facilitate access to clean energy research and technology, including renewable energy, energy efficiency and advanced and cleaner fossil-fuel technology, and promote investment in energy infrastructure and clean energy technology");
		target7.addSubSDG(subtarget7a);
		
		SDG subtarget7b = new SDG(7,"Target7.b");
		subtarget7b.setMainSDG(target7);
		subtarget7b.setDescription("By 2030, expand infrastructure and upgrade technology for supplying modern and sustainable energy services for all in developing countries, in particular least developed countries, small island developing States, and land-locked developing countries, in accordance with their respective programmes of support");
		target7.addSubSDG(subtarget7b);
		//Einde SDG7
		
// ************************************************************************************
		
		//Begin SDG8: Decent work and economic growth
		SDG target8 = new SDG(8,"Decent work and economic growth");
		target8.setMainSDG(null);
		
		SDG subtarget81 = new SDG(8,"Target8.1");
		subtarget81.setMainSDG(target8);
		subtarget81.setDescription("Sustain per capita economic growth in accordance with national circumstances and, in particular, at least 7 per cent gross domestic product growth per annum in the least developed countries");
		target8.addSubSDG(subtarget81);
		
		SDG subtarget82 = new SDG(8,"Target8.2");
		subtarget82.setMainSDG(target8);
		subtarget82.setDescription("Achieve higher levels of economic productivity through diversification, technological upgrading and innovation, including through a focus on high-value added and labour-intensive sectors");
		target8.addSubSDG(subtarget82);
		
		SDG subtarget83 = new SDG(8,"Target8.3");
		subtarget83.setMainSDG(target8);
		subtarget83.setDescription("Promote development-oriented policies that support productive activities, decent job creation, entrepreneurship, creativity and innovation, and encourage the formalization and growth of micro-, small- and medium-sized enterprises, including through access to financial services");
		target8.addSubSDG(subtarget83);
		
		SDG subtarget84 = new SDG(8,"Target8.4");
		subtarget84.setMainSDG(target8);
		subtarget84.setDescription("Improve progressively, through 2030, global resource efficiency in consumption and production and endeavour to decouple economic growth from environmental degradation, in accordance with the 10-year framework of programmes on sustainable consumption and production, with developed countries taking the lead");
		target8.addSubSDG(subtarget84);
		
		SDG subtarget85 = new SDG(8,"Target8.5");
		subtarget85.setMainSDG(target8);
		subtarget85.setDescription("By 2030, achieve full and productive employment and decent work for all women and men, including for young people and persons with disabilities, and equal pay for work of equal value");
		target8.addSubSDG(subtarget85);
		
		SDG subtarget86 = new SDG(8,"Target8.6");
		subtarget86.setMainSDG(target8);
		subtarget86.setDescription("By 2020, substantially reduce the proportion of youth not in employment, education or training");
		target8.addSubSDG(subtarget86);
		
		SDG subtarget87 = new SDG(8,"Target8.7");
		subtarget87.setMainSDG(target8);
		subtarget87.setDescription("Take immediate and effective measures to eradicate forced labour, end modern slavery and human trafficking and secure the prohibition and elimination of the worst forms of child labour, including recruitment and use of child soldiers, and by 2025 end child labour in all its forms");
		target8.addSubSDG(subtarget87);
		
		SDG subtarget88 = new SDG(8,"Target8.8");
		subtarget88.setMainSDG(target8);
		subtarget88.setDescription("Protect labour rights and promote safe and secure working environments for all workers, including migrant workers, in particular women migrants, and those in precarious employment");
		target8.addSubSDG(subtarget88);
		
		SDG subtarget89 = new SDG(8,"Target8.9");
		subtarget89.setMainSDG(target8);
		subtarget89.setDescription("By 2030, devise and implement policies to promote sustainable tourism that creates jobs and promotes local culture and products");
		target8.addSubSDG(subtarget89);
		
		SDG subtarget810 = new SDG(8,"Target8.10");
		subtarget810.setMainSDG(target8);
		subtarget810.setDescription("Strengthen the capacity of domestic financial institutions to encourage and expand access to banking, insurance and financial services for all");
		target8.addSubSDG(subtarget810);
		
		SDG subtarget8a = new SDG(8,"Target8.a");
		subtarget8a.setMainSDG(target8);
		subtarget8a.setDescription("Increase Aid for Trade support for developing countries, in particular least developed countries, including through the Enhanced Integrated Framework for Trade-Related Technical Assistance to Least Developed Countries");
		target8.addSubSDG(subtarget8a);
		
		SDG subtarget8b = new SDG(8,"Target8.b");
		subtarget8b.setMainSDG(target8);
		subtarget8b.setDescription("By 2020, develop and operationalize a global strategy for youth employment and implement the Global Jobs Pact of the International Labour Organization");
		target8.addSubSDG(subtarget8b);
		//Einde SDG8
		
// ***************************************************************************************
		
		//Begin SDG9: Industry, innovation and infrastructure
		SDG target9 = new SDG(9,"Industry, innovation and infrastructure");
		target9.setMainSDG(null);
		
		SDG subtarget91 = new SDG(9,"Target9.1");
		subtarget91.setMainSDG(target9);
		subtarget91.setDescription("Develop quality, reliable, sustainable and resilient infrastructure, including regional and transborder infrastructure, to support economic development and human well-being, with a focus on affordable and equitable access for all");
		target9.addSubSDG(subtarget91);
		
		SDG subtarget92 = new SDG(9,"Target9.2");
		subtarget92.setMainSDG(target9);
		subtarget92.setDescription("Promote inclusive and sustainable industrialization and, by 2030, significantly raise industry�s share of employment and gross domestic product, in line with national circumstances, and double its share in least developed countries");
		target9.addSubSDG(subtarget92);
		
		SDG subtarget93 = new SDG(9,"Target9.3");
		subtarget93.setMainSDG(target9);
		subtarget93.setDescription("Increase the access of small-scale industrial and other enterprises, in particular in developing countries, to financial services, including affordable credit, and their integration into value chains and markets");
		target9.addSubSDG(subtarget93);
		
		SDG subtarget94 = new SDG(9,"Target9.4");
		subtarget94.setMainSDG(target9);
		subtarget94.setDescription("By 2030, upgrade infrastructure and retrofit industries to make them sustainable, with increased resource-use efficiency and greater adoption of clean and environmentally sound technologies and industrial processes, with all countries taking action in accordance with their respective capabilities");
		target9.addSubSDG(subtarget94);
		
		SDG subtarget95 = new SDG(9,"Target9.5");
		subtarget95.setMainSDG(target9);
		subtarget95.setDescription("Enhance scientific research, upgrade the technological capabilities of industrial sectors in all countries, in particular developing countries, including, by 2030, encouraging innovation and substantially increasing the number of research and development workers per 1 million people and public and private research and development spending");
		target9.addSubSDG(subtarget95);
		
		SDG subtarget9a = new SDG(9,"Target9.a");
		subtarget9a.setMainSDG(target9);
		subtarget9a.setDescription("Facilitate sustainable and resilient infrastructure development in developing countries through enhanced financial, technological and technical support to African countries, least developed countries, landlocked developing countries and small island developing States");
		target9.addSubSDG(subtarget9a);
		
		SDG subtarget9b = new SDG(9,"Target9.b");
		subtarget9b.setMainSDG(target9);
		subtarget9b.setDescription("Support domestic technology development, research and innovation in developing countries, including by ensuring a conducive policy environment for, inter alia, industrial diversification and value addition to commodities");
		target9.addSubSDG(subtarget9b);
		
		SDG subtarget9c = new SDG(9,"Target9.c");
		subtarget9c.setMainSDG(target9);
		subtarget9c.setDescription("Significantly increase access to information and communications technology and strive to provide universal and affordable access to the Internet in least developed countries by 2020");
		target9.addSubSDG(subtarget9c);
		//Einde SDG9
		
// ************************************************************************************		
		
		//Begin SDG10: Reduced inequalities
		SDG target10 = new SDG(10,"Reduced inequalities");
		target10.setMainSDG(null);
		
		SDG subtarget101 = new SDG(10,"Target10.1");
		subtarget101.setMainSDG(target10);
		subtarget101.setDescription("By 2030, progressively achieve and sustain income growth of the bottom 40 per cent of the population at a rate higher than the national average");
		target10.addSubSDG(subtarget101);
		
		SDG subtarget102 = new SDG(10,"Target10.2");
		subtarget102.setMainSDG(target10);
		subtarget102.setDescription("By 2030, empower and promote the social, economic and political inclusion of all, irrespective of age, sex, disability, race, ethnicity, origin, religion or economic or other status");
		target10.addSubSDG(subtarget102);
		
		SDG subtarget103 = new SDG(10,"Target10.3");
		subtarget103.setMainSDG(target10);
		subtarget103.setDescription("Ensure equal opportunity and reduce inequalities of outcome, including by eliminating discriminatory laws, policies and practices and promoting appropriate legislation, policies and action in this regard");
		target10.addSubSDG(subtarget103);
		
		SDG subtarget104 = new SDG(10,"Target10.4");
		subtarget104.setMainSDG(target10);
		subtarget104.setDescription("Adopt policies, especially fiscal, wage and social protection policies, and progressively achieve greater equality");
		target10.addSubSDG(subtarget104);
		
		SDG subtarget105 = new SDG(10,"Target10.5");
		subtarget105.setMainSDG(target10);
		subtarget105.setDescription("Improve the regulation and monitoring of global financial markets and institutions and strengthen the implementation of such regulations");
		target10.addSubSDG(subtarget105);
		
		SDG subtarget106 = new SDG(10,"Target10.6");
		subtarget106.setMainSDG(target10);
		subtarget106.setDescription("Ensure enhanced representation and voice for developing countries in decision-making in global international economic and financial institutions in order to deliver more effective, credible, accountable and legitimate institutions");
		target10.addSubSDG(subtarget106);
		
		SDG subtarget107 = new SDG(10,"Target10.7");
		subtarget107.setMainSDG(target10);
		subtarget107.setDescription("Facilitate orderly, safe, regular and responsible migration and mobility of people, including through the implementation of planned and well-managed migration policies");
		target10.addSubSDG(subtarget107);
		
		SDG subtarget10a = new SDG(10,"Target10.a");
		subtarget10a.setMainSDG(target10);
		subtarget10a.setDescription("Implement the principle of special and differential treatment for developing countries, in particular least developed countries, in accordance with World Trade Organization agreements");
		target10.addSubSDG(subtarget10a);
		
		SDG subtarget10b = new SDG(10,"Target10.b");
		subtarget10b.setMainSDG(target10);
		subtarget10b.setDescription("Encourage official development assistance and financial flows, including foreign direct investment, to States where the need is greatest, in particular least developed countries, African countries, small island developing States and landlocked developing countries, in accordance with their national plans and programmes");
		target10.addSubSDG(subtarget10b);
		
		SDG subtarget10c = new SDG(10,"Target10.c");
		subtarget10c.setMainSDG(target10);
		subtarget10c.setDescription("By 2030, reduce to less than 3 per cent the transaction costs of migrant remittances and eliminate remittance corridors with costs higher than 5 per cent");
		target10.addSubSDG(subtarget10c);
		//Einde SDG10
		
// *********************************************************************************************************
		
		//Begin SDG11: Sustainable cities and communities
		SDG target11 = new SDG(11,"Sustainable cities and communities");
		target11.setMainSDG(null);
		
		SDG subtarget111 = new SDG(11,"Target11.1");
		subtarget111.setMainSDG(target11);
		subtarget111.setDescription("By 2030, ensure access for all to adequate, safe and affordable housing and basic services and upgrade slums");
		target11.addSubSDG(subtarget111);
		
		SDG subtarget112 = new SDG(11,"Target11.2");
		subtarget112.setMainSDG(target11);
		subtarget112.setDescription("By 2030, provide access to safe, affordable, accessible and sustainable transport systems for all, improving road safety, notably by expanding public transport, with special attention to the needs of those in vulnerable situations, women, children, persons with disabilities and older persons");
		target11.addSubSDG(subtarget112);
		
		SDG subtarget113 = new SDG(11,"Target11.3");
		subtarget113.setMainSDG(target11);
		subtarget113.setDescription("By 2030, enhance inclusive and sustainable urbanization and capacity for participatory, integrated and sustainable human settlement planning and management in all countries");
		target11.addSubSDG(subtarget113);
		
		SDG subtarget114 = new SDG(11,"Target11.4");
		subtarget114.setMainSDG(target11);
		subtarget114.setDescription("Strengthen efforts to protect and safeguard the world�s cultural and natural heritage");
		target11.addSubSDG(subtarget114);
		
		SDG subtarget115 = new SDG(11,"Target11.5");
		subtarget115.setMainSDG(target11);
		subtarget115.setDescription("By 2030, significantly reduce the number of deaths and the number of people affected and substantially decrease the direct economic losses relative to global gross domestic product caused by disasters, including water-related disasters, with a focus on protecting the poor and people in vulnerable situations");
		target11.addSubSDG(subtarget115);
		
		SDG subtarget116 = new SDG(11,"Target11.6");
		subtarget116.setMainSDG(target11);
		subtarget116.setDescription("By 2030, reduce the adverse per capita environmental impact of cities, including by paying special attention to air quality and municipal and other waste management");
		target11.addSubSDG(subtarget116);
		
		SDG subtarget117 = new SDG(11,"Target11.7");
		subtarget117.setMainSDG(target11);
		subtarget117.setDescription("By 2030, provide universal access to safe, inclusive and accessible, green and public spaces, in particular for women and children, older persons and persons with disabilities");
		target11.addSubSDG(subtarget117);
		
		SDG subtarget11a = new SDG(11,"Target11.a");
		subtarget11a.setMainSDG(target11);
		subtarget11a.setDescription("Support positive economic, social and environmental links between urban, per-urban and rural areas by strengthening national and regional development planning");
		target11.addSubSDG(subtarget11a);
		
		SDG subtarget11b = new SDG(11,"Target11.b");
		subtarget11b.setMainSDG(target11);
		subtarget11b.setDescription("By 2020, substantially increase the number of cities and human settlements adopting and implementing integrated policies and plans towards inclusion, resource efficiency, mitigation and adaptation to climate change, resilience to disasters, and develop and implement, in line with the Sendai Framework for Disaster Risk Reduction 2015-2030, holistic disaster risk management at all levels");
		target11.addSubSDG(subtarget11b);
		
		SDG subtarget11c = new SDG(11,"Target11.c");
		subtarget11c.setMainSDG(target11);
		subtarget11c.setDescription("Support least developed countries, including through financial and technical assistance, in building sustainable and resilient buildings utilizing local materials");
		target11.addSubSDG(subtarget11c);
		//Einde SDG11
		
// ******************************************************************************************************
		
		//Begin SDG12: Responsible consumption and production
		SDG target12 = new SDG(12,"Responsible consumption and production");
		target12.setMainSDG(null);
		
		SDG subtarget121 = new SDG(12,"Target12.1");
		subtarget121.setMainSDG(target12);
		subtarget121.setDescription("Implement the 10-year framework of programmes on sustainable consumption and production, all countries taking action, with developed countries taking the lead, taking into account the development and capabilities of developing countries");
		target12.addSubSDG(subtarget121);
		
		SDG subtarget122 = new SDG(12,"Target12.2");
		subtarget122.setMainSDG(target12);
		subtarget122.setDescription("By 2030, achieve the sustainable management and efficient use of natural resources");
		target12.addSubSDG(subtarget122);
		
		SDG subtarget123 = new SDG(12,"Target12.3");
		subtarget123.setMainSDG(target12);
		subtarget123.setDescription("By 2030, halve per capita global food waste at the retail and consumer levels and reduce food losses along production and supply chains, including post-harvest losses");
		target12.addSubSDG(subtarget123);
		
		SDG subtarget124 = new SDG(12,"Target12.4");
		subtarget124.setMainSDG(target12);
		subtarget124.setDescription("By 2020, achieve the environmentally sound management of chemicals and all wastes throughout their life cycle, in accordance with agreed international frameworks, and significantly reduce their release to air, water and soil in order to minimize their adverse impacts on human health and the environment");
		target12.addSubSDG(subtarget124);
		
		SDG subtarget125 = new SDG(12,"Target12.5");
		subtarget125.setMainSDG(target12);
		subtarget125.setDescription("By 2030, substantially reduce waste generation through prevention, reduction, recycling and reuse");
		target12.addSubSDG(subtarget125);
		
		SDG subtarget126 = new SDG(12,"Target12.6");
		subtarget126.setMainSDG(target12);
		subtarget126.setDescription("Encourage companies, especially large and transnational companies, to adopt sustainable practices and to integrate sustainability information into their reporting cycle");
		target12.addSubSDG(subtarget126);
		
		SDG subtarget127 = new SDG(12,"Target12.7");
		subtarget127.setMainSDG(target12);
		subtarget127.setDescription("Promote public procurement practices that are sustainable, in accordance with national policies and priorities");
		target12.addSubSDG(subtarget127);
		
		SDG subtarget128 = new SDG(12,"Target12.8");
		subtarget128.setMainSDG(target12);
		subtarget128.setDescription("By 2030, ensure that people everywhere have the relevant information and awareness for sustainable development and lifestyles in harmony with nature");
		target12.addSubSDG(subtarget128);
		
		SDG subtarget12a = new SDG(12,"Target12.a");
		subtarget12a.setMainSDG(target12);
		subtarget12a.setDescription("Support developing countries to strengthen their scientific and technological capacity to move towards more sustainable patterns of consumption and production");
		target12.addSubSDG(subtarget12a);
		
		SDG subtarget12b = new SDG(12,"Target12.b");
		subtarget12b.setMainSDG(target12);
		subtarget12b.setDescription("Develop and implement tools to monitor sustainable development impacts for sustainable tourism that creates jobs and promotes local culture and products");
		target12.addSubSDG(subtarget12b);
		
		SDG subtarget12c = new SDG(12,"Target12.c");
		subtarget12c.setMainSDG(target12);
		subtarget12c.setDescription("Rationalize inefficient fossil-fuel subsidies that encourage wasteful consumption by removing market distortions, in accordance with national circumstances, including by restructuring taxation and phasing out those harmful subsidies, where they exist, to reflect their environmental impacts, taking fully into account the specific needs and conditions of developing countries and minimizing the possible adverse impacts on their development in a manner that protects the poor and the affected communities");
		target12.addSubSDG(subtarget12c);
		//Einde SDG12
		
// ****************************************************************************************************
		
		//Begin SDG13: Climate action
		SDG target13 = new SDG(13,"Climate action");
		target13.setMainSDG(null);
		
		SDG subtarget131 = new SDG(13,"Target13.1");
		subtarget131.setMainSDG(target13);
		subtarget131.setDescription("Strengthen resilience and adaptive capacity to climate-related hazards and natural disasters in all countries");
		target13.addSubSDG(subtarget131);
		
		SDG subtarget132 = new SDG(13,"Target13.2");
		subtarget132.setMainSDG(target13);
		subtarget132.setDescription("Integrate climate change measures into national policies, strategies and planning");
		target13.addSubSDG(subtarget132);
		
		SDG subtarget133 = new SDG(13,"Target13.3");
		subtarget133.setMainSDG(target13);
		subtarget133.setDescription("Improve education, awareness-raising and human and institutional capacity on climate change mitigation, adaptation, impact reduction and early warning");
		target13.addSubSDG(subtarget133);
		
		SDG subtarget13a = new SDG(13,"Target13.a");
		subtarget13a.setMainSDG(target13);
		subtarget13a.setDescription("Implement the commitment undertaken by developed-country parties to the United Nations Framework Convention on Climate Change to a goal of mobilizing jointly $100 billion annually by 2020 from all sources to address the needs of developing countries in the context of meaningful mitigation actions and transparency on implementation and fully operationalize the Green Climate Fund through its capitalization as soon as possible");
		target13.addSubSDG(subtarget13a);
		
		SDG subtarget13b = new SDG(13,"Target13.b");
		subtarget13b.setMainSDG(target13);
		subtarget13b.setDescription("Promote mechanisms for raising capacity for effective climate change-related planning and management in least developed countries and small island developing States, including focusing on women, youth and local and marginalized communities * Acknowledging that the United Nations Framework Convention on Climate Change is the primary international, intergovernmental forum for negotiating the global response to climate change.");
		target13.addSubSDG(subtarget13b);
		//Einde SDG13
		
// **********************************************************************************************************		
		
		// Begin SDG14: Life below water
		SDG target14 = new SDG(14,"Life below water");
		target14.setMainSDG(null);
		
		SDG subtarget141 = new SDG(14,"Target14.1");
		subtarget141.setMainSDG(target14);
		subtarget141.setDescription("By 2025, prevent and significantly reduce marine pollution of all kinds, in particular from land-based activities, including marine debris and nutrient pollution");
		target14.addSubSDG(subtarget141);
		
		SDG subtarget142 = new SDG(14,"Target14.2");
		subtarget142.setMainSDG(target14);
		subtarget142.setDescription("By 2020, sustainably manage and protect marine and coastal ecosystems to avoid significant adverse impacts, including by strengthening their resilience, and take action for their restoration in order to achieve healthy and productive oceans");
		target14.addSubSDG(subtarget142);
		
		SDG subtarget143 = new SDG(14,"Target14.3");
		subtarget143.setMainSDG(target14);
		subtarget143.setDescription("Minimize and address the impacts of ocean acidification, including through enhanced scientific cooperation at all levels");
		target14.addSubSDG(subtarget143);
		
		SDG subtarget144 = new SDG(14,"Target14.4");
		subtarget144.setMainSDG(target14);
		subtarget144.setDescription("By 2020, effectively regulate harvesting and end overfishing, illegal, unreported and unregulated fishing and destructive fishing practices and implement science-based management plans, in order to restore fish stocks in the shortest time feasible, at least to levels that can produce maximum sustainable yield as determined by their biological characteristics");
		target14.addSubSDG(subtarget144);
		
		SDG subtarget145 = new SDG(14,"Target14.5");
		subtarget145.setMainSDG(target14);
		subtarget145.setDescription("By 2020, conserve at least 10 per cent of coastal and marine areas, consistent with national and international law and based on the best available scientific information");
		target14.addSubSDG(subtarget145);
		
		SDG subtarget146 = new SDG(14,"Target14.6");
		subtarget146.setMainSDG(target14);
		subtarget146.setDescription("By 2020, prohibit certain forms of fisheries subsidies which contribute to overcapacity and overfishing, eliminate subsidies that contribute to illegal, unreported and unregulated fishing and refrain from introducing new such subsidies, recognizing that appropriate and effective special and differential treatment for developing and least developed countries should be an integral part of the World Trade Organization fisheries subsidies negotiation");
		target14.addSubSDG(subtarget146);
		
		SDG subtarget147 = new SDG(14,"Target14.7");
		subtarget147.setMainSDG(target14);
		subtarget147.setDescription("By 2030, increase the economic benefits to Small Island developing States and least developed countries from the sustainable use of marine resources, including through sustainable management of fisheries, aquaculture and tourism");
		target14.addSubSDG(subtarget147);
		
		SDG subtarget14a = new SDG(14,"Target14.a");
		subtarget14a.setMainSDG(target14);
		subtarget14a.setDescription("Increase scientific knowledge, develop research capacity and transfer marine technology, taking into account the Intergovernmental Oceanographic Commission Criteria and Guidelines on the Transfer of Marine Technology, in order to improve ocean health and to enhance the contribution of marine biodiversity to the development of developing countries, in particular small island developing States and least developed countries");
		target14.addSubSDG(subtarget14a);
		
		SDG subtarget14b = new SDG(14,"Target14.b");
		subtarget14b.setMainSDG(target14);
		subtarget14b.setDescription("Provide access for small-scale artisanal fishers to marine resources and markets");
		target14.addSubSDG(subtarget14b);
		
		SDG subtarget14c = new SDG(14,"Target14.c");
		subtarget14c.setMainSDG(target14);
		subtarget14c.setDescription("Enhance the conservation and sustainable use of oceans and their resources by implementing international law as reflected in UNCLOS, which provides the legal framework for the conservation and sustainable use of oceans and their resources, as recalled in paragraph 158 of The Future We Want");
		target14.addSubSDG(subtarget14c);
		// Einde SDG14
		
// *****************************************************************************************************		
		
		// Begin SDG15: Life on land
		SDG target15 = new SDG(15,"Life on land");
		target15.setMainSDG(null);
		
		SDG subtarget151 = new SDG(15,"Target15.1");
		subtarget151.setMainSDG(target15);
		subtarget151.setDescription("By 2020, ensure the conservation, restoration and sustainable use of terrestrial and inland freshwater ecosystems and their services, in particular forests, wetlands, mountains and drylands, in line with obligations under international agreements");
		target15.addSubSDG(subtarget151);
		
		SDG subtarget152 = new SDG(15,"Target15.2");
		subtarget152.setMainSDG(target15);
		subtarget152.setDescription("By 2020, promote the implementation of sustainable management of all types of forests, halt deforestation, restore degraded forests and substantially increase afforestation and reforestation globally");
		target15.addSubSDG(subtarget152);
		
		SDG subtarget153 = new SDG(15,"Target15.3");
		subtarget153.setMainSDG(target15);
		subtarget153.setDescription("By 2030, combat desertification, restore degraded land and soil, including land affected by desertification, drought and floods, and strive to achieve a land degradation-neutral world");
		target15.addSubSDG(subtarget153);
		
		SDG subtarget154 = new SDG(15,"Target15.4");
		subtarget154.setMainSDG(target15);
		subtarget154.setDescription("By 2030, ensure the conservation of mountain ecosystems, including their biodiversity, in order to enhance their capacity to provide benefits that are essential for sustainable development");
		target15.addSubSDG(subtarget154);
		
		SDG subtarget155 = new SDG(15,"Target15.5");
		subtarget155.setMainSDG(target15);
		subtarget155.setDescription("Take urgent and significant action to reduce the degradation of natural habitats, halt the loss of biodiversity and, by 2020, protect and prevent the extinction of threatened species");
		target15.addSubSDG(subtarget155);
		
		SDG subtarget156 = new SDG(15,"Target15.6");
		subtarget156.setMainSDG(target15);
		subtarget156.setDescription("Promote fair and equitable sharing of the benefits arising from the utilization of genetic resources and promote appropriate access to such resources, as internationally agreed");
		target15.addSubSDG(subtarget156);
		
		SDG subtarget157 = new SDG(15,"Target15.7");
		subtarget157.setMainSDG(target15);
		subtarget157.setDescription("Take urgent action to end poaching and trafficking of protected species of flora and fauna and address both demand and supply of illegal wildlife products");
		target15.addSubSDG(subtarget157);
		
		SDG subtarget158 = new SDG(15,"Target15.8");
		subtarget158.setMainSDG(target15);
		subtarget158.setDescription("By 2020, introduce measures to prevent the introduction and significantly reduce the impact of invasive alien species on land and water ecosystems and control or eradicate the priority species");
		target15.addSubSDG(subtarget158);
		
		SDG subtarget159 = new SDG(15,"Target15.9");
		subtarget159.setMainSDG(target15);
		subtarget159.setDescription("By 2020, integrate ecosystem and biodiversity values into national and local planning, development processes, poverty reduction strategies and accounts");
		target15.addSubSDG(subtarget159);
		
		SDG subtarget15a = new SDG(15,"Target15.a");
		subtarget15a.setMainSDG(target15);
		subtarget15a.setDescription("Mobilize and significantly increase financial resources from all sources to conserve and sustainably use biodiversity and ecosystems");
		target15.addSubSDG(subtarget15a);
		
		SDG subtarget15b = new SDG(15,"Target15.b");
		subtarget15b.setMainSDG(target15);
		subtarget15b.setDescription("Mobilize significant resources from all sources and at all levels to finance sustainable forest management and provide adequate incentives to developing countries to advance such management, including for conservation and reforestation");
		target15.addSubSDG(subtarget15b);
		
		SDG subtarget15c = new SDG(15,"Target15.c");
		subtarget15c.setMainSDG(target15);
		subtarget15c.setDescription("Enhance global support for efforts to combat poaching and trafficking of protected species, including by increasing the capacity of local communities to pursue sustainable livelihood opportunities");
		target15.addSubSDG(subtarget15c);
		//Einde SDG15
		
// *****************************************************************************************************		
		
		//Begin SDG16: Peace, justice and strong institutions
		SDG target16 = new SDG(16,"Peace, justice and strong institutions");
		target16.setMainSDG(null);
		
		SDG subtarget161 = new SDG(16,"Target16.1");
		subtarget161.setMainSDG(target16);
		subtarget161.setDescription("Significantly reduce all forms of violence and related death rates everywhere");
		target16.addSubSDG(subtarget161);
		
		SDG subtarget162 = new SDG(16,"Target16.2");
		subtarget162.setMainSDG(target16);
		subtarget162.setDescription("End abuse, exploitation, trafficking and all forms of violence against and torture of children");
		target16.addSubSDG(subtarget162);
		
		SDG subtarget163 = new SDG(16,"Target16.3");
		subtarget163.setMainSDG(target16);
		subtarget163.setDescription("Promote the rule of law at the national and international levels and ensure equal access to justice for all");
		target16.addSubSDG(subtarget163);
		
		SDG subtarget164 = new SDG(16,"Target16.4");
		subtarget164.setMainSDG(target16);
		subtarget164.setDescription("By 2030, significantly reduce illicit financial and arms flows, strengthen the recovery and return of stolen assets and combat all forms of organized crime");
		target16.addSubSDG(subtarget164);
		
		SDG subtarget165 = new SDG(16,"Target16.5");
		subtarget165.setMainSDG(target16);
		subtarget165.setDescription("Substantially reduce corruption and bribery in all their forms");
		target16.addSubSDG(subtarget165);
		
		SDG subtarget166 = new SDG(16,"Target16.6");
		subtarget166.setMainSDG(target16);
		subtarget166.setDescription("Develop effective, accountable and transparent institutions at all levels");
		target16.addSubSDG(subtarget166);
		
		SDG subtarget167 = new SDG(16,"Target16.7");
		subtarget167.setMainSDG(target16);
		subtarget167.setDescription("Ensure responsive, inclusive, participatory and representative decision-making at all levels");
		target16.addSubSDG(subtarget167);
		
		SDG subtarget168 = new SDG(16,"Target16.8");
		subtarget168.setMainSDG(target16);
		subtarget168.setDescription("Broaden and strengthen the participation of developing countries in the institutions of global governance");
		target16.addSubSDG(subtarget168);
		
		SDG subtarget169 = new SDG(16,"Target16.9");
		subtarget169.setMainSDG(target16);
		subtarget169.setDescription("By 2030, provide legal identity for all, including birth registration");
		target16.addSubSDG(subtarget169);
		
		SDG subtarget1610 = new SDG(16,"Target16.10");
		subtarget1610.setMainSDG(target16);
		subtarget1610.setDescription("Ensure public access to information and protect fundamental freedoms, in accordance with national legislation and international agreements");
		target16.addSubSDG(subtarget1610);
		
		SDG subtarget16a = new SDG(16,"Target16.a");
		subtarget16a.setMainSDG(target16);
		subtarget16a.setDescription("Strengthen relevant national institutions, including through international cooperation, for building capacity at all levels, in particular in developing countries, to prevent violence and combat terrorism and crime");
		target16.addSubSDG(subtarget16a);
		
		SDG subtarget16b = new SDG(16,"Target16.b");
		subtarget16b.setMainSDG(target16);
		subtarget16b.setDescription("Promote and enforce non-discriminatory laws and policies for sustainable development");
		target16.addSubSDG(subtarget16b);
		//Einde SDG16
		
// *****************************************************************************************************		
		
		//Begin SDG17	
		SDG target17 = new SDG(17,"Partnerships for the goals");
		target17.setMainSDG(null);
		
		SDG subtarget171 = new SDG(17,"Target17.1");
		subtarget171.setMainSDG(target17);
		subtarget171.setDescription("Strengthen domestic resource mobilization, including through international support to developing countries, to improve domestic capacity for tax and other revenue collection");
		target17.addSubSDG(subtarget171);
		
		SDG subtarget172 = new SDG(17,"Target17.2");
		subtarget172.setMainSDG(target17);
		subtarget172.setDescription("Developed countries to implement fully their official development assistance commitments, including the commitment by many developed countries to achieve the target of 0.7 per cent of ODA/GNI to developing countries and 0.15 to 0.20 per cent of ODA/GNI to least developed countries; ODA providers are encouraged to consider setting a target to provide at least 0.20 per cent of ODA/GNI to least developed countries");
		target17.addSubSDG(subtarget172);
		
		SDG subtarget173 = new SDG(17,"Target17.3");
		subtarget173.setMainSDG(target17);
		subtarget173.setDescription("Mobilize additional financial resources for developing countries from multiple sources");
		target17.addSubSDG(subtarget173);
		
		SDG subtarget174 = new SDG(17,"Target17.4");
		subtarget174.setMainSDG(target17);
		subtarget174.setDescription("Assist developing countries in attaining long-term debt sustainability through coordinated policies aimed at fostering debt financing, debt relief and debt restructuring, as appropriate, and address the external debt of highly indebted poor countries to reduce debt distress");
		target17.addSubSDG(subtarget174);
		
		SDG subtarget175 = new SDG(17,"Target17.5");
		subtarget175.setMainSDG(target17);
		subtarget175.setDescription("Adopt and implement investment promotion regimes for least developed countries");
		target17.addSubSDG(subtarget175);
		
		SDG subtarget176 = new SDG(17,"Target17.6");
		subtarget176.setMainSDG(target17);
		subtarget176.setDescription("Enhance North-South, South-South and triangular regional and international cooperation on and access to science, technology and innovation and enhance knowledge sharing on mutually agreed terms, including through improved coordination among existing mechanisms, in particular at the United Nations level, and through a global technology facilitation mechanism");
		target17.addSubSDG(subtarget176);
		
		SDG subtarget177 = new SDG(17,"Target17.7");
		subtarget177.setMainSDG(target17);
		subtarget177.setDescription("Promote the development, transfer, dissemination and diffusion of environmentally sound technologies to developing countries on favourable terms, including on concessional and preferential terms, as mutually agreed");
		target17.addSubSDG(subtarget177);
		
		SDG subtarget178 = new SDG(17,"Target17.8");
		subtarget178.setMainSDG(target17);
		subtarget178.setDescription("Fully operationalize the technology bank and science, technology and innovation capacity-building mechanism for least developed countries by 2017 and enhance the use of enabling technology, in particular information and communications technology");
		target17.addSubSDG(subtarget178);
		
		SDG subtarget179 = new SDG(17,"Target17.9");
		subtarget179.setMainSDG(target17);
		subtarget179.setDescription("Enhance international support for implementing effective and targeted capacity-building in developing countries to support national plans to implement all the sustainable development goals, including through North-South, South-South and triangular cooperation");
		target17.addSubSDG(subtarget179);
		
		SDG subtarget1710 = new SDG(17,"Target17.10");
		subtarget1710.setMainSDG(target17);
		subtarget1710.setDescription("Promote a universal, rules-based, open, non-discriminatory and equitable multilateral trading system under the World Trade Organization, including through the conclusion of negotiations under its Doha Development Agenda");
		target17.addSubSDG(subtarget1710);
		
		SDG subtarget1711 = new SDG(17,"Target17.11");
		subtarget1711.setMainSDG(target17);
		subtarget1711.setDescription("Significantly increase the exports of developing countries, in particular with a view to doubling the least developed countries� share of global exports by 2020");
		target17.addSubSDG(subtarget1711);
		
		SDG subtarget1712 = new SDG(17,"Target17.12");
		subtarget1712.setMainSDG(target17);
		subtarget1712.setDescription("Realize timely implementation of duty-free and quota-free market access on a lasting basis for all least developed countries, consistent with World Trade Organization decisions, including by ensuring that preferential rules of origin applicable to imports from least developed countries are transparent and simple, and contribute to facilitating market access");
		target17.addSubSDG(subtarget1712);
		
		SDG subtarget1713 = new SDG(17,"Target17.13");
		subtarget1713.setMainSDG(target17);
		subtarget1713.setDescription("Enhance global macroeconomic stability, including through policy coordination and policy coherence");
		target17.addSubSDG(subtarget1713);
		
		SDG subtarget1714 = new SDG(17,"Target17.14");
		subtarget1714.setMainSDG(target17);
		subtarget1714.setDescription("Enhance policy coherence for sustainable development");
		target17.addSubSDG(subtarget1714);
		
		SDG subtarget1715 = new SDG(17,"Target17.15");
		subtarget1715.setMainSDG(target17);
		subtarget1715.setDescription("Respect each country�s policy space and leadership to establish and implement policies for poverty eradication and sustainable development Multi-stakeholder partnerships");
		target17.addSubSDG(subtarget1715);
		
		SDG subtarget1716 = new SDG(17,"Target17.16");
		subtarget1716.setMainSDG(target17);
		subtarget1716.setDescription("Enhance the global partnership for sustainable development, complemented by multi-stakeholder partnerships that mobilize and share knowledge, expertise, technology and financial resources, to support the achievement of the sustainable development goals in all countries, in particular developing countries");
		target17.addSubSDG(subtarget1716);
		
		SDG subtarget1717 = new SDG(17,"Target17.17");
		subtarget1717.setMainSDG(target17);
		subtarget1717.setDescription("Encourage and promote effective public, public-private and civil society partnerships, building on the experience and resourcing strategies of partnerships Data, monitoring and accountability");
		target17.addSubSDG(subtarget1717);
		
		SDG subtarget1718 = new SDG(17,"Target17.18");
		subtarget1718.setMainSDG(target17);
		subtarget1718.setDescription("By 2020, enhance capacity-building support to developing countries, including for least developed countries and small island developing States, to increase significantly the availability of high-quality, timely and reliable data disaggregated by income, gender, age, race, ethnicity, migratory status, disability, geographic location and other characteristics relevant in national contexts");
		target17.addSubSDG(subtarget1718);
		
		SDG subtarget1719 = new SDG(17,"Target17.19");
		subtarget1719.setMainSDG(target17);
		subtarget1719.setDescription("By 2030, build on existing initiatives to develop measurements of progress on sustainable development that complement gross domestic product, and support statistical capacity-building in developing countries");
		target17.addSubSDG(subtarget1719);
		//Einde SDG17
		
// ********************************************************************************************************		
// Einde SDG's	data	
// ********************************************************************************************************		
		
		
		
		SDGdao.insert(target1);
		SDGdao.insert(target2);
		SDGdao.insert(target3);
		SDGdao.insert(target4);
		SDGdao.insert(target5);
		SDGdao.insert(target6);
		SDGdao.insert(target7);
		SDGdao.insert(target8);
		SDGdao.insert(target9);
		SDGdao.insert(target10);
		SDGdao.insert(target11);
		SDGdao.insert(target12);
		SDGdao.insert(target13);
		SDGdao.insert(target14);
		SDGdao.insert(target15);
		SDGdao.insert(target16);
		SDGdao.insert(target17);
		
		
		Categorie categorie2 = new Categorie("Testcategorie1", doelstellingLijst2);
		categoriedao.insert(categorie2);
		
		Categorie categorie3 = new Categorie("Testcategorie2", doelstellingLijst3);
		categoriedao.insert(categorie3);

		Categorie categorie4 = new Categorie("Testcategorie3",doelstellingLijst4 );
		categoriedao.insert(categorie4);
		
		doelstelling1.setMainSDG(target1);
		doelstelling3.setMainSDG(target2);
		doelstelling4.setMainSDG(target3);
		doelstelling5.setMainSDG(target4);
		doelstelling6.setMainSDG(target2.getSubSDG().get(0));
		doelstelling2.setMainSDG(target1.getSubSDG().get(0));
		doelstelling7.setMainSDG(target2.getSubSDG().get(1));
		
		doelstellingdao.insert(doelstelling1);
		doelstellingdao.insert(doelstelling2);
		doelstellingdao.insert(doelstelling3);
		doelstellingdao.insert(doelstelling4);
		
		UserDaoJpa.commitTransaction();
		
		System.out.print("Database filled, ready to go\n");
	}
}
