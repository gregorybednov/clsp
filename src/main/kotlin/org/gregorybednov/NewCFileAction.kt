package org.gregorybednov

import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import org.gregorybednov.clsp.CIcon


class NewCFileAction :
    CreateFileFromTemplateAction("C File", "Creates new C file", CIcon.FILE ) {
        override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
            builder
                .setTitle("New C File")
                .addKind("My file", CIcon.FILE, "DefaultTemplate.c")
        }

        override fun getActionName(directory: PsiDirectory?, newName: String, templateName: String?): String {
            return "C File"
        }

    override fun getDefaultTemplateProperty(): String {
        return "DefaultTemplate.c";
    }
}