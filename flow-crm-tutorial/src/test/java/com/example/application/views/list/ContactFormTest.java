package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Status;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContactFormTest {

    private static final String FIRST_NAME = "rafael";
    private static final String LAST_NAME = "grando";
    private static final String EMAIL = "rafael@grando.com";

    private List<Company> companies;
    private List<Status> statuses;
    private Contact grando;
    private Company company1;
    private Company company2;
    private Status status1;
    private Status status2;

    @BeforeEach
    public void setupData() {
        companies = new ArrayList<>();
        company1 = new Company();
        company1.setName("Vaadin Ltd");
        company2 = new Company();
        company2.setName("IT Mill");
        companies.add(company1);
        companies.add(company2);

        statuses = new ArrayList<>();
        status1 = new Status();
        status1.setName("Status 1");
        status2 = new Status();
        status2.setName("Status 2");
        statuses.add(status1);
        statuses.add(status2);

        grando = new Contact();
        grando.setFirstName("rafael");
        grando.setLastName("grando");
        grando.setEmail("rafael@grando.com");
        grando.setStatus(status1);
        grando.setCompany(company2);
    }

    @Test
    public void formFieldsPopulated() {
        var form = new ContactForm(companies, statuses);
        form.setContact(grando);
        assertEquals("rafael", FIRST_NAME);
        assertEquals("grando", LAST_NAME);
        assertEquals("rafael@grando.com", EMAIL);
        assertEquals(company2, form.company.getValue());
        assertEquals(status1, form.status.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        ContactForm form = new ContactForm(companies, statuses);
        Contact contact = new Contact();
        form.setContact(contact);
        form.firstName.setValue("rafael");
        form.lastName.setValue("grando");
        form.company.setValue(company1);
        form.email.setValue("rafael@grando.com");
        form.status.setValue(status2);

        AtomicReference<Contact> savedContactRef = new AtomicReference<>(null);
        form.addSaveListener(e -> {
            savedContactRef.set(e.getContact());
        });
        form.save.click();
        Contact savedContact = savedContactRef.get();

        assertEquals("rafael", savedContact.getFirstName());
        assertEquals("grando", savedContact.getLastName());
        assertEquals("rafael@grando.com", savedContact.getEmail());
        assertEquals(company1, savedContact.getCompany());
        assertEquals(status2, savedContact.getStatus());
    }
}