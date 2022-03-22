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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Alumno save(Alumno alumno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Alumno update(Alumno alumno, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

}
