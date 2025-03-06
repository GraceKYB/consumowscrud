package ec.grace.consumows.crud.consumowscrud.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpleadoRequestVo {


    public String nombre;

    public String apellido;

    public String correo;

    public String telefono;
    public Integer id_empresa;
    public Integer id_cargo;
    public byte[] imagen;
    public String pro_estado;

}


