package com.huseyinkiran.favuniversities

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.EmptyFragmentActivity
import androidx.fragment.app.testing.FragmentScenario
//import androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * launchFragmentInContainer from the androidx.fragment:fragment-testing library
 * is NOT possible to use right now as it uses a hardcoded Activity under the hood
 * (i.e. [EmptyFragmentActivity]) which is not annotated with @AndroidEntryPoint.
 *
 * As a workaround, use this function that is equivalent. It requires you to add
 * [HiltTestActivity] in the debug folder and include it in the debug AndroidManifest.xml file
 * as can be found in this project.
 */

inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    @StyleRes themeResId: Int = androidx.fragment.testing.manifest.R.style.FragmentScenarioEmptyFragmentActivityTheme,
    crossinline action: T.() -> Unit = {}
) {
    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    ).putExtra(EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY, themeResId)

    ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            T::class.java.classLoader!!,
            T::class.java.name
        )
        fragment.arguments = fragmentArgs
        activity.supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment, null)
            .commitNow()

        (fragment as T).action()
    }
}
