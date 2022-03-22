package edu.unisys.academy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;//USANDO JPA
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity //con esta anotación, le digo a Hibernate, que esto es una clase de java asociada a una tabla
@Table (name = "alumnos")//este es el nombre que tiene o tendrá la tabla en la base de datos
public class Alumno {
	
	
	@Id//clave primaria sea un numero
	@GeneratedValue(strategy = GenerationType.IDENTITY) //USE EL AUTOINCREMENTO DE MYSQL
	private Long id;
	
	//NO ES NECESARIO ANOTAR ESTOS ATRIBUTOS COMO COLUMNAS
	//LO VA A GENERERA AUTOMÁTICAMENTE
	
	private String nombre;
	private String apellido;
	private String email;
	private int edad;
	
	//Quiero saber cuándo se inserta un registro
	
	@Column(name = "creado_en")//puedo especificar un nombre de columna para este atributo con COLUMN
	@Temporal (TemporalType.TIMESTAMP)//quiero que la fecha se guarde en FECHA HH:MM:SS:MSS
	private Date creadoEn;
	
	@PrePersist//EL MÉTODO QUE ESTÉ ANOTADO ASÍ, SE EJECUTA AUTOMÁTICAMENTE ANTES DE GUARDAR EL OBJETO EN LA BD
	private void generarFecha ()
	{
		//obtengo la fecha actual
		this.creadoEn = new Date();
	}
	
	
	public Date getCreadoEn() {
		return creadoEn;
	}
	public void setCreadoEn(Date creadoEn) {
		this.creadoEn = creadoEn;
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}


	@Override
	public String toString() {
		return "Alumno [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", edad="
				+ edad + ", creadoEn=" + creadoEn + "]";
	}


	public Alumno(Long id, String nombre, String apellido, String email, int edad, Date creadoEn) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.edad = edad;
		this.creadoEn = creadoEn;
	}


	public Alumno() {
		super();
	}
	
	
	
	

}
