package org.gregorybednov.clsp

import com.intellij.openapi.application.PluginPathManager
import org.jetbrains.plugins.textmate.api.TextMateBundleProvider
import org.jetbrains.plugins.textmate.api.TextMateBundleProvider.PluginBundle
import kotlin.io.path.Path
import kotlin.io.path.exists

class CTextMateBundleProvider : TextMateBundleProvider {
    override fun getBundles(): List<PluginBundle> {
        return listOf(
            PluginBundle("c/c++", Path("/Users/gregorybednov/Downloads/better-cpp-syntax"))
        )
        val textmateDir = PluginPathManager.getPluginResource(this::class.java, "textmate") ?: return emptyList()
        val path = textmateDir.toPath()
        return listOf(PluginBundle("better-cpp-syntax", path))
    }
}