package com.printlab.android.constants

object AppConstants {

    object Params {

        const val TITLE: String = "title"
        const val FILE_PATH = "filepath"
        const val POSITION: String = "position"
    }


    object Key {

        const val PREF_FILE: String = "print_lab_prefs"

    }


    object Encryption {
        const val AES_ALGORITHM = "AES"
        const val AES_TRANSFORMATION = "AES/CTR/NoPadding"
        const val AES_PASSWORD = "printlab@1234567"
    }


    object UserDetails {

        const val USER_NAME = "username_print_lab"

        const val PASSWORD = "password_print_lab"

    }

    object FragBundleKeys {

        const val FRAG_PRODUCT_DETAILS = "product_details_frag"


    }


}