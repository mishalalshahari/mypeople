package com.mypeople.Repositories;

import com.mypeople.Entities.Contact;
import com.mypeople.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {

    //find the contact by user
    //custom finder method
    List<Contact> findByUser(User user);

    //custom query method
    @Query("select c from Contact c where c.user.userId = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);
}
