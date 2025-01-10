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
import javax.swing.Icon
import org.jetbrains.annotations.NotNull
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.pathString

object CLanguage : Language("C") {
    private fun readResolve(): Any = CLanguage
    val INSTANCE: CLanguage = CLanguage
}

object CIcon {
    val FILE: Icon = getIcon("/icons/clogo.svg", CIcon::class.java)
}

class CFileType private constructor() : LanguageFileType(CLanguage.INSTANCE) {
    override fun getName(): String { return "C File" }
    override fun getDescription(): String { return "C language file" }
    override fun getDefaultExtension(): String { return "c" }
    override fun getIcon(): Icon { return CIcon.FILE }
    companion object { val INSTANCE: CFileType = CFileType() }
}

class CLanguageServer(project: Project) : OSProcessStreamConnectionProvider() {
    private fun findExecutableInPATH(executable: String) =
        EnvironmentUtil.getEnvironmentMap().values.flatMap { it.split(File.pathSeparator) }
            .map { File(Paths.get(it, executable).pathString) }.find { it.exists() && it.canExecute()  }?.path

    init {
        val clangdPath = findExecutableInPATH("clangd")
        if (clangdPath.isNullOrEmpty()) {
            NotificationGroupManager.getInstance().getNotificationGroup("C/C++ notifications").createNotification(
                "C LSP",
                "LSP server clangd not found. Make sure it is installed properly (and is available in PATH)," +
                        "and restart the IDE.",
                NotificationType.ERROR).notify(project)
            LanguageServerManager.getInstance(project).stop("CLanguageServer")
        } else {
            val commandLine = GeneralCommandLine("clangd")
            commandLine.setWorkDirectory(project.basePath)
            super.setCommandLine(commandLine)
        }
    }
}

class CLanguageServerFactory : LanguageServerFactory {
    @NotNull
    override fun createConnectionProvider(project: Project): StreamConnectionProvider {
        return CLanguageServer(project)
    }
}
