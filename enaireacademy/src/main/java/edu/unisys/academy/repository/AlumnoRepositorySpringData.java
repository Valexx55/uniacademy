package edu.unisys.academy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import edu.unisys.academy.model.Alumno;

//public interface AlumnoRepositorySpringData extends CrudRepository<Alumno, Long>{
public interface AlumnoRepositorySpringData extends PagingAndSortingRepository<Alumno, Long>{
	
	//KEYWORD Queries
		//1-. obtener un listado de alumnos que estén en un rango de edad
		public Iterable<Alumno> findByEdadBetween (int edad_inicial, int edad_final);
		//2-. obtener un listado de alumnos cuyo nombre contenga un patrón
						      //findByFirstnameLike
		public Iterable<Alumno> findByNombreLike (String patron); //TODO revisar el like
	
	//JPQL / NamedQueries HQL -este lenguaje no es exclusivo de Spring- es también del estándar JPA
		//1.- obtener un listado de alumnos cuyo nombre o apellido coincida con un patrón
		@Query("select a from Alumno a where a.nombre like %?1% or a.apellido like %?1% ")
		public Iterable<Alumno> busquedaPorNombreOApellido (String patron); //TODO revisar el like
		
	//Nativas
		//1.- VERSIÓN NATIVA obtener un listado de alumnos cuyo nombre o apellido coincida con un patrón
		@Query(value = "select * from alumnos a where a.nombre like %?1% or a.apellido like %?1% ", nativeQuery = true)
		public Iterable<Alumno> busquedaPorNombreOApellidoNativa (String patron); //TODO revisar el like
	
	
	//PROCEDIMIENTOS ALMACENADOS
	//@NamedQuery
	//API Criteria

}
