package com.batch.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Data
@XmlRootElement(name = "student")
public class StudentXml
{
	@XmlElement(name = "id")
	private Long id;

	@XmlElement(name = "firstName")
	private String firstName;

	@XmlElement(name = "lastName")
	private String lastName;

	@XmlElement(name = "email")
	private String email;
}
