package de.imedia24.shop.exception

import org.springframework.http.HttpStatus

class ProductNotFoundException(val status : HttpStatus, val reason : String): Exception()