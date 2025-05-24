package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.UsuarioDto;
import com.oprosita.backend.model.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDto toUsuarioDto(Usuario usuario);
    Usuario toUsuarioEntity(UsuarioDto usuarioDto);
    List<UsuarioDto> toUsuarioDtoList(List<Usuario> usuarios);
    List<Usuario> toUsuarioEntityList(List<UsuarioDto> usuariosDto);
}
