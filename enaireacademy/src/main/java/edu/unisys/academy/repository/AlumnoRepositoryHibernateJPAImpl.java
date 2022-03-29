package edu.unisys.academy.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
		
		List<Alumno> lista_alumnos = null;
		//no existe el findAll en el entityManager
		//tenemos que hacer una consulta "a pelo"
		
		//Query q = entityManager.createQuery("select a from Alumno a");
		//lista_alumnos = (List<Alumno>)q.getResultList();
		
		TypedQuery<Alumno> tq = entityManager.createQuery("select a from Alumno a", Alumno.class);
		lista_alumnos = tq.getResultList();
		
		return lista_alumnos;
	}

	@Override
	public Alumno actualizarAlumno(Alumno a) {
		this.entityManager.merge(a);
		//this.entityManager.flush()//si incluyera una modificación del ID, sería necesario
		
		return a;
	}

	@Override
	public void borrarAlumno(Long id) {
		
		Alumno alumno_a_eliminar = this.leerAlumnoPorId(id);//this.entityManager.find(Alumno.class, id);
		//TODO opcion mejora, comprobar que es !=null, que ese registro exsite
		this.entityManager.remove(alumno_a_eliminar);
		
	}

	@Override
	public Iterable<Alumno> mayoresDe50() {
		Query query = this.entityManager.createNamedQuery("Alumno.mayoresDe50");
		return query.getResultList();
	}

}
