package edu.unisys.academy.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import edu.unisys.academy.model.Alumno;

@Repository
public class AlumnoRepositoryHibernateJPAImpl implements AlumnoRepositoryHibernateJPA {
	
	@PersistenceContext
	EntityManager entityManager;

	/**
	 * 
JPA

find (class, pk) //leo
merge (entity) //actualizo
persist (entity) //inserto
remove (entity) //elimino
	 */
	
	@Override
	public Alumno leerAlumnoPorId(Long id) {
		// TODO Auto-generated method stub
		
		return this.entityManager.find(Alumno.class, id);
		
	}

	@Override
	public Alumno crearAlumno(Alumno a) {
		// TODO Auto-generated method stub
		this.entityManager.persist(a);
		this.entityManager.flush();//fuerzo/sincronizo el Contexto de Persistencia con la base datos "COMMIT"
		return a;//con esa op consigo que el alumno a tenga su id
	}

	@Override
	public List<Alumno> leerTodosLosAlumnos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alumno actualizarAlumno(Alumno a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void borrarAlumno(Long id) {
		// TODO Auto-generated method stub
		
	}

}
