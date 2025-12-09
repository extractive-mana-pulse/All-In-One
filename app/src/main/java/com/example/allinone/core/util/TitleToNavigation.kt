import com.example.allinone.navigation.screen.Screens

internal fun String.navigateByTitle(
    onNavigate: (route: String, shouldStopTimer: Boolean) -> Unit,
    onError: () -> Unit
) {
    when (this) {
        "Compose article" -> onNavigate(Screens.ComposeArticleScreen.route, false)
        "Quadrant" -> onNavigate(Screens.Quadrant.route, false)
        "Tip Calculator" -> onNavigate(Screens.TipCalculator.route, false)
        "Art Space App" -> onNavigate(Screens.ArtSpace.route, true)
        "Lemonade" -> onNavigate(Screens.Lemonade.route, true)
        "Task Manager" -> onNavigate(Screens.TaskManagerScreen.route, true)
        "Art Space" -> onNavigate(Screens.ArtSpace.route, true)
        "Business card" -> onNavigate(Screens.BusinessCard.route, false)
        "Woof" -> onNavigate(Screens.Woof.route, false)
        else -> onError()
    }
}