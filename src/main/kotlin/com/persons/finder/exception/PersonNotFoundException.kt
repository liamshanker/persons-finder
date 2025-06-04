package com.persons.finder.exception

import java.lang.RuntimeException

class PersonNotFoundException(id: Long): RuntimeException("Person with id:${id} not found")  {
}