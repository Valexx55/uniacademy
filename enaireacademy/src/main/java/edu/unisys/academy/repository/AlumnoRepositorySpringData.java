package edu.unisys.academy.repository;

import org.springframework.data.repository.CrudRepository;

import edu.unisys.academy.model.Alumno;

public interface AlumnoRepositorySpringData extends CrudRepository<Alumno, Long>{

}
