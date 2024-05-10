package com.peppo.springbootapi.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peppo.springbootapi.entity.Address;
import com.peppo.springbootapi.entity.Contact;
import com.peppo.springbootapi.entity.User;
import com.peppo.springbootapi.model.AddressResponse;
import com.peppo.springbootapi.model.CreateAddressRequest;
import com.peppo.springbootapi.model.UpdateAddressRequest;
import com.peppo.springbootapi.model.WebResponse;
import com.peppo.springbootapi.repository.AddressRepository;
import com.peppo.springbootapi.repository.ContactRepository;
import com.peppo.springbootapi.repository.UserRepository;
import com.peppo.springbootapi.security.BCrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        addressRepository.deleteAll();
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 100000000000L);
        userRepository.save(user);

        Contact contact = new Contact();
        contact.setId("test");
        contact.setUser(user);
        contact.setFirstName("haris");
        contact.setLastName("kurniawan");
        contact.setEmail("haris@gmail.com");
        contact.setPhone("01823456789");
        contactRepository.save(contact);
    }

    @Test
    void testCreateAddressBadRequest() throws Exception {

        CreateAddressRequest request = new CreateAddressRequest();
        request.setCountry("");

        mockMvc.perform(
                post("/api/contacts/test/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    new TypeReference<>() {
                    });

            assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void testCreateAddressSuccess() throws Exception {

        CreateAddressRequest request = new CreateAddressRequest();
        request.setStreet("jalan");
        request.setCity("jakarta");
        request.setProvince("dki");
        request.setCountry("indonesia");
        request.setPostalCode("12345");

        mockMvc.perform(
                post("/api/contacts/test/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                    WebResponse<AddressResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertNull(response.getErrors());
                    assertEquals(request.getStreet(), response.getData().getStreet());
                    assertEquals(request.getCity(), response.getData().getCity());
                    assertEquals(request.getProvince(), response.getData().getProvince());
                    assertEquals(request.getCountry(), response.getData().getCountry());
                    assertEquals(request.getPostalCode(), response.getData().getPostalCode());

                    assertTrue(addressRepository.existsById(response.getData().getId()));
                }
        );
    }

    @Test
    void testGetAddressNotFound() throws Exception {

        mockMvc.perform(
                get("/api/contacts/test/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void testGetAddressSuccess() throws Exception {

        Contact contact = contactRepository.findById("test").orElseThrow();

        Address address = new Address();
        address.setId("test");
        address.setStreet("jalan");
        address.setCity("jakarta");
        address.setProvince("dki");
        address.setCountry("indonesia");
        address.setPostalCode("12345");
        address.setContact(contact);
        addressRepository.save(address);

        mockMvc.perform(
                get("/api/contacts/test/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                    WebResponse<AddressResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertNull(response.getErrors());

                    assertEquals(address.getId(), response.getData().getId());
                    assertEquals(address.getStreet(), response.getData().getStreet());
                    assertEquals(address.getCity(), response.getData().getCity());
                    assertEquals(address.getProvince(), response.getData().getProvince());
                    assertEquals(address.getCountry(), response.getData().getCountry());
                    assertEquals(address.getPostalCode(), response.getData().getPostalCode());
                    assertTrue(addressRepository.existsById(response.getData().getId()));
                }
        );
    }

    @Test
    void testUpdateAddressBadRequest() throws Exception {

        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setCountry("");

        mockMvc.perform(
                put("/api/contacts/test/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void testUpdateAddressSuccess() throws Exception {

        Contact contact = contactRepository.findById("test").orElseThrow();

        Address address = new Address();
        address.setId("test");
        address.setStreet("jalan");
        address.setCity("jakarta");
        address.setProvince("dki");
        address.setCountry("indonesia");
        address.setPostalCode("12345");
        address.setContact(contact);
        addressRepository.save(address);

        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setAddressId(address.getId());
        request.setContactId(address.getContact().getId());
        request.setStreet("kwitang");
        request.setCity("surabaya");
        request.setProvince("jawa timur");
        request.setCountry("singapura");
        request.setPostalCode("9875");

        mockMvc.perform(
                put("/api/contacts/test/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                    WebResponse<AddressResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertNull(response.getErrors());
                    assertEquals(request.getStreet(), response.getData().getStreet());
                    assertEquals(request.getCity(), response.getData().getCity());
                    assertEquals(request.getProvince(), response.getData().getProvince());
                    assertEquals(request.getCountry(), response.getData().getCountry());
                    assertEquals(request.getPostalCode(), response.getData().getPostalCode());

                    assertTrue(addressRepository.existsById(response.getData().getId()));
                }
        );
    }

    @Test
    void testDeleteAddressNotFound() throws Exception {

        mockMvc.perform(
                delete("/api/contacts/test/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void testDeleteAddressSuccess() throws Exception {

        Contact contact = contactRepository.findById("test").orElseThrow();

        Address address = new Address();
        address.setId("test");
        address.setStreet("jalan");
        address.setCity("jakarta");
        address.setProvince("dki");
        address.setCountry("indonesia");
        address.setPostalCode("12345");
        address.setContact(contact);
        addressRepository.save(address);

        mockMvc.perform(
                delete("/api/contacts/test/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertNull(response.getErrors());
                    assertEquals("berhasil menghapus alamat", response.getData());
                    assertFalse(addressRepository.existsById(address.getId()));
                }
        );
    }

    @Test
    void testListAddressNotFound() throws Exception {

        mockMvc.perform(
                get("/api/contacts/salah/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void testListAddressSuccess() throws Exception {

        Contact contact = contactRepository.findById("test").orElseThrow();

        for (int i = 0; i < 10; i++) {
            Address address = new Address();
            address.setId("test "+i);
            address.setStreet("jalan");
            address.setCity("jakarta");
            address.setProvince("dki");
            address.setCountry("indonesia");
            address.setPostalCode("12345");
            address.setContact(contact);
            addressRepository.save(address);
        }


        mockMvc.perform(
                get("/api/contacts/test/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                    WebResponse<List<AddressResponse>> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertNull(response.getErrors());
                    assertEquals(10, response.getData().size());
                }
        );
    }

}