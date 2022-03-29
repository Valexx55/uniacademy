package edu.unisys.academy.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import edu.unisys.academy.model.Alumno;

//public interface AlumnoRepositorySpringData extends CrudRepository<Alumno, Long>{
//PagingAndSortingRepository hereda de CrudRepository, de tal manera que añade
//la posibilidad de consultar la base de datos por páginas (bloques, trozos)
//de un tamaño parametrizable
//PENDIENTE este otro método Iterable<T> findAll(Sort sort) obtener los registros por un orden dado
public interface AlumnoRepositorySpringData extends PagingAndSortingRepository<Alumno, Long>{
	
	//KEYWORD Queries
		//1-. obtener un listado de alumnos que estén en un rango de edad
		public Iterable<Alumno> findByEdadBetween (int edad_inicial, int edad_final);
		//2-. obtener un listado de alumnos cuyo nombre contenga un patrón
						   
		//hay que componer el patron en la capa de servicio (concatenando el caracter comodín
		//manualmente para obtener el resultado esperado %)
		public Iterable<Alumno> findByNombreLike (String patron); 
		
		//esta búsqueda es literal si coincide o no con el nombre se recupera
		public Iterable<Alumno> findByNombre (String nombre); 
	
	//JPQL / NamedQueries HQL -este lenguaje no es exclusivo de Spring- es también del estándar JPA
		//1.- obtener un listado de alumnos cuyo nombre o apellido coincida con un patrón
		@Query("select a from Alumno a where a.nombre like %?1% or a.apellido like %?1% ")
		public Iterable<Alumno> busquedaPorNombreOApellido (String patron); //TODO revisar el like
		
	//Nativas
		//1.- VERSIÓN NATIVA obtener un listado de alumnos cuyo nombre o apellido coincida con un patrón
		@Query(value = "select * from alumnos a where a.nombre like %?1% or a.apellido like %?1% ", nativeQuery = true)
		public Iterable<Alumno> busquedaPorNombreOApellidoNativa (String patron); //TODO revisar el like
	
	
		//@NamedQuery
		
		//para referirnos a una NamedQuery desde Spring, sólo necesito un método que se llame igual
		public Iterable<Alumno> mayoresDe50();
		
		//PROCEDIMIENTOS ALMACENADOS
		
		@Procedure(name = "Alumno.alumnoRegistradosHoy")
		public Iterable<Alumno> procedimientoAlumnosAltaHoy();
		
		@Procedure(name = "Alumno.alumnosEdadMediaMinMax")
		public Map<String, Object> procedimientoAlumnosEstadisticasEdad();
		
		@Procedure(name = "Alumno.alumnosNombreComo")
		public Iterable<Alumno> procedimientoAlumnosNombreComo(@Param("patron") String patron);
	
		
	
		//API Criteria -- no lo vamos a ver

}
