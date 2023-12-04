use jni::JNIEnv;
use jni::objects::{JClass, JString};
use jni::sys::jstring;

#[no_mangle]
pub extern "system" fn Java_com_ohyooo_rust_librust_hello <'local>(mut env: JNIEnv<'local>, _class: JClass<'local>, input: JString<'local>)-> jstring {
    let input: String = env.get_string(&input).expect("Couldn't get java string!").into();

    let output = env.new_string(format!("hello, {}!", input))
        .expect("Couldn't create java string!");

    output.into_raw()
}

// package com.ohyooo.rust
// object librust {
//     private external fun hello(input: String): String
//
//     init {
//         System.loadLibrary("rust")
//     }
//
//     fun load() {
//         val output = hello("world")
//         println(output)
//     }
// }