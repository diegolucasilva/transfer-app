package com.transferapp.customerservice.adapter.out.persistence

class CustomerNotFoundException(val id: String): RuntimeException("Could not find customer " + id)