package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import it.polito.tdp.seriea.db.SerieADAO;

public class Simulator {
	
	private List<Partita> listaMatch;
	private PriorityQueue<Partita> queue;
	private Map<Team,Integer> mappaTifosi;
	private int fattore;
	private Map<Team,Integer> mappaPunti;
	
	
	public Simulator() {
		listaMatch=new ArrayList<>();
	}
	
	public void init(int SeasonId,SerieADAO dao) {
		queue=new PriorityQueue<Partita>();
		listaMatch=new ArrayList<>(dao.listaMatch(SeasonId));
		fattore=10;
		mappaTifosi=new HashMap<>();
		mappaPunti=new HashMap<>();
		
		for(Partita p:listaMatch) {
			Team t=p.getHt();
//			Impreciso
			mappaTifosi.put(t, 1000);
			mappaPunti.put(t,0);
		}
//		La coda
		for(Partita p:listaMatch) {
			this.queue.add(p);
		}
	}
	
	
	public void run() {
		while(!queue.isEmpty()) {
			Partita partita=queue.poll();
			this.processEvent(partita);
		}
		this.calcoloPunti();
		
	}

	private void calcoloPunti() {
		for(Partita p:listaMatch) {
			int puntiCasa=mappaPunti.get(p.getHt());
			int puntiTrasferta=mappaPunti.get(p.getAt());
			if(p.getRes()==0) {
				puntiCasa++;
				puntiTrasferta++;
				
			}else {
				if(p.getRes()==1) {
					puntiCasa+=3;
				} else {
					puntiTrasferta+=3;
				}
			}
			mappaPunti.remove(p.getHt());
			mappaPunti.remove(p.getAt());
			mappaPunti.put(p.getHt(), puntiCasa);
			mappaPunti.put(p.getAt(), puntiTrasferta);
		}
		
	}

	private void processEvent(Partita partita) {
		int goalCasa=partita.getFTHG();
		int goalTrasferta=partita.getFTAG();
		
		int TA=mappaTifosi.get(partita.getHt());
		int TB=mappaTifosi.get(partita.getAt());
		
		if(TA<TB) {
			double prob=(1-TA/TB);
			if(Math.random()<=prob && goalCasa>0) {
				goalCasa--;
				partita.setFTHG(goalCasa);			}
		} else {
			if(TB>TA) {
				double prob=(1-TB/TA);
				if(Math.random()<=prob && goalTrasferta>0) {
					goalTrasferta--;
					partita.setFTAG(goalTrasferta);
				}
			}
		}
		if(goalCasa>goalTrasferta) {
			partita.setRes(1);
			int scarto=goalCasa-goalTrasferta;
			double tifPerc=scarto*this.fattore;
			int nTifosi=(int) ((tifPerc*TB)/100);
			mappaTifosi.remove(partita.getHt());
			mappaTifosi.remove(partita.getAt());
			mappaTifosi.put(partita.getHt(),TA+nTifosi);
			mappaTifosi.put(partita.getAt(),TB-nTifosi);
		} else {
			if(goalCasa<goalTrasferta) {
				partita.setRes(2);
				int scarto=-(goalCasa-goalTrasferta);
				double tifPerc=scarto*this.fattore;
				int nTifosi=(int) ((tifPerc*TA)/100);
				mappaTifosi.remove(partita.getHt());
				mappaTifosi.remove(partita.getAt());
				mappaTifosi.put(partita.getHt(),TA-nTifosi);
				mappaTifosi.put(partita.getAt(),TB+nTifosi);
			}
		}
		
		
	}

	public Map<Team, Integer> getMappaTifosi() {
		return mappaTifosi;
	}
	public Map<Team, Integer> getMappaPunti() {
		return mappaPunti;
	}
	
	

}
