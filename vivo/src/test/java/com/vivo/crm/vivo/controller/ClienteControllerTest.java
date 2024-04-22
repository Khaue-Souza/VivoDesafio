package com.vivo.crm.vivo.controller;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.vivo.crm.vivo.model.Cliente;
import com.vivo.crm.vivo.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(clienteController).build();
    }

    @Test
    public void testListarClientes() throws Exception {
        given(clienteRepository.findAll()).willReturn(List.of());

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());

        verify(clienteRepository).findAll();
    }

    @Test
    public void testAdicionarCliente() throws Exception {
        Cliente novoCliente = new Cliente();
        Cliente clienteSalvo = new Cliente();
        given(clienteRepository.save(any(Cliente.class))).willReturn(clienteSalvo);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"Khaue\",\n" +
                                "    \"email\": \"Khaue.souza@hotmail.com\",\n" +
                                "    \"cpf\": \"123456789\"\n" +
                                "}"))
                .andExpect(status().isOk());

        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    public void testAtualizarCliente() throws Exception {
        Long clienteId = 1L;
        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(clienteId);
        clienteExistente.setName("Nome existente");
        clienteExistente.setEmail("email@existente.com");
        clienteExistente.setCpf(Long.valueOf("12345678900"));

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setId(clienteId);
        clienteAtualizado.setName("Khaue");
        clienteAtualizado.setEmail("Khaue.souza@hotmail.com");
        clienteAtualizado.setCpf(Long.valueOf("123456789"));

        given(clienteRepository.findById(clienteId)).willReturn(Optional.of(clienteExistente));
        given(clienteRepository.save(any(Cliente.class))).willReturn(clienteAtualizado);

        mockMvc.perform(put("/clientes/{id}", clienteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"Khaue\",\n" +
                                "    \"email\": \"Khaue.souza@hotmail.com\",\n" +
                                "    \"cpf\": \"123456789\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Khaue"))
                .andExpect(jsonPath("$.email").value("Khaue.souza@hotmail.com"))
                .andExpect(jsonPath("$.cpf").value("123456789"));

        verify(clienteRepository).save(any(Cliente.class));
    }

}
