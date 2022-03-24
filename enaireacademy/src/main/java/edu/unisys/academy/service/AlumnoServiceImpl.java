package edu.unisys.academy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unisys.academy.model.Alumno;
import edu.unisys.academy.repository.AlumnoRepositoryHibernateJPA;
import edu.unisys.academy.repository.AlumnoRepositorySpringData;


@Service
public class AlumnoServiceImpl implements AlumnoService {

	/**
	 * A LA HORA DE ACCEDER A LA BASE DE DATOS (AUN USANDO HIBERNATE), 
	 *  TENGO DOS FORMAS:
	 *  
	 *  	-"ANTIGUA" - Hibnernate Contexto de Persistencia - ENTITY MANAGER 
	 *  
	 *  	-"MODERNA" - Spring DATA JPA AlumnoRepositorySpringData
	 */
	
	@Autowired
	private AlumnoRepositorySpringData alumnoRepositorySpringData;
	
	@Autowired
	private AlumnoRepositoryHibernateJPA alumnoRepositoryHibernateJPA;
	
	//EJEMPLO DE LA COMPRA DE UN BILLETE DE AVE
	/*
	 * @Transactional
	 * COMPRARBILLETE (FECHA, CLIENTE, DATOS DE PAGO, INFO_COMPRA)
	 * {
	 * 
	 * 	MARCAR LOS ASIENTOS COMO NO DISPONIBLES
	 *  EFECTUAR PAGO (PAYPAL, BANCO)
	 *  ACTUALIZAR LA CONTABILIDAD
	 * 
	 * }
	 */
	
	/**
	 * ********************************************
	 * INICIO DE ACCESO A BASE DATOS CON SPRINGDATA
	 * ********************************************
	 */
	
	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> findAll() {
		
		return this.alumnoRepositorySpringData.findAll();
	}

	@Override
	@Transactional (readOnly = true)
	public Optional<Alumno> findById(Long id) {
		
		return this.alumnoRepositorySpringData.findById(id);
	}

	@Override
	@Transactional
	public Alumno save(Alumno alumno) {
		
		return this.alumnoRepositorySpringData.save(alumno);
	}

	@Override
	@Transactional
	public Alumno update(Alumno alumno, Long id) {
		Alumno alumno_actualizado = null;
		Alumno alumno_leido = null;
		Optional<Alumno> oa = null;
		
			//1 LEER EL ALUMO
			oa = this.alumnoRepositorySpringData.findById(id);
			if (oa.isPresent())
			{
				alumno_leido = oa.get();
				//2 MODIFICAR LOS ATRIBUTOS QUE DESEE
				alumno_leido.setApellido(alumno.getApellido());
				alumno_leido.setNombre(alumno.getNombre());
				alumno_leido.setEmail(alumno.getEmail());
				alumno_leido.setEdad(alumno.getEdad());
				
				//3 SAVE SOBRE EL ALUMNO MODIFICADO
				alumno_actualizado = this.alumnoRepositorySpringData.save(alumno_leido);
				
			}
			
		
		return alumno_actualizado;
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.alumnoRepositorySpringData.deleteById(id);
		
	}

	//CONSULTAS NO CRUD
	
	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> findByEdadBetween(int edad_inicial, int edad_final) {
		return this.alumnoRepositorySpringData.findByEdadBetween(edad_inicial, edad_final);
	}
	
	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> findByNombreLike (String patron) {
		return this.alumnoRepositorySpringData.findByNombreLike(patron);
		//return this.alumnoRepositorySpringData.findByNombre(patron);
	}
	
	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> busquedaPorNombreOApellido (String patron)
	{
		return this.alumnoRepositorySpringData.busquedaPorNombreOApellido(patron);
	}
	
	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> busquedaPorNombreOApellidoNativa (String patron)
	{
		return this.alumnoRepositorySpringData.busquedaPorNombreOApellidoNativa(patron);
	}
	
	@Override
	@Transactional (readOnly = true)
	public Page<Alumno> findAll(Pageable pageable) {
		// TODO Terminar de hacer la llamada para leer por bloques (PagingAndSorting Repository)
		return null;
	}
	
	/**
	 * ********************************************
	 * FIN DE ACCESO A BASE DATOS CON SPRINGDATA
	 * ********************************************
	 */
	
	
	/**
	 * **********************************************
	 * INICIO DE ACCESO A BASE DATOS CON HIBERNATE JPA
	 * **********************************************
	 */
	
	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> findAllHbJpa() {
		
		return this.alumnoRepositoryHibernateJPA.leerTodosLosAlumnos();
	}

	@Override
	@Transactional (readOnly = true)
	public Alumno findByIdHbJpa(Long id) {
		
		return this.alumnoRepositoryHibernateJPA.leerAlumnoPorId(id);
	}

	@Override
	@Transactional
	public Alumno saveHbJpa(Alumno alumno) {
		
		return this.alumnoRepositoryHibernateJPA.crearAlumno(alumno);
	}

	@Override
	@Transactional
	public Alumno updateHbJpa(Alumno alumno, Long id) {
		Alumno alumno_actualizado = null;
		Alumno alumno_leido = null;
		
			//1 LEER EL ALUMO
			alumno_leido = this.alumnoRepositoryHibernateJPA.leerAlumnoPorId(id);
			if (alumno_leido!=null)
			{
				//alumno_leido = oa.get();
				//2 MODIFICAR LOS ATRIBUTOS QUE DESEE
				alumno_leido.setApellido(alumno.getApellido());
				alumno_leido.setNombre(alumno.getNombre());
				alumno_leido.setEmail(alumno.getEmail());
				alumno_leido.setEdad(alumno.getEdad());
				
				//3 SAVE SOBRE EL ALUMNO MODIFICADO
				alumno_actualizado = this.alumnoRepositoryHibernateJPA.actualizarAlumno(alumno_leido);
				
			}
			
		
		return alumno_actualizado;
	}

	@Override
	@Transactional
	public void deleteByIdHbJpa(Long id) {
		
		this.alumnoRepositoryHibernateJPA.borrarAlumno(id);
		
	}

	

	
	
	/**
	 * ********************************************
	 * FIN DE ACCESO A BASE DATOS CON HIBERNATEJPA
	 * ********************************************
	 */
	
}
