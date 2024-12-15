package com.mypeople.Services;

import com.mypeople.Entities.Contact;

import java.util.List;

public interface ContactService {

    //save contact
    Contact save(Contact contact);

    //update contact
    Contact update(Contact contact);

    //get contacts
    List<Contact> findAll();

    //get contact by id
    Contact findById(String id);

    //delete contact
    void delete(String id);

    //search contact
    List<Contact> search(String name, String email, String phoneNumber);

    //get contacts by userId
    List<Contact> getByUserId(String userId);
}
