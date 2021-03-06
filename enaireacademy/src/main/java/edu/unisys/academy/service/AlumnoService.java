package edu.unisys.academy.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import edu.unisys.academy.model.Alumno;

//describimos la operaciones que se pueden realizar con el alumno


public interface AlumnoService {
	
	public Iterable<Alumno> findAll ();
	
	public Optional<Alumno> findById (Long id);
	
	public Alumno save (Alumno alumno);
	
	public Alumno update (Alumno alumno, Long id);
	
	public void deleteById (Long id);
	
	public Iterable<Alumno> findByEdadBetween (int edad_inicial, int edad_final);
	
	public Iterable<Alumno> findByNombreLike (String patron);
	
	public Iterable<Alumno> findByNombre (String nombre);
	
	public Iterable<Alumno> busquedaPorNombreOApellido (String patron);
	
	public Iterable<Alumno> busquedaPorNombreOApellidoNativa (String patron);
	
	public Page<Alumno> findAll(Pageable pageable);
	
	public Iterable<Alumno> mayoresDe50();
	
	public Iterable<Alumno> procedimientoAlumnosAltaHoy();
	
	public Map<String, Object> procedimientoAlumnosEstadisticasEdad();
	
	public Iterable<Alumno> procedimientoAlumnosNombreComo(@Param("patron") String patron);
	
	////////////////////////// SPRINGDATA
	
	///////////////////////// HIBERNATE JPA 
	
	public Iterable<Alumno> findAllHbJpa ();
	
	public Alumno findByIdHbJpa (Long id);
	
	public Alumno saveHbJpa (Alumno alumno);
	
	public Alumno updateHbJpa (Alumno alumno, Long id);
	
	public void deleteByIdHbJpa (Long id);
	
	public Iterable<Alumno> mayoresDe50HbJpa();
	
	
	

}
