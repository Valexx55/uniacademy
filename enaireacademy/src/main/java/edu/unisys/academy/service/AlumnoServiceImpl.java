package edu.unisys.academy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unisys.academy.model.Alumno;
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

}
