package ec.grace.consumows.crud.consumowscrud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.grace.consumows.crud.consumowscrud.entity.Empleado;
import ec.grace.consumows.crud.consumowscrud.entity.Logs;
import ec.grace.consumows.crud.consumowscrud.vo.EmpleadoRequestVo;
import ec.grace.consumows.crud.consumowscrud.vo.UsuarioSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.*;

@Service
public class EmpleadoService {
    @Lazy
    @Autowired
    @Qualifier("restTemplateCustomer")
    private RestTemplate restTemplate;

    private final RegistroService registroService;

    private final String URL_TOKEN="https://proyectoapi.bsite.net/api/Auth/login";


    public EmpleadoService(RegistroService registroService) {
        this.registroService = registroService;
    }

    private String obtenerToken(){
        Map<String,String> request = new HashMap<>();
        request.put("username","usuario");
        request.put("password","contrasenia");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.exchange(URL_TOKEN, HttpMethod.POST, entity, String.class);
        String tokenResponse = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, String> jsonMap = mapper.readValue(tokenResponse, Map.class);
            String token = jsonMap.get("token");
            System.out.println("Token obtenido: " + token);
            return token ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<Empleado> listarEmpleados(UsuarioSesion userSesion) {
        String respuesta= obtenerToken();
        String url ="https://proyectoapi.bsite.net/api/Empleado";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + respuesta);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        // Log de registro
        this.registroLog(userSesion.getCedula(), userSesion.getNombre(), "obtenerEmpleados");
        // Convertir el JSON recibido a Collection<Empleado>
        ObjectMapper objectMapper = new ObjectMapper();
        Collection<Empleado> empleados = Collections.emptyList();
        try {
            empleados = objectMapper.readValue(response.getBody(),
                    new TypeReference<Collection<Empleado>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();  // Manejar correctamente en producción
        }

        return empleados;
    }


    public boolean guardar(EmpleadoRequestVo empleado, UsuarioSesion userSesion) {
        String url ="https://proyectoapi.bsite.net/api/Empleado";
        String respuesta= obtenerToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + respuesta);
        HttpEntity<Object> entity = new HttpEntity<>(empleado, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        response.getBody();
        this.registroLog(userSesion.getCedula(), userSesion.getNombre(), "crear");
        return true;
    }

    public boolean eliminar(Integer id, UsuarioSesion userSesion) {
        String url ="https://proyectoapi.bsite.net/api/Empleado/"+id;
        String respuesta= obtenerToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + respuesta);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            this.registroLog(userSesion.getCedula(), userSesion.getNombre(), "eliminar_empleado");
            return true;
        } else {
            // Puedes registrar un error o hacer algo en caso de falla
            System.err.println("Error al eliminar empleado: " + response.getStatusCode());
            return false;
        }
    }

    public boolean actualizar(Integer id, Empleado empleado, UsuarioSesion userSesion) {
        String url ="https://proyectoapi.bsite.net/api/Empleado/"+id;
        String respuesta= obtenerToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + respuesta);
        HttpEntity<Object> entity = new HttpEntity<>(empleado, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                this.registroLog(userSesion.getCedula(), userSesion.getNombre(), "actualizar_empleado");
                return true;
            } else {
                System.err.println("Error al actualizar empleado: " + response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            System.err.println("Excepción al actualizar empleado: " + e.getMessage());
            return false;
        }
    }


    private void registroLog(String cedula, String nombre, String accion){
        Logs nuevoRegistro = new Logs();
        nuevoRegistro.setCedula(cedula);
        nuevoRegistro.setNombre(nombre);
        nuevoRegistro.setAccion(accion);
        nuevoRegistro.setFecha(new Date());
        nuevoRegistro.setEstado("A");
        registroService.saveLogs(nuevoRegistro);
    }


}
