package edu.unisys.academy.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table (name = "cursos")
public class Curso {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	/**
	 * fetch -- ¿Cuándo se hace la Select de los datos relacionados? ¿de los alumnos del curso?
	 * 		CADA VEZ QUE LEO UN CURSO, SE CARGAN TODOS LOS ALUMNOS (DE LA OTRA TABLA) - EAGER
	 * 		CUANDO LEO UN CURSO, NO SE CARGAN LOS ALUMNOS HASTA QUE NO SE ACCEDE
	 * 		A LA LISTA -- LAZY
	 */
	
	//TODO POR VER
	//importante:
		//asignar ALumnos a un curso
		//eliminar Alumnos a un curso
	
	
	@JsonIgnoreProperties(value = {"curso"}) //CUANDO LEAS LOS ALUMNOS DE UN CURSO, DE ESOS ALUMNOS, IGNORA EL ATRIBUTO CURSO. y así evitamos el bucle
	@OneToMany (fetch = FetchType.LAZY, mappedBy = "curso")//CON mappedBy indico qué atributo de la Entidad Alumno, "apunta" a la entidad curso
	private List<Alumno> alumnos;
	
	
	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	@Column(name = "creado_en")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creadoEn;
	
	@PrePersist
	public void prePersist ()
	{
		this.creadoEn = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(Date creadoEn) {
		this.creadoEn = creadoEn;
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nombre=" + nombre + ", creadoEn=" + creadoEn + "]";
	}

	public Curso(Long id, String nombre, Date creadoEn) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.creadoEn = creadoEn;
	}
	
	public Curso() {
		// TODO Auto-generated constructor stub
	}
}
