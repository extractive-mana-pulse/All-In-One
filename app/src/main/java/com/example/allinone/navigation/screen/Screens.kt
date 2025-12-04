package com.example.allinone.navigation.screen

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthScreens(val route: String) {

    @Serializable
    object SignIn : AuthScreens("sign_in")

    @Serializable
    object SignUp : AuthScreens("sign_up")

    @Serializable
    object ForgotPassword : AuthScreens("forgot_password")

    @Serializable
    object ResetPassword : AuthScreens("reset_password")

    @Serializable
    object VerifyEmail : AuthScreens("verify_email")

    @Serializable
    object VerifyPhoneNumber : AuthScreens("verify_phone_number")

    @Serializable
    object VerifyOTP : AuthScreens("verify_otp")

    @Serializable
    object CreatePassword : AuthScreens("create_password")

}

@Serializable
sealed class Screens(val route: String) {

    @Serializable
    object ComposeArticleScreen : Screens("compose_article")

    @Serializable
    object TaskManagerScreen : Screens("task_manager")

    @Serializable
    object Quadrant : Screens("quadrant")

    @Serializable
    object TipCalculator : Screens("tip_calculator")

    @Serializable
    object Lemonade : Screens("lemonade")

    @Serializable
    object ArtSpace : Screens("art_space")

    @Serializable
    object BusinessCard : Screens("business_card")

    @Serializable
    object DiceRoller : Screens("dice_roller")

    @Serializable
    object Woof: Screens("woof")

    @Serializable
    object PLCoding: Screens("pl-coding") {

        @Serializable
        object MiniChallenges: Screens("mini_challenges")

        @Serializable
        object AppChallenges: Screens("app_challenges")
    }

}

@Serializable
sealed class HomeScreens(val route: String) {

    @Serializable
    object Home : HomeScreens("home_screen")

    @Serializable
    data class DetailsScreen(val id: Int) : HomeScreens("details_screen")

    @Serializable
    object Help : HomeScreens("help_and_feedback")

    @Serializable
    data class SectionScreen(val id: Int) : HomeScreens("section_screen")
}

@Serializable
sealed class SettingsScreens(val route: String) {

    @Serializable
    object Settings : SettingsScreens("settings")

    @Serializable
    object Night: SettingsScreens("night_mode")

    @Serializable
    object PowerSaving: SettingsScreens("power_saving")

    @Serializable
    object AdaptiveMode: SettingsScreens("adaptive_mode")

    @Serializable
    object ScheduledMode: SettingsScreens("scheduled_mode")

    @Serializable
    object Temperature: SettingsScreens("temperature")

    @Serializable
    object AllSensors: SettingsScreens("all_sensors")
}

@Serializable
sealed class ProfileScreens(val route: String) {

    @Serializable
    object Profile : ProfileScreens("profile")

    @Serializable
    object EditProfile : ProfileScreens("edit_profile")
}

@Serializable
sealed class BlogsScreens(val route: String) {

}

@Serializable
sealed class CodeLabsScreens(val route: String) {

}