package com.emsi.scientist_manage.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.emsi.scientist_manage.dao.ScientistDao;
import com.emsi.scientist_manage.entities.Scientist;

public class ScientistDaoImpl implements ScientistDao {

	private Connection conn= DB.getConnection();

	@Override
	public void insert(Scientist scientist) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(
					"INSERT INTO scientist (Name,FieldOfStudy, YearsOfExperience, ResearchInterests, Publications, AwardsAndHonors) " +
							"VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, scientist.getName());
			ps.setString(2, scientist.getFieldOfStudy());
			ps.setInt(3, scientist.getYearsOfExperience());
			ps.setString(4, scientist.getResearchInterests());
			ps.setInt(5, scientist.getPublications());
			ps.setInt(6, scientist.getAwardsAndHonors());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);

					scientist.setId(id);
				}

				DB.closeResultSet(rs);
			} else {
				System.out.println("Aucune ligne renvoyée");
			}
		} catch (SQLException e) {
			System.err.println("problème d'insertion d'un scientist");
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override

	public void update(Scientist scientist) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("UPDATE scientist SET Name = ?, FieldOfStudy = ?, YearsOfExperience = ?, ResearchInterests = ?, Publications = ?, AwardsAndHonors = ? WHERE Id = ?");

			ps.setString(1, scientist.getName());
			ps.setString(2, scientist.getFieldOfStudy());
			ps.setInt(3, scientist.getYearsOfExperience());
			ps.setString(4, scientist.getResearchInterests());
			ps.setInt(5, scientist.getPublications());
			ps.setInt(6, scientist.getAwardsAndHonors());
			ps.setInt(7, scientist.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("problème de mise à jour d'un scientist");
		} finally {
			DB.closeStatement(ps);
		}
	}


	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("DELETE FROM scientist WHERE id = ?");

			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("problème de suppression d'un scientist");;
		} finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public Scientist findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM scientist WHERE id = ?");

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				Scientist scientist = new Scientist();

				scientist.setId(rs.getInt("Id"));
				scientist.setName(rs.getString("Name"));
				scientist.setFieldOfStudy(rs.getString("FieldOfStudy"));
				scientist.setYearsOfExperience(rs.getInt("YearsOfExperience"));
				scientist.setPublications(rs.getInt("Publications"));
				scientist.setResearchInterests(rs.getString("ResearchInterests"));
				scientist.setAwardsAndHonors(rs.getInt("AwardsAndHonors"));

				return scientist;
			}

			return null;
		} catch (SQLException e) {
			System.err.println("problème de requête pour trouver un scientist");;
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}

	}

	@Override
	public List<Scientist> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM scientist");
			rs = ps.executeQuery();

			List<Scientist> listScientist = new ArrayList<>();

			while (rs.next()) {
				Scientist scientist = new Scientist();

				scientist.setId(rs.getInt("Id"));
				scientist.setName(rs.getString("Name"));
				scientist.setFieldOfStudy(rs.getString("FieldOfStudy"));
				scientist.setYearsOfExperience(rs.getInt("YearsOfExperience"));
				scientist.setPublications(rs.getInt("Publications"));
				scientist.setResearchInterests(rs.getString("ResearchInterests"));
				scientist.setAwardsAndHonors(rs.getInt("AwardsAndHonors"));

				listScientist.add(scientist);
			}

			return listScientist;
		} catch (SQLException e) {
			System.err.println("problème de requête pour sélectionner un scientist");;
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}

	}

}
