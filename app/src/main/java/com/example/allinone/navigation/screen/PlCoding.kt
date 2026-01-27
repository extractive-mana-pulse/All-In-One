package com.example.allinone.navigation.screen

import kotlinx.serialization.Serializable

@Serializable
sealed interface PlCoding {

    @Serializable
    data object Home: PlCoding {
        @Serializable
        object MiniChallenges : PlCoding {

            @Serializable
            object December : PlCoding {

                @Serializable
                object WinterGreetingEditor : PlCoding

            }
            @Serializable
            object July : PlCoding {

                @Serializable
                object CollapsibleChatThread : PlCoding
            }
        }
        @Serializable
        object AppChallenges : PlCoding
    }
}