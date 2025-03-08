package ec.grace.consumows.crud.consumowscrud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.grace.consumows.crud.consumowscrud.entity.Empresa;
import ec.grace.consumows.crud.consumowscrud.entity.Logs;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {
    @Lazy
    @Autowired
    @Qualifier("restTemplateCustomer")
    private RestTemplate restTemplate;

    private final RegistroService registroService;
    private final String URL_TOKEN = "https://proyectoapi.bsite.net/api/Auth/login";

    public EmpresaService(RegistroService registroService) {
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

    public Collection<Empresa> listarEmpresa() {
        String token = obtenerToken();
        String url = "https://proyectoapi.bsite.net/api/Empresa";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        // Registro de log
        registroLog("1724887125", "grace", "obtenerEmpresas");

        Collection<Empresa> empresas = Collections.emptyList();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            empresas = objectMapper.readValue(response.getBody(), new TypeReference<Collection<Empresa>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return empresas;
    }

    public boolean guardar(Empresa empresa) {
        String token = obtenerToken();
        String url = "https://proyectoapi.bsite.net/api/Empresa";
        // Asegurar que id_empresa es 0 en un nuevo registro
        empresa.setId_empresa(0);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Object> entity = new HttpEntity<>(empresa, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        registroLog("1724887125", "grace", "crear_empresa");
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean actualizar(Integer id, Empresa empresa) {
        // Asegúrate de que el id_empresa en el objeto empresa esté establecido
        empresa.setId_empresa(id);
        String token = obtenerToken();
        String url = "https://proyectoapi.bsite.net/api/Empresa/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Object> entity = new HttpEntity<>(empresa, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                registroLog("1724887125", "grace", "actualizar_empresa");
                return true;
            } else {
                System.err.println("Error al actualizar empresa: " + response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            System.err.println("Excepción al actualizar empresa: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(Long id) {

        String token = obtenerToken();
        String url = "https://proyectoapi.bsite.net/api/Empresa/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            registroLog("1724887125", "grace", "eliminar_empresa");
            return true;
        } else {
            System.err.println("Error al eliminar empresa: " + response.getStatusCode());
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
