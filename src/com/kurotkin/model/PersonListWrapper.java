package com.kurotkin.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by  on 28.09.2017.
 */
@XmlRootElement(name = "persons")
public class PersonListWrapper {
    private List persons;

    @XmlElement(name = "person")
    public List getPersons() {
        return persons;
    }

    public void setPersons(List persons) {
        this.persons = persons;
    }
}
