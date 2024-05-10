package com.peppo.springbootapi.controller;

import com.peppo.springbootapi.entity.User;
import com.peppo.springbootapi.model.*;
import com.peppo.springbootapi.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping(
            path = "/api/contacts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> create(User user, @RequestBody CreateContactRequest request) {
        ContactResponse contactResponse = contactService.create(user, request);
        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @GetMapping(
            path = "/api/contacts/{idContact}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> get(User user, @PathVariable("idContact") String idContact) {
        ContactResponse contactResponse = contactService.get(user, idContact);
        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @PutMapping(
            path = "/api/contacts/{idContact}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> update(
            User user,
            @RequestBody UpdateContactRequest request,
            @PathVariable("idContact") String idContact
    ) {
        request.setId(idContact);
        ContactResponse contactResponse = contactService.update(user, request);
        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @DeleteMapping(
            path = "/api/contacts/{idContact}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("idContact") String idContact) {
        contactService.delete(user, idContact);
        return WebResponse.<String>builder().data("berhasil menghapus data").build();
    }

    @GetMapping(
            path = "/api/contacts",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ContactResponse>> search(
            User user,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

        SearchContactRequest request = SearchContactRequest.builder()
                .page(page)
                .email(email)
                .size(size)
                .name(name)
                .phone(phone)
                .build();

        Page<ContactResponse> contactResponsePage = contactService.search(user, request);

        return WebResponse
                .<List<ContactResponse>>builder()
                .data(contactResponsePage.getContent())
                .paging(PagingResponse
                        .builder()
                        .currentPage(contactResponsePage.getNumber())
                        .size(contactResponsePage.getSize())
                        .totalPage(contactResponsePage.getTotalPages())
                        .build())
                .build();
    }

}
