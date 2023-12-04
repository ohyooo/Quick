package com.ohyooo.rust

object librust {
    external fun hello(input: String): String

    init {
        System.loadLibrary("rust")
    }

}