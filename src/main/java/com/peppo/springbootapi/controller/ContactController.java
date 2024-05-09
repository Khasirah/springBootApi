package com.peppo.springbootapi.controller;

import com.peppo.springbootapi.entity.User;
import com.peppo.springbootapi.model.ContactResponse;
import com.peppo.springbootapi.model.CreateContactRequest;
import com.peppo.springbootapi.model.WebResponse;
import com.peppo.springbootapi.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping(
            path = "/api/contacts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> create(User user,@RequestBody CreateContactRequest request) {
        ContactResponse contactResponse = contactService.create(user, request);
        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @GetMapping(
            path = "/api/contacts/{idContact}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> get(User user,@PathVariable("idContact") String idContact) {
        ContactResponse contactResponse = contactService.get(user, idContact);
        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

}
