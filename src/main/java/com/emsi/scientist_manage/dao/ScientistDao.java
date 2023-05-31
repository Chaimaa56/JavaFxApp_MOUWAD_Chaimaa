package com.emsi.scientist_manage.dao;

import com.emsi.scientist_manage.entities.Scientist;

import java.util.List;


public interface ScientistDao {
	void insert(Scientist scientist);

	void update(Scientist scientist);

	void deleteById(Integer id);

	Scientist findById(Integer id);

	List<Scientist> findAll();

}
