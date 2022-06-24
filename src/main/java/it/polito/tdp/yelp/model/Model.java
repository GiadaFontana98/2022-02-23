package it.polito.tdp.yelp.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	private List<Review> migliore;
	Map<String,Review>idMapReview;
	YelpDao dao;
	Graph <Review,DefaultWeightedEdge>grafo;
	
	public Model()
	{
		dao = new YelpDao();

		idMapReview = new HashMap<>();
	}
	
	public List<String>getCitta()
	{
		return dao.getAllCity();
	}
	
	public List<Business>getBusiness(String c)
	{
		return dao.getAllBusiness(c);
	}
	
	public String creaGrafo(Business b)
	{
        grafo = new SimpleDirectedWeightedGraph<Review,DefaultWeightedEdge>(DefaultWeightedEdge.class);
        Graphs.addAllVertices(grafo, dao.getAllReviews(b,idMapReview));
        List<Adiacenza>adiacenza = dao.getAllArchi(b);
        for(Adiacenza a : adiacenza)
        {
        	if(a.getD1().isBefore(a.getD2()))
        	{
        		int peso = (int) ChronoUnit.DAYS.between(a.getD1(),a.getD2());
        		if(peso>0)
        		{
        			Graphs.addEdgeWithVertices(grafo,idMapReview.get(a.getR1()),idMapReview.get(a.getR2()),peso);
        		}
        	}
        }
        
        
        return "#Vertici = " + grafo.vertexSet().size() + "#Archi =" + grafo.edgeSet().size();
	}
	public List<String>getUscenti()
	{
		List<String>uscentiMaxlista= new ArrayList<>();
		int uscentiMax=0;
		
		for(Review r : grafo.vertexSet())
		{
			int uscenti = Graphs.successorListOf(grafo, r).size();
			if(uscenti>uscentiMax)
			{
				uscentiMax=uscenti;
				
			}
			
		}
		for(Review r : grafo.vertexSet())
		{
			int uscenti = Graphs.successorListOf(grafo, r).size();
			if(uscenti==uscentiMax)
			{
				uscentiMaxlista.add(r.getReviewId()+"USCENTI MAX"+ uscentiMax);
				
			}
		
	}
		return uscentiMaxlista;	
}
	public List<Review> calcolaPercorso()
	  {
	    migliore = new LinkedList<Review>();
	    List<Review> parziale = new LinkedList<>();
	    cercaRicorsiva(parziale);
	    return migliore;
	  }

	  private void cercaRicorsiva(List<Review> parziale) {
	     
	        //condizione di terminazione
	     
	        if(parziale.size()> migliore.size())//la strada piú lunga é la migliore
	        {
	          migliore = new LinkedList<>(parziale);
	        }

	        for(Review r1: grafo.vertexSet())
	        {
	        	parziale.add(r1);
	        
	        for(Review r : Graphs.successorListOf(this.grafo, parziale.get(parziale.size()-1))) //scorro sui vicini dell'ultimo nodo sulla lista
	        {
	          if(!parziale.contains(r))
	          {
	        	  if(r.getStars()>= parziale.get(parziale.size()-1).getStars())
	        	  {
	            parziale.add(r);
	            cercaRicorsiva(parziale);
	            parziale.remove(parziale.size()-1);
	          }
	        
	     }
	        }
	   }
	  }

	  private int pesoTot(List<Review> parziale) {
	    
	    int peso = 0;
	    for(int i = 0; i<parziale.size()-1; i++)
	    {
	      DefaultWeightedEdge e = grafo.getEdge(parziale.get(i), parziale.get(i+1));
	      peso += grafo.getEdgeWeight(e);
	    }
	    System.out.println(peso);
	    return peso;
	  }

	
}
