package it.polito.tdp.seriea.model;

public class TeamPeso implements Comparable<TeamPeso>{

	private Team team;
	private int peso;
	/**
	 * @param team
	 * @param peso
	 */
	public TeamPeso(Team team, int peso) {
		super();
		this.team = team;
		this.peso = peso;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeamPeso other = (TeamPeso) obj;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "team= " + team + ", peso=" + peso;
	}
	@Override
	public int compareTo(TeamPeso o) {
		return -(peso-o.getPeso());
	}
	
	
	
	
	
}
