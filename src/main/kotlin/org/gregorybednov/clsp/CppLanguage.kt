package org.gregorybednov.clsp

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.lang.Language
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader.getIcon
import com.intellij.util.EnvironmentUtil
import com.redhat.devtools.lsp4ij.LanguageServerFactory
import com.redhat.devtools.lsp4ij.LanguageServerManager
import com.redhat.devtools.lsp4ij.server.OSProcessStreamConnectionProvider
import com.redhat.devtools.lsp4ij.server.StreamConnectionProvider
import java.io.File
import java.nio.file.Paths
import javax.swing.Icon
import kotlin.io.path.pathString


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
    private fun findExecutableInPATH(executable: String) =
        EnvironmentUtil.getEnvironmentMap().values.flatMap { it.split(File.pathSeparator) }
            .map { File(Paths.get(it, executable).pathString) }.find { it.exists() && it.canExecute()  }?.path

    init {
        val clangdPath = findExecutableInPATH("clangd")
        if (clangdPath.isNullOrEmpty()) {
            NotificationGroupManager.getInstance().getNotificationGroup("C/C++ notifications").createNotification(
                "C++ LSP",
                "LSP server clangd not found. Make sure it is installed properly (and is available in PATH)," +
                        "and restart the IDE.",
                NotificationType.ERROR).notify(project)
            LanguageServerManager.getInstance(project).stop("CppLanguageServer")
        } else {
            val commandLine = GeneralCommandLine("clangd")
            commandLine.setWorkDirectory(project.basePath)
            super.setCommandLine(commandLine)
        }
    }
}

class CppLanguageServerFactory : LanguageServerFactory {
    override fun createConnectionProvider(project: Project): StreamConnectionProvider {
        return CppLanguageServer(project)
    }
}
