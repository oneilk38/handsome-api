package com.handsome.api.http.response

import java.lang.RuntimeException

class CompanyAlreadyExistsException(message: String?) : RuntimeException(message)
class NotFoundException(message: String?) : RuntimeException(message)
