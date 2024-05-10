package com.peppo.springbootapi.controller;

import com.peppo.springbootapi.entity.User;
import com.peppo.springbootapi.model.AddressResponse;
import com.peppo.springbootapi.model.CreateAddressRequest;
import com.peppo.springbootapi.model.UpdateAddressRequest;
import com.peppo.springbootapi.model.WebResponse;
import com.peppo.springbootapi.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping(
            path = "/api/contacts/{idContact}/addresses",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse> create(
            User user,
            @RequestBody CreateAddressRequest request,
            @PathVariable(name = "idContact") String idContact
    ) {
        request.setContactId(idContact);
        AddressResponse addressResponse = addressService.create(user, request);
        return WebResponse.<AddressResponse>builder()
                .data(addressResponse)
                .build();
    }

    @GetMapping(
            path = "/api/contacts/{idContact}/addresses/{idAddress}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse> get(
            User user,
            @PathVariable(name = "idContact") String contactId,
            @PathVariable(name = "idAddress") String addressId
    ) {
        AddressResponse addressResponse = addressService.get(user, contactId, addressId);
        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }

    @PutMapping(
            path = "/api/contacts/{idContact}/addresses/{addressId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse> update(
            User user,
            @RequestBody UpdateAddressRequest request,
            @PathVariable(name = "idContact") String idContact,
            @PathVariable(name = "addressId") String addressId
    ) {
        request.setContactId(idContact);
        request.setAddressId(addressId);

        AddressResponse addressResponse = addressService.update(user, request);
        return WebResponse.<AddressResponse>builder()
                .data(addressResponse)
                .build();
    }

    @DeleteMapping(
            path = "/api/contacts/{idContact}/addresses/{addressId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> remove(
            User user,
            @PathVariable(name = "idContact") String idContact,
            @PathVariable(name = "addressId") String addressId
    ) {

        addressService.remove(user, idContact, addressId);
        return WebResponse.<String>builder()
                .data("berhasil menghapus alamat")
                .build();
    }

    @GetMapping(
            path = "/api/contacts/{idContact}/addresses",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<AddressResponse>> list(
            User user,
            @PathVariable(name = "idContact") String contactId
    ) {
        List<AddressResponse> addressResponseList = addressService.list(user, contactId);
        return WebResponse.<List<AddressResponse>>builder().data(addressResponseList).build();
    }
}
