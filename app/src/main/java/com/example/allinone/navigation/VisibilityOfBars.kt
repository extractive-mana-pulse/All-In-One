import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavBackStackEntry
import com.example.allinone.navigation.screens.CodeLabScreens
import com.example.allinone.navigation.screens.HomeScreens
import com.example.allinone.navigation.screens.ProfileScreens
import com.example.allinone.navigation.screens.SettingsScreens

@Composable
internal fun VisibilityOfUI(
    gesturesEnabledState: MutableState<Boolean>,
    navBackStackEntry: NavBackStackEntry?,
    bottomBarState: MutableState<Boolean>,
    topBarState: MutableState<Boolean>,
) {
    val route = navBackStackEntry?.destination?.route

    when (route) {
        HomeScreens.Home::class.qualifiedName -> {
            bottomBarState.value = false
            topBarState.value = true
            gesturesEnabledState.value = true
        }
        in listOf(
            ProfileScreens.Profile::class.qualifiedName,
            SettingsScreens.Settings::class.qualifiedName,
            CodeLabScreens.ComposeArticleScreen::class.qualifiedName,
            CodeLabScreens.TipCalculator::class.qualifiedName,
            CodeLabScreens.Quadrant::class.qualifiedName,
            CodeLabScreens.ArtSpace::class.qualifiedName,
            CodeLabScreens.Lemonade::class.qualifiedName,
            CodeLabScreens.TaskManagerScreen::class.qualifiedName
        ) -> {
            bottomBarState.value = false
            topBarState.value = false
            gesturesEnabledState.value = false
        }
        else -> {
            bottomBarState.value = false
            topBarState.value = false
            gesturesEnabledState.value = false
        }
    }
}