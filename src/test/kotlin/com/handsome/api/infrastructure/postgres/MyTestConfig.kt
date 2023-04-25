package com.handsome.api.infrastructure.postgres

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.TestPropertySource

@TestConfiguration
@ComponentScan
@TestPropertySource("classpath:application.yml")
class MyTestConfig
