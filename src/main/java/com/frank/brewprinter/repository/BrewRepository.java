package com.frank.brewprinter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frank.brewprinter.model.Brew;

public interface BrewRepository extends JpaRepository<Brew, Integer> {

}
