package nick.mirosh.newsapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import nick.mirosh.newsapp.di.NetworkResponseBehavior
import nick.mirosh.newsapp.di.TestNetworkBehaviorModule
import nick.mirosh.newsapp.ui.feed.FeedScreen
import nick.mirosh.newsapp.ui.theme.NewsAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*
    We have to create a separate test class for substituting the network behavior , because
    @BindValue will work only per test class. There might be a solution for this, but I didn't
    think it was worth the effort right now.
 */

@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(TestNetworkBehaviorModule::class)
class FeedScreenNoNetworkTest {

    @get:Rule/*(order = 1)*/
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @BindValue
    @JvmField
    val testNetworkBehavior = NetworkResponseBehavior.NO_NETWORK

    @Before
    fun setupHilt() {
        hiltTestRule.inject()
    }

    @Test
    fun onboardingScreen_assertNoNetworkTextIsShown() {
        composeTestRule.apply {
            setContent {
                NewsAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        FeedScreen(
                            viewModel = hiltViewModel(),
                            onArticleClick = {},
                            onSavedArticlesClicked = {}
                        )
                    }
                }
            }
            waitForIdle()
            onNodeWithTag(testTag = "article_item").assertDoesNotExist()
            onNodeWithTag(testTag = "no_network_connection_image").assertIsDisplayed()
        }
    }
}
