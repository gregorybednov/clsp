package org.gregorybednov.clsp

import org.jetbrains.plugins.textmate.api.TextMateBundleProvider
import org.jetbrains.plugins.textmate.api.TextMateBundleProvider.PluginBundle
import kotlin.io.path.Path

class CTextMateBundleProvider : TextMateBundleProvider {
    override fun getBundles(): List<PluginBundle> {
        return listOf(
            PluginBundle("c/c++", Path("/Users/gregorybednov/Downloads/better-cpp-syntax"))
        )
    }
}