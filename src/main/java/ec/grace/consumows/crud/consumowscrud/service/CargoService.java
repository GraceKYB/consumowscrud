package ec.grace.consumows.crud.consumowscrud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.grace.consumows.crud.consumowscrud.entity.Cargo;
import ec.grace.consumows.crud.consumowscrud.entity.Logs;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ec.grace.consumows.crud.consumowscrud.vo.UsuarioSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class CargoService {
    @Lazy
    @Autowired
    @Qualifier("restTemplateCustomer")
    private RestTemplate restTemplate;

    private final RegistroService registroService;
    private final String URL_TOKEN = "https://proyectoapi.bsite.net/api/Auth/login";

    public CargoService(RegistroService registroService) {
        this.registroService = registroService;
    }

    private String obtenerToken() {
        Map<String, String> request = new HashMap<>();
        request.put("username", "usuario");
        request.put("password", "contrasenia");
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
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<Cargo> listarCargos(UsuarioSesion userSesion) {
        String token = obtenerToken();
        String url = "https://proyectoapi.bsite.net/api/Cargo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        // Registro de log
        registroLog(userSesion.getCedula(), userSesion.getNombre(), "obtenerCargos");

        Collection<Cargo> cargos = Collections.emptyList();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            cargos = objectMapper.readValue(response.getBody(), new TypeReference<Collection<Cargo>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return cargos;
    }

    public boolean guardar(Cargo cargo) {
        String token = obtenerToken();
        String url = "https://proyectoapi.bsite.net/api/Cargo";
        // Asegurar que id_cargo es 0 en un nuevo registro
        cargo.setId_cargo(0);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Object> entity = new HttpEntity<>(cargo, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        registroLog("1724887125", "grace", "crear_cargo");
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean actualizar(Integer id, Cargo cargo) {
        // Asegúrate de que el id_cargo en el objeto cargo esté establecido
        cargo.setId_cargo(id);
        String token = obtenerToken();
        String url = "https://proyectoapi.bsite.net/api/Cargo/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Object> entity = new HttpEntity<>(cargo, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                registroLog("1724887125", "grace", "actualizar_cargo");
                return true;
            } else {
                System.err.println("Error al actualizar cargo: " + response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            System.err.println("Excepción al actualizar cargo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(Long id) {

        String token = obtenerToken();
        String url = "https://proyectoapi.bsite.net/api/Cargo/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            registroLog("1724887125", "grace", "eliminar_cargo");
            return true;
        } else {
            System.err.println("Error al eliminar cargo: " + response.getStatusCode());
            return false;
        }
    }

    private void registroLog(String cedula, String nombre, String accion) {
        Logs nuevoRegistro = new Logs();
        nuevoRegistro.setCedula(cedula);
        nuevoRegistro.setNombre(nombre);
        nuevoRegistro.setAccion(accion);
        nuevoRegistro.setFecha(new Date());
        nuevoRegistro.setEstado("A");
        registroService.saveLogs(nuevoRegistro);
    }
}
