package com.mypeople.Services.Impl;

import com.mypeople.Entities.Contact;
import com.mypeople.Exceptions.ResourceNotFoundException;
import com.mypeople.Repositories.ContactRepo;
import com.mypeople.Services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        return null;
    }

    @Override
    public List<Contact> findAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact findById(String id) {
        return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found with given: "+id+"!"));
    }

    @Override
    public void delete(String id) {
        // var contact = contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found with given: "+id+"!"));
        // contactRepo.delete(contact);
        // or
        contactRepo.deleteById(id);
    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) {
        return List.of();
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        // return contactRepo.findByUser(user);
        return contactRepo.findByUserId(userId);
    }
}
