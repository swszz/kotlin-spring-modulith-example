package org.github.swszz

import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter

class ApplicationTests {

    private val modules = ApplicationModules.of(SpringBootModulithTemplateApplication::class.java)

    @Test
    fun verifyModularStructure() {
        modules.verify()
    }

    @Test
    fun writeDocumentationSnippets() {
        Documenter(modules).writeDocumentation()
    }
}