package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;


public class Model {
	
	private Graph<Team,DefaultWeightedEdge> grafo;
	private SerieADAO dao;
	private List<Team> listaVertici;
	private Simulator sim;
	
	public Model() {
		dao=new SerieADAO();
	}
	
	
	public List<Team> getListaVertici() {
		listaVertici=new ArrayList<Team>(dao.listaVertici());
		return listaVertici;
	}


	public void creaGrafo() {
		listaVertici=new ArrayList<Team>(dao.listaVertici());
		grafo=new SimpleWeightedGraph<Team,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<Adiacenza> listaArchi=new ArrayList<Adiacenza>(dao.listaArchi());
		
//		AGGIUNGO VERTICI
		Graphs.addAllVertices(grafo, listaVertici);
		
//		AGGIUNGO ARCHI
		for(Adiacenza a:listaArchi) {
			DefaultWeightedEdge e=grafo.getEdge(a.getT1(), a.getT2());
			if( !grafo.containsEdge(e) ) {
				Graphs.addEdge(grafo, a.getT1(), a.getT2(), a.getPeso());
			} else {
				int peso=(int) grafo.getEdgeWeight(e);
				int newPeso=(peso+a.getPeso());
				grafo.removeEdge(e);
				Graphs.addEdge(grafo, a.getT1(), a.getT2(), newPeso);
			}
		}
	}
	
	public List<TeamPeso> connessioniSquadra(Team team) {
		List<TeamPeso> result=new ArrayList<TeamPeso>();
		List<Team> lista=Graphs.neighborListOf(grafo, team);
		for(Team p:lista) {
			DefaultWeightedEdge edge=grafo.getEdge(team, p);
			int diff=(int) grafo.getEdgeWeight(edge);
			result.add(new TeamPeso(p,diff));
		}
		Collections.sort(result);
		return result;
	}
	
	public String simula(int SeasonId) {
		sim=new Simulator();
		sim.init(SeasonId, this.dao);
		sim.run();
		Map<Team,Integer> mappaTifosi=sim.getMappaTifosi();
		Map<Team,Integer> mappaPunti=sim.getMappaPunti();
		String s=new String();
		for(Team t:mappaTifosi.keySet()) {
			s+="Team= "+t+" Numero Punti= "+mappaPunti.get(t)+" Numero Tifosi= "+mappaTifosi.get(t)+"\n";
		}
		return s;
	}
	
	public List<Season> listaStagioni() {
		return this.dao.listSeasons();
	}

}
