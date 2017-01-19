package com.github.chameleon.eclipse.text.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored original the
 * preference store that belongs to the main plug-original class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class ChameleonPreferencePage
        extends FieldEditorPreferencePage
        implements IWorkbenchPreferencePage
{

    public ChameleonPreferencePage()
    {
        super(GRID);
    }

    /**
     * Creates the field editors. Field editors are abstractions of
     * the common GUI blocks needed to manipulate various types
     * of preferences. Each field editor knows how to save and
     * restore itself.
     */
    @Override
    public void createFieldEditors()
    {
//        final IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode("com.github.chameleon.eclipse.text");
//        final boolean english = preferences.getBoolean("&English", true);

//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&English", getFieldEditorParent()));
//
//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&C", getFieldEditorParent()));
//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&C++", getFieldEditorParent()));
//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&C#", getFieldEditorParent()));
//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&Dart (Google)", getFieldEditorParent()));
//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&Go (Google)", getFieldEditorParent()));
//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&Java", getFieldEditorParent()));
//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&Objective-C (Apple)", getFieldEditorParent()));
//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&Perl", getFieldEditorParent()));
//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&PHP", getFieldEditorParent()));
//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&Python", getFieldEditorParent()));
//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&R", getFieldEditorParent()));
//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&Ruby", getFieldEditorParent()));
//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&SQL", getFieldEditorParent()));
//        addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN, "&Swift (Apple)", getFieldEditorParent()));
        /*
         * addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH,
         * "&Directory preference:", getFieldEditorParent()));
         * 
         * addField(new RadioGroupFieldEditor(
         * PreferenceConstants.P_CHOICE,
         * "An example of a multiple-choice preference",
         * 1,
         * new String[][] {
         * {
         * "&Choice 1", "choice1"
         * }, {
         * "C&hoice 2", "choice2"
         * }
         * }, getFieldEditorParent()));
         * addField(new StringFieldEditor(PreferenceConstants.P_STRING,
         * "A &text preference:", getFieldEditorParent()));
         */
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    @Override
    public void init(final IWorkbench workbench)
    {
        //        setPreferenceStore(Activator.getDefault().getPreferenceStore());
        setDescription("Chameleon settings...");
    }

}
