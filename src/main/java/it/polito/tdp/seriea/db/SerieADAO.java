package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.polito.tdp.seriea.model.Adiacenza;
import it.polito.tdp.seriea.model.Partita;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {

	public List<Season> listSeasons() {
		String sql = "SELECT season, description FROM seasons";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Team> listaVertici() {
		String sql = "SELECT distinct(team) FROM teams";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Team(res.getString("team")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Adiacenza> listaArchi() {
		String sql = "SELECT t1.team,t2.team, COUNT(m1.match_id) as peso " + 
				"FROM teams AS t1, teams AS t2, matches AS m1, matches AS m2 " + 
				"WHERE (t1.team=m1.HomeTeam) AND (t2.team=m2.AwayTeam) AND m1.match_id=m2.match_id AND t1.team<>t2.team " + 
				"GROUP BY t1.team,t2.team";
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Team t1=new Team(res.getString("t1.team"));
				Team t2=new Team(res.getString("t2.team"));
				int peso=res.getInt("peso");
				result.add(new Adiacenza( t1,t2,peso ));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Partita> listaMatch(int seasonId) {
		String sql="SELECT match_id,DATE,HomeTeam,AwayTeam,FTHG,FTAG " + 
				"FROM matches " + 
				"WHERE Season= ?";
		List<Partita> result=new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, seasonId);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Team t1=new Team(res.getString("HomeTeam"));
				Team t2=new Team(res.getString("AwayTeam"));
				result.add(new Partita( res.getInt("match_id"),res.getDate("DATE").toLocalDate(),t1,t2,res.getInt("FTHG"),res.getInt("FTAG")  ));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}

