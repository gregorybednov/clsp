package org.gregorybednov.clsp

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader.getIcon
import com.redhat.devtools.lsp4ij.LanguageServerFactory
import com.redhat.devtools.lsp4ij.server.OSProcessStreamConnectionProvider
import com.redhat.devtools.lsp4ij.server.StreamConnectionProvider
import javax.swing.Icon

object CppLanguage : Language("C++") {
    private fun readResolve(): Any = CppLanguage
    val INSTANCE: CppLanguage = CppLanguage
}

object CppIcon {
    val FILE: Icon = getIcon("/icons/cpplogo.svg", CppIcon::class.java)
}

class CppFileType private constructor() : LanguageFileType(CppLanguage.INSTANCE) {
    override fun getName(): String { return "C++ File" }
    override fun getDescription(): String { return "C++ language file" }
    override fun getDefaultExtension(): String { return "cpp" }
    override fun getIcon(): Icon { return CppIcon.FILE
    }
    companion object { val INSTANCE: CppFileType = CppFileType() }
}

class CppLanguageServer(project: Project) : OSProcessStreamConnectionProvider() {
    init {
        val commandLine = GeneralCommandLine("clangd")
        super.setCommandLine(commandLine)
    }
}

class CppLanguageServerFactory : LanguageServerFactory {
    override fun createConnectionProvider(project: Project): StreamConnectionProvider {
        return CppLanguageServer(project)
    }
}
