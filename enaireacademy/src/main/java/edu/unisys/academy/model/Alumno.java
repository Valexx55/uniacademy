package edu.unisys.academy.model;

import javax.persistence.Entity;//USANDO JPA
import javax.persistence.Table;

@Entity //con esta anotación, le digo a Hibernate, que esto es una clase de java asociada a una tabla
@Table (name = "alumnos")//este es el nombre que tiene o tendrá la tabla en la base de datos
public class Alumno {
	
	//clave primaria sea un numero
	private Long id;
	
	private String nombre;
	private String apellido;
	private String email;
	private int edad;
	

}
