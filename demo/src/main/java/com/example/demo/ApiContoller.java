package com.example.demo;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.*;
import org.apache.jena.query.* ;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequestMapping("/elitemchi")
@RestController
public class ApiContoller {
	
	//all Player mansour
	@RequestMapping(value = "/Player",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> retrieveMovies() {
		List<JSONObject> list=new ArrayList();
		String NS = "";
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/webs.owl");

		if (model != null) {
		//lire le Namespace de l’ontologie
		NS = model.getNsPrefixURI("");

		// apply our rules on the owlInferencedModel
		Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
		// query on the model after inference
		String queryString = "PREFIX ns: <http://www.enib-erasmus.fr/Semantic/ThomasDupuis/assignment_esports.rdf-xml.owl#> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "SELECT ?Player "
				+ "WHERE{ "
				+ "	   ?Player rdf:type ns:Player ."
				+ "} ";
		
		//System.out.println(JenaEngine.executeQuery(inferedModel,queryString));
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, inferedModel);
		    ResultSet results = qexec.execSelect() ;
		    while (results.hasNext())
		    {
		    	QuerySolution soln = results.nextSolution() ;
		    	System.out.println(soln);
		    	RDFNode x = soln.get("Player") ;
		    	//RDFNode y = soln.get("duration") ;
		    	
                JSONObject obj = new JSONObject();
                obj.put("Player" ,x.toString().split("#")[1]);
                //obj.put("duration" ,y.toString());
				list.add(obj);
		    }
		} else {
		System.out.println("Error when reading model from ontology");
		}
		System.out.println("end : "+list);
		return list;
	}

	
	//Return all the players who are more than 21 years old
	@RequestMapping(value = "/PlayerParAge",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> retrievePlayerParAge(@RequestParam int a) {
		List<JSONObject> list=new ArrayList();
		String NS = "";
		
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/webs.owl");

		if (model != null) {
		//lire le Namespace de l’ontologie
		NS = model.getNsPrefixURI("");

		// apply our rules on the owlInferencedModel
		Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
		// query on the model after inference
		String queryString = "PREFIX esports: <http://www.enib-erasmus.fr/Semantic/ThomasDupuis/assignment_esports.rdf-xml.owl#>" +
	             "SELECT ?NickName ?FirstName ?LastName  ?Age " +
	             "WHERE { ?Player esports:Age ?Age FILTER (?Age > "+a+") " +
	             ". ?Player a esports:Player . " +
	             "?Player esports:hasForHumanName ?HumanName . " +
	             "?HumanName a esports:HumanName . " +
	             "?HumanName esports:FirstName ?FirstName . " +
	             "?HumanName esports:LastName ?LastName . " +
	             "?HumanName esports:Nickname ?NickName . " +
	             "}";
		//System.out.println(JenaEngine.executeQuery(inferedModel,queryString));
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, inferedModel);
		    ResultSet results = qexec.execSelect() ;
		    while (results.hasNext())
		    {
		    	QuerySolution soln = results.nextSolution() ;
		    	System.out.println(soln);
		    	RDFNode x = soln.get("Age") ;
		    	RDFNode y = soln.get("FirstName") ;
		    	RDFNode z = soln.get("LastName") ;
		    	RDFNode t = soln.get("NickName") ;

		    	//RDFNode y = soln.get("duration") ;
		    	
                JSONObject obj = new JSONObject();
                obj.put("Age" ,x.toString().substring(0,2));
                obj.put("FirstName" ,y.toString());
                obj.put("LastName" ,z.toString());
                obj.put("NickName" ,t.toString());


                //obj.put("duration" ,y.toString());
				list.add(obj);
		    }
		} else {
		System.out.println("Error when reading model from ontology");
		}
		System.out.println("end : "+list);
		return list;
	}
	

	//Return all the teams and their nationality
	@RequestMapping(value = "/teamsParNationality",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> retrieveTeamsParNationality(@RequestParam String s) {
		List<JSONObject> list=new ArrayList();
		String NS = "";
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/webs.owl");

		if (model != null) {
		//lire le Namespace de l’ontologie
		NS = model.getNsPrefixURI("");

		// apply our rules on the owlInferencedModel
		Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
		// query on the model after inference
		String queryString = "PREFIX esports: <http://www.enib-erasmus.fr/Semantic/ThomasDupuis/assignment_esports.rdf-xml.owl#>" +
	             "SELECT ?TeamName ?Nationality " +
	             "WHERE { ?Team esports:Nationality ?Nationality " +
	                      "FILTER regex(?Nationality, \""+s+"\") . " +
	                      "?Team a esports:Team . " +
	                      "?Team esports:isNamed ?name . " +
	                      "?name esports:simpleName ?TeamName" +
	                      "} ";
		//System.out.println(JenaEngine.executeQuery(inferedModel,queryString));
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, inferedModel);
		    ResultSet results = qexec.execSelect() ;
		    while (results.hasNext())
		    {
		    	QuerySolution soln = results.nextSolution() ;
		    	System.out.println(soln);
		    	RDFNode x = soln.get("Nationality") ;
		    	RDFNode y = soln.get("TeamName") ;
		    			    	
                JSONObject obj = new JSONObject();
                obj.put("Nationality" ,x.toString());
                obj.put("TeamName" ,y.toString());

                list.add(obj);
		    }
		} else {
		System.out.println("Error when reading model from ontology");
		}
		System.out.println("end : "+list);
		return list;
	}

	
	//Return all the American teams
	@RequestMapping(value = "/teams",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> retrieveTeams() {
		List<JSONObject> list=new ArrayList();
		String NS = "";
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/webs.owl");

		if (model != null) {
		//lire le Namespace de l’ontologie
		NS = model.getNsPrefixURI("");

		// apply our rules on the owlInferencedModel
		Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
		// query on the model after inference
		String queryString = "PREFIX esports: <http://www.enib-erasmus.fr/Semantic/ThomasDupuis/assignment_esports.rdf-xml.owl#>" +
	             "SELECT ?TeamName ?Nationality " +
	             "WHERE { ?Team esports:Nationality ?Nationality . " +
	                      "?Team a esports:Team . " +
	                      "?Team esports:isNamed ?name . " +
	                      "?name esports:simpleName ?TeamName . " +
	                      "} ";
		//System.out.println(JenaEngine.executeQuery(inferedModel,queryString));
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, inferedModel);
		    ResultSet results = qexec.execSelect() ;
		    while (results.hasNext())
		    {
		    	QuerySolution soln = results.nextSolution() ;
		    	System.out.println(soln);
		    	RDFNode x = soln.get("Nationality") ;
		    	RDFNode y = soln.get("TeamName") ;
		    			    	
                JSONObject obj = new JSONObject();
                obj.put("Nationality" ,x.toString());
                obj.put("TeamName" ,y.toString());

                list.add(obj);
		    }
		} else {
		System.out.println("Error when reading model from ontology");
		}
		System.out.println("end : "+list);
		return list;
	}
	
	
	//Show all the prices of the equipment
	
	@RequestMapping(value = "/prices",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> retrievePrices() {
		List<JSONObject> list=new ArrayList();
		String NS = "";
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/webs.owl");

		if (model != null) {
		//lire le Namespace de l’ontologie
		NS = model.getNsPrefixURI("");

		// apply our rules on the owlInferencedModel
		Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
		// query on the model after inference
		String queryString = "PREFIX esports: <http://www.enib-erasmus.fr/Semantic/ThomasDupuis/assignment_esports.rdf-xml.owl#>" +
	             "SELECT  ?EquipmentName ?MoneyValue ?Currency " +
	             "WHERE { ?MaterialEquipment esports:cost ?Money . " +
	                     "?MaterialEquipment a esports:MaterialEquipment . " +
	                     "?MaterialEquipment esports:isNamed ?Name . " +
	                     "?Name esports:simpleName ?EquipmentName . " +
	                     "?Money a esports:Money . " +
	                     "?Money esports:MoneyValue ?MoneyValue . " +
	                     "?Money esports:Currency ?Currency }";
		//System.out.println(JenaEngine.executeQuery(inferedModel,queryString));
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, inferedModel);
		    ResultSet results = qexec.execSelect() ;
		    while (results.hasNext())
		    {
		    	QuerySolution soln = results.nextSolution() ;
		    	System.out.println(soln);
		    	RDFNode x = soln.get("EquipmentName") ;
		    	RDFNode y = soln.get("MoneyValue") ;
		    	RDFNode z = soln.get("Currency") ;

		    	
                JSONObject obj = new JSONObject();
                obj.put("EquipmentName" ,x.toString());
                obj.put("MoneyValue" ,y.toString().substring(0,2));
                obj.put("Currency" ,z.toString());


                list.add(obj);
		    }
		} else {
		System.out.println("Error when reading model from ontology");
		}
		System.out.println("end : "+list);
		return list;
	}



//Return all the players who have a team competiting in a Lan Party which is named "World Cyber Games 2009"
	@RequestMapping(value = "/allplayerInLan",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> retrieveallstaf(@RequestParam String lan) {
		List<JSONObject> list=new ArrayList();
		String NS = "";
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/webs.owl");

		if (model != null) {
			//lire le Namespace de l’ontologie
			NS = model.getNsPrefixURI("");

			// apply our rules on the owlInferencedModel
			Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
			// query on the model after inference
			String queryString = "PREFIX esports: <http://www.enib-erasmus.fr/Semantic/ThomasDupuis/assignment_esports.rdf-xml.owl#>" +
             "SELECT ?NickName ?FirstName ?LastName " +
             "WHERE { ?Player esports:hasTeam ?Team . " +
             "?Player a esports:Player . " +
             "?Team a esports:Team . " +
             "?Team esports:engagedIn ?LanParty . " +
             "?LanParty a esports:LanParty . " +
             "?LanParty esports:isNamed ?ThingName . " +
             "?ThingName a esports:ThingName . " +
             "?ThingName esports:simpleName ?simpleName " +
             "FILTER regex(?simpleName, \""+lan+"\") . " +
             "?Player esports:hasForHumanName ?HumanName . " +
             "?HumanName a esports:HumanName . " +
             "?HumanName esports:FirstName ?FirstName . " +
             "?HumanName esports:LastName ?LastName . " +
             "?HumanName esports:Nickname ?NickName . " +
             "}  ";
			//System.out.println(JenaEngine.executeQuery(inferedModel,queryString));
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, inferedModel);
			ResultSet results = qexec.execSelect() ;
			while (results.hasNext())
			{
				QuerySolution soln = results.nextSolution() ;
				System.out.println(soln);
				RDFNode x = soln.get("FirstName") ;
				RDFNode y = soln.get("LastName") ;
				RDFNode z = soln.get("NickName") ;


				JSONObject obj = new JSONObject();
				obj.put("FirstName" ,x.toString());
				obj.put("LastName" ,y.toString());
				obj.put("NickName" ,z.toString());


				list.add(obj);
			}
		} else {
			System.out.println("Error when reading model from ontology");
		}
		System.out.println("end : "+list);
		return list;
	}


	//Return all the players who have a team competiting in a Lan Party which is named "World Cyber Games 2009"
	@RequestMapping(value = "/allBrand",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> retrieveall() {
		List<JSONObject> list=new ArrayList();
		String NS = "";
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/webs.owl");

		if (model != null) {
			//lire le Namespace de l’ontologie
			NS = model.getNsPrefixURI("");

			// apply our rules on the owlInferencedModel
			Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
			// query on the model after inference
			String queryString = " PREFIX ns: <http://www.enib-erasmus.fr/Semantic/ThomasDupuis/assignment_esports.rdf-xml.owl#> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "SELECT ?Brand "
				+ "WHERE{ "
				+ "	   ?Brand rdf:type ns:Brand ."
				+ "}";
			//System.out.println(JenaEngine.executeQuery(inferedModel,queryString));
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, inferedModel);
			ResultSet results = qexec.execSelect() ;
			while (results.hasNext())
			{
				QuerySolution soln = results.nextSolution() ;
				System.out.println(soln);
				RDFNode x = soln.get("Brand") ;
				//RDFNode y = soln.get("duration") ;

				JSONObject obj = new JSONObject();
				obj.put("Brand" ,x.toString().split("#")[1]);
				//obj.put("duration" ,y.toString());
				list.add(obj);
			}
		} else {
			System.out.println("Error when reading model from ontology");
		}
		System.out.println("end : "+list);
		return list;
	}

	//Return all the staff mansour
	@RequestMapping(value = "/allStaff",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> retrieveal() {
		List<JSONObject> list=new ArrayList();
		String NS = "";
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/webs.owl");

		if (model != null) {
			//lire le Namespace de l’ontologie
			NS = model.getNsPrefixURI("");

			// apply our rules on the owlInferencedModel
			Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
			// query on the model after inference
			String queryString = " PREFIX ns: <http://www.enib-erasmus.fr/Semantic/ThomasDupuis/assignment_esports.rdf-xml.owl#> "
					+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
					+ "SELECT ?Staff "
					+ "WHERE{ "
					+ "	   ?Staff rdf:type ns:Staff ."
					+ "}";
			//System.out.println(JenaEngine.executeQuery(inferedModel,queryString));
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, inferedModel);
			ResultSet results = qexec.execSelect() ;
			while (results.hasNext())
			{
				QuerySolution soln = results.nextSolution() ;
				System.out.println(soln);
				RDFNode x = soln.get("Staff") ;
				//RDFNode y = soln.get("duration") ;

				JSONObject obj = new JSONObject();
				obj.put("Staff" ,x.toString().split("#")[1]);
				//obj.put("duration" ,y.toString());
				list.add(obj);
			}
		} else {
			System.out.println("Error when reading model from ontology");
		}
		System.out.println("end : "+list);
		return list;
	}


	//Return all the Tournament mansour
	@RequestMapping(value = "/allTournament",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> retrievea() {
		List<JSONObject> list=new ArrayList();
		String NS = "";
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/webs.owl");

		if (model != null) {
			//lire le Namespace de l’ontologie
			NS = model.getNsPrefixURI("");

			// apply our rules on the owlInferencedModel
			Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
			// query on the model after inference
			String queryString = " PREFIX ns: <http://www.enib-erasmus.fr/Semantic/ThomasDupuis/assignment_esports.rdf-xml.owl#> "
					+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
					+ "SELECT ?Tournament "
					+ "WHERE{ "
					+ "	   ?Tournament rdf:type ns:Tournament ."
					+ "}";
			//System.out.println(JenaEngine.executeQuery(inferedModel,queryString));
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, inferedModel);
			ResultSet results = qexec.execSelect() ;
			while (results.hasNext())
			{
				QuerySolution soln = results.nextSolution() ;
				System.out.println(soln);
				RDFNode x = soln.get("Tournament") ;
				//RDFNode y = soln.get("duration") ;

				JSONObject obj = new JSONObject();
				obj.put("Tournament" ,x.toString().split("#")[1]);
				//obj.put("duration" ,y.toString());
				list.add(obj);
			}
		} else {
			System.out.println("Error when reading model from ontology");
		}
		System.out.println("end : "+list);
		return list;
	}

	//Return all the websites mansour
	@RequestMapping(value = "/allwebsites",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> retrieve() {
		List<JSONObject> list=new ArrayList();
		String NS = "";
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/webs.owl");

		if (model != null) {
			//lire le Namespace de l’ontologie
			NS = model.getNsPrefixURI("");

			// apply our rules on the owlInferencedModel
			Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
			// query on the model after inference
			String queryString = " PREFIX ns: <http://www.enib-erasmus.fr/Semantic/ThomasDupuis/assignment_esports.rdf-xml.owl#> "
					+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
					+ "SELECT ?Website "
					+ "WHERE{ "
					+ "	   ?Website rdf:type ns:Website ."
					+ "}";
			//System.out.println(JenaEngine.executeQuery(inferedModel,queryString));
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, inferedModel);
			ResultSet results = qexec.execSelect() ;
			while (results.hasNext())
			{
				QuerySolution soln = results.nextSolution() ;
				System.out.println(soln);
				RDFNode x = soln.get("Website") ;
				//RDFNode y = soln.get("duration") ;

				JSONObject obj = new JSONObject();
				obj.put("Website" ,x.toString().split("#")[1]);
				//obj.put("duration" ,y.toString());
				list.add(obj);
			}
		} else {
			System.out.println("Error when reading model from ontology");
		}
		System.out.println("end : "+list);
		return list;
	}
	//
	@RequestMapping(value = "/Lans",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> retrieveLans() {
		List<JSONObject> list=new ArrayList();
		String NS = "";
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/webs.owl");

		if (model != null) {
			//lire le Namespace de l’ontologie
			NS = model.getNsPrefixURI("");

			// apply our rules on the owlInferencedModel
			Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
			// query on the model after inference
			String queryString = "PREFIX ns: <http://www.enib-erasmus.fr/Semantic/ThomasDupuis/assignment_esports.rdf-xml.owl#> "
					+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
					+ "SELECT ?LanParty ?ThingName "
					+ "WHERE{ "
					+ "	   ?LanParty rdf:type ns:LanParty ."

					+ "} ";

			//System.out.println(JenaEngine.executeQuery(inferedModel,queryString));
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.create(query, inferedModel);
			ResultSet results = qexec.execSelect() ;
			while (results.hasNext())
			{
				QuerySolution soln = results.nextSolution() ;
				System.out.println(soln);
				RDFNode x = soln.get("LanParty") ;
				//RDFNode y = soln.get("duration") ;

				JSONObject obj = new JSONObject();
				obj.put("LanParty" ,x.toString().split("#")[1]);
				//obj.put("duration" ,y.toString());
				list.add(obj);
			}
		} else {
			System.out.println("Error when reading model from ontology");
		}
		System.out.println("end : "+list);
		return list;
	}

}
