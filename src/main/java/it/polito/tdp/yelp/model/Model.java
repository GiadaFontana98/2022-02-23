package it.polito.tdp.yelp.model;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;


import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	YelpDao dao;
	Graph<Review, DefaultWeightedEdge> grafo;
	Map<String,Review>idMap;
	
	
	public Model()
	{
		dao= new YelpDao();
		idMap= new HashMap<>();
	}
	
	public List<String>getCity()
	{
		return dao.getAllCity();
	}
   
	public List<Business>getLocali(String city)
	{
		return dao.getAllBusiness(city);
	
	}
	public List<Review>getVertici(String city, Business b)
	{
		return dao.getAllReviews(city, b);
	}
	public List<Adiacenza>getArchi(Business b , Map<String,Review>idMap)
	{
		return dao.getAllReviews(b,idMap);
	}
	
	
	public String creaGrafo(String city, Business b)
	{
		grafo= new SimpleDirectedWeightedGraph<Review, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getAllReviews(city, b));
		
		for(Review r : dao.getAllReviews(city, b))
		{
			idMap.put(r.getReviewId(), r);
		}
		 
	        for(Adiacenza a : dao.getAllReviews(b,idMap))
	        {
	        	if(a.getData1().isBefore(a.getData2()))
	        	{
	        		int peso = (int) ChronoUnit.DAYS.between(a.getData1(),a.getData2());
	        		if(peso>0)
	        		{
	        			Graphs.addEdgeWithVertices(grafo,a.getD1(),a.getD2(),peso);
	        		}
	        	}
	        }
		
		return "Grafo creato\n#VERTICI: " + grafo.vertexSet().size() + "\n# ARCHI: " + grafo.edgeSet().size();
	}
}
