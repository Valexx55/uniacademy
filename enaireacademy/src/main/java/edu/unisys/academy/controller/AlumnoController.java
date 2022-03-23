package edu.unisys.academy.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.unisys.academy.model.Alumno;
import edu.unisys.academy.service.AlumnoService;

/*
 * RECIBO Y RESPONDO AL USUARIO HTTP
 * - POSTMAN -
 * 
 * API REST SOBRE ALUMNOS	
 * 
 * 		Leer todos los alumnos - GET
 * 		Leer un alumno - GET
 * 		Crear un alumno - POST 
 * 		Modificar un alumno - PUT
 * 		Borrar un alumno - DELETE
 */

@RestController //AL INDICAR ESTA ANOTACIÓN, EL SERVICIO ESPERA MENSAJES CON EL CUERPO JSON Y DEVUELVE MENSAJES CON EL CUERPO JSON AUTOMÁTICAMENTE
@RequestMapping("/alumno")
public class AlumnoController {
	
	//GET http://localhost:8081/alumno/prueba
	
	@Autowired
	private AlumnoService alumnoService;
	
	Logger log = LoggerFactory.getLogger(AlumnoController.class);
	
	@GetMapping("/prueba")
	public Alumno pruebaAlumno ()
	{
		Alumno alumno = null;
		
			log.debug("ENTRANDO EN /prueba debug");
			log.info("ENTRANDO EN /prueba info");
		
			alumno = new Alumno();//en este momento, el alumno está en estado TRANSIENT 
			alumno.setId(15L);//lo que significa que para Hibernate no existe
			alumno.setNombre("JUAN");
			alumno.setApellido("Solis");
			alumno.setEdad(43);
		
		return alumno;
	}
	
	@GetMapping //GET http://localhost:8081/alumno
	public ResponseEntity<?> leerTodos () //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> lista_alumnos = null;
		
		
			lista_alumnos = this.alumnoService.findAll();
			responseEntity = ResponseEntity.ok(lista_alumnos);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
			
			
		
		return responseEntity;
	}
	
	@GetMapping("/{id}") //GET http://localhost:8081/alumno/3
	public ResponseEntity<?> leerAlumnoPorId (@PathVariable Long id) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> opcional_alumno = null;
		Alumno alumno_leido = null;
		
			opcional_alumno = this.alumnoService.findById(id);
			if (opcional_alumno.isPresent())
			{
				//ese id existía y por tanto, tenemos un alumno que devolver
				alumno_leido = opcional_alumno.get();
				responseEntity = ResponseEntity.ok(alumno_leido);
				
			} else  
			{
				//TODO hacer el log
				//ese id existía y por tanto, tenemos un alumno que devolver
				responseEntity = ResponseEntity.noContent().build();
			}
		
		
		return responseEntity;
	}
	
	
	@PostMapping //POST http://localhost:8081/alumno/
	public ResponseEntity<?> insertarAlumno (@RequestBody Alumno alumno) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_creado = null;
		
			alumno_creado = this.alumnoService.save(alumno);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_creado);
		
		return responseEntity;
	}
	
	@PutMapping("/{id}") //PUT http://localhost:8081/alumno/id
	public ResponseEntity<?> modificarAlumno (@RequestBody Alumno alumno, @PathVariable Long id) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_actualizado = null;
		
			alumno_actualizado = this.alumnoService.update(alumno, id);
			if (alumno_actualizado != null)
			{
				//se ha MODIFICADO correctamente
				responseEntity = ResponseEntity.ok(alumno_actualizado);
			} else {
				responseEntity = ResponseEntity.notFound().build();
			}
		
			 
			 
		return responseEntity;
	}
	
	@DeleteMapping("/{id}") //DELETE http://localhost:8081/alumno/id
	public ResponseEntity<?> borrarAlumno (@PathVariable Long id) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		
			this.alumnoService.deleteById(id);
			responseEntity = ResponseEntity.ok().build();
		
		return responseEntity;
	}

}
