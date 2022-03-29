package edu.unisys.academy.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	
	/**
	 * ********************************************
	 * INICIO DE ACCESO A BASE DATOS CON SPRINGDATA
	 * ********************************************
	 */
	
	@GetMapping //GET http://localhost:8081/alumno
	public ResponseEntity<?> leerTodos () //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> lista_alumnos = null;
		
		
			lista_alumnos = this.alumnoService.findAll();
			responseEntity = ResponseEntity.ok(lista_alumnos);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
			
			
		
		return responseEntity;
	}
	
	@GetMapping("/mayoresDe50") //GET http://localhost:8081/alumno
	public ResponseEntity<?> obtenerAlumnosMayoresDe50 () //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> lista_alumnos_masde50 = null;
		
		
			lista_alumnos_masde50 = this.alumnoService.mayoresDe50();
			responseEntity = ResponseEntity.ok(lista_alumnos_masde50);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
			
		
		return responseEntity;
	}
	
	@GetMapping("/buscarporrangoedad/{edad1}/{edad2}") //GET http://localhost:8081/alumno/6/90
	public ResponseEntity<?> obtenerAlumnosPorRangoDeEdad (@PathVariable int edad1, @PathVariable int edad2 ) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> lista_alumnos = null;
		
			lista_alumnos = this.alumnoService.findByEdadBetween(edad1, edad2);
			responseEntity = ResponseEntity.ok(lista_alumnos);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
		
		return responseEntity;
	}
	
	@GetMapping("/buscarpornombrelike/{patron}") //GET http://localhost:8081/alumno/Pepa
	public ResponseEntity<?> obtenerAlumnosPorNombreLike (@PathVariable String patron ) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> lista_alumnos = null;
		
			lista_alumnos = this.alumnoService.findByNombreLike(patron);
			responseEntity = ResponseEntity.ok(lista_alumnos);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
		
		return responseEntity;
	}
	
	@GetMapping("/buscarpornombre/{nombre}") //GET http://localhost:8081/alumno/Pepa
	public ResponseEntity<?> obtenerAlumnosPorNombre (@PathVariable String nombre ) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> lista_alumnos = null;
		
			lista_alumnos = this.alumnoService.findByNombre(nombre);
			responseEntity = ResponseEntity.ok(lista_alumnos);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
		
		return responseEntity;
	}
	
	
	@GetMapping("/buscarpornombreoape/{patron}") //GET http://localhost:8081/alumno/Pepa
	public ResponseEntity<?> buscarAlumnosPorNombreoApe (@PathVariable String patron) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> lista_alumnos = null;
		
			lista_alumnos = this.alumnoService.busquedaPorNombreOApellido(patron);
			responseEntity = ResponseEntity.ok(lista_alumnos);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
		
		return responseEntity;
	}
	
	
	@GetMapping("/buscarpornombreoapenativa/{patron}") //GET http://localhost:8081/alumno/Pepa
	public ResponseEntity<?> buscarAlumnosPorNombreoApeNativa (@PathVariable String patron) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> lista_alumnos = null;
		
			lista_alumnos = this.alumnoService.busquedaPorNombreOApellidoNativa(patron);
			responseEntity = ResponseEntity.ok(lista_alumnos);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
		
		return responseEntity;
	}
	
	
	@GetMapping("/pagina") //GET http://localhost:8081/alumno/pagina?page=0&size=2
	public ResponseEntity<?> listarAlumnosPorPagina (Pageable pageable) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> pagina_alumnos = null;
		
			pagina_alumnos = this.alumnoService.findAll(pageable);
			responseEntity = ResponseEntity.ok(pagina_alumnos);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
		
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
	
	/**
	 * 
	 * @param br la info de los errores detectados
	 * @return un mensaje HTTP con los errores
	 */
	private ResponseEntity<?> obtenerErrores (BindingResult br)
	{
		ResponseEntity<?> responseEntity = null;
		List<ObjectError> lista_errores = null;
		
			lista_errores = br.getAllErrors();
			responseEntity = ResponseEntity.badRequest().body(lista_errores);
			
			lista_errores.forEach(objeto_error -> {
				log.error(objeto_error.toString());
			});
		
		
		return responseEntity;
	}
	
	@PostMapping //POST http://localhost:8081/alumno/
	public ResponseEntity<?> insertarAlumno (@Valid @RequestBody Alumno alumno, BindingResult br) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_creado = null;
		
			//TENGO ERRORES??
			//validarCP (alumno.cp) o en un FILTRO @WEBfilter
			if (br.hasErrors())
			{
				log.error("El alumno trae errores post");
				responseEntity = obtenerErrores(br);
				
			} else {
				
				log.debug ("El alumno pasa la validación");
				alumno_creado = this.alumnoService.save(alumno);
				responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_creado);
				
			}
		
			
		
		return responseEntity;
	}
	
	
	@PostMapping("/crear-con-foto") //POST http://localhost:8081/alumno/
	public ResponseEntity<?> insertarAlumnoConFoto (@Valid Alumno alumno, BindingResult br, MultipartFile archivo) throws IOException //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_creado = null;
		
			//TENGO ERRORES??
			//validarCP (alumno.cp) o en un FILTRO @WEBfilter
			if (br.hasErrors())
			{
				log.error("El alumno trae errores post");
				responseEntity = obtenerErrores(br);
				
			} else {
				//ANTES DE INSERTAR, VOY A INSPECCIONAR EL ARCHIVOS
				if (!archivo.isEmpty())
				{
					//el archivo, trae contenido
					try {
						alumno.setFoto(archivo.getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("error al acceder al contenido de la foto" , e);
						throw e;
					}
				}
				
				log.debug ("El alumno pasa la validación");
				alumno_creado = this.alumnoService.save(alumno);
				responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_creado);
				
			}
		
			
		
		return responseEntity;
	}
	
	@PutMapping("/editar-con-foto/{id}") //POST http://localhost:8081/alumno/
	public ResponseEntity<?> editarAlumnoConFoto (@Valid Alumno alumno, BindingResult br, @PathVariable Long id, MultipartFile archivo) throws IOException //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_modificado = null;
		
			//TENGO ERRORES??
			//validarCP (alumno.cp) o en un FILTRO @WEBfilter
			if (br.hasErrors())
			{
				log.error("El alumno trae errores post");
				responseEntity = obtenerErrores(br);
				
			} else {
				//ANTES DE INSERTAR, VOY A INSPECCIONAR EL ARCHIVOS
				if (!archivo.isEmpty())
				{
					//el archivo, trae contenido
					try {
						alumno.setFoto(archivo.getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("error al acceder al contenido de la foto" , e);
						throw e;
					}
				}
				
				log.debug ("El alumno pasa la validación");
				alumno_modificado = this.alumnoService.update(alumno, id);
				responseEntity = (alumno_modificado != null) ? (ResponseEntity.ok (alumno_modificado)) : (ResponseEntity.notFound().build());
				
				
			}
		
			
		
		return responseEntity;
	}
	
	@GetMapping("/obtenerfoto/{id}") //GET http://localhost:8081/alumno/3
	public ResponseEntity<?> leerFotoAlumnoPorId (@PathVariable Long id) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> opcional_alumno = null;
		Alumno alumno_leido = null;
		Resource foto_alumno = null;
		
			opcional_alumno = this.alumnoService.findById(id);
			if (opcional_alumno.isPresent())
			{
				//ese id existía y por tanto, tenemos un alumno que devolver
				alumno_leido = opcional_alumno.get();
				foto_alumno = new ByteArrayResource (alumno_leido.getFoto());
				responseEntity = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(foto_alumno);
				
			} else  
			{
				//TODO hacer el log
				//ese id existía y por tanto, tenemos un alumno que devolver
				responseEntity = ResponseEntity.noContent().build();
			}
		
		
		return responseEntity;
	}
	
	
	@PutMapping("/{id}") //PUT http://localhost:8081/alumno/id
	public ResponseEntity<?> modificarAlumno (@Valid @RequestBody Alumno alumno,  BindingResult br, @PathVariable Long id) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_actualizado = null;
		
			if (br.hasErrors())
			{
				log.error("El alumno trae errores put");
				responseEntity = obtenerErrores(br);
			}
			else {
				alumno_actualizado = this.alumnoService.update(alumno, id);
				if (alumno_actualizado != null)
				{
					//se ha MODIFICADO correctamente
					responseEntity = ResponseEntity.ok(alumno_actualizado);
				} else {
					responseEntity = ResponseEntity.notFound().build();
				}
				
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
	
	@GetMapping("/procedimientoAltasHoy") //GET http://localhost:8081/alumno/procedimientoAltasHoy
	public ResponseEntity<?> procedimientoAltasHoy () //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> lista_alumnos = null;
		
		
			lista_alumnos = this.alumnoService.procedimientoAlumnosAltaHoy();
			responseEntity = ResponseEntity.ok(lista_alumnos);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
		
		return responseEntity;
	}
	
	//TODO probar estadisticas POR edad
	@GetMapping("/procedimientoEstadisticasEdad") //GET http://localhost:8081/alumno/procedimientoEstadisticasEdad
	public ResponseEntity<?> procedimientoEstadisticasEdad () //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Map<String, Object> mapa_edades = null;
		
		
			mapa_edades = this.alumnoService.procedimientoAlumnosEstadisticasEdad();
			responseEntity = ResponseEntity.ok(mapa_edades);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
		
		return responseEntity;
	}
	
	@GetMapping("/procedimientoBusquedaPorNombre/{patron}") //GET http://localhost:8081/alumno/procedimientoBusquedaPorNombre/{patron}
	public ResponseEntity<?> procedimientoBusquedaPorNombre (@PathVariable String patron) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> lista_alumnos = null;
		
		
			lista_alumnos = this.alumnoService.procedimientoAlumnosNombreComo(patron);
			responseEntity = ResponseEntity.ok(lista_alumnos);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
		
		return responseEntity;
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
	

	@GetMapping("/hbjpa") //GET http://localhost:8081/alumno/hbjpa
	public ResponseEntity<?> leerTodosHbJpa () //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> lista_alumnos = null;
		
		
			lista_alumnos = this.alumnoService.findAllHbJpa();
			responseEntity = ResponseEntity.ok(lista_alumnos);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
			
			
		
		return responseEntity;
	}
	
	@GetMapping("/hbjpa/{id}") //GET http://localhost:8081/alumno//hbjpa/3
	public ResponseEntity<?> leerAlumnoPorIdHbJpa (@PathVariable Long id) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_leido = null;
		
			alumno_leido = this.alumnoService.findByIdHbJpa(id);
			if (alumno_leido!=null)
			{
				//ese id existía y por tanto, tenemos un alumno que devolver
				responseEntity = ResponseEntity.ok(alumno_leido);
				
			} else  
			{
				//TODO hacer el log
				//ese id existía y por tanto, tenemos un alumno que devolver
				responseEntity = ResponseEntity.noContent().build();
			}
		
		
		return responseEntity;
	}
	
	@PostMapping("/hbjpa") //POST http://localhost:8081/alumno/hbjpa
	public ResponseEntity<?> insertarAlumnoHbJpa (@RequestBody Alumno alumno) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_creado = null;
		
			alumno_creado = this.alumnoService.saveHbJpa(alumno);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_creado);
		
		return responseEntity;
	}
	
	@PutMapping("/hbjpa/{id}") //PUT http://localhost:8081/alumno//hbjpa/id
	public ResponseEntity<?> modificarAlumnoHbJpa (@RequestBody Alumno alumno, @PathVariable Long id) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_actualizado = null;
		
			alumno_actualizado = this.alumnoService.updateHbJpa(alumno, id);
			if (alumno_actualizado != null)
			{
				//se ha MODIFICADO correctamente
				responseEntity = ResponseEntity.ok(alumno_actualizado);
			} else {
				responseEntity = ResponseEntity.notFound().build();
			}
		
		return responseEntity;
	}
	
	@DeleteMapping("/hbjpa/{id}") //DELETE http://localhost:8081/alumno/hbjpa/id
	public ResponseEntity<?> borrarAlumnoHbJpa (@PathVariable Long id) //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		
			this.alumnoService.deleteByIdHbJpa(id);
			responseEntity = ResponseEntity.ok().build();
		
		return responseEntity;
	}

	@GetMapping("/hbjpa/mayoresDe50") //GET http://localhost:8081/alumno/hbjpa/mayoresDe50
	public ResponseEntity<?> obtenerAlumnosMayoresDe50HbJpa () //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> lista_alumnos_masde50 = null;
		
		
			lista_alumnos_masde50 = this.alumnoService.mayoresDe50HbJpa();
			responseEntity = ResponseEntity.ok(lista_alumnos_masde50);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
			
		
		return responseEntity;
	}
	
	
	/**
	 * **********************************************
	 * FIN DE ACCESO A BASE DATOS CON HIBERNATE JPA
	 * **********************************************
	 */

}
