package com.example.allinone.plcoding.mini_challenges.domain.model

data class Challenge(
    val id: String,
    val title: String
)

fun getChallengesForMonth(month: String): List<Challenge> {
    return when (month.lowercase()) {
        "december" -> listOf(
            Challenge("holiday_gift_order", "Holiday Gift Order"),
            Challenge("santa_clap_piano", "Santa Clap Piano"),
            Challenge("snow_globe_shake", "Snow Globe Shake"),
            Challenge("holiday_cashback_activation", "Holiday Cashback Activation"),
            Challenge("winter_greeting_editor", "Winter Greeting Editor")
        )
        "november" -> listOf(
            Challenge("thanksgiving_countdown", "Thanksgiving Countdown"),
            Challenge("gratitude_journal", "Gratitude Journal"),
            Challenge("autumn_color_picker", "Autumn Color Picker"),
            Challenge("harvest_calculator", "Harvest Calculator"),
            Challenge("fall_recipe_randomizer", "Fall Recipe Randomizer")
        )
        "october" -> listOf(
            Challenge("halloween_costume_generator", "Halloween Costume Generator"),
            Challenge("pumpkin_carving_designer", "Pumpkin Carving Designer"),
            Challenge("spooky_sound_mixer", "Spooky Sound Mixer"),
            Challenge("candy_tracker", "Candy Tracker"),
            Challenge("haunted_house_planner", "Haunted House Planner")
        )
        "september" -> listOf(
            Challenge("back_to_school_organizer", "Back to School Organizer"),
            Challenge("study_schedule_builder", "Study Schedule Builder"),
            Challenge("homework_tracker", "Homework Timer"),
            Challenge("class_note_taker", "Class Note Taker"),
            Challenge("subject_grade_calculator", "Subject Grade Calculator")
        )
        "august" -> listOf(
            Challenge("summer_vacation_planner", "Summer Vacation Planner"),
            Challenge("beach_packing_checklist", "Beach Packing Checklist"),
            Challenge("travel_expense_tracker", "Travel Expense Tracker"),
            Challenge("photo_memory_organizer", "Photo Memory Organizer"),
            Challenge("weather_forecast_widget", "Weather Forecast Widget")
        )
        "july" -> listOf(
            Challenge("challenge_1", "Challenge 1"),
            Challenge("challenge_2", "Challenge 2"),
            Challenge("challenge_3", "Challenge 3"),
            Challenge("challenge_4", "Challenge 4"),
            Challenge("collapsible_chat_thread", "Collapsible Chat Thread")
        )
        else -> listOf(
            Challenge("challenge_1", "Challenge 1"),
            Challenge("challenge_2", "Challenge 2"),
            Challenge("challenge_3", "Challenge 3"),
            Challenge("challenge_4", "Challenge 4"),
            Challenge("challenge_5", "Challenge 5")
        )
    }
}