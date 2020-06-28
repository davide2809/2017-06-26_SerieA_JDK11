package it.polito.tdp.seriea.model;

import java.time.LocalDate;

public class Partita implements Comparable<Partita>{
//	match_id,DATE,HomeTeam,AwayTeam,FTHG,FTAG
	private int id;
	private LocalDate date;
	private Team ht;
	private Team at;
	private int FTHG;
	private int FTAG;
	private int res;
	/**
	 * @param id
	 * @param date
	 * @param ht
	 * @param at
	 * @param fTHG
	 * @param fTAG
	 * @param res
	 */
	public Partita(int id, LocalDate date, Team ht, Team at, int fTHG, int fTAG) {
		super();
		this.id = id;
		this.date = date;
		this.ht = ht;
		this.at = at;
		FTHG = fTHG;
		FTAG = fTAG;
		res=0;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Team getHt() {
		return ht;
	}
	public void setHt(Team ht) {
		this.ht = ht;
	}
	public Team getAt() {
		return at;
	}
	public void setAt(Team at) {
		this.at = at;
	}
	public int getFTHG() {
		return FTHG;
	}
	public void setFTHG(int fTHG) {
		FTHG = fTHG;
	}
	public int getFTAG() {
		return FTAG;
	}
	public void setFTAG(int fTAG) {
		FTAG = fTAG;
	}
	public int getRes() {
		return res;
	}
	public void setRes(int res) {
		this.res = res;
	}
	@Override
	public int compareTo(Partita o) {
		return this.date.compareTo(o.getDate());
	}
	
	
	
	
	
	

}
